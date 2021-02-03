package com.galvanize.gmdb.services;

import com.galvanize.gmdb.TestUtils.TestUtils;
import com.galvanize.gmdb.exceptions.NonExistingMovieException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        assertEquals(TestUtils.getAllMovies().get(0).getRatings().toString(), movieSaved.getRatings().toString());
        assertEquals(TestUtils.getAllMovies().get(0).getTitle(), movieSaved.getTitle());
    }

    @Test
    public void getMovieByTitle() {
        when(movieRepository.findByTitle(any())).thenReturn(TestUtils.getAllMovies().get(0));

        Movie actual = movieService.getMovieByTitle(TestUtils.getAllMovies().get(0).getTitle());

        verify(movieRepository).findByTitle(any());

        assertEquals(TestUtils.getAllMovies().get(0).toString(), actual.toString());
        assertEquals(TestUtils.getAllMovies().get(0).getActors().toString(), actual.getActors().toString());
        assertEquals(TestUtils.getAllMovies().get(0).getDescription(), actual.getDescription());
        assertEquals(TestUtils.getAllMovies().get(0).getDirector(), actual.getDirector());
        assertEquals(TestUtils.getAllMovies().get(0).getRelease(), actual.getRelease());
        assertEquals(TestUtils.getAllMovies().get(0).getRatings().toString(), actual.getRatings().toString());
        assertEquals(TestUtils.getAllMovies().get(0).getTitle(), actual.getTitle());
    }

    @Test
    public void getMovieByTitleForNonExisting() {
        Assertions.assertThrows(NonExistingMovieException.class, ()->{movieService.getMovieByTitle("TEST"); });
    }

    @Test
    public void submitStarRating() {
        Movie movie = TestUtils.getAllMovies().get(0);
        movie.setId(1L);
        when(movieRepository.save(any())).thenReturn(movie);
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        Movie actual = movieService.submitStarRating(1L, new Rating(5));


        assertEquals(5, actual.getRatings().get(0).getStars());
    }
}
