package edu.depaul.cdm.se452.rfa.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.depaul.cdm.se452.rfa.messaging.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
