package com.example.android.orange_soda_analyzer.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by abhin on 7/21/2018.
 */

public class TextAnalyticsTask {
    private static String api_key = "aaec304a13fc4b6fb2439f90636423a5";
    private static String uri_base = "https://southcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";

    private static ArrayList<ArrayList<Double>> scores;

    private static TextView resultsText = null;
    private static int index;



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
        new TextAnalysisTask().execute(jsonInput1);
    }

    public static void setResultsText(TextView resultsText) {
        TextAnalyticsTask.resultsText = resultsText;
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
            }

            if(index == 1){

            }

        }
    }

    public static ArrayList<ArrayList<Double>> getScores() {
        return scores;
    }

    private static double compatScore(ArrayList<ArrayList<Double>> scores){
        
        return
    }


}
