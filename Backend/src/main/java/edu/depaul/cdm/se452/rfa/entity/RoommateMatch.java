package edu.depaul.cdm.se452.rfa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roommate_matches", schema = "public")
public class RoommateMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roommate_matches_id_gen")
    @SequenceGenerator(name = "roommate_matches_id_gen", sequenceName = "roommate_matches_match_id_seq", allocationSize = 1)
    @Column(name = "match_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User userId1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User userId2;

    @NotNull
    @Column(name = "match_score", nullable = false)
    private BigDecimal matchScore;

    @NotNull
    @Column(name = "match_ts", nullable = false)
    private LocalDate matchTs;

    @OneToMany(mappedBy = "match")
    private Set<Message> messages = new LinkedHashSet<>();

}