package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LadyDressDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ladydress.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ladydress";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";

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
                COL_MEASUREMENTS + " TEXT)";
        db.execSQL(createTable);

        // Insert sample lady dress items
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ") VALUES " +

                "('Elegant Evening Gown', 'A stunning gown perfect for formal occasions.', 'pic_ladydress1', 'Sizes: S, M, L')," +
                "('Summer Floral Dress', 'Light and breezy floral dress for summer.', 'pic_ladydress2', 'Sizes: XS, S, M')," +
                "('Cocktail Dress', 'Chic and stylish cocktail dress.', 'pic_ladydress3', 'Sizes: S, M, L, XL')," +
                "('Casual Day Dress', 'Comfortable and casual for daily wear.', 'pic_ladydress4', 'Sizes: M, L, XL')," +
                "('Maxi Dress', 'Flowy and elegant maxi dress for any occasion.', 'pic_ladydress5', 'Sizes: S, M, L, XL')," +
                "('Wrap Dress', 'Flattering wrap dress with adjustable waist.', 'pic_ladydress6', 'Sizes: XS, S, M, L')," +
                "('Bodycon Dress', 'Tight-fitting dress perfect for night outs.', 'pic_ladydress7', 'Sizes: S, M, L')," +
                "('Shift Dress', 'Classic straight-cut dress for versatile wear.', 'pic_ladydress8', 'Sizes: S, M, L, XL')," +
                "('Sheath Dress', 'Elegant sheath dress suitable for work or events.', 'pic_ladydress9', 'Sizes: S, M, L')," +
                "('Sundress', 'Lightweight and colorful sundress for warm days.', 'pic_ladydress10', 'Sizes: XS, S, M')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
