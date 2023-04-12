package com.hpcds.gamerestapi.games.application;

import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.domain.GameAlreadyExistException;
import com.hpcds.gamerestapi.games.domain.GameRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameCreationServiceTest {
    private final GameRepository gameRepository = mock(GameRepository.class);
    private final GameCreationService creationService = new GameCreationService(gameRepository);


    @Test
    @DisplayName("if there is another game with the same name, it going to throw a GameAlreadyExistsException")
    void check_if_fail_if_exists(){

        val givenGame = new Game(UUID.randomUUID(),"Factorio",30.5);

        doThrow(GameAlreadyExistException.class).when(gameRepository).save(any());
        assertThrows(GameAlreadyExistException.class,()->creationService.createNew(givenGame));
    }

    @Test
    @DisplayName("if you try to create a new game and is succesful will return a new UUID")
    void check_if_is_ok(){
        val givenGame = new Game(null,"Factorio",30.6);

        val obtainedValue = creationService.createNew(givenGame);
        assertNotNull(obtainedValue);
    }
}