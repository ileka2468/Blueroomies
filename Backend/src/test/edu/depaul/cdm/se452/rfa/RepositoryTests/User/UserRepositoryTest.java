package edu.depaul.cdm.se452.rfa.RepositoryTests.User;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    public User makeUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test");
        user.setUsername("Test");
        return user;
    }

    @Test
    public void testCreateUser() {
        long recordCountBeforeInsert = userRepository.count();
        User user = makeUser();
        userRepository.save(user);
        long recordCountAfterInsert = userRepository.count();
        assert recordCountAfterInsert == recordCountBeforeInsert + 1;
    }

    @Test
    public void testUpdateUser() {
        User user = makeUser();
        userRepository.save(user);

        user.setUsername("Changed Username");
        userRepository.save(user);

        User updatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assert updatedUser.getUsername().equals(user.getUsername());
    }

    @Test
    public void testDeleteUser() {
        User user = makeUser();
        userRepository.save(user);
        userRepository.delete(user);
        assert userRepository.findById(user.getId()).isEmpty();
    }

}
