package com.agomezlucena.games;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class GameCreationServiceTest {
    private final GameRepository repository = mock(GameRepository.class);
    private final GameCreationService creationService = new GameCreationService(repository);

    @Test
    @DisplayName("if you create a new game return its game id")
    void check_if_return_the_game_id() {
        var givenGame = new Game(UUID.randomUUID(), "Factorio");
        doNothing().when(repository).save(any());
        assertEquals(givenGame.getId(),creationService.createGame(givenGame));
    }

    @Test
    @DisplayName("if you try to create the same game two or more times return a null gameid")
    void check_if_game_already_exists_throw_GameExistsException() {
        var givenGame = new Game(UUID.randomUUID(), "Factorio");
        var alreadyInserted = new AtomicBoolean(false);

        doAnswer(invocationOnMock -> {
            if (alreadyInserted.get()) {
                throw new GameExistsException();
            }
            alreadyInserted.set(true);
            return null;
        }).when(repository).save(givenGame);

        creationService.createGame(givenGame);

        assertNull(creationService.createGame(givenGame));
    }

}