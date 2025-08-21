package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BagDatabaseHelper_si extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "bags_si.db"; // Database file name
    private static final int DATABASE_VERSION = 3;            // Must increase if schema changes

    // Table and column names
    public static final String TABLE_NAME = "bags";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";               // Bag name (in Sinhala or English)
    public static final String COL_DESCRIPTION = "description"; // Description in Sinhala
    public static final String COL_IMAGE = "image";             // Image resource name
    public static final String COL_MEASUREMENTS = "measurements"; // Dimensions in Sinhala
    public static final String COL_CAPACITY = "capacity";       // Bag capacity (liters)
    public static final String COL_PRICE = "price";             // Price in rupees (Sinhala format)

    // Constructor
    public BagDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query for table creation
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Auto ID
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_CAPACITY + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        // Insert initial localized Sinhala data
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_CAPACITY + ", " + COL_PRICE + ") VALUES " +

                "('Leather Handbag', 'නිල සහ දෛනික භාවිතය සඳහා සුදුසු අලංකාර ලෙදර් බෑගයක්.', 'pic_bag1', 'උස: 25සෙම් | පළල: 30සෙම්', '8L', 'රු.2500 - රු.4000')," +
                "('Canvas Tote Bag', 'දිනපතා සාප්පු කිරීම් සඳහා සැහැල්ලු හා සාමාන්‍ය ටෝට් බෑගයක්.', 'pic_bag2', 'උස: 35සෙම් | පළල: 40සෙම්', '15L', 'රු.1500- රු.2500')," +
                "('Mini Backpack', 'සංචාර සඳහා සුදුසු නවීන මිනි පසුපෙළ බෑගයක්.', 'pic_bag3', 'උස: 30සෙම් | පළල: 20සෙම්', '10L', 'රු.1800 - රු.3000')," +
                "('Sling Bag', 'අනුකූල කළ හැකි තණ කලාප සහිත සංකීර්ණ ස්ලින්ග් බෑගයක්.', 'pic_bag4', 'උස: 20සෙම් | පළල: 15සෙම්', '5L', 'රු.1200 - රු.2000')," +
                "('Travel Duffel Bag', 'කෙටි සංචාර සඳහා විශාල සහ දිරුම්කම ඇති බෑගයක්.', 'pic_bag5', 'දිග: 50සෙම් | පළල: 25සෙම් | උස: 30සෙම්', '35L', 'රු.3000 - රු.5000')," +
                "('Crossbody Bag', 'දෛනික භාවිතයට විලාසිතාව සහ ආරක්ෂාව ඇති බෑගයක්.', 'pic_bag6', 'උස: 22සෙම් | පළල: 28සෙම්', '7L', 'රු.1800 - රු.2800')," +
                "('Clutch Bag', 'උත්සව සහ පාර්ටි සඳහා සුදුසු අලංකාර සන්ධ්‍යා ක්ලච් එකක්.', 'pic_bag7', 'උස: 15සෙම් | පළල: 25සෙම්', '2L', 'රු.1000 - රු.1500')," +
                "('Laptop Bag', 'ලැප්ටොප් ගෙන යාමේ වෘත්තීයමය සහ ආවරණ සහිත බෑගයක්.', 'pic_bag8', '15 අඟල් දක්වා ලැප්ටොප් සෙට් කළ හැක', '12L', 'රු.3500 - රු.6000')," +
                "('Bucket Bag', 'නවීන සහ විශාල ප්‍රමාණයක් සහිත, දොරක් ඇලූ බෑගයක්.', 'pic_bag9', 'උස: 30සෙම් | පළල: 25සෙම්', '14L', 'රු.2000 - රු.3500')," +
                "('Messenger Bag', 'පටිය සහිත සාම්ප්‍රදායික උරහිස මත පළවන බෑගයක්.', 'pic_bag10', 'උස: 28සෙම් | පළල: 35සෙම්', '13L', 'රු.2200 - රු.3800')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
