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

public class TrousersActivity_si extends AppCompatActivity {

    private LinearLayout trousersContainer; // Container layout to hold all trousers items
    private TrousersDatabaseHelper_si trousersDbHelper; // DB helper for Sinhala trousers
    private OrdersDatabaseHelper_si ordersDbHelper; // DB helper to manage cart/orders in Sinhala DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method
        setContentView(R.layout.si_activity_trousers); // Set Sinhala layout for trousers screen

        // Bind views and initialize database helpers
        trousersContainer = findViewById(R.id.trousersContainer);
        trousersDbHelper = new TrousersDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        loadTrousers(); // Load all trousers from Sinhala DB
    }

    private void loadTrousers() {
        SQLiteDatabase db = trousersDbHelper.getReadableDatabase(); // Open DB for reading
        Cursor cursor = db.rawQuery("SELECT * FROM " + TrousersDatabaseHelper_si.TABLE_NAME, null); // Query all rows

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get data from current row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_PRICE));

                // Resolve image resource ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Fallback image

                // Inflate layout for each trouser item
                View trouserBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, trousersContainer, false);

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
                                "මිනුම්: " + (measurements != null ? measurements : "නැත") + "\n" +
                                "මිල: " + (price != null ? price : "නැත") // Show measurements and price in Sinhala
                );

                // Handle add-to-cart button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price); // Add to orders DB
                    if (id != -1) {
                        Toast.makeText(this, name + " කරත්තයට එකතු කරන ලදී.\nමිනුම්: " + measurements + "\nමිල: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, name + " කරත්තයට එකතු කිරීමට අසමත් විය!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the item view to the container
                trousersContainer.addView(trouserBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor
        } else {
            Toast.makeText(this, "දත්ත සොයාගත නොහැක!", Toast.LENGTH_SHORT).show(); // No data found
        }

        db.close(); // Close database
    }
}
