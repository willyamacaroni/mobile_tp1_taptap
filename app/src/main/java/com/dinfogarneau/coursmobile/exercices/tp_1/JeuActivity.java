package com.dinfogarneau.coursmobile.exercices.tp_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JeuActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables utilisés pour la sauvegarde de données
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MEILLEUR_SCORE = "meilleur_score";
    public static final String NOM_UTILISATEUR = "nom_utilisateur";
    public static final String TXT_BTN1 = "txt_btn1";
    public static final String TXT_BTN2 = "txt_btn2";
    public static final String TXT_BTN3 = "txt_btn3";
    public static final String TXT_BTN4 = "txt_btn4";
    public static final String CLICKED_BTN1 = "clicked_btn1";
    public static final String CLICKED_BTN2 = "clicked_btn2";
    public static final String CLICKED_BTN3 = "clicked_btn3";
    public static final String CLICKED_BTN4 = "clicked_btn4";
    public static final String TEMPS_INITIAL = "temps_initial";

    private final int REQUEST_CODE = 1234;

    private final Context context = this;
    boolean btn_jeu1_clicked;
    boolean btn_jeu2_clicked;
    boolean btn_jeu3_clicked;
    boolean btn_jeu4_clicked;
    private Button btn_settings;
    private Button btn_go;
    private TextView tv_username;
    private TextView tv_meilleur_score;
    private Button btn_jeu1;
    private Button btn_jeu2;
    private Button btn_jeu3;
    private Button btn_jeu4;
    private String nom_utilisateur;
    private long temps_initial;
    private long meilleur_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        btn_settings = findViewById(R.id.btn_settings);
        btn_go = findViewById(R.id.btn_go);
        btn_jeu1 = findViewById(R.id.btn_jeu_1);
        btn_jeu2 = findViewById(R.id.btn_jeu_2);
        btn_jeu3 = findViewById(R.id.btn_jeu_3);
        btn_jeu4 = findViewById(R.id.btn_jeu_4);
        tv_meilleur_score = findViewById(R.id.tv_meilleur_score);
        reset_jeu();

        //Si une instance existe, prend les données gardé dans celle-ci.
        if (savedInstanceState != null) {
            meilleur_score = savedInstanceState.getLong(MEILLEUR_SCORE);
            nom_utilisateur = savedInstanceState.getString(NOM_UTILISATEUR);
            if (savedInstanceState.getBoolean(CLICKED_BTN1, false)) {
                btn_jeu1_clicked = savedInstanceState.getBoolean(CLICKED_BTN1, false);
                btn_jeu1.setAlpha((float) 0.5);
                btn_jeu1.setText(savedInstanceState.getString(TXT_BTN1, "1"));
                temps_initial = savedInstanceState.getLong(TEMPS_INITIAL, 0);
                if (savedInstanceState.getBoolean(CLICKED_BTN2, false)) {
                    btn_jeu2_clicked = savedInstanceState.getBoolean(CLICKED_BTN2, false);
                    btn_jeu2.setAlpha((float) 0.5);
                    btn_jeu2.setText(savedInstanceState.getString(TXT_BTN2, "2"));
                    if (savedInstanceState.getBoolean(CLICKED_BTN3, false)) {
                        btn_jeu3_clicked = savedInstanceState.getBoolean(CLICKED_BTN3, false);
                        btn_jeu3.setAlpha((float) 0.5);
                        btn_jeu3.setText(savedInstanceState.getString(TXT_BTN3, "3"));
                        if (savedInstanceState.getBoolean(CLICKED_BTN4, false)) {
                            btn_jeu4_clicked = savedInstanceState.getBoolean(CLICKED_BTN4, false);
                            btn_jeu4.setAlpha((float) 0.5);
                            btn_jeu4.setText(savedInstanceState.getString(TXT_BTN4, "2"));
                        }
                    }
                }
            }
        } else {
            chargerDonnees();
        }

        //Si l'utilisateur vient de l'activité pour se connecter.
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getStringExtra(NOM_UTILISATEUR) != null) {
                nom_utilisateur = intent.getStringExtra(NOM_UTILISATEUR);
            }
        }

        btn_settings.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        btn_jeu1.setOnClickListener(this);
        btn_jeu2.setOnClickListener(this);
        btn_jeu3.setOnClickListener(this);
        btn_jeu4.setOnClickListener(this);

        set_nom_utilisateur(nom_utilisateur);

        mettre_a_jour_tv_meilleur_score();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_settings:
                Intent intent = new Intent(context, SettingsActivity.class);
                intent.putExtra(NOM_UTILISATEUR, nom_utilisateur);
                intent.putExtra(MEILLEUR_SCORE, meilleur_score);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_go:
                reset_jeu();
                break;
            case R.id.btn_jeu_1:
                if (!btn_jeu1_clicked) {
                    temps_initial = System.currentTimeMillis();
                    btn_jeu1_clicked = true;
                    btn_jeu1.setText("Go!");
                    btn_jeu1.setAlpha((float) 0.5);
                }
                break;
            case R.id.btn_jeu_2:
                if (btn_jeu1_clicked && !btn_jeu2_clicked) {
                    long temps_btn2 = System.currentTimeMillis();
                    btn_jeu2.setText(temps_btn2 - temps_initial + "ms");
                    btn_jeu2.setAlpha((float) 0.5);
                    btn_jeu2_clicked = true;
                }
                break;
            case R.id.btn_jeu_3:
                if (btn_jeu2_clicked && !btn_jeu3_clicked) {
                    long temps_btn3 = System.currentTimeMillis();
                    btn_jeu3.setText(temps_btn3 - temps_initial + "ms");
                    btn_jeu3.setAlpha((float) 0.5);
                    btn_jeu3_clicked = true;
                }
                break;
            case R.id.btn_jeu_4:
                if (btn_jeu3_clicked && !btn_jeu4_clicked) {
                    long temps_btn4 = System.currentTimeMillis();
                    btn_jeu4.setText(temps_btn4 - temps_initial + "ms");
                    btn_jeu4.setAlpha((float) 0.5);
                    btn_jeu4_clicked = true;
                    finir_jeu(temps_btn4 - temps_initial);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sauvegarderDonnees();
    }

    //Sauvegarde le meilleur score, le nom d'utilisateur et l'état des boutons
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MEILLEUR_SCORE, meilleur_score);

        outState.putString(NOM_UTILISATEUR, nom_utilisateur);
        if (btn_jeu1_clicked) {
            outState.putBoolean(CLICKED_BTN1, btn_jeu1_clicked);
            outState.putString(TXT_BTN1, btn_jeu1.getText().toString());
            outState.putLong(TEMPS_INITIAL, temps_initial);
            if (btn_jeu2_clicked) {
                outState.putBoolean(CLICKED_BTN2, btn_jeu2_clicked);
                outState.putString(TXT_BTN2, btn_jeu2.getText().toString());
                if (btn_jeu3_clicked) {
                    outState.putBoolean(CLICKED_BTN3, btn_jeu3_clicked);
                    outState.putString(TXT_BTN3, btn_jeu3.getText().toString());
                    if (btn_jeu4_clicked) {
                        outState.putBoolean(CLICKED_BTN4, btn_jeu4_clicked);
                        outState.putString(TXT_BTN4, btn_jeu4.getText().toString());
                    }
                }
            }
        }

    }

    //Lorsque l'utilisateur revient de l'activité paramètres et qu'il à sauvegarder et quitter.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data.getStringExtra(NOM_UTILISATEUR) != null && data.getStringExtra(NOM_UTILISATEUR).trim() != "") {
                nom_utilisateur = data.getStringExtra(NOM_UTILISATEUR);
            }
            meilleur_score = data.getLongExtra(MEILLEUR_SCORE, 0);

            mettre_a_jour_tv_meilleur_score();
            set_nom_utilisateur(nom_utilisateur);
        }
    }

    /**
     * met à jour le nom d'utilisateur. Prend en paramètre un nom d'utilisateur
     *
     * @param nom_utilisateur le nouveau nom d'utilisateur.
     **/
    private void set_nom_utilisateur(String nom_utilisateur) {
        tv_username = findViewById(R.id.tv_nom_utilisateur);
        tv_username.setText("Joueur: " + nom_utilisateur);
    }

    /**
     * Remet les boutons à leur états initial.
     * couleur, si il a été clické et le text.
     **/
    private void reset_jeu() {
        btn_jeu1_clicked = false;
        btn_jeu2_clicked = false;
        btn_jeu3_clicked = false;
        btn_jeu4_clicked = false;
        btn_jeu1.setText("1");
        btn_jeu1.setAlpha((float) 1);
        btn_jeu2.setText("2");
        btn_jeu2.setAlpha((float) 1);
        btn_jeu3.setText("3");
        btn_jeu3.setAlpha((float) 1);
        btn_jeu4.setText("4");
        btn_jeu4.setAlpha((float) 1);
    }

    /**
     * finalise la partie que l'utilisateur vient de jouer.
     * Vérifie si le temps final est meilleur que son meilleur_score.
     * Vérifie également si le temps est entre 1s et 2s.
     * Démarre l'activity image si le dernier critaire à été remplie.
     *
     * @param temps_final Le temps final de la partie que l'utilisateur vient de compléter
     **/
    private void finir_jeu(long temps_final) {
        if (temps_final < meilleur_score || meilleur_score == 0) {
            meilleur_score = temps_final;
            mettre_a_jour_tv_meilleur_score();
            sauvegarderDonnees();
        }
        if (1000 < temps_final && temps_final < 2000) {
            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra("temps_final", temps_final);
            startActivity(intent);
        }
    }

    /**
     * met à jour le text du meilleur score
     **/
    private void mettre_a_jour_tv_meilleur_score() {
        if (meilleur_score == 0) {
            tv_meilleur_score.setText("Meilleur score:");
        } else {
            tv_meilleur_score.setText("Meilleur score: " + meilleur_score);
        }
    }

    /**
     * Sauvegarde les données pour les shared preferences
     **/
    public void sauvegarderDonnees() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editeur = sharedPreferences.edit();

        editeur.putLong(MEILLEUR_SCORE, meilleur_score);
        editeur.putString(NOM_UTILISATEUR, nom_utilisateur);

        editeur.apply();

    }

    /**
     * Charge les données pour les shared preferences
     **/
    public void chargerDonnees() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        meilleur_score = sharedPreferences.getLong(MEILLEUR_SCORE, 0);
        nom_utilisateur = sharedPreferences.getString(NOM_UTILISATEUR, "");
        mettre_a_jour_tv_meilleur_score();
        set_nom_utilisateur(nom_utilisateur);

        reset_jeu();
    }

}
