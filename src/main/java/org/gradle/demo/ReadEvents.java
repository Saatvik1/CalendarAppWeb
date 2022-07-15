package org.gradle.demo;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/* class to demonstarte use of Calendar events list API */
public class ReadEvents {
    /** Application name. */
    private static final String APPLICATION_NAME = "peepee";

    public static String getApplicationName(){
        return APPLICATION_NAME;
    }

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";


    private static String userEmail;

    public static String getUserEmail(){
        return userEmail;
    }



    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR + " https://www.googleapis.com/auth/userinfo.email");
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //Load client secrets.
        FileReader in;
        try {
             in = new FileReader(CREDENTIALS_FILE_PATH);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }


        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        System.out.println(credential.getAccessToken());

        ReceiveEmail a = new ReceiveEmail();

        userEmail = a.extractEmail(a.getInfo(credential.getAccessToken()));

        return credential;
    }

    public String getEvents() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        String eventList = "";

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            eventList += "No events found";
        } else {
            //eventList += items.toString();
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                if (event.getSummary() == null) {
                    eventList += "(No title)" + " : " + start + "\n";
                } else {
                    eventList += event.getSummary() + " : " + start + "\n";
                }
            }
        }


        return eventList;

    }


    static Credential getCredentialsShallow(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //Load client secrets.
        FileReader in;
        try {
            in = new FileReader(CREDENTIALS_FILE_PATH);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }


        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.

        return credential;
    }
}
