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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Actor> actors = new ArrayList<>();
    private String release;
    private String description;
    private Integer rating;

    public Movie(String title, String director, String release, String description, Integer rating) {
        this.title = title;
        this.director = director;
        this.release = release;
        this.description = description;
        this.rating = rating;
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
                ", rating=" + rating +
                '}';
    }
}