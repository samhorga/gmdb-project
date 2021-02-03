package com.galvanize.gmdb.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.TestUtils.TestUtils;
import com.galvanize.gmdb.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllMovies() throws Exception {
        List<Movie> movieList = TestUtils.getAllMovies();

        String movie1 = objectMapper.writeValueAsString(movieList.get(0));
        String movie2 = objectMapper.writeValueAsString(movieList.get(1));

        mockMvc.perform(post("/gmdb/movies").contentType(MediaType.APPLICATION_JSON).content(movie1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(movieList.get(0).getTitle()))
                .andExpect(jsonPath("$.description").value(movieList.get(0).getDescription()))
                .andExpect(jsonPath("$.director").value(movieList.get(0).getDirector()));

        mockMvc.perform(post("/gmdb/movies").contentType(MediaType.APPLICATION_JSON).content(movie2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(movieList.get(1).getTitle()))
                .andExpect(jsonPath("$.description").value(movieList.get(1).getDescription()))
                .andExpect(jsonPath("$.director").value(movieList.get(1).getDirector()));

       MvcResult mvcResult = mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2)).andReturn();

        List<Movie> customerResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Movie>>(){}
        );

        assertEquals(movieList.get(0).getTitle(), customerResponse.get(0).getTitle());
        assertEquals(movieList.get(1).getTitle(), customerResponse.get(1).getTitle());
        assertEquals(movieList.get(0).getActors().get(0).getName(), customerResponse.get(0).getActors().get(0).getName());
    }

}
