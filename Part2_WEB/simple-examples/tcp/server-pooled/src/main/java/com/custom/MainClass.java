package com.custom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by olga on 05.05.15.
 */
public class MainClass {
    private static final int QUEUE_LENGTH = 256;
    private static final int TESTED_CLIENTS_CNT = 5;

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = new ThreadPoolExecutor(
                4,
                64,
                10L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(QUEUE_LENGTH)
        );
        ServerSocket serverSocket = new ServerSocket(9900, QUEUE_LENGTH);
        int cnt = 0;
        while (true) {
            Socket socket = serverSocket.accept();
            executorService.submit(new HttpHandler(socket, cnt++));
            if (cnt >= TESTED_CLIENTS_CNT) {
                executorService.shutdown();
            }
        }
    }
}
