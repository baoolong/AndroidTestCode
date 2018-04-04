package com.test.caiwen.ui.websocket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.caiwen.R;
import com.test.caiwen.utile.LogUtil;
import com.test.caiwen.utile.WebSocketUtile;

import org.java_websocket.handshake.ServerHandshake;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class WebSocketChat extends Activity implements WebSocketContract.View {

    @BindView(R.id.edit_Ip)
    EditText editIp;
    @BindView(R.id.edit_Port)
    EditText editPort;
    @BindView(R.id.button_Send)
    Button buttonSend;
    @BindView(R.id.text_Message)
    TextView textMessage;
    @BindView(R.id.edit_inputMessage)
    EditText editInputMessage;

    private final String SEND_BUTTON_TEXT = "连接",
            CONNECT_BUTTON_TEXT = "发送";
    private WebSocketUtile webSocketUtile;
    private StringBuilder stringBuilder;
    private final String TAG="WebSocketChat";
    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket_chat);
        ButterKnife.bind(this);
        webSocketUtile = WebSocketUtile.getInstance();
        stringBuilder = new StringBuilder("");
        Log.d(TAG,"onCreate");
    }

    private WebSocketUtile.WebSocketLisenter socketLisenter = new WebSocketUtile.WebSocketLisenter() {
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            LogUtil.i(TAG,"hansshake data is:"+handshakedata.getHttpStatusMessage());
            buttonSend.setClickable(true);
        }

        @Override
        public void onMessage(String message) {
            stringBuilder.append("接收信息：").append(message).append("\n");
            LogUtil.i(TAG,"message is:"+message);
            textMessage.setText(stringBuilder.toString());
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


    @OnClick(R.id.button_Send)
    public void onViewClicked() {
        //当没有连接websocket时 button显示为连接字样，点击可连接服务器，连接以后button显示为发送字样，此时可以发送文字
        if (buttonSend.getText().toString().equals(SEND_BUTTON_TEXT)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        webSocketUtile.addWebSocketLisenter(socketLisenter);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                buttonSend.setClickable(false);
                            }
                        });
                        webSocketUtile.connectWebSocket(editIp.getText().toString(),editPort.getText().toString());
                        loginServer();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).run();
        } else {
            String sendMessage=editInputMessage.getText().toString();
            stringBuilder.append("发送信息：").append(sendMessage).append("\n");
            textMessage.setText(stringBuilder.toString());
        }
    }


    private void loginServer() {
        String loginMessage = ".+landing|dongbaolong| dongbaolong |||"
                + "2016-11-09|#";
        webSocketUtile.sendMessage(loginMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketUtile.removeSocketLisenter(socketLisenter);
    }
}
