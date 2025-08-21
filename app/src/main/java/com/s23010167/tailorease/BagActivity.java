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

public class BagActivity extends AppCompatActivity {

    // UI container that will hold dynamically created bag items
    private LinearLayout itemsContainer;

    // Database helpers for Bags and Orders
    private BagDatabaseHelper dbHelper;
    private OrdersDatabaseHelper ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag); // Layout file for BagActivity

        // Initialize UI references
        itemsContainer = findViewById(R.id.itemsContainer);

        // Initialize database helpers
        dbHelper = new BagDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // Load all bags from DB into the screen
        loadBags();
    }

    private void loadBags() {
        // Clear existing views before adding new ones
        itemsContainer.removeAllViews();

        // Open database in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query all bags from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + BagDatabaseHelper.TABLE_NAME, null);

        // If data exists in the table
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract bag data from the cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_MEASUREMENTS));
                String capacity = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_CAPACITY));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_PRICE)); // New

                // Get image resource ID from name (fallback to default icon if not found)
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                // Inflate a reusable "readymade_box" layout for each bag
                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                // Find UI elements inside the item box
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Set bag data to UI
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description +
                                "\n\nMeasurements: " + (measurements != null ? measurements : "N/A") +
                                "\nCapacity: " + (capacity != null ? capacity : "N/A") +
                                "\nPrice: " + (price != null ? price : "N/A")
                );

                // Handle "Add to Cart" button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);

                    // Show feedback to the user
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nPrice: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                // Finally, add the item box to the main container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor to prevent memory leaks
        }
        db.close();
    }
}
