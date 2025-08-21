package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrousersDatabaseHelper_si extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "trousers_si.db"; // Sinhala DB name
    private static final int DATABASE_VERSION = 3; // Keep same version as English DB

    // Table and column names
    public static final String TABLE_NAME = "trousers";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_MEASUREMENTS = "measurements";
    public static final String COL_PRICE = "price";

    // Constructor
    public TrousersDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Call SQLiteOpenHelper constructor
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 👉 Create trousers table with price
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable); // Execute table creation

        // 👉 Insert sample Sinhala trousers data with measurements and price
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                "('Chino කලිසම්', 'නවීන හැඩයට ගැලපෙන බහුලව භාවිතා කළ හැකි විලාසිතාවකි. කාර්යාල හෝ දෛනික ඇඳුමකට සුදුසුයි.', 'pic_trouser1', 'ඉණ: 28-38 අඟල් | දිග: 40 අඟල්', 'රු.1800 - රු.2500')," +
                "('Cargo කලිසම්', 'බහු පොකට් සහිත තදබදයට සහනාධාරකයි. බාහිර ක්‍රියාකාරකම් හෝ වීදි විලාසිතාවට සුදුසුයි.', 'pic_trouser2', 'ඉණ: 30-42 අඟල් | ගැළපෙන පතුල් කොටස්', 'රු.2000 - රු.3000')," +
                "('Formal කලිසම්', 'වෘත්තීය අවස්ථා සහ උත්සව සඳහා නිවැරදිව හැඩගැන්වූ විලාසිතාවකි.', 'pic_trouser3', 'ඉණ: 28-36 අඟල් | දිග: 38-42 අඟල්', 'රු.2200 - රු.3500')," +
                "('Jogger කලිසම්', 'ඇද හැලෙන පතුල සහිත සෙරිනිටි ඇඳුමකි. ව්‍යායාම හෝ සරල ඇඳුමකට සුදුසුයි.', 'pic_trouser4', 'ඉණ: 28-40 අඟල් | ඇඳ හැලෙන රෙදි', 'රු.1500 - රු.2200')," +
                "('Slim Fit කලිසම්', 'සිරුණු හා නවීන හැඩය. නවීන ෆෝමාල් හෝ අර්ධ ෆෝමාල් ඇඳුමකට සුදුසුයි.', 'pic_trouser5', 'ඉණ: 28-34 අඟල් | ස්ලිම් කට් පතුල', 'රු.2000 - රු.2800')," +
                "('Wide-Leg කලිසම්', 'ඉතා විශාල හැඩය සහ පහසුව. විවේකීය ඇඳුම් සඳහා විලාසිතාවක්.', 'pic_trouser6', 'ඉණ: 30-44 අඟල් | අතිරේක පතුල් ඉඩ', 'රු.2400 - රු.3200')," +
                "('Pleated කලිසම්', 'පෙර පලීට් සහිත සම්ප්‍රදායික හැඩය. සාම්ප්‍රදායික හෝ ෆෝමාල් ඇඳුමකට වඩාත් යෝග්‍යයි.', 'pic_trouser7', 'ඉණ: 28-40 අඟල් | පෙර පලීට්', 'රු.2100 - රු.3000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 👉 Drop table if exists to handle upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Recreate table with new schema
    }
}
