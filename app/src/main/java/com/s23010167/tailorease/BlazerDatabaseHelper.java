package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlazerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blazers.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "blazers";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";

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
                COL_MEASUREMENTS + " TEXT)";
        db.execSQL(createTable);

        // Insert sample blazer items
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ") VALUES " +

                "('Classic Single-Breasted Blazer', 'Timeless design perfect for formal and semi-formal events.', 'pic_blazer1', 'Chest: 38-46 in | Length: 28-30 in')," +
                "('Double-Breasted Blazer', 'Sophisticated style with extra buttons and wider lapels.', 'pic_blazer2', 'Chest: 40-48 in | Tailored fit')," +
                "('Casual Blazer', 'Lightweight and versatile for smart casual looks.', 'pic_blazer3', 'Chest: 38-46 in | Soft fabric')," +
                "('Velvet Blazer', 'Luxurious velvet finish for evening occasions.', 'pic_blazer4', 'Chest: 38-44 in | Slim fit')," +
                "('Checked Pattern Blazer', 'Modern look with classic checkered pattern.', 'pic_blazer5', 'Chest: 38-46 in | Regular fit'),"+
                "('Linen Blazer', 'Breathable and lightweight—perfect for summer events.', 'pic_blazer6', 'Chest: 38-46 in | Natural fabric')," +
                "('Wool Blend Blazer', 'Warm and structured, ideal for cooler weather.', 'pic_blazer7', 'Chest: 40-50 in | Winter wear')," +
                "('Tweed Blazer', 'Textured and stylish for a vintage vibe.', 'pic_blazer8', 'Chest: 38-46 in | Classic cut')," +
                "('Slim Fit Blazer', 'Tailored for a sleek modern silhouette.', 'pic_blazer9', 'Chest: 36-44 in | Slim profile')," +
                "('Peak Lapel Blazer', 'Sharp peak lapels for a bold, formal look.', 'pic_blazer10', 'Chest: 40-48 in | Sharp cut')");        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
