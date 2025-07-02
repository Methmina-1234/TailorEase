package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BagDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bags.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "bags";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";

    public BagDatabaseHelper(Context context) {
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

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ") VALUES " +

                "('Leather Handbag', 'Elegant leather handbag for formal and daily use.', 'pic_bag1', 'Height: 25cm | Width: 30cm')," +
                "('Canvas Tote Bag', 'Casual and lightweight tote bag for everyday shopping.', 'pic_bag2', 'Height: 35cm | Width: 40cm')," +
                "('Mini Backpack', 'Trendy mini backpack perfect for outings.', 'pic_bag3', 'Height: 30cm | Width: 20cm')," +
                "('Sling Bag', 'Compact sling bag with adjustable strap.', 'pic_bag4', 'Height: 20cm | Width: 15cm')," +
                "('Travel Duffel Bag', 'Spacious and durable for short trips.', 'pic_bag5', 'Length: 50cm | Width: 25cm | Height: 30cm')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
