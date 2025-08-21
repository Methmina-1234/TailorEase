package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // DB configuration
    private static final String DATABASE_NAME = "TailorEase.db";
    private static final int DATABASE_VERSION = 3; // ⬅ bumped to 3 to ensure upgrade triggers

    // Table: users
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "ID";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_USE_FINGERPRINT = "use_fingerprint";

    // Table: ClothingPrices
    public static final String TABLE_CLOTHING = "ClothingPrices";
    public static final String COL_GENDER = "Gender";
    public static final String COL_TYPE = "Type";
    public static final String COL_SIZE = "Size";
    public static final String COL_MATERIAL = "Material";
    public static final String COL_PRICE = "Price";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_USE_FINGERPRINT + " INTEGER DEFAULT 0)";
        db.execSQL(createUsersTable);

        // Create clothing prices table
        String createClothingTable = "CREATE TABLE IF NOT EXISTS " + TABLE_CLOTHING + " (" +
                COL_GENDER + " TEXT, " +
                COL_TYPE + " TEXT, " +
                COL_SIZE + " TEXT, " +
                COL_MATERIAL + " TEXT, " +
                COL_PRICE + " INTEGER)";
        db.execSQL(createClothingTable);

        // Insert sample data
        insertSampleClothingData(db);
    }

    private void insertSampleClothingData(SQLiteDatabase db) {
        // Only insert if table is empty
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CLOTHING, null);
        if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'T-Shirt', 'Small', 'Cotton', 1300)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'T-Shirt', 'Large', 'Cotton', 1600)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'Shirt', 'Medium', 'Linen', 2000)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'Trousers', 'Medium', 'Denim', 1700)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'Blazer', 'XL', 'Wool', 4500)");

            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'Lady Dress', 'Medium', 'Cotton', 3000)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'Lady Dress', 'XL', 'Silk', 3500)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'T-Shirt', 'Medium', 'Cotton', 1400)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'Skirt', 'Large', 'Denim', 2200)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'Blazer', 'Large', 'Silk', 4200)");

            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'Kid Dress', 'Medium', 'Cotton', 950)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'T-Shirt', 'Small', 'Polyester', 700)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'Shorts', 'Medium', 'Denim', 800)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'Shirt', 'Large', 'Linen', 1000)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'Trousers', 'Small', 'Cotton', 850)");

            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Male', 'Kurta', 'XL', 'Cotton', 1900)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Female', 'Saree Blouse', 'Medium', 'Silk', 1600)");
            db.execSQL("INSERT INTO " + TABLE_CLOTHING + " VALUES ('Kids', 'Frock', 'Small', 'Net', 1100)");

        }
        cursor.close();
    }

    // Handle Database Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add fingerprint column if missing
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COL_USE_FINGERPRINT + " INTEGER DEFAULT 0");
        }

        // Ensure clothing table exists
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CLOTHING + " (" +
                    COL_GENDER + " TEXT, " +
                    COL_TYPE + " TEXT, " +
                    COL_SIZE + " TEXT, " +
                    COL_MATERIAL + " TEXT, " +
                    COL_PRICE + " INTEGER)");
            insertSampleClothingData(db);
        }
    }

    // User Methods
    // Insert a new user into the database
    public boolean insertUser(String username, String email, String password, boolean useFingerprint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        values.put(COL_USE_FINGERPRINT, useFingerprint ? 1 : 0);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1; // Return true if insert successful
    }

    // Check if a username or email already exists
    public boolean userExists(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=? OR " + COL_EMAIL + "=?",
                new String[]{username, email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Validate username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // Check if fingerprint login is enabled for a user
    public boolean isFingerprintEnabled(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_USE_FINGERPRINT + " FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + "=?",
                new String[]{username});
        boolean enabled = false;
        if (cursor.moveToFirst()) {
            enabled = cursor.getInt(0) == 1;
        }
        cursor.close();
        return enabled;
    }

    // Clothing Price Lookup
    public int getPrice(String gender, String type, String size, String material) {
        int price = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT " + COL_PRICE + " FROM " + TABLE_CLOTHING +
                            " WHERE " + COL_GENDER + "=? AND " + COL_TYPE + "=? AND " +
                            COL_SIZE + "=? AND " + COL_MATERIAL + "=?",
                    new String[]{gender, type, size, material});

            if (cursor != null && cursor.moveToFirst()) {
                price = cursor.getInt(0); // Get price from database
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return price; // Returns -1 if not found
    }


}
