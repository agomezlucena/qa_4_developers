package com.hpcds.gamerestapi.games.infrastructure.rest;

import com.hpcds.gamerestapi.games.application.GameCreationUseCase;
import com.hpcds.gamerestapi.games.application.GameQueryUseCase;
import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameQueryResponse;
import com.hpcds.gamerestapi.games.infrastructure.rest.apis.GameRestController;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationPetition;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationResponse;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameRestControllerTest {
    private final GameCreationUseCase gameCreationUseCase = mock(GameCreationUseCase.class);
    private final GameQueryUseCase gameQueryUseCase = mock(GameQueryUseCase.class);
    private final GameRestController controller = new GameRestController(gameCreationUseCase,gameQueryUseCase);

    @Test
    @DisplayName("when you create a new game return 201 with the id wrapped in a GameCreationResponse")
    void check_creation_return_uuid(){
        val givenGameDto = new GameCreationPetition("Zelda, Links awakening", 150.50);
        val expectedUUID = UUID.randomUUID();
        val expectedValue = ResponseEntity.status(201).body(new GameCreationResponse(expectedUUID));

        when(gameCreationUseCase.createNew(any())).thenReturn(expectedUUID);

        val obtainedValue = controller.create(givenGameDto);

        assertEquals(obtainedValue,expectedValue);
    }

    @Test
    @DisplayName("when you get all games will return always a 200 with a list of games even if that list is empty wrapped in a GameQueryResponse")
    void check_if_always_return_200(){
        val expectedList = List.of(
                new Game(UUID.randomUUID(),"Zelda, Links awakening", 150.50)
        );

        when(gameQueryUseCase.findAll())
                .thenReturn(Collections.emptyList())
                .thenReturn(expectedList);

        val firstExpectedValue = ResponseEntity.ok(new GameQueryResponse(Collections.emptyList()));
        val secondExpectedValue = ResponseEntity.ok(new GameQueryResponse(expectedList));

        val firstObtainedValue = controller.findAll();
        val secondObtainedValue = controller.findAll();

        assertEquals(firstExpectedValue,firstObtainedValue);
        assertEquals(secondExpectedValue,secondObtainedValue);
    }
}
