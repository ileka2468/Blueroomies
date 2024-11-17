package edu.depaul.cdm.se452.rfa.RepositoryTests.Agreement;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profileManagement.repository.ProfileRepository;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import edu.depaul.cdm.se452.rfa.agreements.entity.Agreement;
import edu.depaul.cdm.se452.rfa.agreements.repository.AgreementsRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AgreementRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoommateMatchesRepository matchRepository;

  @Autowired
  private AgreementsRepository agreementRepository;

  private User user1;
  private User user2;
  private User user3;

  private RoommateMatch match1;
  private RoommateMatch match2;

  private Agreement agreement1;
  private Agreement agreement2;

  @BeforeEach
  public void StoreRows() {
    user1 = new User();
    user1.setFirstName("User");
    user1.setLastName("A1");
    user1.setUsername("UserA1");
    user1.setPassword("andfjksnafikl82!?");

    user2 = new User();
    user2.setFirstName("User");
    user2.setLastName("A2");
    user2.setUsername("UserA2");
    user2.setPassword("jaksdnviu!?12Maoa");

    user3 = new User();
    user3.setFirstName("User");
    user3.setLastName("A3");
    user3.setUsername("UserA3");
    user3.setPassword("jnkasdhfijaci12?a!");

    match1 = new RoommateMatch();
    match1.setUserId1(user1);
    match1.setUserId2(user2);
    match1.setMatchScore(BigDecimal.valueOf(0.07));
    match1.setMatchTs(LocalDate.now());

    match2 = new RoommateMatch();
    match2.setUserId1(user1);
    match2.setUserId2(user3);
    match2.setMatchScore(BigDecimal.valueOf(0.07));
    match2.setMatchTs(LocalDate.now());

    //Note that only matched users can send agreements, and only one agreement should exist for a match at a given time
    //Note that validation should be performed before sending an agreement to ensure a second is never sent for any given match
    //Pending agreements (status 2) should be modifiable at any point by the sender or receiver. If modified by receiver, flip the sender and receiver if changes are made.
    agreement1 = new Agreement();
    agreement1.setMatch(match1);
    agreement1.setSender(user1); //User 1 and 2 are in match 1
    agreement1.setReceiver(user2);
    agreement1.setContent("This is a unit test agreement between 1 and 2.");
    agreement1.setStatus(2); //pending status
    agreement1.setDateSent(LocalDateTime.now());

    agreement2 = new Agreement();
    agreement2.setMatch(match2);
    agreement2.setSender(user3); //User 1 and 3 are in match 2
    agreement2.setReceiver(user1);
    agreement2.setContent("This is a unit test agreement between 3 and 1.");
    agreement2.setStatus(2); //pending status
    agreement2.setDateSent(LocalDateTime.now());

    long recordCountBeforeInsert = userRepository.count();
    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);
    long recordCountAfterInsert = userRepository.count();
    assert recordCountAfterInsert == recordCountBeforeInsert + 3;

    recordCountBeforeInsert = matchRepository.count();
    matchRepository.save(match1);
    matchRepository.save(match2);
    recordCountAfterInsert = matchRepository.count();
    assert recordCountAfterInsert == recordCountBeforeInsert + 2;

    recordCountBeforeInsert = agreementRepository.count();
    agreementRepository.save(agreement1);
    agreementRepository.save(agreement2);
    recordCountAfterInsert = agreementRepository.count();
    assert recordCountAfterInsert == recordCountBeforeInsert + 2;

  }

  @AfterEach
  public void DeleteRows(){
    long recordCountBeforeDelete = agreementRepository.count();
    agreementRepository.delete(agreement1);
    agreementRepository.delete(agreement2);
    long recordCountAfterDelete = agreementRepository.count();
    assert recordCountAfterDelete == recordCountBeforeDelete - 2;

    recordCountBeforeDelete = matchRepository.count();
    matchRepository.delete(match1);
    matchRepository.delete(match2);
    recordCountAfterDelete = matchRepository.count();
    assert recordCountAfterDelete == recordCountBeforeDelete - 2;

    recordCountBeforeDelete = userRepository.count();
    userRepository.delete(user1);
    userRepository.delete(user2);
    userRepository.delete(user3);
    recordCountAfterDelete = userRepository.count();
    assert recordCountAfterDelete == recordCountBeforeDelete - 3;

  }

  @Test
  public void MakeMatchAgreeUsers() {
    assert 1 == 1;
  }

}
