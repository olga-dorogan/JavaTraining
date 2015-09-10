package com.custom.handler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by olga on 05.05.15.
 */
public class WriteEventHandler implements EventHandler {
    @Override
    public void handle(SelectionKey handle) throws Exception {
        System.out.println("------------ Write Event handler ------------");
        SocketChannel socketChannel = (SocketChannel) handle.channel();
        ByteBuffer byteBuffer = (ByteBuffer) handle.attachment();
        socketChannel.write(byteBuffer);
        socketChannel.close();
    }
}
