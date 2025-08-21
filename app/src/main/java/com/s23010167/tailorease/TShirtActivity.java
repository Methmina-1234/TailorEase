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

public class TShirtActivity extends AppCompatActivity {

    private LinearLayout itemsContainer; // Container to hold T-Shirt/Shirt items
    private Button btnTShirts, btnShirts; // Buttons to switch between categories

    private TShirtsDatabaseHelper dbHelper; // Database helper for T-Shirts
    private OrdersDatabaseHelper ordersDbHelper; // Database helper for Orders

    private String currentCategory = "T-Shirt"; // Default category to display

    // Define your two colors for button highlight and reset
    private final String COLOR_YELLOW = "#F5AF56"; // Yellow background
    private final String COLOR_BLUE = "#FF0D2D4D"; // Blue background

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt); // Set layout

        // Bind views
        itemsContainer = findViewById(R.id.itemsContainer);
        btnTShirts = findViewById(R.id.btnTShirts);
        btnShirts = findViewById(R.id.btnShirts);

        // Initialize database helpers
        dbHelper = new TShirtsDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // Set default button states (yellow bg, blue text)
        resetButton(btnTShirts);
        resetButton(btnShirts);

        // Highlight T-Shirts button by default (blue bg, yellow text)
        highlightButton(btnTShirts);

        // Set click listener for T-Shirts button
        btnTShirts.setOnClickListener(v -> {
            currentCategory = "T-Shirt"; // Set current category
            highlightButton(btnTShirts); // Highlight selected button
            resetButton(btnShirts); // Reset the other button
            loadItems(currentCategory); // Load items of this category
        });

        // Set click listener for Shirts button
        btnShirts.setOnClickListener(v -> {
            currentCategory = "Shirt"; // Set current category
            highlightButton(btnShirts); // Highlight selected button
            resetButton(btnTShirts); // Reset the other button
            loadItems(currentCategory); // Load items of this category
        });

        // Load default category items
        loadItems(currentCategory);
    }

    // Function to load items from database and display in container
    private void loadItems(String category) {
        itemsContainer.removeAllViews(); // Clear previous items

        SQLiteDatabase db = null; // Database instance
        Cursor cursor = null; // Cursor to read data
        try {
            db = dbHelper.getReadableDatabase(); // Open database
            cursor = db.rawQuery(
                    "SELECT * FROM " + TShirtsDatabaseHelper.TABLE_NAME +
                            " WHERE " + TShirtsDatabaseHelper.COL_CATEGORY + " = ?",
                    new String[]{category}); // Query items by category

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Read item details from database
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_IMAGE));
                    String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_MEASUREMENTS));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_PRICE));

                    // Get drawable resource ID by name
                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Fallback image

                    // Inflate item layout
                    View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                    // Bind views inside item layout
                    ImageView image = itemBox.findViewById(R.id.imageView);
                    TextView title = itemBox.findViewById(R.id.textViewTitle);
                    TextView desc = itemBox.findViewById(R.id.textViewDescription);
                    Button addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                    // Set item details
                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description + "\n\nMeasurements: " +
                            (measurements != null ? measurements : "N/A") +
                            "\nPrice: " + (price != null ? price : "N/A"));

                    // Add click listener to add item to cart
                    addToCartBtn.setOnClickListener(v -> {
                        long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price); // Insert order
                        if (id != -1) {
                            // Show success toast
                            Toast.makeText(this,
                                    name + " added to cart\nMeasurements: " +
                                            (measurements != null ? measurements : "N/A") +
                                            "\nPrice: " + (price != null ? price : "N/A"),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Show failure toast
                            Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add item layout to container
                    itemsContainer.addView(itemBox);

                } while (cursor.moveToNext()); // Loop through all items
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading items: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
