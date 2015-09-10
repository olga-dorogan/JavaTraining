package com.custom;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by olga on 08.05.15.
 */
@ApplicationScoped
public class Credentials {
    private String from;
    private String password;
    private String to;

    public Credentials() {
        try {
            final Properties creds = new Properties();
            creds.load(new FileInputStream(System.getProperty("user.home")
                    + System.getProperty("file.separator")
                    + ".javamail"));
            from = creds.getProperty("from");
            password = creds.getProperty("password");
            to = creds.getProperty("to");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
