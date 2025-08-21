package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrdersDatabaseHelper_si extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orders_si.db"; // Sinhala version DB file name
    private static final int DATABASE_VERSION = 1; // DB version

    // Table and column definitions
    public static final String TABLE_NAME = "orders";
    public static final String COL_ID = "id"; // Primary key
    public static final String COL_NAME = "name"; // Product name
    public static final String COL_DESCRIPTION = "description"; // Product description
    public static final String COL_IMAGE = "image"; // Image resource name

    // Constructor
    public OrdersDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Initialize helper
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create orders table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-increment ID
                COL_NAME + " TEXT, " + // Name column
                COL_DESCRIPTION + " TEXT, " + // Description column
                COL_IMAGE + " TEXT)"; // Image column
        db.execSQL(createTable); // Execute table creation
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade: drop old table and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Helper method to add a new order
    public long addOrder(String name, String description, String image, String measurements, String price) {
        SQLiteDatabase db = this.getWritableDatabase(); // Open DB in write mode
        ContentValues cv = new ContentValues(); // Key-value pairs for new row
        cv.put(COL_NAME, name); // Set product name
        cv.put(COL_DESCRIPTION, description); // Set product description
        cv.put(COL_IMAGE, image); // Set image resource
        long id = db.insert(TABLE_NAME, null, cv); // Insert row and return ID
        return id; // Return newly inserted row ID
    }
}
