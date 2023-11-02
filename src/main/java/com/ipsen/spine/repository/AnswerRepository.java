package com.ipsen.spine.repository;

import com.ipsen.spine.model.Answer;
import com.ipsen.spine.model.Platform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findByQuestionPlatform(Platform platform);
}
