package com.hpcds.gamerestapi.games.infrastructure.database;

import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.domain.GameAlreadyExistException;
import com.hpcds.gamerestapi.games.domain.GameRepository;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.UUID;

@Repository
public class GameH2Repository implements GameRepository {
    private final JdbcTemplate template;

    public GameH2Repository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Game> findAll() {
        val sql = "select id,name,price from games";
        return template.query(sql, (resultSet, rowNumber) ->
                new Game(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                )
        );
    }

    @Override
    public void save(Game game) {
        val sql = "insert into games (id,name,price) values (?,?,?)";
        try {
            template.update(
                    sql,
                    new Object[]{game.getId().toString(),game.getName(),game.getPrice()},
                    new int[]{Types.VARCHAR,Types.VARCHAR,Types.FLOAT}
            );
        } catch (DuplicateKeyException exception){
            throw new GameAlreadyExistException();
        }

    }
}
