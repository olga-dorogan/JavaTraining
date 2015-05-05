package com.custom.handler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by olga on 05.05.15.
 */
public class ReadEventHandler implements EventHandler {
    private Selector demultiplexer;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    @Override
    public void handle(SelectionKey handle) throws Exception {
        System.out.println("------------ Read Event handler ------------");
        SocketChannel socketChannel = (SocketChannel) handle.channel();
        socketChannel.read(inputBuffer);
        inputBuffer.flip();
        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);
        System.out.println("----- Received msg: \n" + new String(buffer));

        inputBuffer.flip();
        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, inputBuffer);
    }
}
