package com.s23010167.tailorease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeActivity_si extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply Sinhala locale before anything else
        Locale locale = new Locale("si");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else {
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_customize); // Load Sinhala layout

        // UI references
        Button btnOrder = findViewById(R.id.btnOrder);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView priceTextView = findViewById(R.id.priceTextView);
        ImageButton btnOpenCamera = findViewById(R.id.btnOpenCamera);

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        RadioGroup sizeRadioGroup = findViewById(R.id.sizeRadioGroup);
        Spinner clothingTypeSpinner = findViewById(R.id.clothingTypeSpinner);
        Spinner materialSpinner = findViewById(R.id.materialSpinner);

        DatabaseHelper_si dbHelper = new DatabaseHelper_si(this);

        // Order button - dial number
        btnOrder.setOnClickListener(v -> {
            // Get selected gender
            String gender = "";
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            if (selectedGenderId == R.id.radioMale) gender = "පුරුෂ";
            else if (selectedGenderId == R.id.radioFemale) gender = "ස්ත්‍රී";
            else if (selectedGenderId == R.id.radioKids) gender = "ළමුන්";

            // Get selected size
            String size = "";
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            if (selectedSizeId == R.id.radioSmall) size = "කුඩා";
            else if (selectedSizeId == R.id.radioMedium) size = "මධ්‍යම";
            else if (selectedSizeId == R.id.radioLarge) size = "විශාල";
            else if (selectedSizeId == R.id.radioXL) size = "XL";
            else if (selectedSizeId == R.id.radioXXL) size = "XXL";

            // Get clothing type and material (in Sinhala)
            String clothingType = clothingTypeSpinner.getSelectedItem().toString();
            String material = materialSpinner.getSelectedItem().toString();

            // Open phone dialer with TailorEase number
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+94740224207"));
            startActivity(intent);
        });

        // Calculate Price Button
        btnCalculate.setOnClickListener(v -> {
            // Get selections
            String gender = "";
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            if (selectedGenderId == R.id.radioMale) gender = "පුරුෂ";
            else if (selectedGenderId == R.id.radioFemale) gender = "ස්ත්‍රී";
            else if (selectedGenderId == R.id.radioKids) gender = "ළමුන්";

            String size = "";
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            if (selectedSizeId == R.id.radioSmall) size = "කුඩා";
            else if (selectedSizeId == R.id.radioMedium) size = "මධ්‍යම";
            else if (selectedSizeId == R.id.radioLarge) size = "විශාල";
            else if (selectedSizeId == R.id.radioXL) size = "XL";
            else if (selectedSizeId == R.id.radioXXL) size = "XXL";

            String type = clothingTypeSpinner.getSelectedItem().toString();
            String material = materialSpinner.getSelectedItem().toString();

            // Fetch price from Sinhala database
            int price = dbHelper.getPrice(gender, type, size, material);

            // Update priceTextView
            if (price != -1) {
                priceTextView.setText("ඇස්තමේන්තු මිල: රු. " + price);
            } else {
                priceTextView.setText("ගැලපෙන මිලක් හමු නොවීය!");
            }
        });

        // Open Camera Button
        btnOpenCamera.setOnClickListener(v -> {
            Intent intent = new Intent(CustomizeActivity_si.this, CameraActivity_si.class);
            startActivity(intent);
        });
    }
}
