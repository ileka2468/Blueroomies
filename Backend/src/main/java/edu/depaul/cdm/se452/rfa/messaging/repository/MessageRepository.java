package edu.depaul.cdm.se452.rfa.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.depaul.cdm.se452.rfa.messaging.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :currentUserId AND m.receiver.id = :otherUserId) OR " +
            "(m.sender.id = :otherUserId AND m.receiver.id = :currentUserId) " +
            "ORDER BY m.timestamp")
    List<Message> findMessagesBetweenUsers(@Param("currentUserId") Integer currentUserId,
                                           @Param("otherUserId") Integer otherUserId);
}
