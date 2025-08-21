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

public class BlazerActivity extends AppCompatActivity {

    private LinearLayout itemsContainer;          // Parent layout that will hold all blazer item boxes
    private BlazerDatabaseHelper dbHelper;        // Database helper for blazer items
    private OrdersDatabaseHelper ordersDbHelper;  // Database helper for user orders (cart)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blazer);

        // Find views
        itemsContainer = findViewById(R.id.itemsContainer);

        // Initialize DB helpers
        dbHelper = new BlazerDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // Load blazer items from database
        loadBlazers();
    }

    private void loadBlazers() {
        // Clear container before adding new items
        itemsContainer.removeAllViews();

        // Open readable DB connection
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BlazerDatabaseHelper.TABLE_NAME, null);

        // Check if there are records
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get blazer details from DB
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper.COL_PRICE)); // New

                // Get drawable resource ID by name
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // fallback image

                // Inflate custom item layout (readymade_box.xml)
                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                // Find UI components inside itemBox
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Bind data to UI
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description + "\n\nMeasurements: " +
                        (measurements != null ? measurements : "N/A") +
                        "\nPrice: " + (price != null ? price : "N/A")); // Display measurements & price

                // Handle "Add to Cart" button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this,
                                name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the itemBox to parent container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close();
        }

        // Close DB
        db.close();
    }
}
