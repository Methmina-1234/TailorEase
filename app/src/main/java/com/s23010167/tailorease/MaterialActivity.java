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

public class MaterialActivity extends AppCompatActivity {

    private LinearLayout materialContainer; // Container layout to hold all material boxes
    private MaterialDatabaseHelper dbHelper; // Helper class to access materials database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        // Bind layout views
        materialContainer = findViewById(R.id.materialContainer);

        // Initialize database helper
        dbHelper = new MaterialDatabaseHelper(this);

        // Load and display materials from database
        loadMaterials();
    }

    private void loadMaterials() {
        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query all records from material table
        Cursor cursor = db.rawQuery("SELECT * FROM " + MaterialDatabaseHelper.TABLE_NAME, null);

        // Loop through each material if any exist
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fetch columns from cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_IMAGE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_URL));
                double priceIncrease = cursor.getDouble(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_PRICE_INCREASE));

                // Convert image resource name to resource ID
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // Default image if not found

                // Inflate material_box layout for each material
                View materialBox = LayoutInflater.from(this).inflate(R.layout.material_box, materialContainer, false);

                // Bind views inside material box
                ImageView image = materialBox.findViewById(R.id.imageView);
                TextView title = materialBox.findViewById(R.id.textViewTitle);
                TextView desc = materialBox.findViewById(R.id.textViewDescription);
                TextView more = materialBox.findViewById(R.id.textViewMore);
                TextView priceIncreaseText = materialBox.findViewById(R.id.textViewPriceIncrease);

                // Set data into views
                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description);
                priceIncreaseText.setText("Using this material increases price by Rs. " + priceIncrease);

                // Set click listener to open URL in browser
                more.setOnClickListener(v -> {
                    if (url != null && !url.isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });

                // Add the material box to the container
                materialContainer.addView(materialBox);

            } while (cursor.moveToNext());

            cursor.close(); // Close cursor after use
        }

        db.close(); // Close database
    }
}
