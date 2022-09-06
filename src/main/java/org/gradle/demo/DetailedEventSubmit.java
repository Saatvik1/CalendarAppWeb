package org.gradle.demo;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.services.calendar.Calendar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


@WebServlet("/setdetailedevent")
public class DetailedEventSubmit extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventTitle = request.getParameter("titleSummary");
        String eventLocation = request.getParameter("Set Location");
        String eventDescription = request.getParameter("Set Description");

        //SimpleDateFormat Class to Format the Data from Date field in index.html
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Parse the Date Data
        //Need to add check for when user puts an end date earlier than start date.
        Date eventStart = null;
        Date eventEnd = null;

        try {
            eventStart = sdf2.parse(request.getParameter("startDate") + " " +  request.getParameter("Set Start Time") + ":00");
            eventEnd   = sdf2.parse(request.getParameter("endDate") + " " + request.getParameter("Set End Time")+ ":00");
        } catch (ParseException e) {

        }

        EventCreator newEvent = new EventCreator();

        if (eventTitle == null)
            eventTitle = "(No title)";
        if(eventDescription == null)
            eventDescription = "";
        if(eventLocation ==null)
            eventLocation = "";

        newEvent.setSummary(eventTitle);
        newEvent.setStartTime(eventStart);
        newEvent.setEndTime(eventEnd);
        newEvent.setDescription(eventDescription);
        newEvent.setLocation(eventLocation);

        try {
            newEvent.insertEvent();
        } catch (TokenResponseException | GeneralSecurityException tokenResponseException ) {


            File a = new File("C:\\Users\\Saatvik Sandal\\IdeaProjects\\GoogleCalTest\\tokens\\StoredCredential");
            a.delete();
            RenewToken b = new RenewToken();
            try {
                b.renewToken();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }


        }

        request.setAttribute("event", eventTitle);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}
