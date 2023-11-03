package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.QuestionForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    private QuestionService SUT;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private PlatformRepository platformRepository;

    @BeforeEach
    public void setup(){
        this.SUT = new QuestionService(this.questionRepository, this.platformRepository);
    }

    @Test
    public void should_return_notfoundexception_when_id_invalid(){
        QuestionForm dummyQuestion = new QuestionForm();
        String expectedErrorMessage = "Question with id: " + 0 + " not found";
        when(this.questionRepository.findById(anyLong())).thenReturn(Optional.empty());
        long dummyId = 0;

        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () -> this.SUT.update(dummyId, dummyQuestion)
        );

        assertThat(expectedErrorMessage, is(thrown.getMessage()));


    }

//    findByPlatformId







}
