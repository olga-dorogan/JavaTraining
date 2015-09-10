package com.custom.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by olga on 05.05.15.
 */
public class AcceptEventHandler implements EventHandler {
    private Selector demultiplexer;

    public AcceptEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }
    @Override
    public void handle(SelectionKey handle) throws Exception {
        System.out.println("---------- Accept Event Handler -------------   ");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) handle.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            socketChannel.register(demultiplexer, SelectionKey.OP_READ);
        }
    }
}
