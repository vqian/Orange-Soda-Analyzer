package com.example.android.orange_soda_analyzer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class InputActivity extends AppCompatActivity {
    Dialog myDialog;
    ArrayList<String> messages1 = new ArrayList<>();
    ArrayList<String> messages2 = new ArrayList<>();

    Button analyze;
    String conversationHistory = "";

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input);

        myDialog = new Dialog(this);
        analyze = (Button) findViewById(R.id.buttonAnalyze);

        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent analyzeIntent = new Intent(context, AnalyzeActivity.class);

                String json1 = convertArrayListToJSON(messages1);
                String json2 = convertArrayListToJSON(messages2);

                analyzeIntent.putExtra("json1", json1);
                analyzeIntent.putExtra("json2", json2);

                startActivity(analyzeIntent);

            }
        });

    }

    public void getMessages1(View v){
        String message = ((EditText)findViewById(R.id.input1)).getText().toString();
        messages1.add(message);
        ((EditText)findViewById(R.id.conversation)).setText(getConversation(message, 1));
    }

    public void getMessages2(View v){
        String message = ((EditText)findViewById(R.id.input2)).getText().toString();
        messages2.add(message);
        ((EditText)findViewById(R.id.conversation)).setText(getConversation(message, 2));
    }

    public String getConversation(String message, int personNumber){
        conversationHistory += "Person " + personNumber + ": \"" + message + "\"\n";
        return conversationHistory;
    }

    public String convertArrayListToJSON(ArrayList<String> messages){
        if(messages.size() != 0) {
            String messagesJSON = "{\"documents\":[";
            for (int i = 0; i < messages.size() - 1; i++) {
                messagesJSON += convertMessageToJSONObject(messages.get(i), i) + ",";
            }
            messagesJSON += convertMessageToJSONObject(messages.get(messages.size() - 1), messages.size() - 1);
            messagesJSON += "]}";
            return messagesJSON;
        } else {
            return "{\"documents\":[]}";
        }
    }

    public String convertMessageToJSONObject(String message, int id){
        String messageJSONObject = "{\"language\": \"en\",\n" +
                "\"id\": \"" + id + "\",\n" +
                "\"text\": \"" + message + "\"}";
        return messageJSONObject;
    }
}
