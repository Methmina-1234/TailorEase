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

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private LinearLayout orderContainer; // Container for dynamically added order items
    private OrdersDatabaseHelper ordersDb; // SQLite database helper for orders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderContainer = findViewById(R.id.orderContainer);
        ordersDb = new OrdersDatabaseHelper(this);

        loadOrders(); // Load orders from the database
    }

    private void loadOrders() {
        SQLiteDatabase db = ordersDb.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query all orders from the database
            cursor = db.rawQuery("SELECT * FROM " + OrdersDatabaseHelper.TABLE_NAME, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Get order details from the cursor
                    int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper.COL_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper.COL_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper.COL_DESCRIPTION));
                    String imageResName = cursor.getString(cursor.getColumnIndexOrThrow(OrdersDatabaseHelper.COL_IMAGE));

                    // Get drawable resource ID from name
                    int imageResId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                    if (imageResId == 0) imageResId = android.R.drawable.ic_menu_report_image;

                    // Inflate order box layout
                    View orderBox = LayoutInflater.from(this).inflate(R.layout.order_box, orderContainer, false);

                    ImageView image = orderBox.findViewById(R.id.imageViewOrder);
                    TextView title = orderBox.findViewById(R.id.textViewOrderTitle);
                    TextView desc = orderBox.findViewById(R.id.textViewOrderDescription);
                    Button orderNowBtn = orderBox.findViewById(R.id.buttonOrderNow);
                    Button cancelBtn = orderBox.findViewById(R.id.buttonCancel);

                    // Set values to UI elements
                    image.setImageResource(imageResId);
                    title.setText(name);
                    desc.setText(description);

                    // Order now button opens WhatsApp with pre-filled message
                    orderNowBtn.setOnClickListener(v -> {
                        String phoneNumber = "94740224207"; // WhatsApp number without "+"
                        String message = "👋 Hello TailorEase Team,\n\n" +
                                "I'd like to place an order with the following details:\n\n" +
                                "🧵 *Product*: " + name + "\n" +
                                "📋 *Description*: " + description + "\n" +
                                "🔢 *Quantity*: 1\n\n" +
                                "Please let me know the availability and estimated delivery time.\n\n" +
                                "Thank you! 🙏";

                        openWhatsApp(phoneNumber, message);
                    });

                    // Cancel button deletes order from database and removes the view
                    cancelBtn.setOnClickListener(v -> {
                        SQLiteDatabase writableDb = ordersDb.getWritableDatabase();
                        int rowsDeleted = writableDb.delete(OrdersDatabaseHelper.TABLE_NAME,
                                OrdersDatabaseHelper.COL_ID + "=?",
                                new String[]{String.valueOf(orderId)});
                        writableDb.close();

                        if (rowsDeleted > 0) {
                            orderContainer.removeView(orderBox);
                            Toast.makeText(this, "Order cancelled", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to cancel order", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add order box to the container
                    orderContainer.addView(orderBox);

                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading orders", e);
            Toast.makeText(this, "Failed to load orders", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // Open WhatsApp with a pre-filled message
    private void openWhatsApp(String phoneNumber, String message) {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not installed or cannot open", Toast.LENGTH_SHORT).show();
        }
    }
}
