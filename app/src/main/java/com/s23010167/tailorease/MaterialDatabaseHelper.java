package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaterialDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "materials.db";
    private static final int DATABASE_VERSION = 3;  // bumped for price column addition

    public static final String TABLE_NAME = "materials";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image"; // resource name like "pic_14"
    public static final String COL_URL = "url"; // external link
    public static final String COL_PRICE_INCREASE = "price_increase"; // new price increase column

    public MaterialDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with price_increase column
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_URL + " TEXT, " +
                COL_PRICE_INCREASE + " REAL)";  // REAL type for price decimal

        db.execSQL(createTable);

        // Insert sample data with price increase
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_URL + ", " + COL_PRICE_INCREASE + ") VALUES " +

                "('Cotton', 'Soft, breathable, and perfect for everyday wear. It is easy to wash and gentle on the skin.', 'pic_14', 'https://en.wikipedia.org/wiki/Cotton', 0.0)," +
                "('Linen', 'Lightweight and cool, ideal for hot climates. Linen has a natural, relaxed texture.', 'pic_15', 'https://en.wikipedia.org/wiki/Linen', 1500.0)," +
                "('Silk', 'Smooth, shiny, and luxurious. Great for formal wear and delicate garments.', 'pic_16', 'https://en.wikipedia.org/wiki/Silk', 2500.0)," +
                "('Denim', 'Thick, durable fabric known for jeans. Offers a rugged and casual look.', 'pic_17', 'https://en.wikipedia.org/wiki/Denim', 800.0)," +
                "('Polyester', 'Wrinkle-resistant and long-lasting. Dries quickly and holds its shape well.', 'pic_18', 'https://en.wikipedia.org/wiki/Polyester', 500.0)," +
                "('Wool', 'Warm and insulating, perfect for winter. Wool is soft and helps retain heat.', 'pic_19', 'https://en.wikipedia.org/wiki/Wool', 2000.0)," +
                "('Net', 'Sheer and lightweight with an open weave. Often used in overlays and decorative fashion.', 'pic_20', 'https://en.wikipedia.org/wiki/Net_(textile)', 1200.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table and create new on upgrade (simple migration)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
