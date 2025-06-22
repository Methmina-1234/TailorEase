package com.s23010167.tailorease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TailorEase.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "email";
    public static final String COL_4 = "password";
    public static final String COL_5 = "use_fingerprint";  // ✅ new column

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // ⬅ Updated version to 2 to trigger onUpgrade
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_2 + " TEXT, " +
                        COL_3 + " TEXT, " +
                        COL_4 + " TEXT, " +
                        COL_5 + " INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Only upgrade if version is less than 2
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_5 + " INTEGER DEFAULT 0");
        }
    }

    // ✅ Updated method to include fingerprint preference
    public boolean insertUser(String username, String email, String password, boolean useFingerprint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, email);
        values.put(COL_4, password);
        values.put(COL_5, useFingerprint ? 1 : 0); // ✅ Store fingerprint flag
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean userExists(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? OR email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // ✅ Method to check if fingerprint login is enabled for a user
    public boolean isFingerprintEnabled(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_5 + " FROM " + TABLE_NAME + " WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean enabled = false;
        if (cursor.moveToFirst()) {
            enabled = cursor.getInt(0) == 1;
        }
        cursor.close();
        return enabled;
    }
}
