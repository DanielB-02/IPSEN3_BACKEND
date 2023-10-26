package com.ipsen.spine.repository;

import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findByPlatformId(long platformId);
}
