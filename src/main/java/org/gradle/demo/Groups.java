package org.gradle.demo;

public class Groups {
//Make child class that extends groups represting the people... emails, names, companies etc.
    private String[] emailAdresses;

    public String[] getEmailAdresses() {
        return emailAdresses;
    }

    public Groups(){
        this.emailAdresses = new String[2];
    }

    public Groups(String... emailAdressesInput){
        this.emailAdresses = new String[emailAdressesInput.length];
        for(int i = 0; i<this.emailAdresses.length; i++){
            this.emailAdresses[i] = emailAdressesInput[i];
        }
    }


}
