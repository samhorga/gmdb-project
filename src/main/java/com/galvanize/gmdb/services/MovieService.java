package com.galvanize.gmdb.services;

import com.galvanize.gmdb.exceptions.NonExistingMovieException;
import com.galvanize.gmdb.exceptions.StarNeededException;
import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Movie submitStarRating(Long movieId, Rating rating) {
        if(rating.getStars() == null || rating.getStars() == 0) {
            throw new StarNeededException("ERROR. Star needed for the rating");
        }
        Optional<Movie> movieRetrieved = movieRepository.findById(movieId);
        if(movieRetrieved.isPresent()) {
            movieRetrieved.get().addRating(rating);
            return movieRepository.save(movieRetrieved.get());
        }
        throw new NonExistingMovieException("Movie not found.");
    }
}
