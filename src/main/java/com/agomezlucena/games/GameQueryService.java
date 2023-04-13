package com.agomezlucena.games;

import java.util.Optional;
import java.util.UUID;

public class GameQueryService {
    private final GameRepository repository;
    public GameQueryService(GameRepository repository){
        this.repository = repository;
    }

    public Optional<Game> findById(UUID gameId){
        try{
            return Optional.of(repository.findById(gameId));
        } catch (GameNotFoundException exception){
            return Optional.empty();
        }

    }
}
