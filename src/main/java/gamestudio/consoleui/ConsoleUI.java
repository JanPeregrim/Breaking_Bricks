package gamestudio.consoleui;

import gamestudio.core.BrickType;
import gamestudio.core.GameState;
import gamestudio.core.Field;
import gamestudio.entity.Score;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceJDBC;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final String ANSI_RED = "\u001B[41m";
    public static final String ANSI_GREEN = "\u001B[42m";
    public static final String ANSI_BLUE = "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static final String GAME_NAME = "breaking bricks";

    private final Field field;

    private Scanner scanner=new Scanner(System.in);

    private ScoreService scoreService = new ScoreServiceJDBC();

    public ConsoleUI(Field field){
        this.field=field;
    }

    public void play(){
        do {
            printField();
            processInput();
        } while (field.getState() == GameState.PLAYING);

        printField();

        if(field.getState()==GameState.FAILED) {
            System.out.println("Game Failed!");
            return;
        }

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
                System.out.print(" ");
                if(brick.getType()!= BrickType.E) {
                    switch (brick.getType()) {
                        case B:
                            //System.out.print("B");
                            System.out.print(ANSI_BLUE+" "+ANSI_RESET);
                            break;
                        case G:
                            //System.out.print("G");
                            System.out.print(ANSI_GREEN+" "+ANSI_RESET);
                            break;
                        case R:
                            //System.out.print("R");
                           System.out.print(ANSI_RED+" "+ANSI_RESET);
                            break;
                    }
                }
                else
                    System.out.print(" ");

            }
            System.out.println();
        }
    }

    private void printFieldHeading() {
        System.out.print("  ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.print("    Life : "+ field.getLifeCount());
        System.out.println("       Score: " +field.getScore());
    }

    private void processInput() {
        System.out.print("Enter command (X - exit, D12 - destroy): ");
        var line =scanner.nextLine();
        if ("X".equals(line))
            System.exit(0);

        if(line.length()==3){
            int row = line.charAt(1) - '1';
            int column = line.charAt(2) - '1';

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
