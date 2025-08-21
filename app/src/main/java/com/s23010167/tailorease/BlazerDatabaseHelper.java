package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlazerDatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "blazers.db"; // Database file name
    private static final int DATABASE_VERSION = 2; // Increment version if schema changes

    // Table & column names
    public static final String TABLE_NAME = "blazers";
    public static final String COL_ID = "id";                 // Primary key
    public static final String COL_NAME = "name";             // Blazer name/title
    public static final String COL_DESCRIPTION = "description"; // Blazer description
    public static final String COL_IMAGE = "image";           // Image resource name (drawable)
    public static final String COL_MEASUREMENTS = "measurements"; // Size / measurements info
    public static final String COL_PRICE = "price";           // Blazer price (as text for now)

    public BlazerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

        // Insert initial blazer data into the table
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Classic Single-Breasted Blazer', 'Timeless design perfect for formal and semi-formal events.', 'pic_blazer1', 'Chest: 38-46 in | Length: 28-30 in', '$45')," +
                "('Double-Breasted Blazer', 'Sophisticated style with extra buttons and wider lapels.', 'pic_blazer2', 'Chest: 40-48 in | Tailored fit', '$55')," +
                "('Casual Blazer', 'Lightweight and versatile for smart casual looks.', 'pic_blazer3', 'Chest: 38-46 in | Soft fabric', '$40')," +
                "('Velvet Blazer', 'Luxurious velvet finish for evening occasions.', 'pic_blazer4', 'Chest: 38-44 in | Slim fit', '$60')," +
                "('Checked Pattern Blazer', 'Modern look with classic checkered pattern.', 'pic_blazer5', 'Chest: 38-46 in | Regular fit', '$50')," +
                "('Linen Blazer', 'Breathable and lightweight—perfect for summer events.', 'pic_blazer6', 'Chest: 38-46 in | Natural fabric', '$48')," +
                "('Wool Blend Blazer', 'Warm and structured, ideal for cooler weather.', 'pic_blazer7', 'Chest: 40-50 in | Winter wear', '$65')," +
                "('Tweed Blazer', 'Textured and stylish for a vintage vibe.', 'pic_blazer8', 'Chest: 38-46 in | Classic cut', '$55')," +
                "('Slim Fit Blazer', 'Tailored for a sleek modern silhouette.', 'pic_blazer9', 'Chest: 36-44 in | Slim profile', '$52')," +
                "('Peak Lapel Blazer', 'Sharp peak lapels for a bold, formal look.', 'pic_blazer10', 'Chest: 40-48 in | Sharp cut', '$58')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Recreate the table and re-insert sample data
        onCreate(db);
    }
}
