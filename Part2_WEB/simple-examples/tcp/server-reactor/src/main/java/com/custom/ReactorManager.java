package com.custom;

import com.custom.handler.AcceptEventHandler;
import com.custom.handler.ReadEventHandler;
import com.custom.handler.WriteEventHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by olga on 05.05.15.
 */
public class ReactorManager {

    private static final int SERVER_PORT = 9900;

    public void startReactor(int port) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);

        reactor.registerEventHandler(SelectionKey.OP_ACCEPT,
                new AcceptEventHandler(reactor.getDemultiplexer()));

        reactor.registerEventHandler(SelectionKey.OP_READ,
                new ReadEventHandler(reactor.getDemultiplexer()));

        reactor.registerEventHandler(SelectionKey.OP_WRITE,
                new WriteEventHandler());

        reactor.run();
    }

    public static void main(String[] args) {
        System.out.println("server started on port " + SERVER_PORT);
        try {
            new ReactorManager().startReactor(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
