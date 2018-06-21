package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcellino.keuzevakkenapp.Models.Vak;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KeuzenvakkenScherm extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference VakkenRef;
    private DatabaseReference UserRef;
    private RecyclerView Vaklijst ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_items, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(KeuzenvakkenScherm.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                KeuzenvakkenScherm.this.finish();
            default:
                break;
        }
        return true;
    }


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

                viewHolder.VakButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(KeuzenvakkenScherm.this, VakScherm.class);
                        Bundle b = new Bundle();
                        b.putInt("ID", model.getID());
                        intent.putExtras(b);
                        startActivity(intent);
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