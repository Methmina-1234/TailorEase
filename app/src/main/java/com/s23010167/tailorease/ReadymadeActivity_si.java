package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ReadymadeActivity_si extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_readymade);

        ImageButton trousers = findViewById(R.id.imageButton8);
        ImageButton tshirt = findViewById(R.id.imageButton9);
        ImageButton ladyDress = findViewById(R.id.imageButton10);
        ImageButton blazer = findViewById(R.id.imageButton11);
        ImageButton kidsDress = findViewById(R.id.imageButton12);
        ImageButton bags = findViewById(R.id.imageButton13);

        trousers.setOnClickListener(v ->
                startActivity(new Intent(this, TrousersActivity_si.class)));

        tshirt.setOnClickListener(v ->
                startActivity(new Intent(this, TShirtActivity_si.class)));

        ladyDress.setOnClickListener(v ->
                startActivity(new Intent(this, LadyDressActivity_si.class)));

        blazer.setOnClickListener(v ->
                startActivity(new Intent(this, BlazerActivity_si.class)));

        kidsDress.setOnClickListener(v ->
                startActivity(new Intent(this, KidDressActivity_si.class)));

        bags.setOnClickListener(v ->
                startActivity(new Intent(this, BagActivity_si.class)));
    }
}
