package com.s23010167.tailorease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton; // ✅ Import this
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        // UI references
        Button btnOrder = findViewById(R.id.btnOrder);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView priceTextView = findViewById(R.id.priceTextView);
        ImageButton btnOpenCamera = findViewById(R.id.btnOpenCamera); // ✅ Camera button

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        RadioGroup sizeRadioGroup = findViewById(R.id.sizeRadioGroup);
        Spinner clothingTypeSpinner = findViewById(R.id.clothingTypeSpinner);
        Spinner materialSpinner = findViewById(R.id.materialSpinner);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // 👉 Order button - dial number
        btnOrder.setOnClickListener(v -> {
            String gender = "";
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            if (selectedGenderId == R.id.radioMale) gender = "Male";
            else if (selectedGenderId == R.id.radioFemale) gender = "Female";
            else if (selectedGenderId == R.id.radioKids) gender = "Kids";

            String size = "";
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            if (selectedSizeId == R.id.radioSmall) size = "Small";
            else if (selectedSizeId == R.id.radioMedium) size = "Medium";
            else if (selectedSizeId == R.id.radioLarge) size = "Large";
            else if (selectedSizeId == R.id.radioXL) size = "XL";
            else if (selectedSizeId == R.id.radioXXL) size = "XXL";

            String clothingType = clothingTypeSpinner.getSelectedItem().toString();
            String material = materialSpinner.getSelectedItem().toString();

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+94740224207"));
            startActivity(intent);
        });

        // 👉 Calculate Price Button
        btnCalculate.setOnClickListener(v -> {
            String gender = "";
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            if (selectedGenderId == R.id.radioMale) gender = "Male";
            else if (selectedGenderId == R.id.radioFemale) gender = "Female";
            else if (selectedGenderId == R.id.radioKids) gender = "Kids";

            String size = "";
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            if (selectedSizeId == R.id.radioSmall) size = "Small";
            else if (selectedSizeId == R.id.radioMedium) size = "Medium";
            else if (selectedSizeId == R.id.radioLarge) size = "Large";
            else if (selectedSizeId == R.id.radioXL) size = "XL";
            else if (selectedSizeId == R.id.radioXXL) size = "XXL";

            String type = clothingTypeSpinner.getSelectedItem().toString();
            String material = materialSpinner.getSelectedItem().toString();

            int price = dbHelper.getPrice(gender, type, size, material);

            if (price != -1) {
                priceTextView.setText("Estimated Price: Rs. " + price);
            } else {
                priceTextView.setText("No matching price found!");
            }
        });

        // 👉 Open Camera Button
        btnOpenCamera.setOnClickListener(v -> {
            Intent intent = new Intent(CustomizeActivity.this, CameraActivity.class);
            startActivity(intent);
        });
    }
}
