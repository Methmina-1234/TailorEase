package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BagDatabaseHelper extends SQLiteOpenHelper {

    // Database file name and version
    private static final String DATABASE_NAME = "bags.db";
    private static final int DATABASE_VERSION = 3; // bumped when schema changed (added price column)

    // Table and column names
    public static final String TABLE_NAME = "bags";
    public static final String COL_ID = "id";                 // Primary Key
    public static final String COL_NAME = "name";             // Bag name
    public static final String COL_DESCRIPTION = "description"; // Bag description
    public static final String COL_IMAGE = "image";           // Image resource name
    public static final String COL_MEASUREMENTS = "measurements"; // Size details
    public static final String COL_CAPACITY = "capacity";     // Storage capacity
    public static final String COL_PRICE = "price";           // Price (new column)

    // Constructor - passes DB name and version to SQLiteOpenHelper
    public BagDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the bags table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // auto-increment ID
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_CAPACITY + " TEXT, " +
                COL_PRICE + " TEXT)"; // Added price column
        db.execSQL(createTable);

        // Insert sample bag items into the table
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_CAPACITY + ", " + COL_PRICE + ") VALUES " +

                "('Leather Handbag', 'Elegant leather handbag for formal and daily use.', 'pic_bag1', 'Height: 25cm | Width: 30cm', '8L', '$25 - $40')," +
                "('Canvas Tote Bag', 'Casual and lightweight tote bag for everyday shopping.', 'pic_bag2', 'Height: 35cm | Width: 40cm', '15L', '$15 - $25')," +
                "('Mini Backpack', 'Trendy mini backpack perfect for outings.', 'pic_bag3', 'Height: 30cm | Width: 20cm', '10L', '$18 - $30')," +
                "('Sling Bag', 'Compact sling bag with adjustable strap.', 'pic_bag4', 'Height: 20cm | Width: 15cm', '5L', '$12 - $20')," +
                "('Travel Duffel Bag', 'Spacious and durable for short trips.', 'pic_bag5', 'Length: 50cm | Width: 25cm | Height: 30cm', '35L', '$30 - $50')," +
                "('Crossbody Bag', 'Stylish and secure bag for daily use.', 'pic_bag6', 'Height: 22cm | Width: 28cm', '7L', '$18 - $28')," +
                "('Clutch Bag', 'Elegant evening clutch perfect for parties and events.', 'pic_bag7', 'Height: 15cm | Width: 25cm', '2L', '$10 - $15')," +
                "('Laptop Bag', 'Professional and padded bag for carrying laptops.', 'pic_bag8', 'Fits up to 15-inch laptops', '12L', '$35 - $60')," +
                "('Bucket Bag', 'Trendy and spacious with a drawstring closure.', 'pic_bag9', 'Height: 30cm | Width: 25cm', '14L', '$20 - $35')," +
                "('Messenger Bag', 'Classic over-the-shoulder bag with flap.', 'pic_bag10', 'Height: 28cm | Width: 35cm', '13L', '$22 - $38')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate the table
        onCreate(db);
    }
}
