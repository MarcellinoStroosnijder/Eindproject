package com.example.marcellino.keuzevakkenapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.marcellino.keuzevakkenapp.Models.Vakken;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class VakScherm extends AppCompatActivity {

    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vak_scherm);

        Bundle data;
        data = getIntent().getExtras();
        Vakken vak =  data.getParcelable("Object");

        Log.d("test", vak.getNaam().toString());

        mChart = findViewById(R.id.chart);
        mChart.setDescription(" description ");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(0);

        Button fab = findViewById(R.id.plusTweeTest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentEcts < MAX_ECTS) {
                    setData(currentEcts += 2);
                } else {
                    setData(currentEcts = 0);
                }
            }
        });
    }

    private void setData(int aantal) {
        currentEcts = aantal;
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(60 - currentEcts, 1));
        xValues.add("Resterende ECTS");

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.rgb(244,81,30));
        } else if (currentEcts < 40){
            colors.add(Color.rgb(235,0,0));
        } else if  (currentEcts < 50) {
            colors.add(Color.rgb(253,216,53));
        } else {
            colors.add(Color.rgb(67,160,71));
        }
        colors.add(Color.rgb(255,0,0));

        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", ""+currentEcts);
    }
}
