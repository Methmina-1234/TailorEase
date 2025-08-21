package com.s23010167.tailorease;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TrousersActivity extends AppCompatActivity {

    private LinearLayout trousersContainer; // Container layout to hold all trousers items
    private TrousersDatabaseHelper trousersDbHelper; // DB helper for readymade trousers
    private OrdersDatabaseHelper ordersDbHelper; // DB helper to manage cart/orders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method
        setContentView(R.layout.activity_trousers); // Set layout for trousers screen

        // Bind views and initialize database helpers
        trousersContainer = findViewById(R.id.trousersContainer);
        trousersDbHelper = new TrousersDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        loadTrousers(); // Load all trousers from database
    }

    private void loadTrousers() {
        SQLiteDatabase db = trousersDbHelper.getReadableDatabase(); // Open DB for reading
        Cursor cursor = db.rawQuery("SELECT * FROM " + TrousersDatabaseHelper.TABLE_NAME, null); // Query all rows

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get data from current row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_PRICE)); // Get price

                // Resolve image resource ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Fallback image

                // Inflate layout for each trouser item
                View trouserBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, trousersContainer, false);

                // Bind views inside the item
                ImageView image = trouserBox.findViewById(R.id.imageView);
                TextView title = trouserBox.findViewById(R.id.textViewTitle);
                TextView desc = trouserBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = trouserBox.findViewById(R.id.buttonAddToCart);

                // Set data to views
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "Measurements: " + (measurements != null ? measurements : "N/A") + "\n" +
                                "Price: " + (price != null ? price : "N/A") // Show price
                );

                // Handle add-to-cart button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price); // Add to orders DB
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the item view to the container
                trousersContainer.addView(trouserBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor
        }

        db.close(); // Close database
    }
}
