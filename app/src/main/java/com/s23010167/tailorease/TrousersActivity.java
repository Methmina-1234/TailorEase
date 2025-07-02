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

public class TrousersActivity extends AppCompatActivity {

    private LinearLayout trousersContainer;
    private TrousersDatabaseHelper trousersDbHelper;
    private OrdersDatabaseHelper ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trousers);

        trousersContainer = findViewById(R.id.trousersContainer);
        trousersDbHelper = new TrousersDatabaseHelper(this);
        ordersDbHelper = new OrdersDatabaseHelper(this);

        loadTrousers();
    }

    private void loadTrousers() {
        SQLiteDatabase db = trousersDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TrousersDatabaseHelper.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper.COL_PRICE)); // ✅ get price

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View trouserBox = LayoutInflater.from(this).inflate(R.layout.readymade_box, trousersContainer, false);

                ImageView image = trouserBox.findViewById(R.id.imageView);
                TextView title = trouserBox.findViewById(R.id.textViewTitle);
                TextView desc = trouserBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = trouserBox.findViewById(R.id.buttonAddToCart);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "Measurements: " + (measurements != null ? measurements : "N/A") + "\n" +
                                "Price: " + (price != null ? price : "N/A")  // ✅ show price
                );

                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this, name + " added to cart\nMeasurements: " + measurements + "\nPrice: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add " + name + " to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                trousersContainer.addView(trouserBox);

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
    }
}
