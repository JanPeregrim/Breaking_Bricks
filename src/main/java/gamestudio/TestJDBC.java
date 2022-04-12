package gamestudio;

import gamestudio.entity.Comment;
import gamestudio.service.CommentService;
import gamestudio.service.CommentServiceJDBC;

import gamestudio.entity.Score;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceJDBC;

import java.security.Provider;
import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception {
/*
        ScoreService service = new ScoreServiceJDBC();
        service.reset();

        service.addScore(new Score("jaro", "breaking bricks", 110, new Date()));
        service.addScore(new Score("marian", "breaking bricks", 112, new Date()));
        service.addScore(new Score("moro bricks", "breaking bricks", 116, new Date()));
        service.addScore(new Score("rasto", "breaking bricks", 120, new Date()));
        service.addScore(new Score("br bricks", "breaking bricks", 100, new Date()));

        var scores = service.getTopScores("breaking bricks");


 */

        CommentService service = new CommentServiceJDBC();

        service.addComment(new Comment("jaro", "breaking bricks", "jodasjidasgf", new Date()));
        service.addComment(new Comment("marian", "breaking bricks", "fdsfdf", new Date()));
        service.addComment(new Comment("moro bricks", "breaking bricks", "dsadsa", new Date()));
        service.addComment(new Comment("rasto ", "breaking bricks", "sadsa", new Date()));
        service.addComment(new Comment("br bricks", "breaking bricks", "fndsjfnd", new Date()));



       // var comment = service.getComments("breaking bricks");

       // System.out.println(comment);
    }
}
