package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.PlatformForm;
import com.ipsen.spine.controller.vo.PlatformScoreResult;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.repository.AnswerRepository;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.security.PermissionDomeinBeheerAdmin;
import com.ipsen.spine.security.PermissionDomeinBeheerFicter;
import com.ipsen.spine.security.PermissionLezen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PermissionDomeinBeheerFicter
    public Platform create(PlatformForm form){
        return savePlatform(form, new Platform());
    }

    @PermissionDomeinBeheerAdmin
    public Platform update(Long id, PlatformForm form){
        Optional<Platform> fetchedPlatform = platformRepository.findById(id);
        if(fetchedPlatform.isEmpty()){
            throw new NotFoundException("Platform with id: " + id + " not found");
        }
        return savePlatform(form, fetchedPlatform.get());
    }

    private Platform savePlatform(PlatformForm form, Platform entityToSave) {
        entityToSave.setPlatformName(form.platformName);
        return this.platformRepository.save(entityToSave);
    }


    @PermissionLezen
    public Iterable<Platform> readAll(){
        return platformRepository.findAll();
    }

    @PermissionLezen
    public Optional<Platform> readSingle(Long id){
        return platformRepository.findById(id);
    }

    @PermissionDomeinBeheerAdmin
    public void delete(Long id){
        if (!platformRepository.existsById(id)) {
            throw new NotFoundException("Platform with id: " + id + " not found");
        }
        platformRepository.deleteById(id);
    }

    @PermissionLezen
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
            platformResults.add(result);
        }
        // Sort the list in order of the scores
        platformResults.sort((o1, o2) -> o2.score - o1.score);
        return platformResults;
    }

    @PermissionLezen
    public List<PlatformScoreResult> readAllSortByScoreDesc() {
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
            platformResults.add(result);
        }
        // Sort the list in order of the scores
        platformResults.sort(Comparator.comparingInt(o -> o.score));
        return platformResults;
    }
}


