package com.example.damngirl4.controller;


import com.example.damngirl4.dao.QuestionDAO;
import com.example.damngirl4.exception.NotFoundException;
import com.example.damngirl4.model.Answer;
import com.example.damngirl4.model.ApiResponse;
import com.example.damngirl4.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionDAO questionDAO;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse save(@RequestBody Question newQuestion){
        Question question = this.questionDAO.save(newQuestion);
        return new ApiResponse(HttpStatus.ACCEPTED, question);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readAll(){
        Iterable<Question> questions = questionDAO.readAll();
        return new ApiResponse(HttpStatus.ACCEPTED, questions);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readSingle(@PathVariable long id){
        Optional<Question> question = this.questionDAO.readSingle(id);
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
            question = this.questionDAO.update(id, newQuestion);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, question, "You replaced question: " + question.getId() + " successfully.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResponse delete(@PathVariable long id){
        try{
            this.questionDAO.delete(id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, "You deleted question: " + id);
    }
}
