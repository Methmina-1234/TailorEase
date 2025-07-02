package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TShirtsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tops.db";
    private static final int DATABASE_VERSION = 2; // bumped for upgrade

    public static final String TABLE_NAME = "tops";
    public static final String COL_ID = "id";
    public static final String COL_CATEGORY = "category";  // "T-Shirt" or "Shirt"
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";         // drawable resource name
    public static final String COL_MEASUREMENTS = "measurements";
    public static final String COL_PRICE = "price";         // new price column

    public TShirtsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        // Insert initial data (both categories) with prices
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_CATEGORY + ", " + COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('T-Shirt', 'Basic Tee', 'Comfortable cotton T-shirt for everyday wear.', 'pic_tshirt1', 'Chest: 36-44 in | Length: 26-30 in', '$10')," +
                "('T-Shirt', 'Graphic Tee', 'Trendy tee with printed graphics.', 'pic_tshirt2', 'Chest: 36-46 in | Regular fit', '$15')," +
                "('T-Shirt', 'Oversized Tee', 'Loose fit for a relaxed style.', 'pic_tshirt3', 'Chest: 40-50 in | Extended length', '$18')," +
                "('T-Shirt', 'Polo Shirt', 'Collared T-shirt perfect for semi-casual looks.', 'pic_tshirt4', 'Chest: 38-46 in | Ribbed collar', '$20')," +
                "('T-Shirt', 'V-Neck Tee', 'Stylish V-neckline and breathable fabric.', 'pic_tshirt5', 'Chest: 36-44 in | Slim fit', '$17')," +

                "('Shirt', 'Formal Shirt', 'Perfect for office and events.', 'pic_shirt1', 'Chest: 38-46 in | Sleeve: 32-36 in', '$25')," +
                "('Shirt', 'Casual Shirt', 'Soft fabric for everyday comfort.', 'pic_shirt2', 'Chest: 36-44 in | Relaxed fit', '$22')," +
                "('Shirt', 'Denim Shirt', 'Trendy and durable denim style.', 'pic_shirt3', 'Chest: 38-48 in | Button-down', '$24')," +
                "('Shirt', 'Linen Shirt', 'Lightweight and breathable for hot days.', 'pic_shirt4', 'Chest: 36-44 in | Slim fit', '$23')," +
                "('Shirt', 'Checkered Shirt', 'Classic style with checks.', 'pic_shirt5', 'Chest: 38-46 in | Long sleeve', '$21')," +

                "('T-Shirt', 'Henley Tee', 'Round neckline with buttons. A casual classic.', 'pic_tshirt6', 'Chest: 36-44 in | Soft cotton blend', '$16')," +
                "('T-Shirt', 'Muscle Fit Tee', 'Tight fit to highlight physique.', 'pic_tshirt7', 'Chest: 34-42 in | Stretch fabric', '$19')," +
                "('T-Shirt', 'Ringer Tee', 'Contrasting sleeve and collar edges.', 'pic_tshirt8', 'Chest: 36-44 in | Retro look', '$14')," +
                "('T-Shirt', 'Longline Tee', 'Extended length with modern urban vibe.', 'pic_tshirt9', 'Chest: 38-46 in | Extra length', '$18')," +
                "('T-Shirt', 'Pocket Tee', 'Includes a stylish chest pocket.', 'pic_tshirt10', 'Chest: 36-44 in | Utility design', '$15')," +

                "('Shirt', 'Oxford Shirt', 'Versatile classic for business and casual.', 'pic_shirt6', 'Chest: 38-46 in | Button-down collar', '$26')," +
                "('Shirt', 'Mandarin Collar Shirt', 'Modern look with stand collar.', 'pic_shirt7', 'Chest: 36-44 in | Short sleeve', '$23')," +
                "('Shirt', 'Flannel Shirt', 'Warm and soft. Ideal for cold weather.', 'pic_shirt8', 'Chest: 40-50 in | Thick cotton', '$28')," +
                "('Shirt', 'Hawaiian Shirt', 'Vibrant prints for beach vibes.', 'pic_shirt9', 'Chest: 38-46 in | Lightweight fabric', '$22')," +
                "('Shirt', 'Slim Fit Shirt', 'Tailored for a sharp and slim profile.', 'pic_shirt10', 'Chest: 36-42 in | Close cut', '$25')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For simplicity, drop and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
