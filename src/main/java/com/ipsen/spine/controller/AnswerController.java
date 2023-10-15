package com.ipsen.spine.controller;

import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.ApiResponse;
import com.ipsen.spine.dao.AnswerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/answer")
public class AnswerController {
    @Autowired
    private AnswerDAO answerService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse all(){
        return new ApiResponse(HttpStatus.ACCEPTED, this.answerService.all());
    }

    @RequestMapping(value = "/question/{questionId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getAnswersOfQuestion(@PathVariable long questionId){
        return new ApiResponse(HttpStatus.ACCEPTED, this.answerService.getByQuestionId(questionId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse get(@PathVariable long id){
        Answer answer;
        try {
            answer = this.answerService.getById(id);
        } catch(NotFoundException exception) {
            return new ApiResponse(HttpStatus.NOT_FOUND, "No post with that id");
        }

        return new ApiResponse(HttpStatus.ACCEPTED, answer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse replace(@RequestBody Answer answer, @PathVariable long id){
        try{
            this.answerService.replace(answer, id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }

        return new ApiResponse(HttpStatus.ACCEPTED, "You replaced comment: " + answer.getId() + " successfully.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ApiResponse update(@RequestBody Answer answer, @PathVariable long id){
        try{
            this.answerService.update(answer, id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }

        return new ApiResponse(HttpStatus.ACCEPTED, "You updated comment: " + answer.getId() + " successfully.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResponse delete(@PathVariable long id){
        try{
            this.answerService.delete(id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }

        return new ApiResponse(HttpStatus.ACCEPTED, "You deleted comment: " + id + " successfully.");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse save(@RequestBody Answer newAnswer){
        Answer answer = this.answerService.save(newAnswer);
        return new ApiResponse(HttpStatus.ACCEPTED, answer);
    }
}


//    @RestController
//    @RequestMapping(value = "/api/posts")
//
//    @RequestMapping()
//    @ResponseBody
//    public ApiResponseAll(){
//        return new ApiResponse(HttpStatus.ACCEPTED, this.postdao.all)
//    }

