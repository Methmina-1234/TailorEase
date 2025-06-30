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
        setContentView(R.layout.activity_material); // Make sure activity_material.xml exists

        // Initialize views and database helper
        materialContainer = findViewById(R.id.materialContainer);
        dbHelper = new MaterialDatabaseHelper(this);

        loadMaterials(); // Load material data from SQLite database
    }

    private void loadMaterials() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MaterialDatabaseHelper.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get values from each row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_DESCRIPTION));
                String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(MaterialDatabaseHelper.COL_IMAGE));

                // Resolve image resource ID from drawable name
                int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());

                // Use default image if the resource is not found
                if (imageResId == 0) {
                    imageResId = R.drawable.pic_14; // Add this to res/drawable
                }

                // Inflate material_box layout for each material item
                View materialBox = LayoutInflater.from(this).inflate(R.layout.material_box, materialContainer, false);

                // Populate the layout with material data
                ImageView image = materialBox.findViewById(R.id.imageView);
                TextView title = materialBox.findViewById(R.id.textViewTitle);
                TextView desc = materialBox.findViewById(R.id.textViewDescription);

                image.setImageResource(imageResId);
                title.setText(name);
                desc.setText(description);

                // Add the material box to the container
                materialContainer.addView(materialBox);

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
    }
}
