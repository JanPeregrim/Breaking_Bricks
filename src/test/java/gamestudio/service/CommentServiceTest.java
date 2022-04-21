package gamestudio.service;

import org.junit.Test;

import static org.junit.Assert.*;
import gamestudio.entity.Comment;

import java.util.Date;

public class CommentServiceTest {

        private CommentService commentService = new CommentServiceJPA();

        @Test
        public void testReset() {
        commentService.reset();
        assertEquals(0,commentService.getComments("").size());
    }
        @Test
        public void testAddScore() {
                commentService.reset();
                var date = new Date();

                commentService.addComment(new Comment("Milan", "breaking bricks", "Aj taka ze dobra aj taka ze zla", date));

                var comments = commentService.getComments("breaking bricks");
                assertEquals(1, comments.size());
                assertEquals("breaking bricks", comments.get(0).getGame());
                assertEquals("Milan", comments.get(0).getPlayer());
                assertEquals("Aj taka ze dobra aj taka ze zla", comments.get(0).getComment());
                assertEquals(date, comments.get(0).getPlayedAt());
        }

}


