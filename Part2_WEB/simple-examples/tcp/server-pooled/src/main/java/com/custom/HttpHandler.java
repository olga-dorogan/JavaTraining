package com.custom;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by olga on 05.05.15.
 */
public class HttpHandler implements Callable<Void> {
    private final Socket socket;
    private final int n;

    public HttpHandler(Socket socket, int n) {
        this.socket = socket;
        this.n = n;
    }

    @Override
    public Void call() throws Exception {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            System.out.println(" -------------------------------- " + n);
            System.out.print(new String(HttpUtils.readRequest(inputStream), Charset.forName("UTF-8")));
            System.out.println(" -------------------------------- " + n);
            String response = new Date().toString();
            outputStream.write(response.getBytes(Charset.forName("UTF-8")));
        } finally {
            socket.close();
        }
        return null;
    }
}
