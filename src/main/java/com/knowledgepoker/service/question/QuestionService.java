package com.knowledgepoker.service.question;

import com.knowledgepoker.service.exception.NoMoreQuestionException;
import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.Question;

import java.util.List;

/**
 * Created by starke on 11.02.2016.
 */
public interface QuestionService {
    Iterable<Question> listAllQuestions(CurrentUser user);

    Question getQuestionById(Long id);

    Question saveQuestion(Question question);

    void deleteQuestion(Long id);

    Question getRandomQuestionExcludingUserAndQuestionIds(List<Long> userIdsToExclude, List<Long> questionIdsToExclude) throws NoMoreQuestionException;

    boolean canAccessQuestion(CurrentUser currentUser, Long questionId);

    long countQuestionsByUser(CurrentUser user);

    long countQuestions();
}
