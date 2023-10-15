package com.ipsen.spine.dao;

import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.QuestionRepository;
import com.ipsen.spine.security.FicterSecurity;
import com.ipsen.spine.security.ReadOnlySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuestionDAO {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question newQuestion){
        Question question = this.questionRepository.save(newQuestion);
        return question;
    }
    @FicterSecurity
    public Iterable<Question> readAll(){
        return questionRepository.findAll();
    }

    @ReadOnlySecurity
    public Optional<Question> readSingle(Long id){
        return questionRepository.findById(id);
    }

    @FicterSecurity
    public Question update(Long id, Question newQuestion){
        Optional<Question> fetchedQuestion = questionRepository.findById(id);
        if(fetchedQuestion.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        Question question = fetchedQuestion.get();
        question.setTextQuestion(newQuestion.getTextQuestion());
        return questionRepository.save(question);
    }

    @FicterSecurity
    public void delete(Long id){
        if (!questionRepository.existsById(id)) {
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        questionRepository.deleteById(id);
    }
}
