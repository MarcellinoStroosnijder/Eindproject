package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView NameTxt = findViewById(R.id.Name);
        final TextView SchoolIDTxt = findViewById(R.id.ID);
        final TextView EmailTxt = findViewById(R.id.EmailTxt);
        final TextView PasswordTxt = findViewById(R.id.PasswordTxt);
        final Button SignUpBtn = findViewById(R.id.SignUpBtn);
        final Switch PropedeuseSwt = findViewById(R.id.Propedeuse);
        final Spinner dropdown = findViewById(R.id.spinner1);


        String[] items = new String[]{"MT", "SE", "BDAM", "FICT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = EmailTxt.getText().toString();
                final String Password = PasswordTxt.getText().toString();
                final String Name = NameTxt.getText().toString();
                final String SchoolId = SchoolIDTxt.getText().toString();
                final Boolean Propedeuse = PropedeuseSwt.isChecked();
                final String Specialisatie = dropdown.getSelectedItem().toString();

                if (!EmailTxt.getText().toString().matches("")|| !PasswordTxt.getText().toString().matches("")) {
                    if (!NameTxt.getText().toString().matches("") || !SchoolIDTxt.getText().toString().matches("")) {
                        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    mDatabase.child("Users").child(mAuth.getUid()).child("Name").setValue(Name);
                                    mDatabase.child("Users").child(mAuth.getUid()).child("ID").setValue(SchoolId);
                                    mDatabase.child("Users").child(mAuth.getUid()).child("Email").setValue(Email);
                                    mDatabase.child("Users").child(mAuth.getUid()).child("Propedeuse").setValue(Propedeuse);
                                    mDatabase.child("Users").child(mAuth.getUid()).child("Specialisatie").setValue(Specialisatie);
                                    Log.d("Completion", "User in Database gezet");

                                    Intent GoToKeuzevakkenScherm = new Intent(SignUpActivity.this, KeuzenvakkenScherm.class);
                                    startActivity(GoToKeuzevakkenScherm);
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Naam of ID is leeg!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Email of Wachtwoord is leeg!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
