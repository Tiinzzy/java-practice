package org.netflix.model.movies;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllMovieTest {

    @Test
    void testMoviesDao() {
        MovieDao newMovie = new MovieDao("Heart of Stone", "08/11/2023", "8.5");
        newMovie.saveToTable();
        assertEquals(newMovie.getMovieTitle(), "Heart of Stone");
        assertEquals(newMovie.getReleaseDate(), "08/11/2023");
        assertEquals(newMovie.getRating(), "8.5");


        MovieDao getInsertedMovie = new MovieDao(newMovie.getOid());
        assertEquals(getInsertedMovie.getOid(), newMovie.getOid());

        List<MovieDao> loadAllMovies = MovieDao.loadAll();
        assertFalse(loadAllMovies.isEmpty());
    }
}
