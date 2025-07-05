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

public class TShirtActivity_si extends AppCompatActivity {

    private LinearLayout itemsContainer;
    private Button btnTShirts, btnShirts;

    private TShirtsDatabaseHelper_si dbHelper;          // ✅ use Sinhala DB
    private OrdersDatabaseHelper_si ordersDbHelper;     // ✅ use Sinhala orders DB

    private String currentCategory = "T-Shirt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_tshirt); // ✅ use localized layout

        itemsContainer = findViewById(R.id.itemsContainer);
        btnTShirts = findViewById(R.id.btnTShirts);
        btnShirts = findViewById(R.id.btnShirts);

        dbHelper = new TShirtsDatabaseHelper_si(this); // ✅
        ordersDbHelper = new OrdersDatabaseHelper_si(this); // ✅

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

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM " + TShirtsDatabaseHelper_si.TABLE_NAME +
                            " WHERE " + TShirtsDatabaseHelper_si.COL_CATEGORY + " = ?",
                    new String[]{category});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_IMAGE));
                    String measurements = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_MEASUREMENTS));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow(TShirtsDatabaseHelper_si.COL_PRICE));

                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                    View itemBox = LayoutInflater.from(this).inflate(R.layout.si_readymade_box, itemsContainer, false);

                    ImageView image = itemBox.findViewById(R.id.imageView);
                    TextView title = itemBox.findViewById(R.id.textViewTitle);
                    TextView desc = itemBox.findViewById(R.id.textViewDescription);
                    Button addToCartBtn = itemBox.findViewById(R.id.buttonAddToCart);

                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description + "\n\nමිනුම්: " + (measurements != null ? measurements : "නැත") + "\nමිල: " + (price != null ? price : "නැත"));

                    addToCartBtn.setOnClickListener(v -> {
                        long id = ordersDbHelper.addOrder(name, description, imageResName, measurements, price);
                        if (id != -1) {
                            Toast.makeText(this, name + " කරත්තයට එකතු කරන ලදී\nමිනුම්: " + measurements + "\nමිල: " + price, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, name + " කරත්තයට එකතු කිරීමට අසමත් විය", Toast.LENGTH_SHORT).show();
                        }
                    });

                    itemsContainer.addView(itemBox);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this, "අයිතම පූරණය කිරීමේ දෝෂයකි: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
}
