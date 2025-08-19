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

    private LinearLayout itemsContainer;
    private Button btnTShirts, btnShirts;

    private TShirtsDatabaseHelper dbHelper;
    private OrdersDatabaseHelper ordersDbHelper;

    private String currentCategory = "T-Shirt";

    // 🔹 Define your two colors (you can also move them to colors.xml if you like)
    private final String COLOR_YELLOW = "#F5AF56"; // Yellow
    private final String COLOR_BLUE = "#FF0D2D4D";   // Blue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt);

        itemsContainer = findViewById(R.id.itemsContainer);
        btnTShirts = findViewById(R.id.btnTShirts);
        btnShirts = findViewById(R.id.btnShirts);

        dbHelper = new TShirtsDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        // 🔹 Set default state (both yellow bg, blue text)
        resetButton(btnTShirts);
        resetButton(btnShirts);

        // 🔹 Highlight T-Shirts by default
        highlightButton(btnTShirts);

        btnTShirts.setOnClickListener(v -> {
            currentCategory = "T-Shirt";
            highlightButton(btnTShirts);
            resetButton(btnShirts);
            loadItems(currentCategory);
        });

        btnShirts.setOnClickListener(v -> {
            currentCategory = "Shirt";
            highlightButton(btnShirts);
            resetButton(btnTShirts);
            loadItems(currentCategory);
        });

        loadItems(currentCategory);
    }

    private void loadItems(String category) {
        itemsContainer.removeAllViews();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM " + TShirtsDatabaseHelper.TABLE_NAME +
                            " WHERE " + TShirtsDatabaseHelper.COL_CATEGORY + " = ?",
                    new String[]{category});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_IMAGE));
                    String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_MEASUREMENTS));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_PRICE));

                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                    View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                    ImageView image = itemBox.findViewById(R.id.imageView);
                    TextView title = itemBox.findViewById(R.id.textViewTitle);
                    TextView desc = itemBox.findViewById(R.id.textViewDescription);
                    Button addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description + "\n\nMeasurements: " +
                            (measurements != null ? measurements : "N/A") +
                            "\nPrice: " + (price != null ? price : "N/A"));

                    addToCartBtn.setOnClickListener(v -> {
                        long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                        if (id != -1) {
                            Toast.makeText(this,
                                    name + " added to cart\nMeasurements: " +
                                            (measurements != null ? measurements : "N/A") +
                                            "\nPrice: " + (price != null ? price : "N/A"),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                        }
                    });

                    itemsContainer.addView(itemBox);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading items: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Highlight selected button (Blue bg, Yellow text)
    private void highlightButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(COLOR_BLUE)));
        button.setTextColor(Color.parseColor(COLOR_YELLOW));
    }

    // Reset unselected button (Yellow bg, Blue text)
    private void resetButton(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(COLOR_YELLOW)));
        button.setTextColor(Color.parseColor(COLOR_BLUE));
    }
}
