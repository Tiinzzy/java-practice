package org.netflix.model.tv_series;

import org.junit.jupiter.api.Test;

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
        var episodesOfSeasonOne =  tvSeriesDao.getSeason(1).loadEpisodes();
        assertNotEquals(episodesOfSeasonOne.size(), 0);
    }
}
