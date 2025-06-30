package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MaterialDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "materials.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "materials";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image"; // store resource name like "pic_14"

    public MaterialDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT)";
        db.execSQL(createTable);

        // Sample data
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, description, image) VALUES " +
                "('Cotton', 'Soft, breathable, and perfect for everyday wear. It is easy to wash and gentle on the skin.', 'pic_14')," +
                "('Linen', 'Lightweight and cool, ideal for hot climates. Linen has a natural, relaxed texture.', 'pic_15')," +
                "('Silk', 'Smooth, shiny, and luxurious. Great for formal wear and delicate garments.', 'pic_16')," +
                "('Denim', 'Thick, durable fabric known for jeans. Offers a rugged and casual look.', 'pic_17')," +
                "('Polyester', 'Wrinkle-resistant and long-lasting. Dries quickly and holds its shape well.', 'pic_18')," +
                "('Wool', 'Warm and insulating, perfect for winter. Wool is soft and helps retain heat.', 'pic_19')," +
                "('Net', 'Sheer and lightweight with an open weave. Often used in overlays and decorative fashion.', 'pic_20')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
