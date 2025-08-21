package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlazerDatabaseHelper_si extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "blazers_si.db"; // Sinhala blazer database file
    private static final int DATABASE_VERSION = 2; // Increment if schema changes

    // Table & column names
    public static final String TABLE_NAME = "blazers";
    public static final String COL_ID = "id";               // Primary key
    public static final String COL_NAME = "name";           // Blazer name (Sinhala)
    public static final String COL_DESCRIPTION = "description"; // Description (Sinhala)
    public static final String COL_IMAGE = "image";         // Drawable image name
    public static final String COL_MEASUREMENTS = "measurements"; // Measurements in Sinhala
    public static final String COL_PRICE = "price";         // Price (Sinhala format: රු.)

    public BlazerDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table schema
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        // Insert initial blazer data (Sinhala content)
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Classic Single-Breasted බ්ලේසර්', 'නිත්‍ය නවීන ආකෘතියක්, නිල හා අර්ධ නිල අවස්ථා සඳහා සුදුසුය.', 'pic_blazer1', 'උරහිස්: 38-46 අඟල් | දිග: 28-30 අඟල්', 'රු.4500')," +
                "('Double-Breasted බ්ලේසර්', 'අමතර බොත්තම් සහ පළල් කලාප ඇති උතුම් රූපය.', 'pic_blazer2', 'උරහිස්: 40-48 අඟල් | සකස් කළ හැකි හැඩය', 'රු.5500')," +
                "('Casual බ්ලේසර්', 'සැහැල්ලු හා බහුකාර්ය භාවය ඇති නිවැරදි අනියම් රූපයකි.', 'pic_blazer3', 'උරහිස්: 38-46 අඟල් | මෘදු රෙදි', 'රු.4000')," +
                "('Velvet බ්ලේසර්', 'සන්ධ්‍යා අවස්ථා සඳහා සුදුසු ආඩම්බර වැල්වට් මතුපිට.', 'pic_blazer4', 'උරහිස්: 38-44 අඟල් | ස්ලිම් හැඩය', 'රු.6000')," +
                "('Checked Pattern බ්ලේසර්', 'නාගරික පෙනුමක් සහිත සම්ප්‍රදායික චෙක් නිරූපණය.', 'pic_blazer5', 'උරහිස්: 38-46 අඟල් | සාමාන්‍ය හැඩය', 'රු.5000')," +
                "('Linen බ්ලේසර්', 'සෙරින හා සැහැල්ලු—ග්‍රිෂ්ම උත්සව සඳහා හොඳම තේරීම.', 'pic_blazer6', 'උරහිස්: 38-46 අඟල් | ස්වභාවික රෙදි', 'රු.4800')," +
                "('Wool Blend බ්ලේසර්', 'සීතල කාලයට සුදුසු උණුසුම් හා ව්‍යුහවත් හැඩය.', 'pic_blazer7', 'උරහිස්: 40-50 අඟල් | සීතල ඇඳුම්', 'රු.6500')," +
                "('Tweed බ්ලේසර්', 'ප්‍රාචීන ආකාරයේ විලාසිතාව සඳහා ගැළපෙන පටල සහිත බ්ලේසර්.', 'pic_blazer8', 'උරහිස්: 38-46 අඟල් | සම්ප්‍රදායික කැපුම', 'රු.5500')," +
                "('Slim Fit බ්ලේසර්', 'නවීන රූපයකට ගැළපෙන තදින් සකසා ඇති හැඩය.', 'pic_blazer9', 'උරහිස්: 36-44 අඟල් | ස්ලිම් පැතිකඩ', 'රු.5200')," +
                "('Peak Lapel බ්ලේසර්', 'විශේෂිත ලැපල් රටාවක් සහිත තද නිල පෙනුමක්.', 'pic_blazer10', 'උරහිස්: 40-48 අඟල් | තද කැපුම', 'රු.5800')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Remove old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate table and reinsert data
        onCreate(db);
    }
}
