package com.galvanize.gmdb.services;

import com.galvanize.gmdb.exceptions.NonExistingMovieException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieByTitle(String title) {
        Movie movie =  movieRepository.findByTitle(title);
        if (movie != null) {
            return movie;
        }
        throw new NonExistingMovieException("Movie not found.");
    }
}
