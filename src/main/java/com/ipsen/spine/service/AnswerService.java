package com.ipsen.spine.service;

import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public ArrayList<Answer> all(){
        ArrayList<Answer> allAnswers = (ArrayList<Answer>) this.answerRepository.findAll();
        return allAnswers;
    }

    public List<Answer> getByQuestionId(Long questionId) {
        return this.answerRepository.findByQuestionId(questionId);
    }

    public Answer getById(long id){
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + "not found");
        }

        return optionalAnswer.get();
    }

    public Answer save(Answer newAnswer){
        Answer answer = this.answerRepository.save(newAnswer);
        return answer;
    }

    public Answer replace(Answer newAnswer, long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);

        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }

        Answer currentAnswer = optionalAnswer.get();
        currentAnswer.setTextAnswer(newAnswer.getTextAnswer());

        this.answerRepository.save(currentAnswer);
        return currentAnswer;
    }


    public Answer update(Answer updatedAnswer, long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);

        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }

        Answer currentAnswer = optionalAnswer.get();
        currentAnswer.setTextAnswer(updatedAnswer.getTextAnswer());

        this.answerRepository.save(currentAnswer);
        return currentAnswer;
    }

    public void delete(long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);

        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }

        Answer answer = optionalAnswer.get();
        this.answerRepository.delete(answer);
    }


}
