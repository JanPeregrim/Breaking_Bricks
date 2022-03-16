package core;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        var field = new Field(12, 12);

        printField(field);
        field.destroyBrick(0,0);
        printField(field);
    }

    private static void printField(Field field) {
        System.out.println();

        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                var brick  = field.getBrick(row, column);
                System.out.print(" ");
                System.out.print((brick.getType()));
            }
            System.out.println();

        }
    }
}
