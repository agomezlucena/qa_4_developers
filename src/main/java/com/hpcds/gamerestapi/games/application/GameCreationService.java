package com.hpcds.gamerestapi.games.application;

import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.domain.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameCreationService implements GameCreationUseCase {
    private final GameRepository gameRepository;

    @Override
    public UUID createNew(Game game) {
        val newGameId = UUID.randomUUID();
        game.setId(newGameId);
        gameRepository.save(game);
        return newGameId;
    }
}
