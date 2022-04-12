package gamestudio.service;

import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RatingServiceTest {

    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testReset() {
        ratingService.reset();
        assertEquals(0, ratingService.getRatings("").size());
    }

    @Test
    public void testAddRating() {
        //ratingService.reset();
        var date = new Date();

        ratingService.addRating(new Rating("Milan", "breaking bricks", 1, date));

        var ratings = ratingService.getRatings("breaking bricks");
        assertEquals(1, ratings.size());
        assertEquals("breaking bricks", ratings.get(0).getGame());
        assertEquals("Milan", ratings.get(0).getPlayer());
        assertEquals(1, ratings.get(0).getStars());
        assertEquals(date, ratings.get(0).getPlayedAt());
    }
}
