package com.s23010167.tailorease;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MaterialActivity_si extends AppCompatActivity {

    private LinearLayout materialContainer; // Container to hold all material items
    private MaterialDatabaseHelper_si dbHelper; // Sinhala database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_material); // Layout for Sinhala version

        materialContainer = findViewById(R.id.materialContainer);

        dbHelper = new MaterialDatabaseHelper_si(this); // Initialize DB helper

        loadMaterials(); // Load and display all materials
    }

    private void loadMaterials() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Get readable database
        Cursor cursor = db.rawQuery("SELECT * FROM " + MaterialDatabaseHelper_si.TABLE_NAME, null); // Query all materials

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fetch data from database
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper_si.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper_si.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper_si.COL_IMAGE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper_si.COL_URL));
                double priceIncrease = cursor.getDouble(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper_si.COL_PRICE_INCREASE));

                // Get image resource ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Default image if not found

                // Inflate a single material box layout
                View materialBox = LayoutInflater.from(this).inflate(R.layout.si_material_box, materialContainer, false);

                // Bind views inside the material box
                ImageView image = materialBox.findViewById(R.id.imageView);
                TextView title = materialBox.findViewById(R.id.textViewTitle);
                TextView desc = materialBox.findViewById(R.id.textViewDescription);
                TextView more = materialBox.findViewById(R.id.textViewMore);
                TextView priceIncreaseText = materialBox.findViewById(R.id.textViewPriceIncrease);

                // Set data into views
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description);
                priceIncreaseText.setText("මෙම රෙදි භාවිතා කිරීමෙන් මිල රු. " + priceIncrease + "කින් වැඩිවේ");

                // Open URL when "More" clicked
                more.setOnClickListener(v -> {
                    if (url != null && !url.isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });

                // Add the material box to container
                materialContainer.addView(materialBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor
        }

        db.close(); // Close database connection
    }
}
