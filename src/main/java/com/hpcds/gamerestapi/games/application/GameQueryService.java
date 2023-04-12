package com.hpcds.gamerestapi.games.application;

import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.domain.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameQueryService implements GameQueryUseCase{
    private final GameRepository repository;
    @Override
    public List<Game> findAll() {
        return repository.findAll();
    }
}
