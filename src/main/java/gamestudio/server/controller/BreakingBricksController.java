package gamestudio.server.controller;


import gamestudio.core.Brick;
import gamestudio.core.GameState;
import gamestudio.entity.Comment;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import gamestudio.core.Field;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;


@Controller
@RequestMapping("/breakingBricks")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BreakingBricksController {

    @Autowired
    private UserController userController;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    public static final String GAME_NAME = "Breaking Bricks";
    private Field field = new Field(0, 0, 0);

    @RequestMapping
    public String breakingBricks(@RequestParam(required = false) Integer row,@RequestParam(required = false) Integer column,Model model) {
        if (field.getState() == GameState.PLAYING) {
            if (row != null && column != null) {
                field.destroyBrick(row, column);

            }
            if (field.getState() == GameState.FAILED && userController.isLogged()){
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(),GAME_NAME, field.getScore(), new Date()));
            }
            getFailedGame();
            scoreModel(model);
            commentModel(model);
        }
        else {
            scoreModel(model);
            commentModel(model);
        }
        return "breakingBricks";
    }

    @RequestMapping("/new/Easy")
    public String newGameEasy(Model model){
        field=new Field(6,4,6);
        scoreModel(model);
        commentModel(model);
        return "breakingBricks";
    }
    @RequestMapping("/new/Medium")
    public String newGameMedium(Model model){
        field=new Field(10,10,5);
        scoreModel(model);
        commentModel(model);
        return "breakingBricks";
    }
    @RequestMapping("/new/Hard")
    public String newGameHard(Model model){
        field=new Field(20,45,3);
        scoreModel(model);
        commentModel(model);
        return "breakingBricks";
    }

    public int getCurrentScore(){
        return field.getScore();
    }

    public int getLifeCount(){
        return field.getLifeCount();
    }

    public boolean getFailedGame(){
        if (field.getState()==GameState.FAILED){
            return true;
        }
        else
            return false;
    }

    public String getGif(){
        StringBuilder sb = new StringBuilder();
        sb.append("<img src='/images/giph.gif'>");
        return sb.toString();
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var brick = field.getBrick(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/breakingBricks?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src='/images/" + getBrickImage(brick) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private String getBrickImage(Brick brick) {
        switch (brick.getType()){
            case B:
                return "blue";
            case G:
                return "green";
            case R:
                return "red";
        }
        return "empty";
    }
    private void scoreModel(Model model) {
        model.addAttribute("scores", scoreService.getTopScores(GAME_NAME));
    }
    private void commentModel(Model model) {
        model.addAttribute("comments", commentService.getComments(GAME_NAME));
    }

    public double averageRatingAtBrowser(){
        return ratingService.getAverageRating(GAME_NAME);
    }

}
