package com.hpcds.gamerestapi.games.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.util.UUID;

@Value
public class GameCreationResponse {
    UUID gameId;
    @JsonCreator
    public GameCreationResponse(UUID gameId) {
        this.gameId = gameId;
    }
}
