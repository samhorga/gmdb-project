package com.galvanize.gmdb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Rating {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer stars;
    private String review;

    public Rating(Integer stars) {
        this.stars = stars;
    }

    public Rating(Integer stars, String review) {
        this.stars = stars;
        this.review = review;
    }

    public Rating() {

    }

    public Rating(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "stars=" + stars +
                ", review='" + review + '\'' +
                '}';
    }
}
