package com.custom;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by olga on 02.05.15.
 */
@ServerEndpoint("/websocket")
public class MyWebSocketEndpoint {
    @OnMessage
    public String echo(String text) {
        System.out.println("Got text: "+text);
        return "From websocket: " + text;
    }
}
