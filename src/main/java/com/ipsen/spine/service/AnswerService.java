package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.AnswerForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.QuestionRepository;
import com.ipsen.spine.security.PermissionDomeinBeheerFicter;
import com.ipsen.spine.security.PermissionLezen;
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

    @PermissionLezen
    public Iterable<Answer> all(){
        return this.answerRepository.findAll();
    }

    @PermissionLezen
    public List<Answer> getByQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new NotFoundException("Question with id " + questionId + " not found");
        }
        return this.answerRepository.findByQuestionId(questionId);
    }


    @PermissionLezen
    public Answer getById(long id){
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + "not found");
        }

        return optionalAnswer.get();
    }

    @PermissionDomeinBeheerFicter
    public Answer create(AnswerForm form){
        return save(form, new Answer());
    }

    @PermissionDomeinBeheerFicter
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
        entityToSave.setScore(form.score);
        return this.answerRepository.save(entityToSave);
    }

    @PermissionDomeinBeheerFicter
    public void delete(long id) throws NotFoundException{
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);

        if(optionalAnswer.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }

        Answer answer = optionalAnswer.get();
        this.answerRepository.delete(answer);
    }


}



