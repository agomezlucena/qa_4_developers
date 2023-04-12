package com.hpcds.gamerestapi.games.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameCreationResponse;
import com.hpcds.gamerestapi.games.infrastructure.rest.dtos.GameQueryResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class GameRestControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("when you try to obtain all games will return a status 200")
    void check_200_status_when_you_try_to_obtain_all_games() throws Exception {
        val result = mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        val jsonResponse = result.getResponse().getContentAsString();

        val responseAsJavaObject = mapper.readValue(jsonResponse, GameQueryResponse.class);

        assertThat(responseAsJavaObject.getGames().size())
                .isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("when you create a game save it in database, and status is 201 and return the id wrapped in a json")
    void check_if_save_a_game_in_database() throws Exception {
        val givenInput = """
                {
                    "name":"Orpheus",
                    "price": 10.5
                }
                """;

        val result = mockMvc.perform(
                post("/games")
                        .content(givenInput)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        val resultAsJavaObject = mapper.readValue(result, GameCreationResponse.class);

        assertNotNull(resultAsJavaObject.getGameId());

        try(
                val connection = dataSource.getConnection();
                val statement = connection.prepareStatement("select count(*) from games where id = ?")
        ) {
            statement.setString(1,resultAsJavaObject.getGameId().toString());
            try(val resultSet = statement.executeQuery()){
                if(!resultSet.next()) fail("there is not data for new game");
                assertEquals(1,resultSet.getInt(1));
            }
        }
    }

    @AfterEach
    void check_if_game_exists_in_database(TestInfo testInfo) throws Exception {
        try(
                val connection = dataSource.getConnection();
                val statement = connection.prepareStatement("delete from games where name = ?");
        ){
            statement.setString(1,"Orpheus");
            val result = statement.executeUpdate();

            log.atInfo().log("test: {} se han eliminado: {}",testInfo.getDisplayName(),result);

        }
    }
}
