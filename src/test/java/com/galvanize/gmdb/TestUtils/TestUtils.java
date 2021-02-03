package com.galvanize.gmdb.TestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.model.Actor;
import com.galvanize.gmdb.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static List<Movie> getAllMovies() {

        List<Actor> actors1 = Arrays.asList(new Actor("Robert Downey Jr."), new Actor("Chris Evans"));
        List<Actor> actors2 = Arrays.asList(new Actor("Brandon Routh"), new Actor("Kate Bosworth"));

        Movie movie1 = new Movie("The Avengers", "Joss Whedon", "2012" ,
                "Earth's mightiest heroes must come together" +
                        " and learn to fight as a team if they are" +
                        " going to stop the mischievous Loki and his alien army from enslaving humanity.", null);
        movie1.setActors(actors1);

         Movie movie2 = new Movie("Superman Returns", "Bryan Singer", "2006" ,
                "Superman returns to Earth after spending five years in space examining his homeworld Krypton." +
                        " But he finds things have changed while he was gone, and he must once again prove himself " +
                        "important to the world.", null);
         movie2.setActors(actors2);

         return Arrays.asList(movie1,movie2);

    }
}
