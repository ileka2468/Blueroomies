package edu.depaul.cdm.se452.rfa.messaging.wsMessaging.client;

import edu.depaul.cdm.se452.rfa.messaging.wsMessaging.UserMessage;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private final String username;

    public MyStompSessionHandler(String username) {
        this.username = username;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connected to WebSocket Server");

        session.send("/app/connect", username);
        System.out.println("Sent username: " + username);

        //subscribe to the route to handle messages
        session.subscribe("/topic/messages", new StompFrameHandler() {

            //handle incoming messages using StompFrameHandler
            @Override
            //convert into a message object
            public Type getPayloadType(StompHeaders headers) {
                return UserMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    //store if of message type
                    if (payload instanceof UserMessage userMessage) {
                        System.out.println("Received message: " + userMessage.getUser() + ": " + userMessage.getMessage());
                    } else {
                        System.err.println("Did not receive message type: " + payload.getClass());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //test
        System.out.println("Subscribed to /topic/messages");

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.err.println("Transport error: " + exception.getMessage());
        exception.printStackTrace();
    }

}
