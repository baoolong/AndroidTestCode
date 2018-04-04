package com.test.caiwen.ui.websocket;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class WebSocketPersenter implements WebSocketContract.Persenter{
    private WebSocketContract.View mView;

    public WebSocketPersenter(WebSocketContract.View mView){
        this.mView=mView;
    }
}
