package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaterialDatabaseHelper_si extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "materials_si.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "materials";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_URL = "url";
    public static final String COL_PRICE_INCREASE = "price_increase";

    public MaterialDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT, " +
                        COL_DESCRIPTION + " TEXT, " +
                        COL_IMAGE + " TEXT, " +
                        COL_URL + " TEXT, " +
                        COL_PRICE_INCREASE + " REAL)";
        db.execSQL(CREATE_TABLE);

        // Insert Sinhala material data (example)
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_URL + ", " + COL_PRICE_INCREASE + ") VALUES " +

                "('කපු රෙදි', 'මෘදු, හුස්ම ගත හැකි සහ එදිනෙදා ඇඳුම් සඳහා පරිපූර්ණයි. එය සේදීමට පහසු සහ සමට මෘදුයි.', 'pic_14', 'https://en.wikipedia.org/wiki/Cotton', 0.0)," +
                "('ලිනන් රෙදි', 'සැහැල්ලු සහ සිසිල්, උණුසුම් දේශගුණයට වඩාත් සුදුසුය. ලිනන් රෙදි ස්වභාවික, ලිහිල් වයනයකින් යුක්ත වේ.', 'pic_15', 'https://en.wikipedia.org/wiki/Linen', 1500.0)," +
                "('සේද රෙදි', 'සිනිඳු, දිලිසෙන සහ සුඛෝපභෝගී. විධිමත් ඇඳුම් සහ සියුම් ඇඳුම් සඳහා විශිෂ්ටයි.', 'pic_16', 'https://en.wikipedia.org/wiki/Silk', 2500.0)," +
                "('ඩෙනිම් රෙදි', 'ජීන්ස් සඳහා ප්\u200Dරසිද්ධ ඝන, කල් පවතින රෙදි. රළු සහ අනියම් පෙනුමක් ලබා දෙයි.', 'pic_17', 'https://en.wikipedia.org/wiki/Denim', 800.0)," +
                "('පොලියෙස්ටර් රෙදි', 'රැළි වලට ඔරොත්තු දෙන සහ කල් පවතින. ඉක්මනින් වියළී යන අතර එහි හැඩය හොඳින් රඳවා ගනී.', 'pic_18', 'https://en.wikipedia.org/wiki/Polyester', 500.0)," +
                "('ලොම් රෙදි', 'උණුසුම් හා පරිවාරක, ශීත කාලය සඳහා පරිපූර්ණයි. ලොම් මෘදු වන අතර තාපය රඳවා ගැනීමට උපකාරී වේ.', 'pic_19', 'https://en.wikipedia.org/wiki/Wool', 2000.0)," +
                "('දැල් රෙදි', 'විවෘත වියමනක් සහිත විනිවිද පෙනෙන සහ සැහැල්ලුයි. බොහෝ විට ආවරණ සහ අලංකාර විලාසිතාවන්හි භාවිතා වේ.', 'pic_20', 'https://en.wikipedia.org/wiki/Net_(textile)', 1200.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
