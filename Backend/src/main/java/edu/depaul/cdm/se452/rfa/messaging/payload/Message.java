package edu.depaul.cdm.se452.rfa.messaging.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;
    private String content;
    private String sender;
    private String receiver;
    private LocalDateTime timestamp;
}
