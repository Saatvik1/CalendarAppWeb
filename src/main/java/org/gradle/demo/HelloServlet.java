package org.gradle.demo;

import com.google.api.services.calendar.Calendar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Hello, World!");
        ReadEvents re = new ReadEvents();
        try {
            response.getWriter().print("\n" + re.getEvents());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventSummary = request.getParameter("titleSummary");

        //SimpleDateFormat Class to Format the Data from Date field in index.html
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Parse the Date Data
        //Need to add check for when user puts an end date earlier than start date.
        try {
            Date eventStart = sdf.parse(request.getParameter("startDate"));
            Date endStart = sdf.parse(request.getParameter("endDate"));
        } catch (ParseException e) {}

        EventCreator newEvent = new EventCreator();

        if (eventSummary == null)
            eventSummary = "(No title)";
        newEvent.setSummary(eventSummary);
        try {
            newEvent.insertEvent();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("event", eventSummary);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}
