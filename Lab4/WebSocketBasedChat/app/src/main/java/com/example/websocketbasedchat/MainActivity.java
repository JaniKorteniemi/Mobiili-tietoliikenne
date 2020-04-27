package com.example.websocketbasedchat;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity implements ChatClientInterface {
    final String  strUri = "wss://obscure-waters-98157.herokuapp.com";

    URI uri;
    ChatClient chatClient = null;
    boolean ftsMessage = true;

    Button sendMsg;
    EditText msg;
    ListView chatText;
    TextView statusText;

    public MyAdapter myAdapter;
    ArrayList<String> senderArr;
    ArrayList<String> messageArr;
    ArrayList<String> timeArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        chatClient = new ChatClient(uri, this);
        chatClient.connect();

        myAdapter = new MyAdapter(getApplicationContext(), senderArr, messageArr, timeArr);
        chatText.setAdapter(myAdapter);
    }
    private void init(){
        sendMsg = findViewById(R.id.sendMassage);
        msg = findViewById(R.id.sendEditText);
        chatText = findViewById(R.id.chatListView);
        statusText = findViewById(R.id.statusTextView);

        senderArr = new ArrayList<>();
        messageArr = new ArrayList<>();
        timeArr = new ArrayList<>();

        try {
            uri = new URI(strUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(View v){
        if (chatClient != null && chatClient.isOpen()){
            String message = msg.getText().toString();
            msg.setText("");
            chatClient.send(message);
        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stringParse(message);
            }
        });
    }

    @Override
    public void onStatusChange(final String newStatus ) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusText.setText(newStatus);
            }
        });
    }

    public void stringParse(String message){
        String[] separator = message.split(" ");

        Log.d("####Messgae####", message);

        //0 aika, 4 ip, 6 text
        if (separator.length == 5 && ftsMessage == true){
            ftsMessage = false;

            senderArr.add(separator[0]);
            messageArr.add(separator[0] + " " + separator[1].toLowerCase() + " " +separator[2].toLowerCase() + " " +separator[3] + " You!!!");
            timeArr.add("Server Message");
        }else if (separator.length == 6){
            senderArr.add("@" + separator[4]);
            messageArr.add("         ");
            timeArr.add(separator[0]);
        }

        if (separator.length >= 7) {
            Log.d("####MEssage Separator####",
                    "\n0 " + separator[0] + "\n1 " + separator[1] + "\n2 " + separator[2] + "\n3 " + separator[3]
                            + "\n4 " + separator[4] + "\n5 " + separator[5] + "\n6 " + separator[6] + "\nseparator lenght " + separator.length);

            String messageText = "";
            for (int i = 6; i < separator.length; i++){
                messageText = messageText + separator[i] + " ";
            }
            senderArr.add("@" + separator[4]);
            messageArr.add(messageText);
            timeArr.add(separator[0]);
        }

        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (chatClient != null && chatClient.isOpen()){
            chatClient.close();
            statusText.setText("");
        }
        super.onBackPressed();
        return;
    }
}
