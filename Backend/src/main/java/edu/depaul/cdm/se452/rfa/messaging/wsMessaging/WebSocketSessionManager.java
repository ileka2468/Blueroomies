package edu.depaul.cdm.se452.rfa.messaging.wsMessaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WebSocketSessionManager {
    private final Set<String> activeUsernames = new HashSet<>();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void addUsername(String username) {
        if (activeUsernames.add(username)) {
            System.out.println("Username added: " + username);
        } else {
            System.out.println("Username already exists: " + username);
        }
    }

    public void removeUsername(String username) {
        if (activeUsernames.remove(username)) {
            System.out.println("Username removed: " + username);
        } else {
            System.out.println("Username not found: " + username);
        }
    }

    public void broadcastActiveUsernames() {
        messagingTemplate.convertAndSend("/topic/users", activeUsernames);
        System.out.println("Broadcasting active usernames: " + activeUsernames);
    }

    public boolean isUsernameActive(String username) {
        return activeUsernames.contains(username);
    }

}
