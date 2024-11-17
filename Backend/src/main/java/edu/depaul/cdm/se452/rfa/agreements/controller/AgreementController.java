package edu.depaul.cdm.se452.rfa.agreements.controller;

import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import edu.depaul.cdm.se452.rfa.agreements.service.AgreementService;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.messaging.payload.SocketsAuthResponse;
import edu.depaul.cdm.se452.rfa.roomate.service.MatchStorageService;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agreements")
public class AgreementController {

    private final AgreementService agreementService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final MatchStorageService matchStorageService;

    @Autowired
    public AgreementController(AgreementService agreementService, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService, MatchStorageService matchStorageService) {
        this.agreementService = agreementService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.matchStorageService = matchStorageService;
    }

    public static ResponseEntity<?> authenticate(HttpServletRequest request, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        String username = null;

        if (accessToken != null) {
            username = jwtTokenProvider.getUsernameFromJWT(accessToken);
        }

        if (accessToken == null || username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        if (!accessTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
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

    //Checks if the opened agreement was sent by the current user and returns a bool
    @GetMapping("/checkSender")
    public ResponseEntity<Boolean> checkAgreementSenderByUserId(@RequestParam("otherUser") String otherUsername, 
                                                                                                      HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        String currentUsername = extractUsername(request);

        Agreement agreement = agreementService.getAgreementBetweenUsers(currentUsername, otherUsername);
        boolean isSender = false;
        User userChecking = customUserDetailsService.getUserByUsername(extractUsername(request));
        if(agreement.getSender() == userChecking)
        {
            isSender = true;
        }
        else{
            isSender = false;
        }
        
        return ResponseEntity.ok(isSender);
    }

    //Rejects the open agreement and sets status to 0 (rejected)
    @PostMapping("/reject")
    public ResponseEntity<?> rejectAgreement(@RequestBody edu.depaul.cdm.se452.rfa.agreements.payload.AgreementActionRequest agreementActionRequest, HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);

        String currentUsername = extractUsername(request);
        Agreement agreement = agreementService.getAgreementBetweenUsers(currentUsername, agreementActionRequest.getOtherUsername());

        agreement.setStatus(0);
        agreementService.updateAgreement(agreement.getId(), agreement);

        return ResponseEntity.ok().build();
    }

    //Accepts the open agreement and sets status to 1 (accepted)
    @PostMapping("/accept")
    public ResponseEntity<?> acceptAgreement(@RequestBody edu.depaul.cdm.se452.rfa.agreements.payload.AgreementActionRequest agreementActionRequest, HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);

        String currentUsername = extractUsername(request);
        Agreement agreement = agreementService.getAgreementBetweenUsers(currentUsername, agreementActionRequest.getOtherUsername());

        agreement.setStatus(1);
        agreementService.updateAgreement(agreement.getId(), agreement);

        return ResponseEntity.ok().build();
    }

    //Returns a bool based on if the agreement exists between two users
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAgreementExists(@RequestParam("otherUser") String otherUsername, HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);

        String currentUsername = extractUsername(request);
        boolean exists = agreementService.checkAgreementBetweenUsers(currentUsername, otherUsername);

        return ResponseEntity.ok(exists);
    }

    //Returns an integer containing the status of the open agreement
    @GetMapping("/status")
    public ResponseEntity<Integer> getAgreementState(@RequestParam("otherUser") String otherUsername, HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);

        String currentUsername = extractUsername(request);
        Agreement agreement = agreementService.getAgreementBetweenUsers(currentUsername, otherUsername);

        return ResponseEntity.ok(agreement.getStatus());
    }

    //Creates a new agreement between matched users and deletes the old one if necessary
    //Needs input validation
    @PostMapping("/create")
    public ResponseEntity<?> makeAgreementByUserId(@RequestBody edu.depaul.cdm.se452.rfa.agreements.payload.AgreementCreateRequest agreementCreateRequest, HttpServletRequest request){
        
        authenticate(request, jwtTokenProvider, customUserDetailsService);

        Agreement agreement = new Agreement();

        //This is really excessive and slow but new functionality would need to be added to matches to find based on two users
        List<RoommateMatch> matches = matchStorageService.findAllMatches();
        User userReceiver = customUserDetailsService.getUserByUsername(agreementCreateRequest.getReceiver());
        User userSender = customUserDetailsService.getUserByUsername(extractUsername(request));
        RoommateMatch targetMatch = new RoommateMatch();
        for(int i = 0; i < matches.size(); i++)
        {
            if(matches.get(i).getUserId1() == userReceiver && matches.get(i).getUserId2() == userSender)
            {
                targetMatch = matches.get(i);
                break;
            }
            if(matches.get(i).getUserId1() == userSender && matches.get(i).getUserId2() == userReceiver)
            {
                targetMatch = matches.get(i);
                break;
            }
        }

        agreement.setMatch(targetMatch);
        agreement.setReceiver(userReceiver);
        agreement.setSender(userSender);
        agreement.setContent(agreementCreateRequest.getContent());
        agreement.setDateSent(LocalDateTime.now());
        agreement.setStatus(2);

        boolean alreadyExists = agreementService.checkAgreementBetweenUsers(extractUsername(request), agreementCreateRequest.getReceiver());
        if(alreadyExists){
            Agreement oldAgreement = agreementService.getAgreementBetweenUsers(extractUsername(request), agreementCreateRequest.getReceiver());
            agreementService.deleteById(oldAgreement.getId());
        }

        agreementService.saveAgreement(agreement);

        return ResponseEntity.ok().build();
    }

    //Returns agreement info for viewing, parsed to exclude sensitive info
    //Needs validation so only matched agreements are ensured to be seen
    @GetMapping("/view")
    public ResponseEntity<edu.depaul.cdm.se452.rfa.agreements.payload.AgreementViewResponse> getAgreementByUserId(@RequestParam("otherUser") String otherUsername, 
                                                                                                      HttpServletRequest request){
        authenticate(request, jwtTokenProvider, customUserDetailsService);
        String currentUsername = extractUsername(request);

        Agreement agreement = agreementService.getAgreementBetweenUsers(currentUsername, otherUsername);
        edu.depaul.cdm.se452.rfa.agreements.payload.AgreementViewResponse agreementData = new edu.depaul.cdm.se452.rfa.agreements.payload.AgreementViewResponse();
        agreementData.setContent(agreement.getContent());
        
        return ResponseEntity.ok(agreementData);
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

    public  String extractUsername(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        String username = null;

        if (accessToken != null) {
            username = jwtTokenProvider.getUsernameFromJWT(accessToken);
        }

        if (accessToken == null || username == null) {
            return null;
        }
        return username;
    }

}//class
