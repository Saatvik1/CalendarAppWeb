package org.gradle.demo;



import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;



import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.*;

public class EventCreator extends ReadEvents {
    private Event event;
    private String creatorEmail;



    public EventCreator(){
        this.event = new Event();
        this.event.setSummary("No title");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        this.event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        this.event.setEnd(new EventDateTime().setDateTime(end));
    }

    public void setCreatorEmail(String email){
        this.creatorEmail = email;
    }

    public String getCreatorEmail(){
        return this.creatorEmail;
    }

    public void setTitle(String title){
        this.event.setSummary(title);
    }

    public void setStartTime(Date startTime){
        this.event.setStart(new EventDateTime().setDateTime(new DateTime(startTime,TimeZone.getTimeZone("EDT"))));
    }

    public void setEndTime(Date endTime){
        this.event.setEnd(new EventDateTime().setDateTime(new DateTime(endTime,TimeZone.getTimeZone("EDT"))));
    }

    public void addAttendees(String... attendee){
        EventAttendee[] attendees = new EventAttendee[attendee.length];

        for(int i = 0; i< attendee.length; i++){
            attendees[i] = new EventAttendee().setEmail(attendee[i]);
        }

        this.event.setAttendees(Arrays.asList(attendees));
    }

    public void setSummary(String summary){
        this.event.setSummary(summary);
    }

    public void setDescription(String description){
        this.event.setDescription(description);
    }

    public Event getEvent() {
        return this.event;
    }

    public EventDateTime getStartTime(){
        return this.event.getStart();
    }

    public EventDateTime getEndTime(){
        return this.event.getEnd();
    }

    public String getTitle(){
        return this.event.getSummary();
    }

    public String getDescription(){
        return this.event.getDescription();
    }

    public List<EventAttendee> getAttendees(){
        return this.event.getAttendees();
    }

    public void insertEvent() throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, ReadEvents.getJsonFactory(), ReadEvents.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(ReadEvents.getApplicationName())
                .build();
        this.event = service.events().insert("primary",this.event).execute();
    }

    public void setLocation(String location){
        //this.event.setLocation(event.get);
        this.event.setLocation(location);
    }
}


