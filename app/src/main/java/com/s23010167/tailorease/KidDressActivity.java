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

public class KidDressActivity extends AppCompatActivity {

    // Container where all kid dress items will be dynamically added
    private LinearLayout itemsContainer;

    // Database helpers
    private KidDressDatabaseHelper dbHelper;      // For fetching kid dresses
    private OrdersDatabaseHelper ordersDbHelper;  // For saving selected items as orders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiddress);

        // Link the container from layout
        itemsContainer = findViewById(R.id.itemsContainer);

        // Initialize database helpers
        dbHelper = new KidDressDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // Load all kid dresses from database and display
        loadKidDresses();
    }

    private void loadKidDresses() {
        // Clear previous views before loading new ones
        itemsContainer.removeAllViews();

        // Get readable database instance
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query all kid dresses
        Cursor cursor = db.rawQuery("SELECT * FROM " + KidDressDatabaseHelper.TABLE_NAME, null);

        // If data exists, loop through all rows
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fetch values from columns
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_PRICE)); // New

                // Convert image resource name (string) to actual drawable resource ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Default image if not found

                // Inflate custom item layout (readymade_box.xml)
                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                // Bind UI elements inside itemBox
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Set values into UI
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "Measurements: " + (measurements != null ? measurements : "N/A") + "\n" +
                                "Price: " + (price != null ? price : "N/A")
                );

                // Handle Add to Cart button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        // Successfully added to cart
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        // Failed to add
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add this item view to container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext()); // Move to next row

            cursor.close(); // Close cursor after use
        }

        db.close(); // Close database connection
    }
}
