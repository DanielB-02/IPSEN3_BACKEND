package com.ipsen.spine.controller;


import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.ApiResponse;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.dao.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionDAO questionService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse save(@RequestBody Question newQuestion){
        Question question = this.questionService.save(newQuestion);
        return new ApiResponse(HttpStatus.ACCEPTED, question);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readAll(){
        Iterable<Question> questions = questionService.readAll();
        return new ApiResponse(HttpStatus.ACCEPTED, questions);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readSingle(@PathVariable long id){
        Optional<Question> question = this.questionService.readSingle(id);
        if (question.isEmpty()) {
            return new ApiResponse(HttpStatus.NOT_FOUND, "No post with that id");
        } else {
            return new ApiResponse(HttpStatus.ACCEPTED, question.get());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse update(@PathVariable long id, @RequestBody Question newQuestion){
        Question question;
        try{
            question = this.questionService.update(id, newQuestion);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, question, "You replaced question: " + question.getId() + " successfully.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResponse delete(@PathVariable long id){
        try{
            this.questionService.delete(id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, "You deleted question: " + id);
    }
}
