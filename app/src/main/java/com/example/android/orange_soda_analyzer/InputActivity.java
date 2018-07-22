package com.example.android.orange_soda_analyzer;

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
    String conversationHistory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDialog = new Dialog(this);
        setContentView(R.layout.activity_input);
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
        String messagesJSON = "{\"documents\":[";
        for(int i = 0; i < messages.size() - 1; i++){
            messagesJSON += convertMessageToJSONObject(messages.get(i)) + ",";
        }
        messagesJSON += convertMessageToJSONObject(messages.get(messages.size() - 1));
        messagesJSON += "]}";
        return messagesJSON;
    }

    public String convertMessageToJSONObject(String message){
        String messageJSONObject = "\"language\": \"en\",\n" +
                "\"id\": \"1\",\n" +
                "\"text\": \"" + message + "\"";
        return messageJSONObject;
    }
}
