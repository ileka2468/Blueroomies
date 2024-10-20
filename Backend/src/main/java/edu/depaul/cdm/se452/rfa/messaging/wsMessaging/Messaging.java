package edu.depaul.cdm.se452.rfa.messaging.wsMessaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//Used for configuring Beans (Objects springs will manage)
@Configuration
//Sprig will set up a message broker services
@EnableWebSocketMessageBroker

public class Messaging implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //fallback withSockJS if websocket is not supported
        registry.addEndpoint("/ws").withSockJS();
    }



}//class
