package gamestudio.core;
import java.util.Random;

public class Field {
    private GameState state = GameState.PLAYING;

    private int score = 0;

    private int lifeCount ;

    private final int rowCount;

    private final int columnCount;

    private final Brick[][] bricks;

    private static final Random rand = new Random();

    public Field(int rowCount, int columnCount,int lifeCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.lifeCount=lifeCount;
        bricks = new Brick[rowCount][columnCount];
        generate();
    }

    private void generate() {
        generateField();
        generateType();
    }

    private void generateField() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Brick brick = getBrick(row, column);
                if (brick == null) {
                    bricks[row][column] = new Brick();
                }
            }
        }
    }

    private void generateType() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var brick = getBrick(row, column);

                Random random = new Random();
                int type = random.nextInt(10);
                if (type < 3) {
                    brick.setType(BrickType.G);
                } else if (type > 6) {
                    brick.setType(BrickType.B);
                } else {
                    brick.setType(BrickType.R);
                }


            }
        }
    }

    public void destroyBrick(int row, int column) {
        var brick = getBrick(row, column);
        BrickType type = brick.getType();
        if (type != BrickType.E) {
            aloneBrick(row, column, type);
            destroyNeighbour(row, column, type);
            seetleBricks();
        }
        if (isSolved()) {
            generateType();
        }
        if (isFailed()) {
            state = GameState.FAILED;
        }
    }

    private boolean isFailed() {
        if (getLifeCount() == 0)
            return true;
        else
            return false;
    }

    private boolean isSolved() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var brick = getBrick(row, column);
                if (brick.getType() != BrickType.E)
                    return false;
            }
        }
        return true;
    }

    private boolean aloneBrick(int row, int column, BrickType type) {
        if (row < rowCount - 1) {
            var downBrick = getBrick(row + 1, column);
            if (type == downBrick.getType()) {
                return false;
            }
        }
        if (row >= 1) {
            var Brick = getBrick(row - 1, column);
            if (type == Brick.getType()) {
                return false;
            }
        }
        if (column < columnCount - 1) {
            var downBrick = getBrick(row, column + 1);
            if (type == downBrick.getType()) {
                return false;
            }
        }
        if (column >= 1) {
            var downBrick = getBrick(row, column - 1);
            if (type == downBrick.getType()) {
                return false;
            }
        }
        var brick = getBrick(row, column);
        brick.setType(BrickType.E);
        lifeCount--;
        return true;
    }

    private void destroyNeighbour(int row, int column, BrickType type) {
        setScore();
        if (row < rowCount - 1) {
            var downBrick = getBrick(row + 1, column);
            if (type == downBrick.getType()) {
                downBrick.setType(BrickType.E);
                destroyNeighbour(row + 1, column, type);
            }
        }
        if (row >= 1) {
            var upBrick = getBrick(row - 1, column);
            if (type == upBrick.getType()) {
                upBrick.setType(BrickType.E);
                destroyNeighbour(row - 1, column, type);
            }
        }
        if (column >= 1) {
            var leftBrick = getBrick(row, column - 1);
            if (type == leftBrick.getType()) {
                leftBrick.setType(BrickType.E);
                destroyNeighbour(row, column - 1, type);
            }
        }
        if (column < columnCount - 1) {
            var rightBrick = getBrick(row, column + 1);
            if (type == rightBrick.getType()) {
                rightBrick.setType(BrickType.E);
                destroyNeighbour(row, column + 1, type);
            }
        }
    }

    public void seetleBricks() {
        //Seetle bricks down
        int emptyRow = 0;

        for (int column = 0; column < columnCount; column++) {
            emptyRow=0;
            for (int row = rowCount - 1; row >= 0; row--) {
                var brick = getBrick(row, column);
                BrickType type = brick.getType();
                if(type==BrickType.E && row>0){
                    emptyRow=row;
                    while(getBrick(row-1,column).getType()==BrickType.E && row>1){
                        row--;
                    }
                }
                brick = getBrick(row, column);
                type = brick.getType();

                if(type!=BrickType.E && emptyRow!=0){
                    getBrick(emptyRow,column).setType(type);
                    brick.setType(BrickType.E);
                    if(row==0)
                        break;
                    else
                        emptyRow=0;
                        row=rowCount-1;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore() {
        this.score = score + 50;
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

    public int getLifeCount() {
        return lifeCount;
    }

    public Brick getBrick(int row, int column) {
        return bricks[row][column];
    }
}

