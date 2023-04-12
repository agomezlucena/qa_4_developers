package com.hpcds.gamerestapi.games.infrastructure.rest.dtos;

import lombok.Value;

@Value
public class GameCreationPetition {
    String name;
    Double price;
}
