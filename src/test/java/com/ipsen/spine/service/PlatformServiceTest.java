package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.PlatformScoreResult;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("personal")
public class PlatformServiceTest {

    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private PlatformService platformService;

    @Test
    @Transactional
    public void order_by_score_ascending() {
        createPlatform("Twitter", 3, 0, 1);
        createPlatform("Instagram", 1, 1, 1);
        createPlatform("Snapchat", 0, 3, 3);

        List<PlatformScoreResult> platforms = platformService.readAllSortByScore();

        assertEquals(3, platforms.size());
        assertEquals("Snapchat", platforms.get(0).platformName);
        assertEquals("Twitter", platforms.get(1).platformName);
        assertEquals("Instagram", platforms.get(2).platformName);
    }

    @Test
    @Transactional
    public void order_by_score_descending() {
        createPlatform("Twitter", 3, 0, 1);
        createPlatform("Instagram", 1, 1, 1);
        createPlatform("Snapchat", 0, 3, 3);

        List<PlatformScoreResult> platforms = platformService.readAllSortByScoreDesc();

        assertEquals(3, platforms.size());
        assertEquals("Snapchat", platforms.get(2).platformName);
        assertEquals("Twitter", platforms.get(1).platformName);
        assertEquals("Instagram", platforms.get(0).platformName);
    }

    @Test
    @Transactional
    public void get_answer_of_platform() {
        Platform platform = createPlatform("Twitter", 3);
        List<Answer> answers = answerRepository.findByQuestionPlatform(platform);
        assertEquals(1, answers.size());
        assertEquals(3, answers.get(0).getScore());
    }

    @Test
    public void should_return_list_of_questions_with_id(){

        String platformName = "Facebook";
        Platform platform = createPlatform(platformName, 3);

        List<Question> questionList = this.questionRepository.findByPlatformId(platform.getId());

        for (Question question: questionList
        ) {
            System.out.println(question.getTextQuestion());

        }

        assertEquals(1, questionList.size());
    }


    private Platform createPlatform(String platformName, Integer... scores){
        var platform = Platform.builder()
                .platformName(platformName).build();
        platformRepository.save(platform);
        for (Integer score : scores) {
            Question question = new Question();
            question.setPlatform(platform);
            question.setTextQuestion("Question does not matter");
            questionRepository.save(question);
            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setTextAnswer("Answer does not matter");
            answer.setScore(score);
            answerRepository.save(answer);
        }
        return platform;
    }

}
