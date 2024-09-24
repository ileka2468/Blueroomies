package edu.depaul.cdm.se452.rfa.invalidatedtokens.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "invalidatedtokens", schema = "public")
public class Invalidatedtoken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invalidatedtokens_id_gen")
    @SequenceGenerator(name = "invalidatedtokens_id_gen", sequenceName = "invalidatedtokens_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "token_hash", nullable = false, length = 64, unique = true)
    private String tokenHash;

    @NotNull
    @Column(name = "expiry_time", nullable = false)
    private OffsetDateTime expiryTime;

    @NotNull
    @Column(name = "invalidated_at", nullable = false)
    private OffsetDateTime invalidatedAt;

}