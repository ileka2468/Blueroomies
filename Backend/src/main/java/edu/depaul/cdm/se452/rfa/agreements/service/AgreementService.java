package edu.depaul.cdm.se452.rfa.agreements.service;


import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import edu.depaul.cdm.se452.rfa.agreements.repository.AgreementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgreementService {

    private final AgreementsRepository agreementRepository;

    @Autowired
    public AgreementService(AgreementsRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    public List<Agreement> findAll() {
        return agreementRepository.findAll();
    }

    public Agreement findAgreementById(Integer id) {
        return agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found, ID: " + id));
    }

    public Agreement saveAgreement(Agreement agreement) {
        return agreementRepository.save(agreement);
    }

    public Agreement updateAgreement(Integer id, Agreement agreementDetails) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found, ID " + id));
        agreement.setMatch(agreementDetails.getMatch());
        agreement.setSender(agreementDetails.getSender());
        agreement.setReceiver(agreementDetails.getReceiver());
        agreement.setContent(agreementDetails.getContent());
        agreement.setStatus(agreementDetails.getStatus());
        agreement.setDateSent(agreementDetails.getDateSent());

        return agreementRepository.save(agreement);
    }

    public void deleteById(Integer id) {
        agreementRepository.deleteById(id);
    }
}//class
