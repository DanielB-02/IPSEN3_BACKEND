package com.ipsen.spine.testdata;

import com.ipsen.spine.model.Role;
import com.ipsen.spine.model.User;
import com.ipsen.spine.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.ipsen.spine.model.Role.*;

@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void saveTestData() {
        createUser("Arie", "Administrator", "admin@spine.com", "12345", ADMIN);
        createUser("Floris", "Ficter", "ficter@spine.com", "12345", FICTER);
        createUser("Robert", "Readonly", "readonly@spine.com", "12345", READONLY);
    }

    private User createUser(String firstName, String lastName, String email, String password, Role role) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role).build();
        return userRepository.save(user);
    }
}
