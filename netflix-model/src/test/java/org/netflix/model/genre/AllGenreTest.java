package org.netflix.model.genre;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllGenreTest {

    @Test
    void testGenreDao() {
    GenreDao gn = new GenreDao(-1);
    assertEquals(gn.getOid(), -1);

    GenreDao newGenre = new GenreDao("Psyhcological");
    newGenre.saveToTable();
    assertEquals(newGenre.getDescription(), "Psyhcological");

    GenreDao newGenreName = new GenreDao(67);
    var aString = LocalDateTime.now().toString();
    newGenreName.setDescription(aString);
    newGenreName.saveToTable();
    assertEquals(newGenreName.getDescription(), aString);

    List<GenreDao> loadAllGenres = GenreDao.loadAll();
    assertFalse(loadAllGenres.isEmpty());

    }
}
