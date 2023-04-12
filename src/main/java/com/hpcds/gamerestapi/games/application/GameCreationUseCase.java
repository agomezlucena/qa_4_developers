package com.hpcds.gamerestapi.games.application;

import com.hpcds.gamerestapi.games.domain.Game;

import java.util.UUID;

public interface GameCreationUseCase {
    UUID createNew(Game game);
}
