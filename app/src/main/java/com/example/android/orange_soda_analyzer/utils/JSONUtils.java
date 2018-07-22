package com.example.android.orange_soda_analyzer.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abhin on 7/21/2018.
 */

public class JSONUtils {

    public static final String sampleOut = "{\n" +
            "  \"documents\": [\n" +
            "    {\n" +
            "      \"score\": 0.92,\n" +
            "      \"id\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"score\": 0.85,\n" +
            "      \"id\": \"2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"score\": 0.34,\n" +
            "      \"id\": \"3\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"errors\": null\n" +
            "}";

    public static final String sampleIn = "{\n" +
            "  \"documents\": [\n" +
            "    {\n" +
            "      \"language\": \"en\",\n" +
            "      \"id\": \"1\",\n" +
            "      \"text\": \"Hello world. This is some input text that I love.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"language\": \"fr\",\n" +
            "      \"id\": \"2\",\n" +
            "      \"text\": \"Bonjour tout le monde\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"language\": \"es\",\n" +
            "      \"id\": \"3\",\n" +
            "      \"text\": \"La carretera estaba atascada. Había mucho tráfico el día de ayer.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static ArrayList<Double> jsonSentimentOuputParse(String s){
        ArrayList<Double> scores = new ArrayList<>();
        try {
            JSONObject call = new JSONObject(s);
            JSONArray texts = call.getJSONArray("documents");
            for (int i = 0; i < texts.length(); i++){
                scores.add(texts.getJSONObject(i).getDouble("score"));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return scores;
    }
}
