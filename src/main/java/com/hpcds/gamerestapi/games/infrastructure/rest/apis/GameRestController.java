package com.hpcds.gamerestapi.games.infrastructure.rest.apis;

import com.hpcds.gamerestapi.games.application.GameCreationUseCase;
import com.hpcds.gamerestapi.games.application.GameQueryUseCase;
import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationPetition;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationResponse;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameRestController implements CreateGameApi, FindGameApi {
    private final GameCreationUseCase creationUseCase;
    private final GameQueryUseCase queryUseCase;
    @Override
    public ResponseEntity<GameCreationResponse> create(GameCreationPetition dto) {
        val newGame = new Game(null,dto.getName(), dto.getPrice());
        val gameId = creationUseCase.createNew(newGame);
        return ResponseEntity.status(201).body(new GameCreationResponse(gameId));
    }

    @Override
    public ResponseEntity<GameQueryResponse> findAll() {
        val result = queryUseCase.findAll();
        return ResponseEntity.ok(new GameQueryResponse(result));
    }
}
