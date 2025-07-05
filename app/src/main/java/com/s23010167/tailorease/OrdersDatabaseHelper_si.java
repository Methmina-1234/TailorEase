package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrdersDatabaseHelper_si extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orders_si.db"; // Unique DB name for Sinhala version
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "orders";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";

    public OrdersDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // fixed constructor name
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Helper method to add an order
    public long addOrder(String name, String description, String image, String measurements, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_IMAGE, image);
        long id = db.insert(TABLE_NAME, null, cv);
        return id;
    }
}
