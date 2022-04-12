package gamestudio.service;

import gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating);
    List<Rating> getRatings(String game);
    void reset();
}

