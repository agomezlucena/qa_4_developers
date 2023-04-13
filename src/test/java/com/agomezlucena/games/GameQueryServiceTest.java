package com.agomezlucena.games;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameQueryServiceTest {
    //mock declaration
    private final GameRepository gameRepository = mock(GameRepository.class);
    //mock injection
    private final GameQueryService gameQueryService = new GameQueryService(gameRepository);

    @Test
    @DisplayName("check when you search by id and there is any game return an empty optional")
    void check_for_empty_optional_by_id(){
        var givenId = UUID.randomUUID();
        //mock behaviour definition
        when(gameRepository.findById(any())).thenThrow(GameNotFoundException.class);
        var obtainedValue = gameQueryService.findById(givenId);

        assertTrue(obtainedValue.isEmpty());
    }

    @Test
    @DisplayName("check when you search by id and there is a game return a optional with its data")
    void check_for_no_empty_optional_by_id(){
        var givenId = UUID.randomUUID();
        var mockMustReturn = new Game(givenId,"Call of Cthulhu, dark corners of the earth");
        var expectedValue = Optional.of(mockMustReturn);

        when(gameRepository.findById(givenId))
                .thenAnswer(invocationOnMock ->  {
                    System.out.println("this a test for then answer method you can create another mock inside");
                    return mockMustReturn;
                } );

        var obtainedValue = gameQueryService.findById(givenId);

        assertEquals(expectedValue,obtainedValue);
    }
}