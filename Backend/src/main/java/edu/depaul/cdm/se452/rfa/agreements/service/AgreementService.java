package edu.depaul.cdm.se452.rfa.agreements.service;


import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import edu.depaul.cdm.se452.rfa.agreements.repository.AgreementsRepository;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementService {

    private final AgreementsRepository agreementRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AgreementService(AgreementsRepository agreementRepository, CustomUserDetailsService customUserDetailsService) {
        this.agreementRepository = agreementRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public List<Agreement> findAll() {
        return agreementRepository.findAll();
    }

    public Agreement findAgreementById(Integer id) {
        return agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found, ID: " + id));
    }

    //Gets an agreement using two users using their usernames. If none exists, return a dummy.
    public Agreement getAgreementBetweenUsers(String currentUsername, String otherUsername) {
        User currentUser = customUserDetailsService.getUserByUsername(currentUsername);
        User otherUser = customUserDetailsService.getUserByUsername(otherUsername);
        List<Agreement> agreements = agreementRepository.findAgreementBetweenUsers(currentUser.getId(), otherUser.getId());
        if(!agreements.isEmpty())
        {
            return agreementRepository.findAgreementBetweenUsers(currentUser.getId(), otherUser.getId()).getFirst();
        }
        else
        {
            Agreement agreement = new Agreement();
            agreement.setContent("");
            agreement.setStatus(2);
            return agreement;
        }
    }

    //Retruns a bool if an agreement exists between two users using their usernames.
    public boolean checkAgreementBetweenUsers(String currentUsername, String otherUsername){
        User currentUser = customUserDetailsService.getUserByUsername(currentUsername);
        User otherUser = customUserDetailsService.getUserByUsername(otherUsername);
        List<Agreement> agreements = agreementRepository.findAgreementBetweenUsers(currentUser.getId(), otherUser.getId());
        if(!agreements.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
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
