package com.custom;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olga on 21.04.15.
 */
@Named
public class MyItemReader extends AbstractItemReader {
    private BufferedReader reader;
    @Override
    public void open(Serializable checkpoint) throws Exception {
        reader = new BufferedReader(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("/META-INF/data.csv")
        ));
    }

    @Override
    public Object readItem() throws Exception {
        try {
            return reader.readLine();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e);
        }
        return null;
    }
}
