package com.s23010167.tailorease;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        setContentView(R.layout.activity_material); // activity_material.xml

        materialContainer = findViewById(R.id.materialContainer);
        dbHelper = new MaterialDatabaseHelper(this);

        loadMaterials();
    }

    private void loadMaterials() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM materials", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                // Get drawable resource ID by name
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());

                // Inflate material_box.xml for each material
                View materialBox = LayoutInflater.from(this).inflate(R.layout.material_box, materialContainer, false);

                ImageView image = materialBox.findViewById(R.id.imageView);
                TextView title = materialBox.findViewById(R.id.textViewTitle);
                TextView desc = materialBox.findViewById(R.id.textViewDescription);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description);

                materialContainer.addView(materialBox);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}
