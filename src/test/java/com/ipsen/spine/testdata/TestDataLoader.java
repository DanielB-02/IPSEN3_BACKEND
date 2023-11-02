package com.ipsen.spine.testdata;

import com.ipsen.spine.model.*;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.repository.QuestionRepository;
import com.ipsen.spine.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.ipsen.spine.model.Role.*;

@Component
@RequiredArgsConstructor
@Profile("testdata")
public class TestDataLoader {
    private static final List<String> QUESTIONS = List.of(
            "Jurisprudentie",
            "Online Outreach",
            "Chat met Fier",
            "Meldingen overig",
            "Wetenschappelijke artikelen",
            "Krantenartikelen"
    );

    private static final Map<String, List<AnswerScore>> ANSWERS = Map.of(
            "Twitter", List.of(
                    new AnswerScore(3, "nvt, feit (seksuele uitbuiting, criminele uitbuiting, seksueel geweld), rol technologie (ronselen, controle, aanbod]"),
                    new AnswerScore(1, "Ja, 2023"),
                    new AnswerScore(0, "Nee"),
                    new AnswerScore(1, "2022-2023, ronselen"),
                    new AnswerScore(1, "[The British Journal of Social Work, 7-9-2023, Engels, https://academic.oup.com/bjsw/advance-article/doi/10.1093/bjsw/bcad201/7263182]"),
                    new AnswerScore(1, "[NRC Handelsblad, 6-6-2023,Nederlands,https://www.nrc.nl/nieuws/2023/06/06/bescherm-jongeren-tegen-sociale-media-opgroeien-zonder-cyberstress-is-al-ingewikkeld-zat-a4166484]")
            ),
            "Facebook", List.of(
                    new AnswerScore(3, "nvt, feit (seksuele uitbuiting, criminele uitbuiting, seksueel geweld), rol technologie (ronselen, controle, aanbod]"),
                    new AnswerScore(3, "Ja, 2021, 2022 en 2023"),
                    new AnswerScore(1, "Ja, 2022"),
                    new AnswerScore(0, "nvt"),
                    new AnswerScore(1, "International Journal of Environmental Research and Public Health, 12-8-2022, Engels, https://www.ncbi.nlm.nih.gov/pmc/articles/PMC9407706/ "),
                    new AnswerScore(1, "De Telegraaf, 30-6-2021, Nederlands, https://www.telegraaf.nl/financieel/702193461/sociale-media-grootste-gevaar-voor-seksuele-uitbuiting-van-kinderen ")
            ),
            "Instagram", List.of(
                    new AnswerScore(2, "nvt, feit (seksuele uitbuiting, criminele uitbuiting, seksueel geweld), rol technologie (aanbod]"),
                    new AnswerScore(0, "Nee"),
                    new AnswerScore(1, "Ja, 2020"),
                    new AnswerScore(0, "nvt"),
                    new AnswerScore(1, "Pediatrics, 1-11-2017, Engels, https://publications.aap.org/pediatrics/article/140/Supplement_2/S67/34168/Benefits-and-Costs-of-Social-Media-in-Adolescence"),
                    new AnswerScore(1, "NOS, 29-3-2022, Nederlands, https://stories.nos.nl/artikel/2423085-als-social-media-zo-slecht-is-moeten-we-er-dan-niet-mee-stoppen ")
            ),
            "Snapchat", List.of(
                    new AnswerScore(2, "nvt, feit (seksuele uitbuiting), rol technologie (ronselen, controle]"),
                    new AnswerScore(0, "Nee"),
                    new AnswerScore(0, "Nee"),
                    new AnswerScore(0, "Nvt"),
                    new AnswerScore(1, "Telematics and Informatics Reports, 9-2023, Engels, https://www.sciencedirect.com/science/article/pii/S2772503023000476 "),
                    new AnswerScore(1, "Volkskrant, 31-7-2023, Nederlands, https://www.volkskrant.nl/columns-opinie/de-snap-kaart-van-snapchat-is-een-kaart-waarvan-staatsveiligheidsagenten-gaan-watertanden~baa4248d/ ")
            ),
            "Tiktok", List.of(
                    new AnswerScore(2, "nvt, feit (seksuele uitbuiting, seksueel geweld), rol technologie (aanbod]"),
                    new AnswerScore(1, "Ja, 2023"),
                    new AnswerScore(1, "Ja, 2022"),
                    new AnswerScore(0, "Nvt"),
                    new AnswerScore(1, "International Journal of Environmental Research and Public Health, 18-8-2021, Engels, https://www.ncbi.nlm.nih.gov/pmc/articles/PMC8393543/"),
                    new AnswerScore(1, "Psychiatric Times, 22-3-2022, Engels, https://www.psychiatrictimes.com/view/tiktok-biggest-concerns-for-children-and-adolescents ")
            )
    );

    private final UserRepository userRepository;
    private final PlatformRepository platformRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void saveTestData() {
        createUser("Arie", "Administrator", "admin@spine.com", "12345", ADMIN);
        createUser("Floris", "Ficter", "ficter@spine.com", "12345", FICTER);
        createUser("Robert", "Readonly", "readonly@spine.com", "12345", READONLY);
        Platform twitter = createPlatform("Twitter");
        Platform facebook = createPlatform("Facebook");
        Platform instagram = createPlatform("Instagram");
        Platform snapchat = createPlatform("Snapchat");
        Platform tiktok = createPlatform("Tiktok");
        List<Platform> platforms = List.of(twitter, instagram, facebook, tiktok, snapchat);
        platforms.forEach(this::createQuestionsAndAnswers);
    }

    private void createQuestionsAndAnswers(Platform platform) {
        int position = 0;
        List<AnswerScore> answers = ANSWERS.get(platform.getPlatformName());
        for (String questionText : QUESTIONS) {
            Question question = createQuestion(platform, questionText);
            AnswerScore answerScore = answers.get(position);
            createAnswer(question, answerScore.getText(), answerScore.getScore());
            position++;
        }
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

    private Question createQuestion(Platform platform, String textQuestion){
        var question = Question.builder()
                .platform(platform)
                .textQuestion(textQuestion)
                .build();
        return questionRepository.save(question);
    }

    private Answer createAnswer(Question question, String textAnswer, int score) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setTextAnswer(textAnswer);
        answer.setScore(score);
        return answerRepository.save(answer);
    }
}
