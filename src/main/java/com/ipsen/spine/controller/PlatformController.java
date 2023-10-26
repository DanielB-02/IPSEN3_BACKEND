package com.ipsen.spine.controller;

import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.model.ApiResponse;
import com.ipsen.spine.dao.PlatformDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/platform")

public class PlatformController {
    @Autowired
    private PlatformDAO platformService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse save(@RequestBody Platform newPlatform){
        Platform platform = this.platformService.save(newPlatform);
        return new ApiResponse(HttpStatus.ACCEPTED, platform);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readAll(){
        Iterable<Platform> platform = platformService.readAll();
        return new ApiResponse(HttpStatus.ACCEPTED, platform);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse readSingle(@PathVariable long id){
        Optional<Platform> platform = this.platformService.readSingle(id);
        if (platform.isEmpty()) {
            return new ApiResponse(HttpStatus.NOT_FOUND, "No platform with that id");
        } else {
            return new ApiResponse(HttpStatus.ACCEPTED, platform.get());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse update(@PathVariable long id, @RequestBody Platform newPlatform){
            Platform platform;
        try{
            platform = this.platformService.update(id, newPlatform);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, platform, "You replaced question: " + platform.getId() + " successfully.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResponse delete(@PathVariable long id){
        try{
            this.platformService.delete(id);
        } catch(NotFoundException exception){
            return new ApiResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return new ApiResponse(HttpStatus.ACCEPTED, "You deleted question: " + id);
    }
}


