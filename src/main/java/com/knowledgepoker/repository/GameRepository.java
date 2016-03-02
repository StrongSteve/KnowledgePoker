package com.knowledgepoker.repository;

import com.knowledgepoker.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by starke on 14.02.2016.
 */
public interface GameRepository extends JpaRepository<Game, Long>{
    List<Game> findByCreatedByUserId(Long id);
}
