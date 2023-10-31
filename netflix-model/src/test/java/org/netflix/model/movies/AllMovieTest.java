package org.netflix.model.movies;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllMovieTest {

    @Test
    void testMoviesDao() {
        MovieDao newMovie = new MovieDao("Heart of Gold", "08/10/2023", "5");
        newMovie.saveToTable();
        assertEquals(newMovie.getMovieTitle(), "Heart of Gold");
        assertEquals(newMovie.getReleaseDate(), "08/10/2023");
        assertEquals(newMovie.getRating(), "5");


        MovieDao getInsertedMovie = new MovieDao(newMovie.getOid());
        assertEquals(getInsertedMovie.getOid(), newMovie.getOid());

        List<MovieDao> loadAllMovies = MovieDao.loadAll();
        assertFalse(loadAllMovies.isEmpty());

        MovieDao checkMovie = new MovieDao(newMovie.getOid());
        assertEquals(checkMovie.getMovieTitle(), "Heart of Gold");
        assertEquals(checkMovie.getReleaseDate(), "08/10/2023");
        assertEquals(checkMovie.getRating(), "5");

        var aString = LocalDateTime.now().toString();
        newMovie.setMovieTitle(aString);
        newMovie.setReleaseDate(aString);
        newMovie.setRating(aString);
        newMovie.saveToTable();

        assertEquals(newMovie.getMovieTitle(), aString);
        assertEquals(newMovie.getReleaseDate(), aString);
        assertEquals(newMovie.getRating(), aString);
    }

    @Test
    void testDeleteAMovie(){
        MovieDao newMovie = new MovieDao("Test1", "06/06/6666", "6");
        newMovie.saveToTable();
        assertEquals(newMovie.getMovieTitle(), "Test1");
        assertEquals(newMovie.getReleaseDate(), "06/06/6666");
        assertEquals(newMovie.getRating(), "6");

        newMovie.deleteMovie(newMovie.getOid());
        assertEquals(newMovie.getMovieTitle(), "Test1");
        assertEquals(newMovie.getReleaseDate(), "06/06/6666");
        assertEquals(newMovie.getRating(), "6");
    }
}
