package com.ipsen.spine.testdata;

import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.model.Role;
import com.ipsen.spine.model.User;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.repository.QuestionRepository;
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
    private final PlatformRepository platformRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void saveTestData() {
        createUser("Arie", "Administrator", "admin@spine.com", "12345", ADMIN);
        createUser("Floris", "Ficter", "ficter@spine.com", "12345", FICTER);
        createUser("Robert", "Readonly", "readonly@spine.com", "12345", READONLY);
        Platform platform = createPlatform("x");
        createQuestions("question", platform);
    }

    private void createUser(String firstName, String lastName, String email, String password, Role role) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role).build();
            userRepository.save(user);
    }

    private Platform createPlatform(String platformName){
        var platform = Platform.builder()
                .platformName(platformName).build();
            platformRepository.save(platform);
            return platform;
    }

    private void createQuestions(String textQuestion, Platform platform){
        var question = Question.builder()
                .textQuestion(textQuestion)
                .build();
        question.setPlatform(platform);
        questionRepository.save(question);

        var question1 = Question.builder()
                .textQuestion(textQuestion.toUpperCase())
                .build();
        question1.setPlatform(platform);
        questionRepository.save(question1);
    }

}
