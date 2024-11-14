package edu.depaul.cdm.se452.rfa.messaging.entity;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages", schema = "public")
public class Message {
    @Id
    @ColumnDefault("nextval('messages_message_id_seq')")
    @Column(name = "message_id", nullable = false)
    private Integer id;

    @Size(max = 500)
    @NotNull
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @NotNull
    private LocalDateTime timestamp;

}