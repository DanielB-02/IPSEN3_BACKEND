package com.ipsen.spine.repository;

import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Platform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PlatformRepository  extends CrudRepository<Platform, Long>  {
    List<Platform> findByPlatformId(long platformId);
}
