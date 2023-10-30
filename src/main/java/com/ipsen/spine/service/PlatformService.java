package com.ipsen.spine.service;

import com.ipsen.spine.controller.vo.PlatformForm;
import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.security.FicterSecurity;
import com.ipsen.spine.security.ReadOnlySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @FicterSecurity
    public Platform create(PlatformForm form){
        return savePlatform(form, new Platform());
    }

    @FicterSecurity
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

    @FicterSecurity
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
}


