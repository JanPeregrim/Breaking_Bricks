package gamestudio.server.controller;

import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.entity.User;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    private User loggedUser;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            loggedUser = new User(login);
            return "redirect:/breakingBricks";
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    @RequestMapping("/addComment")
    public String addCommentAtBrowser(String comment) {
        commentService.addComment(new Comment(loggedUser.getLogin(),"Breaking Bricks", comment, new Date()));
            return "redirect:/breakingBricks";
    }
    @RequestMapping("/addRating")
    public String addRatingAtBrowser(int rating) {
        ratingService.addRating(new Rating(loggedUser.getLogin(),"Breaking Bricks", rating, new Date()));
        return "redirect:/breakingBricks";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

}


