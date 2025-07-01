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
        setContentView(R.layout.activity_readymade);

        ImageButton trousers = findViewById(R.id.imageButton8);
        ImageButton tshirt = findViewById(R.id.imageButton9);
        ImageButton ladyDress = findViewById(R.id.imageButton10);
        ImageButton blazer = findViewById(R.id.imageButton11);
        ImageButton kidsDress = findViewById(R.id.imageButton12);
        ImageButton bags = findViewById(R.id.imageButton13);

        trousers.setOnClickListener(v ->
                startActivity(new Intent(this, TrousersActivity.class)));

        tshirt.setOnClickListener(v ->
                startActivity(new Intent(this, TShirtActivity.class)));

        ladyDress.setOnClickListener(v ->
                startActivity(new Intent(this, LadyDressActivity.class)));

        blazer.setOnClickListener(v ->
                startActivity(new Intent(this, BlazerActivity.class)));

        kidsDress.setOnClickListener(v ->
                startActivity(new Intent(this, KidsDressActivity.class)));

        bags.setOnClickListener(v ->
                startActivity(new Intent(this, BagsActivity.class)));
    }
}
