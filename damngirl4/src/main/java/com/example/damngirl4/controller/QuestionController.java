package com.example.damngirl4.controller;


import com.example.damngirl4.dao.QuestionDAO;
import com.example.damngirl4.model.ApiResponse;
import com.example.damngirl4.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
