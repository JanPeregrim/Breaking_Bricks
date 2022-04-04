package gamestudio.consoleui;

import gamestudio.core.BrickType;
import gamestudio.core.GameState;
import gamestudio.core.Field;
import gamestudio.entity.Score;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private ScoreService scoreService = new ScoreServiceJDBC();

    public static final String GAME_NAME = "breaking bricks";

    private  Field field;

    private Scanner scanner=new Scanner(System.in);

    public ConsoleUI(){
    }

    public void play(){
        printGameSettings();
        do {
            printField();
            processInput();
        } while (field.getState() == GameState.PLAYING);

        printField();

        if(field.getState()==GameState.FAILED) {
            setPlayerName();
            return;
        }

    }

    private void printGameSettings() {
        System.out.println(ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+ANSI_RESET);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX BRICKS BREAKING XXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("XXXXXXXXXXXXXX Please select difficulty for your game XXXXXXXXXXXXX");
        System.out.println("XXXXXXXXXXXXXXXXXX type E-easy,M-medium,H-hard XXXXXXXXXXXXXXXXXXXX");
        System.out.println(ANSI_RED+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+ANSI_RESET);

        var line =scanner.nextLine();
        if(line.startsWith("E")) {
            field = new Field(6, 6,10);
        }
        else if(line.startsWith("M")) {
             field = new Field(9, 9,7);
        }
        else if(line.startsWith("H")) {
             field = new Field(9, 35,5);
        }
        else
            return ;


    }

        private void setPlayerName() {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX GAME FAILED! XXXXXXXXXXXXXXXXXXXXXXX");
        System.out.print("XXXXXXXXXXXXXXXXXXXX Please Select your name XXXXXXXXXXXXXXXXX");
        var name =scanner.nextLine();
        scoreService.addScore(new Score(name, "breaking bricks", field.getScore(), new Date()));
    }

    private void printField() {
        printTopScores();
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
        System.out.printf("-----------------------------------------------------------\n");
        List<Score> scores = scoreService.getTopScores(GAME_NAME);

        for(int topFive=0;topFive<5;topFive++) {
            var score = scores.get(topFive);
            System.out.printf("%d        %s \n",score.getPoints(),score.getPlayer());
        }
        System.out.printf("-----------------------------------------------------------\n");
    }
}
