package com.example.android.orange_soda_analyzer.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.android.orange_soda_analyzer.AnalyzeActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by abhin on 7/21/2018.
 */

public class TextAnalyticsTask {
    private static String api_key = "aaec304a13fc4b6fb2439f90636423a5";
    private static String uri_base = "https://southcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";

    private static ArrayList<ArrayList<Double>> scores;

    private static TextView resultsText = null;
    private static int index;

    private static boolean taskEnd;

    private static String jsonInput1;
    private static String jsonInput2;

    private static URL makeUrl(String uri_base){
        URL url = null;
        Uri uri = Uri.parse(uri_base).buildUpon().build();

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static void runSentimentTask(String jsonInput){
        new TextAnalysisTask().execute(jsonInput);
    }
    public static void runSentimentTask(){
        index = 0;
        scores = new ArrayList<ArrayList<Double>>();
        taskEnd = false;
        new TextAnalysisTask().execute(jsonInput1);
    }

    public static void setResultsText(TextView resultsText) {
        TextAnalyticsTask.resultsText = resultsText;
    }

    public static boolean isTaskEnd() {
        return taskEnd;
    }

    public static void setJsonInput1(String jsonInput1) {
        TextAnalyticsTask.jsonInput1 = jsonInput1;
    }

    public static void setJsonInput2(String jsonInput2) {
        TextAnalyticsTask.jsonInput2 = jsonInput2;
    }

    // JSON Input
    // JSON Ouput
    private static class TextAnalysisTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = makeUrl(uri_base);
            try{
                HttpURLConnection client = (HttpURLConnection) url.openConnection();

                client.setDoOutput(true);
                client.setRequestMethod("POST");
                client.setRequestProperty("Content-Type", "application/json");
                client.setRequestProperty("Ocp-Apim-Subscription-Key",api_key);

                byte [] jsonIn = strings[0].getBytes();
                OutputStream os = client.getOutputStream();
                os.write(jsonIn);
                os.flush();

                if(client.getResponseCode() != HttpURLConnection.HTTP_OK){
                    throw new RuntimeException("Failed : HTTP error code " + client.getResponseCode());
                }

                InputStream in = client.getInputStream();
                Scanner sc = new Scanner(in);
                sc.useDelimiter("\\A");

                if (sc.hasNext()) {
                    return sc.next();
                } else {
                    return null;
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            scores.add(JSONUtils.jsonSentimentOuputParse(s));
            resultsText.setText(scores.toString());
            if(index == 0){
                runSentimentTask(jsonInput2);
                index++;
            } else if(index == 1){
                ArrayList<Double> person1 = scores.get(0);
                ArrayList<Double> person2 = scores.get(1);

                double crush2on1 = calculateCrushPercentage(person1, person2);
                double crush1on2 = calculateCrushPercentage(person2, person1);

                resultsText.setText("Person 1 Crush Percent on Person 2: " + crush1on2 + "\n\n" + "Person 2 Crush Percent on Person 1: " + crush2on1);
            }

        }
    }

    public static ArrayList<ArrayList<Double>> getScores() {
        return scores;
    }

    public static double calculateCrushPercentage(ArrayList<Double> sentiments1, ArrayList<Double> sentiments2){
        double crushPercentage = 0;
        for(int i = 0; i < sentiments1.size() - 1; i++){
            crushPercentage += 1 - abs(abs(sentiments1.get(i)) - abs(sentiments2.get(i + 1)));
        }
        return crushPercentage / (sentiments1.size() - 1) * 100; // % sentiments2 person "crushes" on sentiments1 person
    }

}
