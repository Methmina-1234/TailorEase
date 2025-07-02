package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LadyDressDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ladydress.db";
    private static final int DATABASE_VERSION = 2; // ⬅️ Updated version

    public static final String TABLE_NAME = "ladydress";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";
    public static final String COL_PRICE = "price"; // ⬅️ New column

    public LadyDressDatabaseHelper(Context context) {
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
