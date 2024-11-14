package edu.depaul.cdm.se452.rfa.messaging.controller;


import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.messaging.payload.SocketsAuthResponse;
import edu.depaul.cdm.se452.rfa.messaging.service.MessageService;
import edu.depaul.cdm.se452.rfa.messaging.entity.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public MessageController(MessageService messageService, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.messageService = messageService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateSocket(HttpServletRequest request) {
        return authenticate(request, jwtTokenProvider, customUserDetailsService);
    }

    public static ResponseEntity<?> authenticate(HttpServletRequest request, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        String username = null;

        if (accessToken != null) {
            username = jwtTokenProvider.getUsernameFromJWT(accessToken);
        }

        if (accessToken == null || username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        if (!accessTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = customUserDetailsService.getUserByUsername(username);
        return ResponseEntity.ok(new SocketsAuthResponse(user.getId(), user.getUsername(), user.getUserRoles()));
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<?> createMessage(@RequestBody edu.depaul.cdm.se452.rfa.messaging.payload.Message message,  HttpServletRequest request) {
        log.info(String.valueOf(message));
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        User sender = customUserDetailsService.getUserByUsername(message.getSender());
        User receiver = customUserDetailsService.getUserByUsername(message.getReceiver());
        Message messageEntity = new Message(message.getId(), message.getContent(), sender, receiver, message.getTimestamp());
        Message newMessage = messageService.createMessage(messageEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Read
    @GetMapping("/conversation")
    public ResponseEntity<List<edu.depaul.cdm.se452.rfa.messaging.payload.Message>> getConversationHistory(
            @RequestParam("otherUser") String otherUsername,
            HttpServletRequest request) {

        authenticate(request, jwtTokenProvider, customUserDetailsService);
        String currentUsername = extractUsername(request);


        List<Message> messages = messageService.getMessagesBetweenUsers(currentUsername, otherUsername);

        // Map each MessageEntity to the DTO and return
        List<edu.depaul.cdm.se452.rfa.messaging.payload.Message> messageDTOs = messages.stream()
                .map(msg -> {
                    edu.depaul.cdm.se452.rfa.messaging.payload.Message dto = new edu.depaul.cdm.se452.rfa.messaging.payload.Message();
                    dto.setId(msg.getId());
                    dto.setContent(msg.getContent());
                    dto.setSender(msg.getSender().getUsername());
                    dto.setReceiver(msg.getReceiver().getUsername());
                    dto.setTimestamp(msg.getTimestamp());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id, HttpServletRequest request) {
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Integer id, @RequestBody Message messageDetails, HttpServletRequest request) {
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        try {
            Message updatedMessage = messageService.updateMessage(id, messageDetails);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id, HttpServletRequest request) {
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public  String extractUsername(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        String username = null;

        if (accessToken != null) {
            username = jwtTokenProvider.getUsernameFromJWT(accessToken);
        }

        if (accessToken == null || username == null) {
            return null;
        }
        return username;
    }
}
