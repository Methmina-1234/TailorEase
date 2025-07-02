package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KidDressDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kiddress.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "kiddress";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";

    public KidDressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT)";
        db.execSQL(createTable);

        // Insert sample kid dress items
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ") VALUES " +

                "('Party Frock', 'Colorful and sparkly frock perfect for birthdays and events.', 'pic_kiddress1', 'Ages: 2-8 yrs')," +
                "('Casual Play Dress', 'Soft and comfy dress for everyday playtime.', 'pic_kiddress2', 'Ages: 3-7 yrs')," +
                "('Traditional Dress', 'Ethnic style dress for cultural events and ceremonies.', 'pic_kiddress3', 'Ages: 4-10 yrs')," +
                "('Princess Gown', 'Elegant gown with layers and lace for special occasions.', 'pic_kiddress4', 'Ages: 5-10 yrs')," +
                "('Summer Sundress', 'Light and sleeveless dress ideal for warm weather.', 'pic_kiddress5', 'Ages: 2-6 yrs')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
