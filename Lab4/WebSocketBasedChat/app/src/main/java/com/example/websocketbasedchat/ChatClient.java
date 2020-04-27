package com.example.websocketbasedchat;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface ChatClientInterface{
    void onMessage(String message);
    void onStatusChange(String newStatus);
}

public class ChatClient extends WebSocketClient {

    ChatClientInterface observer;

    public ChatClient(URI serverUri, ChatClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("####SOCKET onOpen####", "onOpen called");
        observer.onStatusChange("Connection Open");
    }

    @Override
    public void onMessage(String message) {
        Log.d("####SOCKET onMessage####", "onMessage called");
        observer.onMessage(message);
        observer.onStatusChange("Received Message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("####SOCKET onClose####", "onClose called");
        observer.onStatusChange("Socket closed");
    }

    @Override
    public void onError(Exception ex) {
        Log.d("####SOCKET onError####", "onError called");
        observer.onStatusChange("Error in Socket: " + ex.toString());
    }
}
