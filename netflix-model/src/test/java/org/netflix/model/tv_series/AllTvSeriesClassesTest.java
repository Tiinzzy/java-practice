package org.netflix.model.tv_series;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AllTvSeriesClassesTest {

    @Test
    void testEpisodeDao() {
        EpisodeDao episodeDao = new EpisodeDao(-1);
        assertEquals(episodeDao.getOid(), -1);

        episodeDao = new EpisodeDao("From Pole to Pole", 49, "2006-03-05", 10666);
        assertEquals(episodeDao.getTitle(), "From Pole to Pole");
        assertEquals(episodeDao.getRunTime(), 49);
        assertEquals(episodeDao.getAirDate(), "2006-03-05");
        assertEquals(episodeDao.getSeasonOid(), 10666);

        episodeDao.saveToTable();

        EpisodeDao episodeDaoFromDb = new EpisodeDao(episodeDao.getOid());
        assertEquals(episodeDaoFromDb.getTitle(), episodeDao.getTitle());

        List<EpisodeDao> allEpisodes = EpisodeDao.loadAll(10666);
        assertFalse(allEpisodes.isEmpty());

        var toBeDeletedEpisode = new EpisodeDao("To be deleted", 1, "6666-66-66", -666);
        toBeDeletedEpisode.saveToTable();
        assertEquals(toBeDeletedEpisode.getSeasonOid(), -666);

        EpisodeDao.delete(toBeDeletedEpisode.getOid());
        var deletedEpisode = new EpisodeDao(toBeDeletedEpisode.getOid());
        assertEquals(deletedEpisode.getOid(), -1);

        var e1 = new EpisodeDao("To be deleted 1", 1, "6666-66-66", -666);
        var e2 = new EpisodeDao("To be deleted 2", 1, "6666-66-66", -666);
        e1.saveToTable();
        e2.saveToTable();

        var episodes = EpisodeDao.loadAll(-666);
        assertEquals(episodes.size(), 2);

        EpisodeDao.deleteSeasonEpisodes(-666);
        episodes = EpisodeDao.loadAll(-666);
        assertEquals(episodes.size(), 0);
    }

    @Test
    void testSeasonDao() {
        SeasonDao seasonDao = new SeasonDao(-1);
        assertEquals(seasonDao.getOid(), -1);

        seasonDao = new SeasonDao(1, "2005-3-24", "2005-04-26", 2089);
        assertEquals(seasonDao.getSeasonNumber(), 1);
        assertEquals(seasonDao.getStartDate(), "2005-3-24");
        assertEquals(seasonDao.getEndDate(), "2005-04-26");
        assertEquals(seasonDao.getTvSeriesOid(), 2089);

        seasonDao.saveToTable();

        SeasonDao seasonDaoFromDb = new SeasonDao(seasonDao.getOid());
        assertEquals(seasonDaoFromDb.getSeasonNumber(), seasonDao.getSeasonNumber());
        assertEquals(seasonDaoFromDb.getStartDate(), seasonDao.getStartDate());
        assertEquals(seasonDaoFromDb.getEndDate(), seasonDao.getEndDate());
        assertEquals(seasonDaoFromDb.getTvSeriesOid(), seasonDao.getTvSeriesOid());

        List<SeasonDao> allSeasons = SeasonDao.loadAll(2089);
        assertFalse(allSeasons.isEmpty());
    }

    @Test
    void testTvSeriesDao() {
        TvSeriesDao tvSeriesDao = new TvSeriesDao(-1);
        assertEquals(tvSeriesDao.getOid(), -1);

        tvSeriesDao = new TvSeriesDao("Stranger Things", "Just another children like series", "2020-01-01", "2022-01-01");
        assertTrue(tvSeriesDao.getOid() > -1);
        assertEquals(tvSeriesDao.getStartDate(), "2020-01-01");

        tvSeriesDao.saveToTable();

        TvSeriesDao readFromDb = new TvSeriesDao(tvSeriesDao.getOid());
        assertEquals(readFromDb.getOid(), tvSeriesDao.getOid());
        assertEquals(readFromDb.getTitle(), tvSeriesDao.getTitle());

        tvSeriesDao.addSeason(1, "2020-10-10", "2020-11-10");
        tvSeriesDao.addSeason(2, "2020-10-10", "2020-11-10");

        var seasonDaoList = tvSeriesDao.loadSeasons();
        assertNotEquals(seasonDaoList.size(), 0);

        tvSeriesDao.addEpisode(1, "From Pole to Pole", 49, "2006-03-05");
        var episodesOfSeasonOne = tvSeriesDao.getSeason(1).loadEpisodes();
        assertNotEquals(episodesOfSeasonOne.size(), 0);

        var wildCardSearchTest = TvSeriesDao.loadAll("stra", 5);
        assertFalse(wildCardSearchTest.isEmpty());

        wildCardSearchTest = TvSeriesDao.loadAll("how-do-you-spend-your-time-during-the-weekends", 10);
        assertTrue(wildCardSearchTest.isEmpty());

        wildCardSearchTest = TvSeriesDao.loadAll();
        assertFalse(wildCardSearchTest.isEmpty());
    }

    @Test
    void testTvSeriesDaoEdit() {
        TvSeriesDao readFromDb = new TvSeriesDao(26);
        System.out.println(readFromDb.getOid());
        System.out.println(readFromDb.getTitle());

        var aString = LocalDateTime.now().toString();
        readFromDb.setTitle(aString);
        readFromDb.setSummary(aString);
        readFromDb.setStartDate(aString);
        readFromDb.setEndDate(aString);
        readFromDb.saveToTable();

        readFromDb = new TvSeriesDao(26);
        assertEquals(readFromDb.getTitle(), aString);
        assertEquals(readFromDb.getSummary(), aString);
    }

    @Test
    void testTvSeriesDaoDelete() {
        TvSeriesDao newAddMovie = new TvSeriesDao("Tina's Very Own Movie", "Memoir of my life!", "2020-01-01", "2022-01-01");
        newAddMovie.saveToTable();

        assertEquals(newAddMovie.getTitle(), "Tina's Very Own Movie");
        assertEquals(newAddMovie.getSummary(), "Memoir of my life!");
        assertEquals(newAddMovie.getStartDate(), "2020-01-01");
        assertEquals(newAddMovie.getEndDate(), "2022-01-01");

        long addedMovieId = newAddMovie.getOid();

    }
}
