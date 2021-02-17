package com.dinfogarneau.coursmobile.exercices.tp_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText et_nom_login;
    EditText et_mdp_login;
    String nom_utilisateur = "bot";
    String mdp_utilisateur = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialisation des widgets
        btn_login = findViewById(R.id.btn_login);
        et_mdp_login = findViewById(R.id.et_mdp_login);
        et_nom_login = findViewById(R.id.et_nom_login);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Click du bouton pour se connecter
            case R.id.btn_login:
                String mdp_input = et_mdp_login.getText().toString();
                String nom_input = et_nom_login.getText().toString();

                //vérification du nom d'utilisateur et mot de passe
                if ((nom_input.equals(nom_utilisateur)) & (mdp_input.equals(mdp_utilisateur))) {
                    Intent intent = new Intent(getApplicationContext(), JeuActivity.class);
                    intent.putExtra(JeuActivity.NOM_UTILISATEUR, nom_input);
                    startActivity(intent);
                } else {
                    String message_erreur = "Erreur, mot de passe ou pseudo invalide.";
                    Toast toast_message_erreur = Toast.makeText(getApplicationContext(), message_erreur, Toast.LENGTH_SHORT);
                    toast_message_erreur.show();
                }
                break;
        }
    }
}