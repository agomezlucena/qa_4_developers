package com.hpcds.gamerestapi.games.application;

import com.hpcds.gamerestapi.games.domain.Game;

import java.util.List;

public interface GameQueryUseCase {
    List<Game> findAll();
}
