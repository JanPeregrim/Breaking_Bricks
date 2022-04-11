package gamestudio.service;


import org.junit.Test;

import static org.junit.Assert.*;
import gamestudio.entity.Score;

import java.util.Date;

public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset() {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("").size());
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
        scoreService.addScore(new Score("Katka", "breaking bricks", 120, date));
        scoreService.addScore(new Score("Jaro", "breaking bricks", 150, date));
        scoreService.addScore(new Score("Jaro", "breaking bricks", 180, date));
        scoreService.addScore(new Score("Miro", "breaking bricks", 80, date));
        scoreService.addScore(new Score("Jakub", "breaking bricks", 90, date));

        var scores = scoreService.getTopScores("breaking bricks");

        assertEquals(5, scores.size());

        assertEquals("breaking bricks", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(180, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("breaking bricks", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(150, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("breaking bricks", scores.get(2).getGame());
        assertEquals("Katka", scores.get(2).getPlayer());
        assertEquals(120, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedAt());
    }
}

