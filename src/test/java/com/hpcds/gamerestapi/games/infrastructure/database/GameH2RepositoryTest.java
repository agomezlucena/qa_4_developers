package com.hpcds.gamerestapi.games.infrastructure.database;

import com.hpcds.gamerestapi.games.domain.Game;
import com.hpcds.gamerestapi.games.domain.GameAlreadyExistException;
import com.hpcds.gamerestapi.games.infrastructure.database.GameH2Repository;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameH2RepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private GameH2Repository repository;

    @Test
    @DisplayName("check if return the expected list of games")
    void check_if_return_the_expected_list_of_games() {
        val expectedSize = 1;
        val obtainedValue = repository.findAll();

        assertAll(
                () -> assertNotNull(obtainedValue,"el valor es nulo"),
                () -> assertThat(obtainedValue.size()).isGreaterThanOrEqualTo(expectedSize)
        );
    }

    @Test
    @DisplayName("check if you insert two games with same name throw GameAlreadyExistException")
    void check_if_you_insert_two_games_with_same_name_throw_GameAlreadyExistException(){
        val givenGame = new Game(UUID.randomUUID(),"Factorio",10.0);
        repository.save(givenGame);
        givenGame.setId(UUID.randomUUID());
        assertThrows(GameAlreadyExistException.class,()->repository.save(givenGame));
    }

    @Test
    @DisplayName("check if you insert a game correctly add it into the table")
    void check_that_is_correctly_added_to_the_database(){
        val givenGame = new Game(UUID.randomUUID(),"Factorio",10.0);
        repository.save(givenGame);

        try(
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("select count(*) from games where id = ?")
        ) {
            statement.setString(1,givenGame.getId().toString());
            try(var resultSet = statement.executeQuery()){
                if (!resultSet.next()) fail();
                assertEquals(1,resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    @AfterEach
    void tearDownDatabase() {
        val dropTableSql = """
                delete from games where name in ('Factorio')
                """;

        try (
                val connection = dataSource.getConnection();
                val dropStatement = connection.prepareStatement(dropTableSql)
        ) {
            dropStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}