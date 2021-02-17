package com.dinfogarneau.coursmobile.exercices.tp_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    private TextView tv_temps_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        tv_temps_final = findViewById(R.id.tv_temps);

        Intent intent = getIntent();
        long temps_final = intent.getLongExtra("temps_final", 0);
        tv_temps_final.setText(temps_final + "ms");

    }
}