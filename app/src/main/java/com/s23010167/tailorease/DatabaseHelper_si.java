package com.s23010167.tailorease;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_si extends SQLiteOpenHelper {

    // Database Configuration
    private static final String DATABASE_NAME = "TailorEase_SI.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // Create Tables
    public void onCreate(SQLiteDatabase db) {
        // Create ReadymadeItems table
        String createTable = "CREATE TABLE ReadymadeItems (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "gender TEXT, " +
                "clothingType TEXT, " +
                "size TEXT, " +
                "material TEXT, " +
                "price INTEGER)";
        db.execSQL(createTable);

        // Insert sample clothing items with Sinhala names
        db.execSQL("INSERT INTO ReadymadeItems (gender, clothingType, size, material, price) VALUES " +
                "('පුරුෂ', 'ටී-ශර්ට්', 'කුඩා', 'කපු රෙදි', 1300), " +
                "('පුරුෂ', 'ටී-ශර්ට්', 'විශාල', 'කපු රෙදි', 1600), " +
                "('පුරුෂ', 'කමිස', 'මධ්‍යම', 'ලිනන් රෙදි', 2000), " +
                "('පුරුෂ', 'කලිසම්', 'මධ්‍යම', 'ඩෙනිම් රෙදි', 1700), " +
                "('පුරුෂ', 'බ්ලේසර්', 'XL', 'ලොම් රෙදි', 4500), " +

                "('ස්ත්‍රී', 'කාන්තා ඇඳුම', 'මධ්‍යම', 'කපු රෙදි', 3000), " +
                "('ස්ත්‍රී', 'කාන්තා ඇඳුම', 'XL', 'සේද රෙදි', 3500), " +
                "('ස්ත්‍රී', 'ටී-ශර්ට්', 'මධ්‍යම', 'කපු රෙදි', 1400), " +
                "('ස්ත්‍රී', 'බ්ලේසර්', 'විශාල', 'සේද රෙදි', 4200), " +
                "('ස්ත්‍රී', 'සාරි බ්ලවුස්', 'මධ්‍යම', 'සේද රෙදි', 1600), " +

                "('ළමුන්', 'ළමුන්ගේ ඇඳුම', 'මධ්‍යම', 'කපු රෙදි', 950), " +
                "('ළමුන්', 'ටී-ශර්ට්', 'කුඩා', 'පොලියෙස්ටර් රෙදි', 700), " +
                "('ළමුන්', 'කොට කලිසම්', 'මධ්‍යම', 'ඩෙනිම් රෙදි', 800), " +
                "('ළමුන්', 'කමිස', 'විශාල', 'ලිනන් රෙදි', 1000), " +
                "('ළමුන්', 'කලිසම්', 'කුඩා', 'කපු රෙදි', 850), " +
                "('ළමුන්', 'ගවුම්', 'කුඩා', 'දැල් රෙදි', 1100), " +
                "('ළමුන්', 'ගවුම්', 'මධ්‍යම', 'දැල් රෙදි', 2000)");


    }

    // Upgrade Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS ReadymadeItems");
        onCreate(db);
    }

    // Get Price Method
    public int getPrice(String gender, String clothingType, String size, String material) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT price FROM ReadymadeItems WHERE gender = ? AND clothingType = ? AND size = ? AND material = ?",
                new String[]{gender, clothingType, size, material});
        if (cursor.moveToFirst()) {
            int price = cursor.getInt(0);
            cursor.close();
            return price;
        } else {
            cursor.close();
            return -1; // Return -1 if no matching item found
        }
    }
}
