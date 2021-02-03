package com.galvanize.gmdb.controllers;

import com.galvanize.gmdb.model.Movie;
import com.galvanize.gmdb.model.Rating;
import com.galvanize.gmdb.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gmdb/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getMovies();
    }

    @GetMapping("/{title}")
    public Movie getByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @PutMapping("/rate/{movieId}")
    public Movie submitStarRating(@PathVariable Long movieId, @RequestBody Rating rating) {
        return movieService.submitStarRating(movieId, rating);
    }

}
