package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

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

                String Email = EmailTxt.getText().toString();
                String Password = PasswordTxt.getText().toString();
                String Name = NameTxt.getText().toString();
                String SchoolId = SchoolIDTxt.getText().toString();
                Boolean Propedeuse = PropedeuseSwt.isChecked();
                String Specialisatie = dropdown.getSelectedItem().toString();

                if (!EmailTxt.getText().toString().matches("")|| !PasswordTxt.getText().toString().matches("")) {
                    mAuth.createUserWithEmailAndPassword(Email, Password);
                    Log.d("Completion", "User Aangemaakt");

                    if(!NameTxt.getText().toString().matches("")|| !SchoolIDTxt.getText().toString().matches("")){
                        mDatabase.child("Users").child(Name).child("Name").setValue(Name);
                        mDatabase.child("Users").child(Name).child("ID").setValue(SchoolId);
                        mDatabase.child("Users").child(Name).child("Email").setValue(Email);
                        mDatabase.child("Users").child(Name).child("Propedeuse").setValue(Propedeuse);
                        mDatabase.child("Users").child(Name).child("Specialisatie").setValue(Specialisatie);
                        Log.d("Completion", "User in Database gezet");

                    } else{
                        Log.d("Foutcode", "Naam od ID niet ingevuld");
                    }

                } else {
                    Log.d("Foutcode", "Email of Wachtwoord is leeg" + Email + " " + Password);
                }
            }
        });
    }
}
