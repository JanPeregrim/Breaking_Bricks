package core;
import java.util.Random;
import java.util.List;
import java.util.Random;


public class Field {
    private GameState state = GameState.PLAYING;

    private int level = 0;


    private final int rowCount;

    private final int columnCount;

    private final Brick[][] bricks;

    private static final Random rand = new Random();


    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        bricks = new Brick[rowCount][columnCount];
        generateBricks();
    }
    private void generateBricks() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Brick brick = getBrick(row, column);
                if (brick ==null ) {
                    bricks[row][column] = new Brick();
                }
            }
         }

    }

    public GameState getState() {
        return state;
    }
    public int getRowCount() {
        return rowCount;
    }
    public int getColumnCount() {
        return columnCount;
    }

    public Brick getBrick(int row, int column){
        return bricks[row][column];
    }
    public void destroyBrick(int row,int column){
        var brick =getBrick(row,column);
        if(brick.getType()==BrickType.R){
            brick.setType(BrickType.E);
        }
    }
    public boolean isLevelCompleted(Field field) {
        var brick =getBrick(rowCount,columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if(brick.getType()!=BrickType.E){
                    return false;
                }
            }
        }
        return true;
    }

    private void newLevel()
    {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                // Continuous color
                if (column > 0 && rand.nextDouble() < 0.20) {
                    bricks[row][column] = bricks[row - 1][column];
                } else if (column > 0 && rand.nextDouble() < 0.20) {
                    bricks[row][column] = bricks[row][column - 1];
                }
                // Random
                else {
                   // bricks[row][column]=brick.setType(BrickType.E);

                }
            }
        }
        System.out.println("Current level: " + level++);
    }




}
