package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //Firebase Authenticatie
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


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

                if (!TextUtils.isEmpty(Email)|| !TextUtils.isEmpty(Password)) {
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                                Intent GoToKeuzevakkenScrem = new Intent(MainActivity.this, KeuzenvakkenScherm.class);
                                startActivity(GoToKeuzevakkenScrem);
                            } else {
                                Toast.makeText(MainActivity.this, "There was a sign in problem", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
