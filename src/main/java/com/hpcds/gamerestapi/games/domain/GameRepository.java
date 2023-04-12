package com.hpcds.gamerestapi.games.domain;

import java.util.List;

public interface GameRepository {
    List<Game> findAll();
    void save(Game game);
}
