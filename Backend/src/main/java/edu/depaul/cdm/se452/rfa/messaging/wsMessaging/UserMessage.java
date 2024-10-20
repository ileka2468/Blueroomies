package edu.depaul.cdm.se452.rfa.messaging.wsMessaging;

public class UserMessage {

    private String user;
    private String message;

    public UserMessage() {}

    //default constructor
    public UserMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    //getter setter for user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    //getter setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}//class