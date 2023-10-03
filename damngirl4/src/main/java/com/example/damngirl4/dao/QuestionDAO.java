package com.example.damngirl4.dao;

import com.example.damngirl4.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionDAO {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question newQuestion){
        Question answer = this.questionRepository.save(newQuestion);
        return answer;
    }
}
