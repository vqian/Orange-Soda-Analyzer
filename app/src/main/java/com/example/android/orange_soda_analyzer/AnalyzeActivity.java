package com.example.android.orange_soda_analyzer;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.orange_soda_analyzer.utils.JSONUtils;
import com.example.android.orange_soda_analyzer.utils.TextAnalyticsTask;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class AnalyzeActivity extends AppCompatActivity {

    private static final String TAG = "Analyze Activity";
    TextView scoresText;
    ArrayList<ArrayList<Double>> scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        scoresText = (TextView) findViewById(R.id.results_text);
        TextAnalyticsTask.setResultsText(scoresText);

        Intent launchIntent = getIntent();

        String json1 = launchIntent.getStringExtra("json1");
        String json2 = launchIntent.getStringExtra("json2");

        Log.d(TAG, json1);
        Log.d(TAG, json2);

        TextAnalyticsTask.setJsonInput1(json1);
        TextAnalyticsTask.setJsonInput2(json2);

        TextAnalyticsTask.runSentimentTask();

        // Use scores double arrayList to analyze compatibility here
        // scores.get(0) is person 1 double arrayList
        // scores.get(1) is person 2 double arrayList

        // Each arrayList has the sentiment for the corresponding phrase in order added



    }
}
