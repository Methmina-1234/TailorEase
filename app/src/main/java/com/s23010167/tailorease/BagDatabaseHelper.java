package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BagDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bags.db";
    private static final int DATABASE_VERSION = 2; // Increment version to trigger onUpgrade

    public static final String TABLE_NAME = "bags";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";
    public static final String COL_CAPACITY = "capacity"; // ⬅️ New column for liters

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
                COL_MEASUREMENTS + " TEXT, " +
                COL_CAPACITY + " TEXT)";
        db.execSQL(createTable);

        db.execSQL("INSERT INTO " + TABLE_NAME + (" (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_CAPACITY + ") VALUES ") +

                "('Leather Handbag', 'Elegant leather handbag for formal and daily use.', 'pic_bag1', 'Height: 25cm | Width: 30cm', '8L')," +
                "('Canvas Tote Bag', 'Casual and lightweight tote bag for everyday shopping.', 'pic_bag2', 'Height: 35cm | Width: 40cm', '15L')," +
                "('Mini Backpack', 'Trendy mini backpack perfect for outings.', 'pic_bag3', 'Height: 30cm | Width: 20cm', '10L')," +
                "('Sling Bag', 'Compact sling bag with adjustable strap.', 'pic_bag4', 'Height: 20cm | Width: 15cm', '5L')," +
                "('Travel Duffel Bag', 'Spacious and durable for short trips.', 'pic_bag5', 'Length: 50cm | Width: 25cm | Height: 30cm', '35L')," +
                "('Crossbody Bag', 'Stylish and secure bag for daily use.', 'pic_bag6', 'Height: 22cm | Width: 28cm', '7L')," +
                "('Clutch Bag', 'Elegant evening clutch perfect for parties and events.', 'pic_bag7', 'Height: 15cm | Width: 25cm', '2L')," +
                "('Laptop Bag', 'Professional and padded bag for carrying laptops.', 'pic_bag8', 'Fits up to 15-inch laptops', '12L')," +
                "('Bucket Bag', 'Trendy and spacious with a drawstring closure.', 'pic_bag9', 'Height: 30cm | Width: 25cm', '14L')," +
                "('Messenger Bag', 'Classic over-the-shoulder bag with flap.', 'pic_bag10', 'Height: 28cm | Width: 35cm', '13L')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
