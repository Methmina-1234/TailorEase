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

    private LinearLayout materialContainer;
    private MaterialDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        materialContainer = findViewById(R.id.materialContainer);
        dbHelper = new MaterialDatabaseHelper(this);

        loadMaterials();
    }

    private void loadMaterials() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MaterialDatabaseHelper.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_IMAGE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_URL));
                double priceIncrease = cursor.getDouble(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_PRICE_INCREASE));

                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                View materialBox = LayoutInflater.from(this).inflate(R.layout.material_box, materialContainer, false);

                ImageView image = materialBox.findViewById(R.id.imageView);
                TextView title = materialBox.findViewById(R.id.textViewTitle);
                TextView desc = materialBox.findViewById(R.id.textViewDescription);
                TextView more = materialBox.findViewById(R.id.textViewMore);
                TextView priceIncreaseText = materialBox.findViewById(R.id.textViewPriceIncrease);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description);
                priceIncreaseText.setText("Using this material increases price by Rs. " + priceIncrease);

                more.setOnClickListener(v -> {
                    if (url != null && !url.isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });

                materialContainer.addView(materialBox);

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
    }
}
