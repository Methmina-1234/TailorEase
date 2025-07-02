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

    private LinearLayout itemsContainer;
    private BagDatabaseHelper dbHelper;
    private OrdersDatabaseHelper ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        itemsContainer = findViewById(R.id.itemsContainer);
        dbHelper = new BagDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        loadBags();
    }

    private void loadBags() {
        itemsContainer.removeAllViews();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BagDatabaseHelper.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_MEASUREMENTS));
                String capacity = cursor.getString(cursor.getColumnIndexOrThrow(BagDatabaseHelper.COL_CAPACITY)); // New

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description + "\n\nMeasurements: " + (measurements != null ? measurements : "N/A") +
                        "\nCapacity: " + (capacity != null ? capacity : "N/A")); // Show capacity

                addToCartBtn.setOnClickListener(v -> {
                    // Update addOrder if it supports capacity, otherwise send null or ignore capacity
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements /* you can add capacity here if supported */);
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nCapacity: " + capacity, Toast.LENGTH_SHORT).show();
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
