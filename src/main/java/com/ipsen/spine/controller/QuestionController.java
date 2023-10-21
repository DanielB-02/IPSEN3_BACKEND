package com.ipsen.spine.controller;


import com.ipsen.spine.controller.vo.QuestionForm;
import com.ipsen.spine.model.Question;
import com.ipsen.spine.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Question create(@RequestBody @Valid QuestionForm newQuestion){
        return this.questionService.create(newQuestion);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Question update(@PathVariable long id, @RequestBody @Valid QuestionForm newQuestion){
        return this.questionService.update(id, newQuestion);
    }

    @RequestMapping(value = "/platform/{platformId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getQuestionsOfPlatform(@PathVariable long platformId){
        return new ApiResponse(HttpStatus.ACCEPTED, this.questionService.getByPlatformId(platformId));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Question> readAll(){
        return questionService.readAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Question readSingle(@PathVariable long id){
        return this.questionService.readSingle(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
        this.questionService.delete(id);
    }
}
