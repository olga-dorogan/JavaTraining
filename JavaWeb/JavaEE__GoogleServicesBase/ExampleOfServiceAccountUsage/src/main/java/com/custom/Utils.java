package com.custom;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by olga on 22.05.15.
 */
public class Utils {
    private static final String emailAddress = "413719209435-kgvn4ti1mv0q6jh754thrh4kl3svra04@developer.gserviceaccount.com";
    private static final String APPLICATION_NAME = "AppWithServiceAccount";
    private static final String PATH_TO_PRIVATE_KEY_FILE = "WEB-INF/classes/MyProject.p12";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static GoogleCredential getCredential(HttpServletRequest req) throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(emailAddress)
                .setServiceAccountPrivateKeyFromP12File(new File(req.getServletContext().getRealPath(PATH_TO_PRIVATE_KEY_FILE)))
                .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                .build();
        return credential;
    }

    public static Calendar getCalendarService(HttpServletRequest req) throws GeneralSecurityException, IOException {
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredential(req))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static String getPathToPrivateKeyFile() {
        return PATH_TO_PRIVATE_KEY_FILE;
    }

    private Utils() {
    }
}
