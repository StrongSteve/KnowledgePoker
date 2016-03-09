package com.knowledgepoker.repository;

import com.knowledgepoker.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by starke on 11.02.2016.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySubmittedByUserId(Long userId);

    @Query(value = "select id from Question where id NOT IN (:questionIds) AND submittedByUserId NOT IN (:userIds)")
    List<Long> findIdsExcludingUsersAndQuestions(@Param("userIds") Collection<Long> userIds, @Param("questionIds")Collection<Long> questionIds);

    @Query(value = "select id from Question where id NOT IN (:questionIds) AND submittedByUserId = :userId")
    List<Long> findIdsFromUserAndExcludingQuestions(@Param("userId") Long userId, @Param("questionIds")Collection<Long> questionIds);

    long countBySubmittedByUserId(Long id);
}
