package com.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by olga on 03.05.15.
 */
public class Client {
    private final String hostIp;
    private final int portAddr;

    public Client(String hostIp, int portAddr) {
        this.hostIp = hostIp;
        this.portAddr = portAddr;
    }

    public void runClient() {
        try (Socket clientSocket = new Socket(hostIp, portAddr);
             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Client connected to host " + hostIp + " on port " + portAddr);
            System.out.println("Enter something (or bye to quit): ");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                System.out.println("Sent: " + userInput);
                printWriter.print(userInput);
                printWriter.print(new String(new byte[]{'\r','\n','\r','\n'}));//, Charset.forName("UTF-8")));
                printWriter.flush();
                System.out.println("Sent: " + userInput);
                if (userInput.equalsIgnoreCase("Bye")) {
                    break;
                }
                System.out.println("Server says: " + reader.readLine());
            }
            stdIn.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 9900).runClient();
    }
}
