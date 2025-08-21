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

public class LadyDressActivity extends AppCompatActivity {

    // Container where all dress items will be dynamically added
    private LinearLayout itemsContainer;

    // Database helper to fetch lady dress data
    private LadyDressDatabaseHelper dbHelper;

    // Database helper to store orders (cart functionality)
    private OrdersDatabaseHelper ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladydress); // Load the UI layout

        // Initialize views and helpers
        itemsContainer = findViewById(R.id.itemsContainer);
        dbHelper = new LadyDressDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // Load dress items from the database
        loadLadyDresses();
    }

    private void loadLadyDresses() {
        // Remove old views before loading fresh data
        itemsContainer.removeAllViews();

        // Open database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Fetch all rows from lady dress table
        Cursor cursor = db.rawQuery("SELECT * FROM " + LadyDressDatabaseHelper.TABLE_NAME, null);

        // If data exists, loop through each record
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract values from the current row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper.COL_PRICE)); //new column

                // Convert image resource name to actual drawable ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // fallback image if not found

                // Inflate a custom UI box for each dress item
                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                // Find views inside the inflated layout
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Set values to UI
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description +
                        "\n\nMeasurements: " + (measurements != null ? measurements : "N/A") +
                        "\nPrice: " + (price != null ? price : "N/A")); // show measurements and price

                // Handle Add-to-Cart button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);

                    if (id != -1) {
                        // Successfully added → show confirmation with measurements & price
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        // Failed → show error
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                // Finally, add the itemBox into the main container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext()); // Move to next record

            // Close cursor after use
            cursor.close();
        }

        // Close DB connection
        db.close();
    }
}
