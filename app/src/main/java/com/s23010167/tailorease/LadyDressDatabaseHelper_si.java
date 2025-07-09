package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LadyDressDatabaseHelper_si extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ladydress_si.db";
    private static final int DATABASE_VERSION = 2; // ⬅️ Updated version

    public static final String TABLE_NAME = "ladydress";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";
    public static final String COL_PRICE = "price"; // ⬅️ New column

    public LadyDressDatabaseHelper_si(Context context) {
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
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Elegant Evening Gown ගවුම', 'සන්ධ්‍යා උත්සව සඳහා සුදුසු දීප්තිමත් ගවුමකි.', 'pic_ladydress1', 'ප්‍රමාණ: S, M, L', 'රු.4000 - රු.6000')," +
                "('Summer Floral ගවුම', 'ග්‍රිෂ්මය සඳහා සුදුසු සෙරින මල් මැවූ ගවුමකි.', 'pic_ladydress2', 'ප්‍රමාණ: XS, S, M', 'රු.2500 - රු.3500')," +
                "('Cocktail ගවුම', 'අලංකාර සහ විලාසිතා සහිත කොක්ටේල් ගවුමකි.', 'pic_ladydress3', 'ප්‍රමාණ: S, M, L, XL', 'රු.3500 - රු.5000')," +
                "('සාමාන්‍ය ගවුම', 'දිනපතා ඇඳීමට සුදුසු සුවපහසු ගවුමකි.', 'pic_ladydress4', 'ප්‍රමාණ: M, L, XL', 'රු.2000 - රු.3000')," +
                "('Maxi ගවුම', 'සෑම අවස්ථාවකටම සුදුසු නවහස සහ අලංකාර මැක්සි ගවුමකි.', 'pic_ladydress5', 'ප්‍රමාණ: S, M, L, XL', 'රු.3000 - රු.4500')," +
                "('Wrap ගවුම', 'අනුකූල කළ හැකි ඉරියව් සහිත ව්‍රැප් ගවුමකි.', 'pic_ladydress6', 'ප්‍රමාණ: XS, S, M, L', 'රු.2800 - රු.3800')," +
                "('Bodycon ගවුම', 'රාත්‍රී අවස්ථා සඳහා සුදුසු ටයිට්-ෆිට් ගවුමකි.', 'pic_ladydress7', 'ප්‍රමාණ: S, M, L', 'රු.3200 - රු.4200')," +
                "('Shift ගවුම', 'බහුකාර්ය භාවයට සුදුසු සෘජු ඇඳුම් ආකාරයකි.', 'pic_ladydress8', 'ප්‍රමාණ: S, M, L, XL', 'රු.3000 - රු.4000')," +
                "('Sheath ගවුම', 'කාර්ය මණ්ඩල හෝ උත්සව සඳහා සුදුසු අලංකාර ගවුමකි.', 'pic_ladydress9', 'ප්‍රමාණ: S, M, L', 'රු.3300 - රු.4700')," +
                "('Sun ගවුම', 'උණුසුම් දිනවලට සුදුසු සැහැල්ලු සහ වර්ණවත් ගවුමකි.', 'pic_ladydress10', 'ප්‍රමාණ: XS, S, M', 'රු.2200 - රු.3400')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
