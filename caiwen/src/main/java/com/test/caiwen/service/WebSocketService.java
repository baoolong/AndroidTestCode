package com.test.caiwen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.test.caiwen.utile.LogUtil;
import com.test.caiwen.utile.WebSocketUtile;

import org.java_websocket.handshake.ServerHandshake;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class WebSocketService extends Service{
    private final String TAG="WebSocketService";

    private WebSocketUtile webSocketUtile;
    private WebSocketUtile.WebSocketLisenter webSocketLisenter=new WebSocketUtile.WebSocketLisenter() {
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            LogUtil.i(TAG,"hansshake data is:"+handshakedata.getHttpStatusMessage());
        }

        @Override
        public void onMessage(String message) {
            LogUtil.i(TAG,"message is:"+message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            LogUtil.w(TAG,"code is:"+code+"  ---  reason is:"+reason);
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        webSocketUtile=WebSocketUtile.getInstance();
        webSocketUtile.addWebSocketLisenter(webSocketLisenter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocketUtile.removeSocketLisenter(webSocketLisenter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
