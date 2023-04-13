package com.agomezlucena.games;

import java.util.UUID;

public class GameCreationService {
    private final GameRepository gameRepository;
    public GameCreationService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public UUID createGame(Game game){
        try{
            gameRepository.save(game);
            return game.getId();
        }catch (GameExistsException ex){
            return null;
        }
    }
}
