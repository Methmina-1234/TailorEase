package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrousersDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trousers.db";
    private static final int DATABASE_VERSION = 2; // ⬅️ bumped version to trigger onUpgrade

    public static final String TABLE_NAME = "trousers";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements"; // ⬅️ new column

    public TrousersDatabaseHelper(Context context) {
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

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ") VALUES " +

                "('Chino Pants', 'Versatile and casual with a modern fit. Great for office or daily wear.', 'pic_trouser1', 'Waist: 28-38 in | Length: 40 in')," +
                "('Cargo Pants', 'Durable with multiple pockets. Ideal for outdoor or street-style looks.', 'pic_trouser1', 'Waist: 30-42 in | Adjustable cuffs')," +
                "('Formal Trousers', 'Polished and tailored for professional settings and events.', 'pic_trouser1', 'Waist: 28-36 in | Length: 38-42 in')," +
                "('Jogger Pants', 'Comfortable with an elastic cuff. Great for workouts and casual wear.', 'pic_trouser1', 'Waist: 28-40 in | Stretchable fabric')," +
                "('Slim Fit Trousers', 'Snug and trendy fit. Suitable for modern formal or semi-formal looks.', 'pic_trouser1', 'Waist: 28-34 in | Slim leg cut')," +
                "('Wide-Leg Trousers', 'Loose fit offering great comfort and movement. Stylish for relaxed outfits.', 'pic_trouser1', 'Waist: 30-44 in | Extra leg room')," +
                "('Pleated Trousers', 'Classic design with front pleats. Often used in formal or vintage looks.', 'pic_trouser1', 'Waist: 28-40 in | Pleated front')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
