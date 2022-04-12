package gamestudio.service;


import gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

    @Transactional
    public class CommentServiceJPA implements CommentService{

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public void addComment(Comment comment) {
            entityManager.persist(comment);
        }

        @Override
        public List<Comment> getComments(String game) {
            return entityManager.createQuery("select s from Score s where s.game = :game order by played_at desc")
                    .setParameter("game", game)
                    .setMaxResults(10)
                    .getResultList();
        }


        @Override
        public void reset() {
            entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
        }


    }
}
