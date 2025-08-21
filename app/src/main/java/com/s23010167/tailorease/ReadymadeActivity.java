package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ReadymadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readymade); // Set layout for ReadymadeActivity

        // Find ImageButtons by ID
        ImageButton trousers = findViewById(R.id.imageButton8); // Trousers category
        ImageButton tshirt = findViewById(R.id.imageButton9);   // T-Shirts category
        ImageButton ladyDress = findViewById(R.id.imageButton10); // Lady Dress category
        ImageButton blazer = findViewById(R.id.imageButton11);   // Blazers category
        ImageButton kidsDress = findViewById(R.id.imageButton12); // Kids Dress category
        ImageButton bags = findViewById(R.id.imageButton13);     // Bags category

        // Set click listeners to open respective activities
        trousers.setOnClickListener(v ->
                startActivity(new Intent(this, TrousersActivity.class))); // Open TrousersActivity

        tshirt.setOnClickListener(v ->
                startActivity(new Intent(this, TShirtActivity.class)));   // Open TShirtActivity

        ladyDress.setOnClickListener(v ->
                startActivity(new Intent(this, LadyDressActivity.class))); // Open LadyDressActivity

        blazer.setOnClickListener(v ->
                startActivity(new Intent(this, BlazerActivity.class)));    // Open BlazerActivity

        kidsDress.setOnClickListener(v ->
                startActivity(new Intent(this, KidDressActivity.class)));  // Open KidDressActivity

        bags.setOnClickListener(v ->
                startActivity(new Intent(this, BagActivity.class)));       // Open BagActivity
    }
}
