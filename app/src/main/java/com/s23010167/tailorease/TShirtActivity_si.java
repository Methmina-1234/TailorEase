package com.s23010167.tailorease;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TShirtActivity_si extends AppCompatActivity {

    private LinearLayout itemsContainer;          // Container to hold T-Shirt/Shirt items
    private Button btnTShirts, btnShirts;        // Buttons to switch categories

    private TShirtsDatabaseHelper_si dbHelper;   // Sinhala DB helper
    private OrdersDatabaseHelper_si ordersDbHelper; // Sinhala Orders DB helper

    private String currentCategory = "T-Shirt";  // Default category

    // Define custom colors
    private final String COLOR_YELLOW = "#F5AF56"; // Yellow
    private final String COLOR_BLUE = "#0D2D4D";   // Blue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_tshirt); // Load Sinhala layout

        // Bind views
        itemsContainer = findViewById(R.id.itemsContainer);
        btnTShirts = findViewById(R.id.btnTShirts);
        btnShirts = findViewById(R.id.btnShirts);

        // Initialize database helpers
        dbHelper = new TShirtsDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        // Default button state (both yellow bg, blue text)
        resetButton(btnTShirts);
        resetButton(btnShirts);

        // Highlight T-Shirts button by default
        highlightButton(btnTShirts);

        // Set click listener for T-Shirts button
        btnTShirts.setOnClickListener(v -> {
            currentCategory = "T-Shirt";         // Set category
            highlightButton(btnTShirts);         // Highlight selected button
            resetButton(btnShirts);              // Reset other button
            loadItems(currentCategory);          // Load T-Shirt items
        });

        // Set click listener for Shirts button
        btnShirts.setOnClickListener(v -> {
            currentCategory = "Shirt";           // Set category
            highlightButton(btnShirts);          // Highlight selected button
            resetButton(btnTShirts);             // Reset other button
            loadItems(currentCategory);          // Load Shirt items
        });

        // Load default category items
        loadItems(currentCategory);
    }

    // Function to load items from database and display in container
    private void loadItems(String category) {
        itemsContainer.removeAllViews(); // Clear previous items

        SQLiteDatabase db = null; // Database instance
        Cursor cursor = null;     // Cursor to fetch data
        try {
            db = dbHelper.getReadableDatabase(); // Open database
            cursor = db.rawQuery(
                    "SELECT * FROM " + TShirtsDatabaseHelper_si.TABLE_NAME +
                            " WHERE " + TShirtsDatabaseHelper_si.COL_CATEGORY + " = ?",
                    new String[]{category});       // Query items by category

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Get item data
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_IMAGE));
                    String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_MEASUREMENTS));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_PRICE));

                    // Get image resource ID, fallback if not found
                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                    // Inflate item layout
                    View itemBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, itemsContainer, false);

                    // Bind item views
                    ImageView image = itemBox.findViewById(R.id.imageView);
                    TextView title = itemBox.findViewById(R.id.textViewTitle);
                    TextView desc = itemBox.findViewById(R.id.textViewDescription);
                    Button addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                    // Set item details
                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description + "\n\nමිනුම්: " +
                            (measurements != null ? measurements : "නැත") +
                            "\nමිල: " + (price != null ? price : "නැත"));

                    // Set click listener to add item to cart
                    addToCartBtn.setOnClickListener(v -> {
                        long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price); // Insert order
                        if (id != -1) {
                            // Success toast
                            Toast.makeText(this,
                                    name + " කරත්තයට එකතු කරන ලදී\nමිනුම්: " +
                                            (measurements != null ? measurements : "නැත") +
                                            "\nමිල: " + (price != null ? price : "නැත"),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Failure toast
                            Toast.makeText(this,
                                    name + " කරත්තයට එකතු කිරීමට අසමත් විය",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add item layout to container
                    itemsContainer.addView(itemBox);

                } while (cursor.moveToNext()); // Repeat for all items
            }
        } catch (Exception e) {
            Toast.makeText(this, "අයිතම පූරණය කිරීමේ දෝෂයකි: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            // Close cursor and database
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Highlight selected button (Blue bg, Yellow text)
    private void highlightButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(COLOR_BLUE))); // Blue background
        button.setTextColor(Color.parseColor(COLOR_YELLOW)); // Yellow text
    }

    // Reset unselected button (Yellow bg, Blue text)
    private void resetButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(COLOR_YELLOW))); // Yellow background
        button.setTextColor(Color.parseColor(COLOR_BLUE)); // Blue text
    }
}
