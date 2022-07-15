package org.gradle.demo;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.sun.tools.javac.Main;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.Duration;
;

public class ReceiveEmail {

    public String getInfo(String Token){
        String userInfoContent = "";
        try {
            URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + Token);
            InputStream content = url.openStream();
            int c;
            while ((c = content.read())!=-1) {
                userInfoContent += ((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();

                System.out.println("IN THE CATCHHHHHHHHH");

                File a = new File("C:\\Users\\Saatvik Sandal\\IdeaProjects\\GoogleCalTest\\tokens\\StoredCredential");
                System.out.println(a.delete());

                RenewToken b = new RenewToken();

                try {
                    b.renewToken();
                    System.out.println("Renewed");
                } catch (GeneralSecurityException | IOException k) {
                    throw new RuntimeException(e);
                }

                try{
                    userInfoContent = getInfo(ReadEvents.getCredentialsShallow(GoogleNetHttpTransport.newTrustedTransport()).getAccessToken());
                    System.out.println("Tried");
                } catch (GeneralSecurityException | IOException i){
                    throw new IllegalArgumentException("Fuck");
                }



        }
        System.out.println(userInfoContent);
        return userInfoContent;
    }

    public String extractEmail(String userInfoContent){
        String email = "";
        email = userInfoContent.split("\n")[2];
        email = email.substring(12,email.lastIndexOf("m")+1);


        // request.setAttribute("event", eventSummary); The request will come from a button press. When button is pressed, do a doPost
        // method and request.setAttribute("email", email);. Then insert that.
        return email;
    }



}
