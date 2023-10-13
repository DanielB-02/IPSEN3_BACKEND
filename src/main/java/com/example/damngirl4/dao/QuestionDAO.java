package com.example.damngirl4.dao;

import com.example.damngirl4.exception.NotFoundException;
import com.example.damngirl4.model.ApiResponse;
import com.example.damngirl4.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionDAO {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question newQuestion){
        Question question = this.questionRepository.save(newQuestion);
        return question;
    }

    public Iterable<Question> readAll(){
        return questionRepository.findAll();
    }

    public Optional<Question> readSingle(Long id){
        return questionRepository.findById(id);
    }

    public Question update(Long id, Question newQuestion){
        Optional<Question> fetchedQuestion = questionRepository.findById(id);
        if(fetchedQuestion.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        Question question = fetchedQuestion.get();
        question.setTextQuestion(newQuestion.getTextQuestion());
        return questionRepository.save(question);
    }

    public void delete(Long id){
        if (!questionRepository.existsById(id)) {
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        questionRepository.deleteById(id);
    }
}
