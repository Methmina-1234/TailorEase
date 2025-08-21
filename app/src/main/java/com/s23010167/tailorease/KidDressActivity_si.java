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

public class KidDressActivity_si extends AppCompatActivity {

    // UI container where items will be dynamically added
    private LinearLayout itemsContainer;

    // Database helpers (Sinhala versions)
    private KidDressDatabaseHelper_si dbHelper;
    private OrdersDatabaseHelper_si ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_kiddress);

        // Find layout container
        itemsContainer = findViewById(R.id.itemsContainer);

        // Initialize database helpers
        dbHelper = new KidDressDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        // Load all kids' dresses from DB
        loadKidDresses();
    }

    private void loadKidDresses() {
        // Clear the container before loading
        itemsContainer.removeAllViews();

        // Open DB for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get all records from Sinhala KidDress DB
        Cursor cursor = db.rawQuery("SELECT * FROM " + KidDressDatabaseHelper_si.TABLE_NAME, null);

        // If data is found
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from DB
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper_si.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper_si.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper_si.COL_PRICE)); // New

                // Get drawable resource ID by name
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // fallback image

                // Inflate custom box layout (Sinhala version)
                View itemBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, itemsContainer, false);

                // UI references inside box
                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                // Fill data
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "මිනුම්: " + (measurements != null ? measurements : "නැත") + "\n" +
                                "මිල: " + (price != null ? price : "නැත")
                );

                // Add to Cart button functionality
                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this, name + " කරත්තයට එකතු කරන ලදී.\nමිනුම්: " + measurements + "\nමිල: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, name + " කරත්තයට එකතු කිරීමට අසමත් විය!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Finally add this box to container
                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close(); // close cursor
        }
        db.close(); // close database
    }
}
