package com.custom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by olga on 12.02.15.
 */
public class HttpUtils {
    private static final int BUF_LENGTH = 8192;

    public static byte[] readRequest(InputStream in) {
        byte[] request = new byte[BUF_LENGTH];
        int read = 0;
        while (true) {
            try {
                System.out.println("Read: " + request);
                read += in.read(request, read, BUF_LENGTH - read);
                if ((read == 0) || (read >= BUF_LENGTH)) {
                    throw new RuntimeException();
                }
                if (checkEnd(request, read)) {
                    System.out.println("End of request");
                    return Arrays.copyOf(request, read);
                }

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    private static boolean checkEnd(byte[] response, int read) {
        System.out.println("Response: " + new String(response));
        if (read <= 4) {
            return false;
        }
        if (response[read - 4] == '\r' &&
                response[read - 3] == '\n' &&
                response[read - 2] == '\r' &&
                response[read - 1] == '\n') {
            return true;
        }
        return false;
    }
}
