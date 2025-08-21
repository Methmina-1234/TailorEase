package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KidDressDatabaseHelper_si extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "kiddress_si.db";
    private static final int DATABASE_VERSION = 2; // Incremented version to force upgrade when schema changes

    // Table and column names
    public static final String TABLE_NAME = "kiddress";
    public static final String COL_ID = "id";               // Primary key
    public static final String COL_NAME = "name";           // Dress name
    public static final String COL_DESCRIPTION = "description"; // Description
    public static final String COL_IMAGE = "image";         // Image resource name
    public static final String COL_MEASUREMENTS = "measurements"; // Age/size info
    public static final String COL_PRICE = "price";         // Price of the dress

    // Constructor - creates a new instance of the database helper
    public KidDressDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table SQL query
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-increment ID
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";

        // Execute table creation
        db.execSQL(createTable);

        // Insert some sample dresses into the table
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Party Frock', 'උපන්දිනයන් සහ උත්සව සඳහා සුදුසු වර්ණවත් සහ දිදුලන ෆ්රොක් එකකි.', 'pic_kiddress1', 'වයස්: 2-8 වසර', 'රු.1800')," +
                "('Casual Play Dress', 'දිනපතා ක්‍රීඩා සඳහා සුදුසු මෘදු සහ සුවපහසු ගවුමකි.', 'pic_kiddress2', 'වයස්: 3-7 වසර', 'රු.1200')," +
                "('Traditional Dress', 'සංස්කෘතික උත්සව සහ ආගමික අවස්ථා සඳහා සම්ප්‍රදායික රූපයෙන් යුතු ගවුමකි.', 'pic_kiddress3', 'වයස්: 4-10 වසර', 'රු.2000')," +
                "('Princess Gown', 'විශේෂ අවස්ථාවන් සඳහා තට්ටු සහ ලේස් වලින් සැරසුනු අලංකාර ගවුමකි.', 'pic_kiddress4', 'වයස්: 5-10 වසර', 'රු.2500')," +
                "('Summer Sundress', 'උණුසුම් කාලගුණයට සුදුසු සැහැල්ලු සහ අතකෙළියෙන් තොර ගවුමකි.', 'pic_kiddress5', 'වයස්: 2-6 වසර', 'රු.1400')," +

                "('Floral Dress', 'වසන්තය සහ ග්‍රිෂ්මය සඳහා මල මුද්‍රිත ආකෘතියකින් යුතු සන්සුන් ගවුමකි.', 'pic_kiddress6', 'වයස්: 3-8 වසර', 'රු.1500')," +
                "('Winter Wool Dress', 'සීතල කාලයට සුදුසු උණුසුම් සහ ආරක්ෂිත වූල් ගවුමකි.', 'pic_kiddress7', 'වයස්: 4-9 වසර', 'රු.2200')," +
                "('Denim Dress', 'විලාසිතාව සහ දිරුම්කම සහිත ළමුන් සඳහා නිම කරන ලද ඩෙනිම් ගවුමකි.', 'pic_kiddress8', 'වයස්: 3-7 වසර', 'රු.1900')," +
                "('Fairy Dress', 'කල්පනාකාරී ක්‍රීඩාව සඳහා පිහාටු සහ දිදුලන නිර්මාණ සහිත ආකර්ෂණීය ගවුමකි.', 'pic_kiddress9', 'වයස්: 4-9 වසර', 'රු.2300')," +
                "('Polka Dot Dress', 'පැරණි විලාසිතාවක් සහිත සම්ප්‍රදායික පොල්කා ඩොට් නිර්මාණයකි.', 'pic_kiddress10', 'වයස්: 2-6 වසර', 'රු.1600')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate the database
        onCreate(db);
    }
}
