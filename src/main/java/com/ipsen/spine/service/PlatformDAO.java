package com.ipsen.spine.dao;


import com.ipsen.spine.exception.NotFoundException;
import com.ipsen.spine.model.Platform;
import com.ipsen.spine.repository.PlatformRepository;
import com.ipsen.spine.security.FicterSecurity;
import com.ipsen.spine.security.ReadOnlySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlatformDAO {

    @Autowired
    private PlatformRepository platformRepository;

    @FicterSecurity
    public Platform save(Platform newPlatform){
        Platform platform = this.platformRepository.save(newPlatform);
        return platform;
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
    public Platform update(Long id, Platform newPlatform){
        Optional<Platform> fetchedPlatform = platformRepository.findById(id);
        if(fetchedPlatform.isEmpty()){
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        Platform platform = fetchedPlatform.get();
        platform.setPlatformName(newPlatform.getPlatformName());
        return platformRepository.save(platform);
    }

    @FicterSecurity
    public void delete(Long id){
        if (!platformRepository.existsById(id)) {
            throw new NotFoundException("Post with id: " + id + " not found");
        }
        platformRepository.deleteById(id);
    }
}


