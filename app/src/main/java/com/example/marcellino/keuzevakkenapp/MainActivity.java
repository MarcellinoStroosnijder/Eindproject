package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //Firebase Authenticatie
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Componenten
        final Button LogIn = findViewById(R.id.LogInBtn);
        final Button SignUp = findViewById(R.id.SignUpBtn);
        final TextView EmailTxt = findViewById(R.id.EmailTxt);
        final TextView PasswordTxt = findViewById(R.id.PasswordTxt);

        mAuth = FirebaseAuth.getInstance();

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = EmailTxt.getText().toString();
                String Password = PasswordTxt.getText().toString();

                if (Email != "" || Password != "") {
                    mAuth.signInWithEmailAndPassword(Email, Password);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user != null){
                        Log.d("User", user.getEmail());
                        Intent GoToKeuzevakkenScreen = new Intent(MainActivity.this, KeuzenvakkenScherm.class);
                        startActivity(GoToKeuzevakkenScreen);
                    } else {
                        Log.d("Foutcode", "Er is iets mis gegaan met het inloggen?");
                    }

                } else {
                    Log.d("Foutcode", "Email of Wachtwoord is leeg");
                }
            }


        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToSignUpPage = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(GoToSignUpPage);
            }
        });

    }
}
