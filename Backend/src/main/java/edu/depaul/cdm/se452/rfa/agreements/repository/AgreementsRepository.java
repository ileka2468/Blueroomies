package edu.depaul.cdm.se452.rfa.agreements.repository;

import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementsRepository extends JpaRepository<Agreement, Integer> {
    @Query("SELECT m FROM Agreement m WHERE " +
    "(m.sender.id = :currentUserId AND m.receiver.id = :otherUserId) OR " +
    "(m.sender.id = :otherUserId AND m.receiver.id = :currentUserId) " +
    "ORDER BY m.dateSent")
    List<Agreement> findAgreementBetweenUsers(@Param("currentUserId") Integer currentUserId,
                                              @Param("otherUserId") Integer otherUserId);
}
