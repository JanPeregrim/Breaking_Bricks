package consoleui;

import core.BrickType;
import core.Field;
import core.GameState;

import java.util.Scanner;

public class ConsoleUI {
    private final  Field field;

    private Scanner scanner=new Scanner(System.in);

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
        printFieldHeading();
        printFieldAndNumbers();
    }

    private void printFieldAndNumbers() {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print(row + 1 +" ");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var brick  = field.getBrick(row, column);
                System.out.print(" ");
                if(brick.getType()!= BrickType.E)
                    System.out.print((brick.getType()));
                else
                    System.out.print("-");

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
        System.out.println("       Score: ");
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
}
