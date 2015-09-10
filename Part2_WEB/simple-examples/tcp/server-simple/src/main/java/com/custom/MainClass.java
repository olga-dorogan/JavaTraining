package com.custom;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olga on 03.05.15.
 */
public class MainClass {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin to listen...");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        ServerSocket serverSocket = new ServerSocket(80);
        Socket acceptedSocket;
        while (true) {
            acceptedSocket = serverSocket.accept();

            System.out.println(sdf.format(new Date()));
            try (InputStream inputStream = acceptedSocket.getInputStream();
                 OutputStream outputStream = acceptedSocket.getOutputStream()) {
                System.out.println(" -------------------------------- ");
                System.out.print(new String(HttpUtils.readRequest(inputStream), Charset.forName("UTF-8")));
                System.out.println(" -------------------------------- ");
                String response = new Date().toString();
                outputStream.write(response.getBytes(Charset.forName("UTF-8")));
            } finally {
                acceptedSocket.close();
            }

        }
    }
}
