package com.galvanize.gmdb.services;

import com.galvanize.gmdb.TestUtils.TestUtils;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceUnitTests {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void addMovie() {
        when(movieRepository.save(any())).thenReturn(TestUtils.getAllMovies().get(0));
        Movie movieSaved = movieService.createMovie(TestUtils.getAllMovies().get(0));

        verify(movieRepository).save(any());

        assertEquals(TestUtils.getAllMovies().get(0).toString(), movieSaved.toString());
        assertEquals(TestUtils.getAllMovies().get(0).getActors().toString(), movieSaved.getActors().toString());
        assertEquals(TestUtils.getAllMovies().get(0).getDescription(), movieSaved.getDescription());
        assertEquals(TestUtils.getAllMovies().get(0).getDirector(), movieSaved.getDirector());
        assertEquals(TestUtils.getAllMovies().get(0).getRelease(), movieSaved.getRelease());
        assertEquals(TestUtils.getAllMovies().get(0).getRating(), movieSaved.getRating());
        assertEquals(TestUtils.getAllMovies().get(0).getTitle(), movieSaved.getTitle());
    }
}
