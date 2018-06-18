package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marcellino.keuzevakkenapp.Models.Vak;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class KeuzenvakkenScherm extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference VakkenRef;
    private DatabaseReference UserRef;
    private RecyclerView Vaklijst ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuzenvakken_scherm);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Vaklijst = findViewById(R.id.Vaklijst);
        VakkenRef = FirebaseDatabase.getInstance().getReference().child("Vakken");
        mDatabase.keepSynced(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        Vaklijst.setHasFixedSize(true);
        Vaklijst.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Vak, VakViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Vak, VakViewHolder>(

                Vak.class,
                R.layout.vakkaart,
                VakViewHolder.class,
                VakkenRef) {


            @Override
            protected void populateViewHolder(final VakViewHolder viewHolder, final Vak model, int position) {

                viewHolder.setAfkorting(model.getAfkorting());
                viewHolder.setPeriode(model.getPeriode());
                viewHolder.setPlaatsen(model.getPlaatsen());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(KeuzenvakkenScherm.this, VakScherm.class);
                        Bundle b = new Bundle();
                        b.putString("Naam", model.getAfkorting()); 	// Your id
                        intent.putExtras(b); 	// Put your id to your next Intent
                        startActivity(intent);	// start
                    }
                });
            }
        };
        Vaklijst.setAdapter(firebaseRecyclerAdapter);

    }

    public static class VakViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button VakButton;


        public VakViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            VakButton = mView.findViewById(R.id.VakButton);
        }

        public void setPeriode(String Periode){
            TextView periode = mView.findViewById(R.id.Periode);
            periode.setText(Periode);
        }

        public void setPlaatsen(int Plaatsen){
            TextView plaatsen = mView.findViewById(R.id.Plaatsen);
            plaatsen.setText(String.valueOf(Plaatsen));
        }

        public void setAfkorting(String Afkorting){
            VakButton.setText(Afkorting);
        }

    }
}