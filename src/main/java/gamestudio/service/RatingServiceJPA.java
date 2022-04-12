package gamestudio.service;

import gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public List<Rating> getRatings(String game) {
        return entityManager.createQuery("select s from Rating s where s.game = :game order by s.playedAt desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();

    }


    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }


}
