package com.hpcds.gamerestapi.games.infrastructure.rest.apis;

import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameQueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface FindGameApi {
    @GetMapping("/games")
    ResponseEntity<GameQueryResponse> findAll();
}
