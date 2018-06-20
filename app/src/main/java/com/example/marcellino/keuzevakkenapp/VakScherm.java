package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VakScherm extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private DatabaseReference InschrijvingRef;
    private DatabaseReference VakRef;

    private PieChart mChart;
    public static final int Leeg = 0;

    int plekken;
    int id;
    String periode;
    String Afkorting;


    ArrayList<Entry> yValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vak_scherm);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        InschrijvingRef = mDatabase.child("Inschrijvingen");
        VakRef = mDatabase.child("Vakken");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String UserId = user.getUid();

        final TextView NaamTxt = findViewById(R.id.textView2);
        final TextView ModuleleiderTxt = findViewById(R.id.textView4);
        final TextView SpecialisatieTxt = findViewById(R.id.textView3);
        final TextView PeriodeTxt = findViewById(R.id.textView5);
        final TextView PlaatsenTxt = findViewById(R.id.textView6);
        final Button InschrijvenBtn = findViewById(R.id.Inschrijven);

        mChart = findViewById(R.id.chart);
        mChart.setDescription(" description ");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("ID");
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Vakken").child(String.valueOf(value));
        }

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plekken = dataSnapshot.child("Plaatsen").getValue(Integer.class);
                String Moduleleider = dataSnapshot.child("Moduleleider").getValue().toString();
                periode = dataSnapshot.child("Periode").getValue().toString();
                String Naam = dataSnapshot.child("Naam").getValue().toString();
                String Richting = dataSnapshot.child("Richting").getValue().toString();
                Afkorting = dataSnapshot.child("Afkorting").getValue().toString();
                id = dataSnapshot.child("ID").getValue(Integer.class);


                NaamTxt.setText("Naam: " + Naam);
                ModuleleiderTxt.setText("Moduleleider: " + Moduleleider);
                SpecialisatieTxt.setText("Richting: " + Richting);
                PeriodeTxt.setText("Periode: " + periode);
                PlaatsenTxt.setText("Plekken: " + plekken);

                Log.d("test", "ik heb geluisterd");
                setData(0, plekken);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);

        InschrijvenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(VakScherm.this, popup.class);
               Bundle b = new Bundle();
               b.putString("UserID", UserId);
               b.putString("Periode", periode);
               b.putString("Afkorting", Afkorting);
               b.putInt("VakId", id);
               b.putInt("Plekken", plekken);
               intent.putExtras(b);
               startActivity(intent);

            }
        });

//        Button fab = findViewById(R.id.plusTweeTest);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (plekken > Leeg) {
//                    mDatabase.child("Plaatsen").setValue(plekken - 1);
//                    setData(0, plekken);
//                } else {
//                    Toast.makeText(VakScherm.this, "Keuzevak is vol", Toast.LENGTH_SHORT).show();
//                    setData(0, plekken);
//                }
//            }
//        });
    }

    private void setData(int aantal, int plekken) {
        aantal = 24;

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal - plekken, 0));
        xValues.add("Plekken ingenomen");

        yValues.add(new Entry(plekken, 1));
        xValues.add("Plekken Vrij");

        Log.d("test", String.valueOf(plekken));

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (plekken > 15) {
            colors.add(Color.rgb(67,160,71));
        } else if (plekken > 7){
            colors.add(Color.rgb(253,216,53));
        } else if  (plekken < 8) {
            colors.add(Color.rgb(255,0,0));
        }

        PieDataSet dataSet = new PieDataSet(yValues, "Plekken");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
    }
}
