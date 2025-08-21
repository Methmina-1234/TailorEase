package com.s23010167.tailorease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TShirtsDatabaseHelper_si extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "tops_si.db";
    private static final int DATABASE_VERSION = 2; // Version bump for schema changes

    // Table and column names
    public static final String TABLE_NAME = "tops";         // Table name
    public static final String COL_ID = "id";               // Primary key
    public static final String COL_CATEGORY = "category";   // "T-Shirt" or "Shirt"
    public static final String COL_NAME = "name";           // Item name
    public static final String COL_DESCRIPTION = "description"; // Description in Sinhala
    public static final String COL_IMAGE = "image";         // Drawable resource name
    public static final String COL_MEASUREMENTS = "measurements"; // Size info
    public static final String COL_PRICE = "price";         // Price in LKR

    // Constructor
    public TShirtsDatabaseHelper_si(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_MEASUREMENTS + " TEXT, " +
                COL_PRICE + " TEXT)";
        db.execSQL(createTable);

        // Insert initial Sinhala items with measurements and prices
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COL_CATEGORY + ", " + COL_NAME + ", " + COL_DESCRIPTION + ", " + COL_IMAGE + ", " + COL_MEASUREMENTS + ", " + COL_PRICE + ") VALUES " +

                // T-Shirt items
                "('T-Shirt', 'Basic ටී-ශර්ට්', 'සෑම දිනකම පැළඳිය හැකි සරල කපු ටී-ශර්ට්.', 'pic_tshirt1', 'උරහිස: 36-44 අඟල් | දිග: 26-30 අඟල්', 'රු.1000')," +
                "('T-Shirt', 'Graphic ටී-ශර්ට්', 'මුද්‍රිත ආකෘති සහිත නවීන විලාසිතාවක්.', 'pic_tshirt2', 'උරහිස: 36-46 අඟල් | සාමාන්‍ය හැඩය', 'රු.1500')," +
                "('T-Shirt', 'Oversized ටී-ශර්ට්', 'ඇලී නොසිටි විවේකීය හැඩය.', 'pic_tshirt3', 'උරහිස: 40-50 අඟල් | දිග වැඩි', 'රු.1800')," +
                "('T-Shirt', 'Polo ටී-ශර්ට්', 'අර්ධ සාමාන්‍ය අවස්ථාවන් සඳහා සුදුසු කොලු සහිත ටී-ශර්ට්.', 'pic_tshirt4', 'උරහිස: 38-46 අඟල් | කොලු රෙදි', 'රු.2000')," +
                "('T-Shirt', 'V-Neck ටී-ශර්ට්', 'වී-ඇට සහිත නවීන විලාසිතාවක්.', 'pic_tshirt5', 'උරහිස: 36-44 අඟල් | සිරුණු හැඩය', 'රු.1700')," +

                // Shirt items
                "('Shirt', 'Formal කමිස', 'කාර්යාලය සහ උත්සව සඳහා පරිපූර්ණයි.', 'pic_shirt1', 'උරහිස: 38-46 අඟල් | අත දිග: 32-36 අඟල්', 'රු.2500')," +
                "('Shirt', 'Casual කමිස', 'දිනපතා පැළඳිය හැකි මෘදු රෙදි.', 'pic_shirt2', 'උරහිස: 36-44 අඟල් | ලිහිල් හැඩය', 'රු.2200')," +
                "('Shirt', 'Denim කමිස', 'දැඩි සහ විලාසිතාවෙන් පිරුණු ඩෙනිම් කොට්ටය.', 'pic_shirt3', 'උරහිස: 38-48 අඟල් | බොත්තම්', 'රු.2400')," +
                "('Shirt', 'Linen කමිස', 'උණුසුම් දින සඳහා පරිපූර්ණ සෙරිනිටි රෙදි.', 'pic_shirt4', 'උරහිස: 36-44 අඟල් | සිරුණු හැඩය', 'රු.2300')," +
                "('Shirt', 'Checkered කමිස', 'සාම්ප්‍රදායික චෙක් අඳුන.', 'pic_shirt5', 'උරහිස: 38-46 අඟල් | දිග අත', 'රු.2100')," +

                // More T-Shirt items
                "('T-Shirt', 'Henley ටී-ශර්ට්', 'බොත්තම් සහිත වටාපිටිය. සරල හැඩය.', 'pic_tshirt6', 'උරහිස: 36-44 අඟල් | මෘදු රෙදි මිශ්‍රණය', 'රු.1600')," +
                "('T-Shirt', 'Muscle Fit ටී-ශර්ට්', 'ආකෘතිය දැක්විය හැකි තද හැඩය.', 'pic_tshirt7', 'උරහිස: 34-42 අඟල් | ඇඳ හැලෙන රෙදි', 'රු.1900')," +
                "('T-Shirt', 'Ringer ටී-ශර්ට්', 'අත් සහ කොලු වල විෂම පාට මාදිලි.', 'pic_tshirt8', 'උරහිස: 36-44 අඟල් | පැරණි විලාසය', 'රු.1400')," +
                "('T-Shirt', 'Longline ටී-ශර්ට්', 'දිග වැඩි නවීන නගරීය විලාසය.', 'pic_tshirt9', 'උරහිස: 38-46 අඟල් | දිග වැඩි', 'රු.1800')," +
                "('T-Shirt', 'Pocket ටී-ශර්ට්', 'උරහිස පොකට් එකක් සහිත විලාසිතාවක්.', 'pic_tshirt10', 'උරහිස: 36-44 අඟල් | උපයෝගී හැඩය', 'රු.1500')," +

                // More Shirt items
                "('Shirt', 'Oxford කමිස', 'ව්‍යාපාරික සහ සාමාන්‍ය අවස්ථාවන් සඳහා සුදුසුයි.', 'pic_shirt6', 'උරහිස: 38-46 අඟල් | බොත්තම් සහිත කොලු', 'රු.2600')," +
                "('Shirt', 'Mandarin Collar කමිස', 'නවීන පෙනුමක් සහිත ඉහළ කොලු.', 'pic_shirt7', 'උරහිස: 36-44 අඟල් | කෙටි අත', 'රු.2300')," +
                "('Shirt', 'Flannel කමිස', 'සීතල කාලය සඳහා උණුසුම් හා මෘදු.', 'pic_shirt8', 'උරහිස: 40-50 අඟල් | තද කපු රෙදි', 'රු.2800')," +
                "('Shirt', 'Hawaiian කමිස', 'මුද්‍රිත ආකෘති සහිත වෙරළ විලාසය.', 'pic_shirt9', 'උරහිස: 38-46 අඟල් | සෙරිනිටි රෙදි', 'රු.2200')," +
                "('Shirt', 'Slim Fit කමිස', 'තද හැඩයට ගැලපෙන නවීන පෙනුම.', 'pic_shirt10', 'උරහිස: 36-42 අඟල් | ලඟින් කපන හැඩය', 'රු.2500')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple upgrade: drop and recreate table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
