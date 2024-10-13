package edu.depaul.cdm.se452.rfa;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "agreements", schema = "public")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agreements_id_gen")
    @SequenceGenerator(name = "agreements_id_gen", sequenceName = "agreements_agreement_id_seq", allocationSize = 1)
    @Column(name = "agreement_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "match_id", nullable = false)
    private RoommateMatch match;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "status")
    private Integer status;

    @Column(name = "date_sent")
    private Instant dateSent;

}
