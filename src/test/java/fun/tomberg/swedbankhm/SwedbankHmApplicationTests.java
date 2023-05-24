package fun.tomberg.swedbankhm;

import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.repository.RoleRepository;
import fun.tomberg.swedbankhm.repository.UserRepository;
import fun.tomberg.swedbankhm.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class SwedbankHmApplicationTests {

    // SERVICES
    @Autowired
    UserServiceImpl userService;

    // REPOS
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void contextLoads() {

    }

    @Test
    void crudTest() {
        User user = new User(0, "Yevhenii Tomberg", "01/04/2002", "Paul Kerese 14", "yevhenii@tomberg.com", "password", true, "password", null);

        userService.saveUser(user);
        assertTrue(userService.isAlreadyExists(user.getEmail()));
        System.out.println("USER SAVING PASSED");

        userService.disableUser(user);
        assertFalse(user.isEnabled());
        System.out.println("USER DISABLING PASSED");

        userService.saveUser(userService.switchUserStatus(user, new BindException(user, "user"), user.getId()));
        assertTrue(user.isEnabled());
        System.out.println("USER SWITCHING STATUS PASSED");

        Iterable<User> users = userRepository.findAll();
        assertThat(users).extracting(User::getFullName).containsOnly("Yevhenii Tomberg");
        System.out.println("USER EXISTING PASSED");

        userRepository.deleteAll();
        assertThat(userRepository.findAll()).isEmpty();
        System.out.println("USER DELETING PASSED");

    }

}
