package edu.depaul.cdm.se452.rfa.messaging.wsMessaging.client;

import edu.depaul.cdm.se452.rfa.messaging.wsMessaging.UserMessage;

import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String username = "cristianJ";
        String userMessage = "here is a message";

        MyStompClient myStompClient = new MyStompClient(username);
        myStompClient.sendMessage(new UserMessage(username, userMessage));
        myStompClient.disconnectUser(username);

    }
}
