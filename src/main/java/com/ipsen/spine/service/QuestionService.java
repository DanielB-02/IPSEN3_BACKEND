package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.QuestionForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.repository.QuestionRepository;
import com.ipsen.spine.security.PermissionDomeinBeheerFicter;
import com.ipsen.spine.security.PermissionLezen;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final PlatformRepository platformRepository;

    public QuestionService(QuestionRepository questionRepository, PlatformRepository platformRepository) {
        this.questionRepository = questionRepository;
        this.platformRepository = platformRepository;
    }

    @PermissionLezen
    public Iterable<Question> readAll(){
        return questionRepository.findAll();
    }

    @PermissionLezen
    public Question readSingle(Long id){
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new NotFoundException("Question with ID " + id + " not found");
        }
        return question.get();
    }

    @PermissionDomeinBeheerFicter
    public Question create(QuestionForm questionForm){
        return save(questionForm, new Question());
    }

    @PermissionDomeinBeheerFicter
    public Question update(Long id, QuestionForm questionForm){
        Optional<Question> fetchedQuestion = questionRepository.findById(id);
        if(fetchedQuestion.isEmpty()){
            throw new NotFoundException("Question with id: " + id + " not found");
        }
        return save(questionForm, fetchedQuestion.get());
    }

    @PermissionDomeinBeheerFicter
    private Question save(QuestionForm form, Question question) {
        Optional<Platform> fetchedPlatform = platformRepository.findById(form.platformId);
        if(fetchedPlatform.isEmpty()){
            throw new NotFoundException("Platform with id: " + form.platformId + " not found");
        }
        question.setPlatform(fetchedPlatform.get());
        question.setTextQuestion(form.textQuestion);
        return this.questionRepository.save(question);
    }

    @PermissionLezen
    public List<Question> getByPlatformId(Long platformId) {
        return this.questionRepository.findByPlatformId(platformId);
    }

    @PermissionDomeinBeheerFicter
    public void delete(Long id){
        if (!questionRepository.existsById(id)) {
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        questionRepository.deleteById(id);
    }
}
