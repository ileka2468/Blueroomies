package edu.depaul.cdm.se452.rfa.messaging.service;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.messaging.entity.Message;
import edu.depaul.cdm.se452.rfa.messaging.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public MessageService(MessageRepository messageRepository, CustomUserDetailsService customUserDetailsService) {
        this.messageRepository = messageRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    public Message updateMessage(Integer id, Message messageDetails) {
        return messageRepository.findById(id).map(message -> {
            message.setContent(messageDetails.getContent());
            message.setSender(messageDetails.getSender());
            message.setReceiver(messageDetails.getReceiver());
            return messageRepository.save(message);
        }).orElseThrow(() -> new RuntimeException("Message not found, ID: " + id));
    }

    public List<Message> getMessagesBetweenUsers(String currentUsername, String otherUsername) {
        User currentUser = customUserDetailsService.getUserByUsername(currentUsername);
        User otherUser = customUserDetailsService.getUserByUsername(otherUsername);
        return messageRepository.findMessagesBetweenUsers(currentUser.getId(), otherUser.getId());
    }
}
