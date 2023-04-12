package com.hpcds.gamerestapi.games.infrastructure.rest.apis;

import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationPetition;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
public interface CreateGameApi {
    @PostMapping("/games")
    ResponseEntity<GameCreationResponse> create (@RequestBody GameCreationPetition dto);
}
