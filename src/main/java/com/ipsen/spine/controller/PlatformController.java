package com.ipsen.spine.controller;

import com.ipsen.spine.controller.vo.PlatformForm;
import com.ipsen.spine.controller.vo.PlatformScoreResult;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.service.PlatformService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/platform")

public class PlatformController {
    @Autowired
    private PlatformService platformService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Platform create(@RequestBody @Valid PlatformForm form){
        return this.platformService.create(form);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Platform> readAll(){
        return this.platformService.readAll();
    }

    @RequestMapping(value = "/sorted", method = RequestMethod.GET)
    public List<PlatformScoreResult> readAllSortByScore(){
        return this.platformService.readAllSortByScore();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Platform> readSingle(@PathVariable long id){
        return this.platformService.readSingle(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Platform update(@PathVariable long id, @RequestBody @Valid PlatformForm form){
        return this.platformService.update(id, form);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
        platformService.delete(id);
    }
}


