package com.knowledgepoker.service.game;

import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.Game;
import com.knowledgepoker.domain.Role;
import com.knowledgepoker.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by starke on 14.02.2016.
 */
@Service
public class GameServiceImpl implements GameService {

    //Comment
    private GameRepository gameRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllAvailableGames(CurrentUser user) {
        if (Objects.equals(user.getRole(), Role.ADMIN)) {
            return gameRepository.findAll();
        }
        else {
            return gameRepository.findByCreatedByUserId(user.getId());
        }
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findOne(id);
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.delete(id);
    }

    @Override
    public boolean canAccessGame(CurrentUser currentUser, Long questionId) {
        Game g = gameRepository.findOne(questionId);
        return Objects.equals(currentUser.getRole(), Role.ADMIN) || g.getCreatedByUserId().equals(currentUser.getId());

    }
}
