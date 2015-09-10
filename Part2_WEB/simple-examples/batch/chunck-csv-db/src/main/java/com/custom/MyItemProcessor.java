package com.custom;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

/**
 * Created by olga on 21.04.15.
 */
@Named
public class MyItemProcessor implements ItemProcessor {
    private static int id = 1;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yy");

    public Object processItem(Object item) throws Exception {
        System.out.println("Process item: " + item);
        StringTokenizer tokenizer = new StringTokenizer((String) item, ",");
        String name = tokenizer.nextToken();
        String date;
        try {
            date = tokenizer.nextToken();
            sdf.setLenient(false);
            sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return new Person(id++, name, date);
    }
}
