package edu.depaul.cdm.se452.rfa.messaging.wsMessaging.client;

import edu.depaul.cdm.se452.rfa.messaging.wsMessaging.UserMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MyStompClient {
    //allows us to connect to stomp server
    private StompSession session;
    private String username;


    public MyStompClient(String username) throws ExecutionException, InterruptedException {
        this.username = username;

        //Spring Websocket transport defines how the message is carried out
        Set<Transport> transports = new HashSet<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        //fallback if websocket is unavailable (convert back into list bc SockJsClient)
        SockJsClient sockJsClient = new SockJsClient(new ArrayList<>(transports));

        //Initializing STOMP client for websocket communication
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        //converter to convert Java objects into JSON data for messages sent and received
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(username);
        String url = "ws://localhost:8080/ws";

        //connect to websocket
        session = stompClient.connectAsync(url, sessionHandler).get();

    }
    public void sendMessage(UserMessage userMessage) {
        try {
            session.send("/app/message", userMessage);
            System.out.println("Sent message: " + userMessage.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectUser(String username){
        session.send("app/disconnect", username);
        System.out.println("Disconnected User: " + username);
    }

}//class
