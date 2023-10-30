package com.ipsen.spine.repository;

import com.ipsen.spine.model.Platform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PlatformRepository  extends CrudRepository<Platform, Long>  {
}
