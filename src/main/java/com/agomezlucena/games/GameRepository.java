package com.agomezlucena.games;

import java.util.UUID;

public interface GameRepository {
    /**
     * this method allows to find a game in our infrastructure using its id.
     * In case that there aren't any game with that id throw a GameNotFoundException.
     *
     * @param gameId uuid
     * @return a game entity
     * @throws GameNotFoundException when not game is found
     */
    Game findById(UUID gameId);

    /**
     * this method allows to save a game in our infrastructure,
     * if game already exists throw an GameExistsException
     */
    void save(Game game);
}
