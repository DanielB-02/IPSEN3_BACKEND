package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.PlatformForm;
import com.ipsen.spine.controller.vo.PlatformScoreResult;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.security.FicterSecurity;
import com.ipsen.spine.security.ReadOnlySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @FicterSecurity
    public Platform create(PlatformForm form){
        return savePlatform(form, new Platform());
    }

    @FicterSecurity
    public Platform update(Long id, PlatformForm form){

        Platform existingPlatform = platformRepository.findPlatformById(id);
        existingPlatform.setStatus(form.isStatus());
        existingPlatform.setPlatformName(form.platformName);
        return platformRepository.save(existingPlatform);
    }



    private Platform savePlatform(PlatformForm form, Platform entityToSave) {
        entityToSave.setPlatformName(form.platformName);
        return this.platformRepository.save(entityToSave);
    }


    public Iterable<Platform> readAll(){
        return platformRepository.findAll();
    }

    @ReadOnlySecurity
    public Optional<Platform> readSingle(Long id){
        return platformRepository.findById(id);
    }

    @FicterSecurity
    public void delete(Long id){
        if (!platformRepository.existsById(id)) {
            throw new NotFoundException("Platform with id: " + id + " not found");
        }
        platformRepository.deleteById(id);
    }

    public List<PlatformScoreResult> readAllSortByScore() {
        List<PlatformScoreResult> platformResults = new ArrayList<>();
        // Gather the platforms with their scores
        for (Platform platform : platformRepository.findAll()) {
            int score = 0;
            for (Answer answer : answerRepository.findByQuestionPlatform(platform)) {
                score += answer.getScore();
            }
            PlatformScoreResult result = new PlatformScoreResult();
            result.id = platform.getId();
            result.platformName = platform.getPlatformName();
            result.score = score;
            result.status = platform.isStatus();
            platformResults.add(result);
        }
        // Sort the list in order of the scores
        platformResults.sort((o1, o2) -> o2.score - o1.score);
        return platformResults;
    }

    public List<PlatformScoreResult> readAllSortByScoreDesc() {
        List<PlatformScoreResult> platformResults = new ArrayList<>();

        for (Platform platform : platformRepository.findAll()) {
            if (!platform.isStatus()) {
                int score = 0;
                for (Answer answer : answerRepository.findByQuestionPlatform(platform)) {
                    score += answer.getScore();
                }
                PlatformScoreResult result = new PlatformScoreResult();
                result.id = platform.getId();
                result.platformName = platform.getPlatformName();
                result.score = score;
                result.status = platform.isStatus();
                platformResults.add(result);
            }
        }

            platformResults.sort(Comparator.comparingInt(o -> o.score));
            return platformResults;
        }
}


