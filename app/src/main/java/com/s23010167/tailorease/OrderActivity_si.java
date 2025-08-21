package com.s23010167.tailorease;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity_si extends AppCompatActivity {

    private static final String TAG = "OrderActivity_si"; // Tag for logging

    private LinearLayout orderContainer; // Container to hold order boxes dynamically
    private OrdersDatabaseHelper_si ordersDb; // SQLite helper for Sinhala orders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_order); // Set the Sinhala order layout

        orderContainer = findViewById(R.id.orderContainer); // Link container from layout
        ordersDb = new OrdersDatabaseHelper_si(this); // Initialize database helper

        loadOrders(); // Load all orders from the database
    }

    // Load orders from database and display dynamically
    private void loadOrders() {
        SQLiteDatabase db = ordersDb.getReadableDatabase(); // Open database in read mode
        Cursor cursor = null;
        try {
            // Query all orders from the orders table
            cursor = db.rawQuery("SELECT * FROM " + OrdersDatabaseHelper_si.TABLE_NAME, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve order data from the cursor
                    int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper_si.COL_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper_si.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper_si.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper_si.COL_IMAGE));

                    // Get image resource ID from its name in drawable
                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image; // fallback image

                    // Inflate the order box layout
                    View orderBox = LayoutInflater.from(this).inflate(R.layout.si_order_box, orderContainer, false);

                    // Link UI elements inside the order box
                    ImageView image = orderBox.findViewById(R.id.imageViewOrder);
                    TextView title = orderBox.findViewById(R.id.textViewOrderTitle);
                    TextView desc = orderBox.findViewById(R.id.textViewOrderDescription);
                    Button orderNowBtn = orderBox.findViewById(R.id.buttonOrderNow);
                    Button cancelBtn = orderBox.findViewById(R.id.buttonCancel);

                    // Set order data into UI elements
                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description);

                    // Set listener for "Order Now" button
                    orderNowBtn.setOnClickListener(v -> {
                        String phoneNumber = "94740224207"; // WhatsApp number
                        String message = "🙏 හෙලෝ TailorEase කණ්ඩායම,\n\n" +
                                "මම ඇනවුමක් දාන්න කැමතියි:\n\n" +
                                "🧵 *නිෂ්පාදනය*: " + name + "\n" +
                                "📋 *විස්තරය*: " + description + "\n" +
                                "🔢 *ප්‍රමාණය*: 1\n\n" +
                                "කරුණාකර ලබා ගැනීමේ හැකියාව සහ බෙදාහැරීමේ කාලය කියන්න.\n\n" +
                                "ඔබට ස්තුතියි! 🙏";

                        openWhatsApp(phoneNumber, message); // Open WhatsApp with pre-filled message
                    });

                    // Set listener for "Cancel" button
                    cancelBtn.setOnClickListener(v -> {
                        // Delete order from the database
                        SQLiteDatabase writableDb = ordersDb.getWritableDatabase();
                        int rowsDeleted = writableDb.delete(OrdersDatabaseHelper_si.TABLE_NAME,
                                OrdersDatabaseHelper_si.COL_ID + "=?",
                                new String[]{String.valueOf(orderId)});
                        writableDb.close();

                        // Remove order box from container if deleted
                        if (rowsDeleted > 0) {
                            orderContainer.removeView(orderBox);
                            Toast.makeText(this, "ඇනවුම අවලංගු විය", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "ඇනවුම අවලංගු කළ නොහැක", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add the order box to the container
                    orderContainer.addView(orderBox);

                } while (cursor.moveToNext()); // Move to next order
            } else {
                // No orders found
                Toast.makeText(this, "කිසිඳු ඇනවුමක් හමු නොවීය", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading orders", e);
            Toast.makeText(this, "ඇනවුම් පූරණය කිරීමට අසමත් විය", Toast.LENGTH_SHORT).show();
        } finally {
            // Close cursor and database
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // Function to open WhatsApp with pre-filled message
    private void openWhatsApp(String phoneNumber, String message) {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // Show message if WhatsApp is not installed
            Toast.makeText(this, "WhatsApp ස්ථාපනය කර නොමැත", Toast.LENGTH_SHORT).show();
        }
    }
}
