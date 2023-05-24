package fun.tomberg.swedbankhm;

import fun.tomberg.swedbankhm.entity.Role;
import fun.tomberg.swedbankhm.entity.User;
import fun.tomberg.swedbankhm.repository.RoleRepository;
import fun.tomberg.swedbankhm.repository.UserRepository;
import fun.tomberg.swedbankhm.service.implementation.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SwedbankHmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwedbankHmApplication.class, args);
    }


    @Bean
    CommandLineRunner run(RoleRepository roleRepository,
                          UserRepository userRepository,
                          UserServiceImpl userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {

            Role role1 = new Role(1, "ROLE_USER", null);
            Role role2 = new Role(2, "ROLE_ADMIN", null);

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.save(role1));
            roles.add(roleRepository.save(role2));

            User user = User
                    .builder()
                    .id(1)
                    .address(null)
                    .fullName("Yevhenii Tomberg")
                    .dateOfBirth(null)
                    .password(bCryptPasswordEncoder.encode("password"))
                    .commitPassword("password")
                    .email("yevhenii@tomberg.com")
                    .isEnabled(true)
                    .roles(roles)
                    .build();

            if (!userService.isAlreadyExists(user.getEmail())) {
                userRepository.save(user);
            }
        };
    }
}
