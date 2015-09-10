package org.javatraining.integration.google.calendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.javatraining.integration.google.calendar.exception.CalendarException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by olga on 22.05.15.
 */
@ApplicationScoped
public class ServicesFromServiceAccountFactory {
    private static final String EMAIL_ADDRESS = "413719209435-kgvn4ti1mv0q6jh754thrh4kl3svra04@developer.gserviceaccount.com";
    private static final String APPLICATION_NAME = "AppWithServiceAccount";
    private static final String PATH_TO_PRIVATE_KEY_FILE = "WEB-INF/classes/MyProject.p12";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Inject
    private ServletContext servletContext;

    @Produces
    public Calendar getCalendarService() {
        try {
            return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredential())
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new CalendarException(e);
        }
    }

    private GoogleCredential getCredential() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(EMAIL_ADDRESS)
                .setServiceAccountPrivateKeyFromP12File(new File(servletContext.getRealPath(PATH_TO_PRIVATE_KEY_FILE)))
                .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                .build();
        return credential;
    }

    private ServicesFromServiceAccountFactory() {
    }
}
