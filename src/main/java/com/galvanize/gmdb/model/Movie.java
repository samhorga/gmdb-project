package com.galvanize.gmdb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Movie {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String title;
    private String director;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Actor> actors = new ArrayList<>();
    private String release;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();


    public Movie(String title, String director, String release, String description) {
        this.title = title;
        this.director = director;
        this.release = release;
        this.description = description;
    }

    public Movie() {

    }

    @Override
    public String toString() {
        return "Movie{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", actors=" + actors +
                ", release='" + release + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + ratings +
                '}';
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public double getAverageStarRating() {
        double sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getStars();
        }
        return sum / ratings.size();
    }
}
