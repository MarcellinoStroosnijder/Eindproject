package com.example.marcellino.keuzevakkenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.marcellino.keuzevakkenapp.Models.Vakken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KeuzenvakkenScherm extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference VakkenRef;
    private DatabaseReference UserRef;
    public  ArrayList<Vakken> VakkenLijst = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuzenvakken_scherm);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        VakkenRef = mDatabase.child("Vakken");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    VakkenLijst.add( new Vakken(
                        postSnapshot.child("Naam").getValue(),
                        postSnapshot.child("Moduleleider").getValue(),
                        postSnapshot.child("Richting").getValue(),
                        postSnapshot.child("ID").getValue(),
                        postSnapshot.child("EC").getValue(),
                        postSnapshot.child("Periode").getValue(),
                        postSnapshot.child("Plaatsen").getValue()
                    ));


                }
                for(int i = 0; i < VakkenLijst.size(); i++){
                    Log.d("vak", VakkenLijst.get(i).toString());
                }

                TextView text = findViewById(R.id.textView2);
                TextView text2 = findViewById(R.id.textView3);
                TextView text3 = findViewById(R.id.textView4);


                text.setText(VakkenLijst.get(0).toString());
                text2.setText(VakkenLijst.get(0).getEC().toString());
                text3.setText(VakkenLijst.get(0).getPeriode().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Foutcode", "Er is iets misgegaan met het ophalen van de data");
            }
        };

        VakkenRef.addValueEventListener(postListener);

    }
}
