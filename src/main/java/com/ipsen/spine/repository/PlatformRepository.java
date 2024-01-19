package com.ipsen.spine.repository;

import com.ipsen.spine.model.Platform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface PlatformRepository  extends CrudRepository<Platform, Long>  {

    Platform findPlatformById(long id);
}
