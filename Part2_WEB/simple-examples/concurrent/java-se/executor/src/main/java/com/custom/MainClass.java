package com.custom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {

    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("You must pass URL");
            System.exit(0);
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<List<String>> callable = new Callable<List<String>>() {
            public List<String> call() throws Exception {
                List<String> readLines = new ArrayList<String>();
                URL url = new URL(args[0]);
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = reader.readLine();
                    while (line != null) {
                        readLines.add(line);
                        line = reader.readLine();
                    }

                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
                return readLines;
            }
        };

        Future<List<String>> future = executorService.submit(callable);

        try {
            List<String> readLines = future.get(30, TimeUnit.SECONDS);
            for (String line : readLines) {
                System.out.println(line);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
