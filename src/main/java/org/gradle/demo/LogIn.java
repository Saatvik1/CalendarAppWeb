package org.gradle.demo;

import com.google.api.client.auth.oauth2.StoredCredential;
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

@WebServlet("/LogIn")
public class LogIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        File a = new File("C:\\Users\\Saatvik Sandal\\IdeaProjects\\GoogleCalTest\\tokens\\StoredCredential");

        System.out.println(a.delete());

        try {
            RenewToken.renewToken();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("backToMainPage.jsp").forward(request, response);

    }
}
