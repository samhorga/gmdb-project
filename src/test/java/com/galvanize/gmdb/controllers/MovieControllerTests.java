package com.galvanize.gmdb.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.TestUtils.TestUtils;
import com.galvanize.gmdb.exceptions.NonExistingMovieException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
    }
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

        List<Movie> customerResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Movie>>() {
                }
        );

        assertEquals(movieList.get(0).getTitle(), customerResponse.get(0).getTitle());
        assertEquals(movieList.get(1).getTitle(), customerResponse.get(1).getTitle());
        assertEquals(movieList.get(0).getActors().get(0).getName(), customerResponse.get(0).getActors().get(0).getName());
        assertEquals(movieList.get(1).getActors().get(1).getName(), customerResponse.get(1).getActors().get(1).getName());
    }

    @Test
    public void getMovieByTitleForExistingMovie() throws Exception {
        Movie movie = TestUtils.getAllMovies().get(0);
        Movie movie1 = TestUtils.getAllMovies().get(1);
        Movie movieSaved1 = movieRepository.save(movie);
        movieRepository.save(movie1);

        Movie movieRetrieved = movieRepository.findByTitle(movieSaved1.getTitle());

        MvcResult mvcResult = mockMvc.perform(get("/gmdb/movies/" + movieRetrieved.getTitle()))
                .andExpect(status().isOk()).andReturn();

        Movie movieResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Movie.class);

        assertEquals(movieRetrieved.getTitle(), movieResponse.getTitle());
        assertEquals(movieRetrieved.getDirector(), movieResponse.getDirector());
        assertEquals(movieRetrieved.getDescription(), movieResponse.getDescription());
        assertEquals(movieRetrieved.getRelease(), movieResponse.getRelease());
        assertEquals(movieRetrieved.getActors().toString(), movieResponse.getActors().toString());
    }

        /*

   Rule: Movie details include title, director, actors, release year, description and star rating.

Given an existing movie
When I visit that title
Then I can see all the movie details.

Given a non-existing movie
When I visit that title
Then I receive a friendly message that it doesn't exist.
     */

    @Test
    public void getMovieByTitleForNonExistingMovie() throws Exception {
        mockMvc.perform(get("/gmdb/movies/" + "TEST"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NonExistingMovieException))
                .andExpect(result -> assertEquals("Movie not found.", result.getResolvedException().getMessage()));
    }
}
