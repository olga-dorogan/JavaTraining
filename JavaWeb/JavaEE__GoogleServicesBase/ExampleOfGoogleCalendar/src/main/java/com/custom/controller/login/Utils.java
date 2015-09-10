package com.custom.controller.login;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.oauth2.Oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by olga on 21.05.15.
 */
public class Utils {
    private static final String SCOPE_EMAIL = "email";
    private static final String SCOPE_PROFILE = "profile";
    private static final DataStoreFactory DATA_STORE_FACTORY = MemoryDataStoreFactory.getDefaultInstance();
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static GoogleClientSecrets clientSecrets = null;

    static GoogleClientSecrets getClientCredential() throws IOException {
        if (clientSecrets == null) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(Utils.class.getResourceAsStream("/client_secrets.json")));
        }
        return clientSecrets;
    }

    static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(req.getContextPath() + "/oauth2callback");
        return url.build();
    }

    static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, getClientCredential(),
                Arrays.asList(SCOPE_EMAIL, SCOPE_PROFILE, CalendarScopes.CALENDAR))
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
    }

    public static Calendar loadCalendarClient(HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Credential credential = newFlow().loadCredential(session.getId());
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
    }


    public static Oauth2 loadOAuthClient(HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Credential credential = newFlow().loadCredential(session.getId());
        return new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
    }

    static Calendar loadCalendarClient(Credential credential) throws IOException {
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
    }

    private Utils() {
    }
}

