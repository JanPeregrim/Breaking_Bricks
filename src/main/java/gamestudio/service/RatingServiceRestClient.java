package gamestudio.service;

import gamestudio.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RatingServiceRestClient implements RatingService {
    private String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public List<Rating> getRatings(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Rating[].class).getBody());
    }

    public double getAverageRating(String game){
        return 0.2;
    }
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported on eb interface");
    }
}
