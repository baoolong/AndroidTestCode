package com.test.caiwen.utile;



import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class WebSocketUtile {

    private static WebSocketUtile webSocketUtile;
    private List<WebSocketLisenter> listeners;
    private WebSocketClient socketClient;

    private  WebSocketUtile(){
        listeners=new ArrayList<>();
    }

    public static WebSocketUtile getInstance(){
        if(webSocketUtile == null) {
            synchronized (WebSocketUtile.class) {
                if (webSocketUtile == null) {
                    webSocketUtile = new WebSocketUtile();
                }
            }
        }
        return webSocketUtile;
    }



    public void connectWebSocket(String ip,String port){
        String webSocketUri="ws://"+ip+":"+port;
        socketClient=new WebSocketClient(URI.create(webSocketUri),new Draft_6455(),null,30*1000) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                for (WebSocketLisenter socketLisenter:listeners) {
                    socketLisenter.onOpen(handshakedata);
                }
            }

            @Override
            public void onMessage(String message) {
                for (WebSocketLisenter socketLisenter:listeners) {
                    socketLisenter.onMessage(message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                for (WebSocketLisenter socketLisenter:listeners) {
                    socketLisenter.onClose(code,reason,remote);
                }
            }

            @Override
            public void onError(Exception ex) {
                for (WebSocketLisenter socketLisenter:listeners) {
                    socketLisenter.onError(ex);
                }
            }
        };
        socketClient.connect();
    }

    public void closeWebSocket(){
        socketClient.close();
    }

    public void sendMessage(String message){
        socketClient.send(message);
    }


    public interface WebSocketLisenter{
        void onOpen(ServerHandshake handshakedata);
        void onMessage(String message);
        void onClose(int code, String reason, boolean remote);
        void onError(Exception ex);
    }

    public void addWebSocketLisenter(WebSocketLisenter webSocketLisenter){
        listeners.add(webSocketLisenter);
    }

    public void removeSocketLisenter(WebSocketLisenter webSocketLisenter){
        listeners.remove(webSocketLisenter);
    }
}
