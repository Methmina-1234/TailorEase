package com.s23010167.tailorease;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt);

        itemsContainer = findViewById(R.id.itemsContainer);
        btnTShirts = findViewById(R.id.btnTShirts);
        btnShirts = findViewById(R.id.btnShirts);

        dbHelper = new TShirtsDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        btnTShirts.setOnClickListener(v -> {
            currentCategory = "T-Shirt";
            loadItems(currentCategory);
        });

        btnShirts.setOnClickListener(v -> {
            currentCategory = "Shirt";
            loadItems(currentCategory);
        });

        loadItems(currentCategory);
    }

    private void loadItems(String category) {
        itemsContainer.removeAllViews();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TShirtsDatabaseHelper.TABLE_NAME +
                        " WHERE " + TShirtsDatabaseHelper.COL_CATEGORY + " = ?",
                new String[]{category});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper.COL_MEASUREMENTS));

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                Button addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description + "\n\nMeasurements: " + (measurements != null ? measurements : "N/A"));

                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements);
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                itemsContainer.addView(itemBox);

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
    }
}
