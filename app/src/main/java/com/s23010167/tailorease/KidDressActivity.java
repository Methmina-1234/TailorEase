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

    private LinearLayout itemsContainer;
    private KidDressDatabaseHelper dbHelper;
    private OrdersDatabaseHelper ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiddress);

        itemsContainer = findViewById(R.id.itemsContainer);
        dbHelper = new KidDressDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        loadKidDresses();
    }

    private void loadKidDresses() {
        itemsContainer.removeAllViews();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + KidDressDatabaseHelper.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(KidDressDatabaseHelper.COL_PRICE)); // ⬅️ New

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View itemBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, itemsContainer, false);

                ImageView image = itemBox.findViewById(R.id.imageView);
                TextView title = itemBox.findViewById(R.id.textViewTitle);
                TextView desc = itemBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "Measurements: " + (measurements != null ? measurements : "N/A") + "\n" +
                                "Price: " + (price != null ? price : "N/A")
                );

                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price, Toast.LENGTH_SHORT).show();
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
