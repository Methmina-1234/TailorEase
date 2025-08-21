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

public class BlazerActivity_si extends AppCompatActivity {

    // Container layout to hold dynamically created blazer item views
    private LinearLayout itemsContainer;

    // Database helper for fetching blazer data
    private BlazerDatabaseHelper_si dbHelper;

    // Database helper for saving orders (shopping cart)
    private OrdersDatabaseHelper_si ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_blazer);

        // Initialize layout and database helpers
        itemsContainer = findViewById(R.id.itemsContainer);
        dbHelper = new BlazerDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        // Load blazer items from database and display
        loadBlazers();
    }

    private void loadBlazers() {
        // Remove any previously added views
        itemsContainer.removeAllViews();

        // Open database in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query all blazer records from the blazer table
        Cursor cursor = db.rawQuery("SELECT * FROM " + BlazerDatabaseHelper_si.TABLE_NAME, null);

        // Check if there is at least one record
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract blazer details from the database
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper_si.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper_si.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(BlazerDatabaseHelper_si.COL_PRICE));

                // Get the image resource ID by name (fallback to default if not found)
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                // Inflate custom layout for each blazer item
                View itemBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, itemsContainer, false);

                // Find views inside the item box
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Set blazer details into the views
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "මිනුම්: " + (measurements != null ? measurements : "නැත") + "\n" +
                                "මිල: " + (price != null ? price : "නැත")
                );

                // Add click listener for "Add to Cart" button
                addToCartBtn.setOnClickListener(v -> {
                    // Save item into orders database
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);

                    if (id != -1) {
                        // Success: Show a confirmation toast with measurements + price
                        Toast.makeText(this,
                                name + " කරත්තයට එකතු කරන ලදී.\nමිනුම්: " + measurements + "\nමිල: " + price,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Failure: Show error message
                        Toast.makeText(this,
                                name + " කරත්තයට එකතු කිරීමට අසමත් විය!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                // Finally, add the itemBox view to the container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close();
        }

        // Close the database connection
        db.close();
    }
}