package com.knowledgepoker.service.question;

import com.knowledgepoker.service.exception.NoMoreQuestionException;
import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.Question;
import com.knowledgepoker.domain.Role;
import com.knowledgepoker.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by starke on 08.02.2016.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Override
    public Iterable<Question> listAllQuestions(CurrentUser user) {
        if (Objects.equals(user.getRole(), Role.ADMIN)) {
            return questionRepository.findAll();
        }
        else {
            return questionRepository.findBySubmittedByUserId(user.getId());
        }
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.delete(id);
    }

    @Override
    public Question getRandomQuestionExcludingUserAndQuestionIds(List<Long> userIdsToExclude, List<Long> questionIdsToExclude) throws NoMoreQuestionException {
        List<Long> questionIds =
                this.questionRepository.findIdsExcludingUsersAndQuestions(userIdsToExclude.isEmpty() ? Collections.singletonList(-1L) : userIdsToExclude,
                        questionIdsToExclude.isEmpty() ? Collections.singleton(-1L) : questionIdsToExclude);
        if (!questionIds.isEmpty()) {
            Random r = new Random();
            Question q = questionRepository.findOne(questionIds.get(r.nextInt(questionIds.size())));
            return q;
        }
        throw new NoMoreQuestionException("Out of new questions");
    }

    @Override
    public boolean canAccessQuestion(CurrentUser currentUser, Long questionId) {
        Question q = questionRepository.findOne(questionId);
        return Objects.equals(currentUser.getRole(), Role.ADMIN) || q.getSubmittedByUserId().equals(currentUser.getId());
    }

    @Override
    public long countQuestionsByUser(CurrentUser user) {
        return questionRepository.countBySubmittedByUserId(user.getId());
    }

    @Override
    public long countQuestions() {
        return questionRepository.count();
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
