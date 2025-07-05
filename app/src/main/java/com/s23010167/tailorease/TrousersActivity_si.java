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

public class TrousersActivity_si extends AppCompatActivity {

    private LinearLayout trousersContainer;
    private TrousersDatabaseHelper_si trousersDbHelper;
    private OrdersDatabaseHelper_si ordersDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_trousers);

        trousersContainer = findViewById(R.id.trousersContainer);
        trousersDbHelper = new TrousersDatabaseHelper_si(this);
        ordersDbHelper = new OrdersDatabaseHelper_si(this);

        loadTrousers();
    }

    private void loadTrousers() {
        SQLiteDatabase db = trousersDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TrousersDatabaseHelper_si.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_IMAGE));
                String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_MEASUREMENTS));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(TrousersDatabaseHelper_si.COL_PRICE));

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View trouserBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, trousersContainer, false);

                ImageView image = trouserBox.findViewById(R.id.imageView);
                TextView title = trouserBox.findViewById(R.id.textViewTitle);
                TextView desc = trouserBox.findViewById(R.id.textViewDescription);
                View addToCartBtn = trouserBox.findViewById(R.id.buttonAddToCart);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(
                        description + "\n\n" +
                                "මිනුම්: " + (measurements != null ? measurements : "නැත") + "\n" +
                                "මිල: " + (price != null ? price : "නැත")
                );

                addToCartBtn.setOnClickListener(v -> {
                    long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                    if (id != -1) {
                        Toast.makeText(this, name + " කරත්තයට එකතු කරන ලදී.\nමිනුම්: " + measurements + "\nමිල: " + price, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, name + " කරත්තයට එකතු කිරීමට අසමත් විය!", Toast.LENGTH_SHORT).show();
                    }
                });

                trousersContainer.addView(trouserBox);

            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Toast.makeText(this, "දත්ත සොයාගත නොහැක!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
