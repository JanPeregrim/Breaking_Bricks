package gamestudio;

import gamestudio.consoleui.ConsoleUI;
import gamestudio.core.Field;

public class Main {
    public static void main(String[] args) {
        var field = new Field(8, 8);
        var ui = new ConsoleUI(field);
        ui.play();
    }
}

