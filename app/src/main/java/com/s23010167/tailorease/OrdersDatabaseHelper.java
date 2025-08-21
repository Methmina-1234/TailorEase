package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrdersDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orders.db"; // Database file name
    private static final int DATABASE_VERSION = 1; // Database version for upgrades

    // Table and column definitions
    public static final String TABLE_NAME = "orders";
    public static final String COL_ID = "id"; // Primary key
    public static final String COL_NAME = "name"; // Product name
    public static final String COL_DESCRIPTION = "description"; // Product description
    public static final String COL_IMAGE = "image"; // Image resource name

    // Constructor
    public OrdersDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create orders table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-increment ID
                COL_NAME + " TEXT, " + // Name column
                COL_DESCRIPTION + " TEXT, " + // Description column
                COL_IMAGE + " TEXT)"; // Image column
        db.execSQL(createTable); // Execute SQL to create table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if exists and create a new one (simple upgrade strategy)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Helper method to add a new order into the database
    public long addOrder(String name, String description, String image, String measurements, String price) {
        SQLiteDatabase db = this.getWritableDatabase(); // Open database in write mode
        ContentValues cv = new ContentValues(); // Container for row values
        cv.put(COL_NAME, name); // Set product name
        cv.put(COL_DESCRIPTION, description); // Set product description
        cv.put(COL_IMAGE, image); // Set image resource name
        long id = db.insert(TABLE_NAME, null, cv); // Insert row into table and get generated ID
        // Note: Do NOT close db here; caller manages the DB lifecycle if needed
        return id; // Return the row ID of the newly inserted order
    }
}
