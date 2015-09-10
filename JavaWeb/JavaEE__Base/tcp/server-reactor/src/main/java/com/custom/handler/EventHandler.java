package com.custom.handler;

import java.nio.channels.SelectionKey;

/**
 * Created by olga on 05.05.15.
 */
public interface EventHandler {
    public void handle(SelectionKey handle) throws Exception;
}
