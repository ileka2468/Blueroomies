package edu.depaul.cdm.se452.rfa.profileManagement.entity;
import edu.depaul.cdm.se452.rfa.profileManagement.JsonToMapConverter;
import lombok.Data;
import jakarta.persistence.*;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import java.util.Map;


@Entity
@Table(name = "Profiles")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")  // Explicit mapping to "profile_id" column
    private Integer profileId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // Explicit mapping to "user_id" foreign key
    private User user;

    @Column(name = "is_actively_looking", nullable = false)  // Explicit mapping to "is_actively_looking"
    private boolean isActivelyLooking;

    @Column(name = "bio", length = 500)  // Explicit mapping to "bio" column
    private String bio;

    @Column(name = "characteristics", columnDefinition = "jsonb")  // Explicit mapping to "characteristics" column
    @Convert(converter = JsonToMapConverter.class)  // Convert JSONB to Map in Java
    private Map<String, Object> characteristics;
}

