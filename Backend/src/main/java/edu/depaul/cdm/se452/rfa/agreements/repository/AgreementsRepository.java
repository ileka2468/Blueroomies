package edu.depaul.cdm.se452.rfa.agreements.repository;

import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementsRepository extends JpaRepository<Agreement, Integer> {
    public Agreement findAgreementById(int agreementId);
}
