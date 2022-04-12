package gamestudio.server.webservice;


import gamestudio.entity.Rating;
import gamestudio.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}")
    public List<Rating> getRatings(@PathVariable String game) {
        return ratingService.getRatings(game);
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
    }
}