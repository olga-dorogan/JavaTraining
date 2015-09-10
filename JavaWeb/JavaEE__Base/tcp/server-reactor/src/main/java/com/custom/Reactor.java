package com.custom;

import com.custom.handler.EventHandler;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olga on 05.05.15.
 */
public class Reactor {
    private Map<Integer, EventHandler> registeredHandlers = new ConcurrentHashMap<>();
    private Selector demultiplexer;

    public Reactor() throws IOException {
        demultiplexer = Selector.open();
    }

    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    public void registerEventHandler(int eventType, EventHandler eventHandler) {
        registeredHandlers.put(eventType, eventHandler);
    }

    public void registerChannel(int eventType, SelectableChannel channel) throws ClosedChannelException {
        channel.register(demultiplexer, eventType);
    }

    public void run() {
        try {
            while (true) {
                demultiplexer.select();
                Set<SelectionKey> readyHandlers = demultiplexer.selectedKeys();
                Iterator<SelectionKey> iteratorHandles = readyHandlers.iterator();
                while (iteratorHandles.hasNext()) {
                    SelectionKey handle = iteratorHandles.next();
                    if (handle.isAcceptable()) {
                        EventHandler eventHandler = registeredHandlers.get(SelectionKey.OP_ACCEPT);
                        eventHandler.handle(handle);
                    }
                    if (handle.isWritable()) {
                        EventHandler eventHandler = registeredHandlers.get(SelectionKey.OP_WRITE);
                        eventHandler.handle(handle);
                        iteratorHandles.remove();
                    }
                    if (handle.isReadable()) {
                        EventHandler eventHandler = registeredHandlers.get(SelectionKey.OP_READ);
                        eventHandler.handle(handle);
                        iteratorHandles.remove();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
