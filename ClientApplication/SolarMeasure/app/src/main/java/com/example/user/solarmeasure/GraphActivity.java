package com.example.user.solarmeasure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GraphActivity extends AppCompatActivity {

    boolean charge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        charge = intent.getBooleanExtra("charge", false);
        MyDBHandler dbHandler = new MyDBHandler(GraphActivity.this, null);
        Vector v = dbHandler.getData(charge);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);


        List<Entry> entries1 = new ArrayList<>();

        for(int i = 0; i < v.size(); i++) {
            entries1.add(new Entry(i, (int) v.get(i)));
        }
        LineDataSet set1;
        if(!charge) {
            set1 = new LineDataSet(entries1, "Discharging");
            set1.setDrawFilled(true);
            set1.setValueTextSize(15f);
            set1.setCircleColor(Color.RED);
            set1.setFillDrawable(getDrawable(R.drawable.gradient_red));
            set1.setColor(Color.RED);
        } else {
            set1 = new LineDataSet(entries1, "Charging");
            set1.setDrawFilled(true);
            set1.setValueTextSize(15f);
            set1.setFillDrawable(getDrawable(R.drawable.gradient_green));
            set1.setColor(Color.GREEN);
        }

        LineData lineData = new LineData(set1);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
