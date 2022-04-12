package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private String comment;

    private Date playedAt;

    public Comment(String player, String game, String comment, Date playedAt) {
        this.player = player;
        this.game = game;
        this.comment = comment;
        this.playedAt = playedAt;
    }

    public Comment() {
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", comment='" + comment + '\'' +
                ", playedAt=" + playedAt +
                '}';
    }
}
