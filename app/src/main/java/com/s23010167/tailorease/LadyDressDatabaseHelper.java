package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Database helper class for managing Lady Dress data
public class LadyDressDatabaseHelper extends SQLiteOpenHelper {

    // Database file name and version
    private static final String DATABASE_NAME = "ladydress.db";
    private static final int DATABASE_VERSION = 2; // Increment when schema changes

    // Table and column names
    public static final String TABLE_NAME = "ladydress";
    public static final String COL_ID = "id"; // Primary Key
    public static final String COL_NAME = "name"; // Dress name
    public static final String COL_DESCRIPTION = "description"; // Dress description
    public static final String COL_IMAGE = "image"; // Drawable resource name
    public static final String COL_MEASUREMENTS = "measurements"; // Available sizes
    public static final String COL_PRICE = "price"; // Price range

    // Constructor
    public LadyDressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called only once when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        // Insert initial sample data
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Elegant Evening Gown', 'A stunning gown perfect for formal occasions.', 'pic_ladydress1', 'Sizes: S, M, L', '$40 - $60')," +
                "('Summer Floral Dress', 'Light and breezy floral dress for summer.', 'pic_ladydress2', 'Sizes: XS, S, M', '$25 - $35')," +
                "('Cocktail Dress', 'Chic and stylish cocktail dress.', 'pic_ladydress3', 'Sizes: S, M, L, XL', '$35 - $50')," +
                "('Casual Day Dress', 'Comfortable and casual for daily wear.', 'pic_ladydress4', 'Sizes: M, L, XL', '$20 - $30')," +
                "('Maxi Dress', 'Flowy and elegant maxi dress for any occasion.', 'pic_ladydress5', 'Sizes: S, M, L, XL', '$30 - $45')," +
                "('Wrap Dress', 'Flattering wrap dress with adjustable waist.', 'pic_ladydress6', 'Sizes: XS, S, M, L', '$28 - $38')," +
                "('Bodycon Dress', 'Tight-fitting dress perfect for night outs.', 'pic_ladydress7', 'Sizes: S, M, L', '$32 - $42')," +
                "('Shift Dress', 'Classic straight-cut dress for versatile wear.', 'pic_ladydress8', 'Sizes: S, M, L, XL', '$30 - $40')," +
                "('Sheath Dress', 'Elegant sheath dress suitable for work or events.', 'pic_ladydress9', 'Sizes: S, M, L', '$33 - $47')," +
                "('Sundress', 'Lightweight and colorful sundress for warm days.', 'pic_ladydress10', 'Sizes: XS, S, M', '$22 - $34')");
    }

    // Called when database version changes (e.g., if DATABASE_VERSION is updated)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
