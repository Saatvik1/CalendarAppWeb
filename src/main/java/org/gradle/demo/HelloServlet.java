package org.gradle.demo;

import com.google.api.services.calendar.Calendar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

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


        EventCreator newEvent;

        if (eventSummary == null) {
            eventSummary = "(No title)";
             newEvent = new EventCreator();
            try {
                newEvent.insertEvent();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        } else {
            newEvent = new EventCreator();
            newEvent.setSummary(eventSummary);
            try {
                newEvent.insertEvent();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        request.setAttribute("event", eventSummary);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}
