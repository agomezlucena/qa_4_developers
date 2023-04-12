package com.hpcds.gamerestapi.games.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hpcds.gamerestapi.games.domain.Game;
import lombok.Data;

import java.util.List;

@Data
public class GameQueryResponse {
    private final List<Game> games;

    @JsonCreator
    public GameQueryResponse(List<Game> games) {
        this.games = games;
    }


}
