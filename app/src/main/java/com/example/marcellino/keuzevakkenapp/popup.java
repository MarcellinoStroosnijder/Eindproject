package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class popup extends AppCompatActivity {

    private String UserId;
    private String Periode;
    private String Afkorting;
    private int VakId;
    private int Plekken;
    private Integer KeuzeEen;
    private Integer KeuzeTwee;

    private DatabaseReference mDatabase;
    private DatabaseReference InschrijvingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        InschrijvingRef = mDatabase.child("Inschrijvingen");

        final Button Keuze1 = findViewById(R.id.Keuze1);
        final Button Keuze2 = findViewById(R.id.Keuze2);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            UserId = b.getString("UserID");
            Periode = b.getString("Periode");
            VakId = b.getInt("VakId");
            Afkorting = b.getString("Afkorting");
            Plekken = b.getInt("Plekken");
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KeuzeEen = dataSnapshot.child(UserId).child(Periode).child("Keuze 1").child("VakId").getValue(Integer.class);
                KeuzeTwee = dataSnapshot.child(UserId).child(Periode).child("Keuze 2").child("VakId").getValue(Integer.class);

                Toast.makeText(popup.this, String.valueOf(KeuzeEen) + " " + String.valueOf(KeuzeTwee), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        InschrijvingRef.addValueEventListener(postListener);

        Keuze1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KeuzeEen == VakId || KeuzeTwee == VakId) {
                    Toast.makeText(popup.this , "Al ingeschreven voor dit vak!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(popup.this, KeuzenvakkenScherm.class);
                    startActivity(intent);
                } else{
                    InschrijvingRef.child(UserId).child(Periode).child("Keuze 1").child("VakId").setValue(VakId);
                    mDatabase.child("Vakken").child(Afkorting).child("Plaatsen").setValue(Plekken - 1);
                    Intent intent = new Intent(popup.this, KeuzenvakkenScherm.class);
                    Toast.makeText(popup.this , "Inschrijving voltooid", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

        Keuze2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KeuzeEen != VakId || KeuzeTwee != VakId) {
                    Toast.makeText(popup.this , "Al ingeschreven voor dit vak!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(popup.this, KeuzenvakkenScherm.class);
                    startActivity(intent);
                } else{
                    InschrijvingRef.child(UserId).child(Periode).child("1").child("VakId").setValue(VakId);
                    mDatabase.child("Vakken").child(Afkorting).child("Plaatsen").setValue(Plekken - 1);
                    Toast.makeText(popup.this , "Inschrijving voltooid", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(popup.this, KeuzenvakkenScherm.class);
                    startActivity(intent);
                }

            }
        });
    }
}
