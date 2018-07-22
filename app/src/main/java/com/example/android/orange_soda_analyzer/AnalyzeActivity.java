package com.example.android.orange_soda_analyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.orange_soda_analyzer.utils.JSONUtils;
import com.example.android.orange_soda_analyzer.utils.TextAnalyticsTask;

public class AnalyzeActivity extends AppCompatActivity {

    TextView scoresText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        scoresText = (TextView) findViewById(R.id.results_text);
        TextAnalyticsTask.setResultsText(scoresText);

        String sIn = JSONUtils.sampleIn;
        TextAnalyticsTask.setJsonInput1(sIn);
        TextAnalyticsTask.setJsonInput2(sIn);

        TextAnalyticsTask.runSentimentTask();

    }
}
