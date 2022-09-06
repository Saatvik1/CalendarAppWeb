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
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReceiveEmail {

    public String getInfo(String Token){
        String userInfoContent = "";
        try {
            /**
             * Scary lookin token here
             */

            URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + Token);
            InputStream content = url.openStream();
            int c;
            while ((c = content.read())!=-1) {
                userInfoContent += ((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();

                System.out.println("IN THE CATCHHHHHHHHH");

            /**
             * Here
             */

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
                    //System.out.println("Tried");
                } catch (GeneralSecurityException | IOException i){
                    throw new IllegalArgumentException();
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
        JSONObject userEmailToJSON = new JSONObject();


        JSONObject emailObject = new JSONObject();
        emailObject.put(email, userEmailToJSON);

        try (FileWriter file = new FileWriter("emailList.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(emailObject.toJSONString());
            file.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(email);
        return email;
    }

    //Made JSON file with email

    //Make a button that asks for OAuth login in the groups window
    // HTML button --> doGetRequest (doesn't redirect to new page) --> Create JSON File --> When original button is pressed,
    // wait for JSON file and then make script.js read JSON file. Add the email to the group list.
    // If can't wait for JSON file, make a reload button that the user presses after they completed OAUTH. In this case, a redirect
    //is fine as long as it redirects back to the original page.

    //Whenever the token is refreshed, make sure the JSON file is deleted.


}
