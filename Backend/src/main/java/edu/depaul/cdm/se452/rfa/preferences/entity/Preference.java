package edu.depaul.cdm.se452.rfa.preferences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer preferenceId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "pref_characteristics", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> prefCharacteristics;

   
}
