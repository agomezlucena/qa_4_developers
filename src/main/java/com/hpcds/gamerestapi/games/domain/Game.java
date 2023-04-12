package com.hpcds.gamerestapi.games.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Game {
    private UUID id;
    private String name;
    private Double price;
}
