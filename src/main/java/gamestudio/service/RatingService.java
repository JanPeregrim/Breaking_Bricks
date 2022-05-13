package gamestudio.service;

import gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void addRating(Rating rating);
    List<Rating> getRatings(String game);
    double getAverageRating(String game);
    void reset();
}

