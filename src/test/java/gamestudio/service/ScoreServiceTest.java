package gamestudio.service;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import gamestudio.entity.Score;

import java.util.Date;

public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset() {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("Marian").size());
    }

    @Test
    public void testAddScore() {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("Milan", "breaking bricks", 100, date));

        var scores = scoreService.getTopScores("breaking bricks");
        assertEquals(1, scores.size());
        assertEquals("breaking bricks", scores.get(0).getGame());
        assertEquals("Milan", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());
    }

    @Test
    public void testGetTopScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("mines", "breaking bricks", 120, date));
        scoreService.addScore(new Score("mines", "breaking bricks", 150, date));
        scoreService.addScore(new Score("tiles", "breaking bricks", 180, date));
        scoreService.addScore(new Score("mines", "breaking bricks", 100, date));

        var scores = scoreService.getTopScores("breaking bricks");

        assertEquals(3, scores.size());

        assertEquals("mines", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getPlayer());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("mines", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(120, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("", scores.get(2).getGame());
        assertEquals("Jaro", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedAt());
    }
}

