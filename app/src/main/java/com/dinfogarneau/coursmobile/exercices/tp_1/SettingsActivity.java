package com.dinfogarneau.coursmobile.exercices.tp_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public String nom_utilisateur;
    private EditText et_nom_utilisateur;
    private TextView tv_meilleur_temps;
    private Button btn_reset;
    private ImageButton btn_partager;
    private Button btn_sauv_et_quitter;
    private long meilleur_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        et_nom_utilisateur = findViewById(R.id.et_nom_utilisateur);
        tv_meilleur_temps = findViewById(R.id.tv_meilleur_temps);
        btn_sauv_et_quitter = findViewById(R.id.btn_sauv_et_quitter);
        btn_reset = findViewById(R.id.btn_renitialiser_meilleur_temps);
        btn_partager = findViewById(R.id.btn_partager);

        nom_utilisateur = intent.getStringExtra(JeuActivity.NOM_UTILISATEUR);
        meilleur_score = intent.getLongExtra(JeuActivity.MEILLEUR_SCORE, 0);

        tv_meilleur_temps.setText(meilleur_score + "");
        et_nom_utilisateur.setText(nom_utilisateur);

        btn_partager.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_sauv_et_quitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_renitialiser_meilleur_temps:
                meilleur_score = 0;
                tv_meilleur_temps.setText(meilleur_score + "");
                break;
            case R.id.btn_sauv_et_quitter:
                Intent intent = new Intent();

                nom_utilisateur = et_nom_utilisateur.getText().toString().trim();
                if (nom_utilisateur != null && nom_utilisateur != "") {
                    intent.putExtra(JeuActivity.NOM_UTILISATEUR, nom_utilisateur);
                }

                intent.putExtra(JeuActivity.MEILLEUR_SCORE, meilleur_score);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_partager:
                Intent intentPartager = new Intent(Intent.ACTION_SEND);
                intentPartager.setType("text/plain");
                intentPartager.putExtra(Intent.EXTRA_SUBJECT, "Sujet");
                intentPartager.putExtra(Intent.EXTRA_TEXT, "Voici mon meilleur score : " + meilleur_score);
                startActivity(intentPartager);
                break;

        }
    }

}