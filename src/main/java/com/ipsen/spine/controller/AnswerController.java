package com.ipsen.spine.controller;

import com.ipsen.spine.model.Answer;
import com.ipsen.spine.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Answer> all(){
        return this.answerService.all();
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.GET)
    public List<Answer> getAnswersOfQuestion(@PathVariable Long questionId){
        return this.answerService.getByQuestionId(questionId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Answer get(@PathVariable long id){
        return this.answerService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Answer replace(@RequestBody Answer answer, @PathVariable long id){
        return this.answerService.replace(answer, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Answer update(@RequestBody Answer answer, @PathVariable long id){
        return replace(answer, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
        this.answerService.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Answer save(@RequestBody Answer newAnswer){
        return this.answerService.save(newAnswer);
    }
}

