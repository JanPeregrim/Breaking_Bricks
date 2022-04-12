package gamestudio.consoleui;

import gamestudio.core.BrickType;
import gamestudio.core.GameState;
import gamestudio.core.Field;
import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    public static final String GAME_NAME = "breaking bricks";

    private  Field field;

    private Scanner scanner=new Scanner(System.in);

    public ConsoleUI(){
    }

    public void play(){
        printGameSettings();
        printTopScores();
        do {
            printField();
            processInput();
        } while (field.getState() == GameState.PLAYING);

        printField();

        if(field.getState()==GameState.FAILED) {
            setPlayerName();
            printTopScores();
            return;
        }

    }

    private void printGameSettings() {

        for(int i=0;i<5;i++) {
            System.out.println(ANSI_BLUE + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        System.out.println(ANSI_BLUE+"XXXXXXXXXXXXXXXXXXXXXXXXX BRICKS BREAKING XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("XXXXXXXXXXXXXX Please select difficulty for your game XXXXXXXXXXXXX"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"XXXXXXXXXXXXXXXXXX type E-easy,M-medium,H-hard XXXXXXXXXXXXXXXXXXXX");
        for(int i=0;i<5;i++) {
            System.out.println(ANSI_YELLOW+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + ANSI_RESET);
        }
        var line =scanner.nextLine();
        if(line.startsWith("E")) {
            field = new Field(6, 6,10);
        }
        else if(line.startsWith("M")) {
             field = new Field(9, 9,7);
        }
        else if(line.startsWith("H")) {
             field = new Field(9, 35,1);
        }
        else
            return;
    }

        private void setPlayerName() {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX GAME FAILED! XXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("XXXXXXXXXXXXXXXXXXXX Please Select your name XXXXXXXXXXXXXXXXX");
        var name =scanner.nextLine();
        scoreService.addScore(new Score(name, "breaking bricks", field.getScore(), new Date()));
            System.out.print("Please rate our game (1-5) :");
            var rate=scanner.nextLine();
            if(rate.charAt(0)>='1' || rate.charAt(0)<='5'){
                int stars = rate.charAt(0)-'0';
                ratingService.addRating(new Rating(name, "breaking bricks", stars, new Date()));
            }
            System.out.print("Do you want write some comment ? [Y/N]  ");
        var answer =scanner.nextLine();
        if(answer.charAt(0) =='Y'){
            System.out.print("Please write your comment  ");
            var comment =scanner.nextLine();
            commentService.addComment(new Comment(name,"breaking bricks",comment,new Date()));
        }
        printComment();
        printRating();
    }

    private void printRating() {
        System.out.print("\n                        NEWEST RATINGS \n");
        System.out.printf("-----------------------------------------------------------\n");
        List<Rating> ratings = ratingService.getRatings(GAME_NAME);

        for(int firstTen = 0; firstTen <ratings.size() && firstTen<10; firstTen++) {
            var rating = ratings.get(firstTen);
            System.out.printf("%s        %s     %s \n",rating.getPlayedAt(),rating.getStars(),rating.getPlayer());
        }
        System.out.printf("-----------------------------------------------------------\n");

    }

    private void printComment(){
        System.out.print("\n                        NEWEST COMMENTS \n");
        System.out.printf("-----------------------------------------------------------\n");
        List<Comment> comments = commentService.getComments(GAME_NAME);

        for(int firstTen = 0; firstTen <comments.size() && firstTen<6; firstTen++) {
            var comment = comments.get(firstTen);
            System.out.printf("%s        %s     %s \n",comment.getPlayedAt(),comment.getPlayer(),comment.getComment());
        }
        System.out.printf("-----------------------------------------------------------\n");
    }

    private void printField() {
        printFieldHeading();
        printFieldAndNumbers();
    }

    private void printFieldAndNumbers() {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print(row + 1 +" ");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var brick  = field.getBrick(row, column);
                if(brick.getType()!= BrickType.E) {
                    switch (brick.getType()) {
                        case B:
                            System.out.print(ANSI_BLUE_BACKGROUND +"  "+ANSI_RESET);
                            break;
                        case G:
                            System.out.print(ANSI_GREEN_BACKGROUND +"  "+ANSI_RESET);
                            break;
                        case R:
                           System.out.print(ANSI_RED_BACKGROUND +"  "+ANSI_RESET);
                            break;
                    }
                }
                else
                    System.out.print("  ");

            }
            System.out.println();
        }
    }

    private void printFieldHeading() {
        int columnAlpha ='A';
        System.out.print("  ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            if(column>=9){
                System.out.print(" ");
                System.out.print((char)(columnAlpha));
                columnAlpha++;
            }
            else {
                System.out.print(" ");
                System.out.print(column + 1);
            }
        }
        System.out.print("    Life : ");
        for(int liveCount=0;liveCount<field.getLifeCount();liveCount++){
            System.out.print("\u2661 ");
        }
        System.out.println("       Score: " +field.getScore());
    }

    private void processInput() {
        System.out.print("Enter command (X - exit, D12 - destroy): ");
        var line =scanner.nextLine();
        if ("X".equals(line))
            System.exit(0);

        if(line.length()==3){
            int column=0;
            if(line.charAt((2))>64 && field.getColumnCount()+56>line.charAt(2)) {
                column = line.charAt(2) - '8';
            }
            else {
                column = line.charAt(2) - '1';
            }
            int row = line.charAt(1) - '1';
            if (line.startsWith("D")) {
                field.destroyBrick(row, column);
            }
            else
                System.out.println("Wrong input, check command");
        }
        else
            System.out.println("Wrong input, check command");
    }

    private void printTopScores() {
        System.out.print("\n                         TOP 5 SCORES \n");
        System.out.printf("-----------------------------------------------------------\n");
        List<Score> scores = scoreService.getTopScores(GAME_NAME);

        for(int topFive=0;topFive< scores.size() && topFive<5;topFive++) {
            var score = scores.get(topFive);
            System.out.printf("%d        %s \n",score.getPoints(),score.getPlayer());
        }
        System.out.printf("-----------------------------------------------------------\n");
    }
}
