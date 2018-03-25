package com.hamoudi.j.classroomcommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView nomUtilisateur;
    private TextView motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nomUtilisateur = (EditText) findViewById(R.id.nom);
        motDePasse = (TextView) findViewById(R.id.mdp);
        nomUtilisateur.setText("Joe");
    }

    public void login(View view) {
        //Log.d("DEBUG : ", "Tu as cliqué !");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        /*if(nomUtilisateur.getText().toString().equals("Joe") && motDePasse.getText().toString().equals("abc")) {
            Toast.makeText(view.getContext(), "Tu es connecté", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("nom", nomUtilisateur.getText().toString());
            bundle.putString("mdp", motDePasse.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(view.getContext(), "Identifiants incorrects", Toast.LENGTH_SHORT).show();
        }*/

    }


}
