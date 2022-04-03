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
    public static final String ANSI_RED = "\u001B[41m";
    public static final String ANSI_GREEN = "\u001B[42m";
    public static final String ANSI_BLUE = "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private ScoreService scoreService = new ScoreServiceJDBC();

    public static final String GAME_NAME = "breaking bricks";

    private final Field field;

    private Scanner scanner=new Scanner(System.in);

    public ConsoleUI(Field field){
        this.field=field;
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
        System.out.println("--------------------BRICKS BREAKING------------------");
        System.out.println("Please select dificulty for your game");
        System.out.println("type E-easy,M-medium,H-hard");
        /*
        var line =scanner.nextLine();
        if(line.startsWith("E")) {
            var field = new Field(6, 6,10);
        }
        else if(line.startsWith("M")) {
            var field = new Field(9, 9,7);
        }
        else if(line.startsWith("H")) {
            var field = new Field(9, 35,5);
        }
        else
            return ;

         */
    }

        private void setPlayerName() {
        System.out.println("XXXXXXXXXXX GAME FAILED! XXXXXXXXXXXXXXX");
        System.out.print("Please Select your name: ");
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
                            System.out.print(ANSI_BLUE+"  "+ANSI_RESET);
                            break;
                        case G:
                            System.out.print(ANSI_GREEN+"  "+ANSI_RESET);
                            break;
                        case R:
                           System.out.print(ANSI_RED+"  "+ANSI_RESET);
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
        int columnChar='A';
        System.out.print("  ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            if(column>=9){
                System.out.print(" ");
                System.out.print((char)(columnChar));
                columnChar++;
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
            //System.out.print(line.charAt(1));
            if(line.charAt((2))>53) {
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
        List<Score> scores = scoreService.getTopScores(GAME_NAME);
        for(Score score : scores) {
            System.out.printf("%d        %s \n",score.getPoints(),score.getPlayer());
        }
        System.out.printf("----------------------------------------\n");
    }
}
