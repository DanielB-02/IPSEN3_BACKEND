package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.AnswerForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.QuestionRepository;
import com.ipsen.spine.security.FicterSecurity;
import com.ipsen.spine.security.ReadOnlySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @FicterSecurity
    public Iterable<Answer> all(){
        return this.answerRepository.findAll();
    }

    @FicterSecurity
    public List<Answer> getByQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new NotFoundException("Question with id " + questionId + " not found");
        }
        return this.answerRepository.findByQuestionId(questionId);
    }


    @ReadOnlySecurity
    public Answer getById(long id){
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + "not found");
        }

        return optionalAnswer.get();
    }

    @FicterSecurity
    public Answer create(AnswerForm form){
        return save(form, new Answer());
    }

    @FicterSecurity
    public Answer update(AnswerForm form, long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        return save(form, optionalAnswer.get());
    }

    private Answer save(AnswerForm form, Answer entityToSave) {
        Optional<Question> fetchedQuestion = questionRepository.findById(form.questionId);
        if(fetchedQuestion.isEmpty()){
            throw new NotFoundException("Question with id: " + form.questionId + " not found");
        }
        entityToSave.setQuestion(fetchedQuestion.get());
        entityToSave.setTextAnswer(form.textAnswer);
        return this.answerRepository.save(entityToSave);
    }

    @FicterSecurity
    public void delete(long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);

        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }

        Answer answer = optionalAnswer.get();
        this.answerRepository.delete(answer);
    }


}
