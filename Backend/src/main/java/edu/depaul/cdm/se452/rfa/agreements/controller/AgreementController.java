package edu.depaul.cdm.se452.rfa.agreements.controller;

import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import edu.depaul.cdm.se452.rfa.agreements.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agreements")
public class AgreementController {

    private final AgreementService agreementService;

    @Autowired
    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    //Create
    @PostMapping
    public Agreement createAgreement(@RequestBody Agreement agreement) {
        return agreementService.saveAgreement(agreement);
    }

    // Read
    @GetMapping
    public List<Agreement> getAllAgreements() {
        return agreementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agreement> getAgreementById(@PathVariable Integer id) {
        Agreement agreement = agreementService.findAgreementById(id); // Uses findAgreementById in the service
        return ResponseEntity.ok(agreement);
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Agreement> updateAgreement(@PathVariable Integer id, @RequestBody Agreement agreementDetails) {
        Agreement updatedAgreement = agreementService.updateAgreement(id, agreementDetails);
        return ResponseEntity.ok(updatedAgreement);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgreement(@PathVariable Integer id) {
        agreementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}//class
