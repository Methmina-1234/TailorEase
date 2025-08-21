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

public class LadyDressActivity_si extends AppCompatActivity {

    private LinearLayout itemsContainer; // Layout container for displaying dress items
    private LadyDressDatabaseHelper_si dbHelper; // Sinhala dress database helper
    private OrdersDatabaseHelper_si ordersDbHelper; // Sinhala orders database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_ladydress); // Sinhala layout

        // Initialize UI components and database helpers
        itemsContainer = findViewById(R.id.itemsContainer);
        dbHelper = new LadyDressDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        // Load and display dresses from database
        loadLadyDresses();
    }

    private void loadLadyDresses() {
        itemsContainer.removeAllViews(); // Clear old views before loading

        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LadyDressDatabaseHelper_si.TABLE_NAME, null);

        // If data exists, loop through each record
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from current row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper_si.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper_si.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(LadyDressDatabaseHelper_si.COL_PRICE)); // new

                // Get image resource ID (fallback to default if not found)
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                // Inflate custom item box layout for each dress
                View itemBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, itemsContainer, false);

                // Find views inside the item box
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Set values
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "මිනුම්: " + (measurements != null ? measurements : "නැත") + "\n" +
                                "මිල: " + (price != null ? price : "නැත")
                );

                // Handle "Add to Cart" button click
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this, name + " කරත්තයට එකතු කරන ලදී.\nමිනුම්: " + measurements + "\nමිල: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, name + " කරත්තයට එකතු කිරීමට අසමත් විය!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add item box to container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor
        }
        db.close(); // Close database
    }
}
