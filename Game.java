import java.util.Random;
import java.util.TimerTask;

public class Game extends TimerTask {
    public final Cell[][] cells;
    public final int cellSize;
    public final Snake snake;
    public Position applePos;
    public boolean isRunning;
    private boolean keyPressedInFrame = false;

    Game() {
        // Number of cells should be a perfect square so a square grid can be made
        int numCellsOnSide = (int) Math.sqrt(Constants.TOTAL_CELLS);
        this.cellSize = Constants.SCREEN_WIDTH / numCellsOnSide;

        // Initialize cells on the board
        this.cells = new Cell[numCellsOnSide][numCellsOnSide];
        for (int i = 0; i < numCellsOnSide; i++) {
            for (int j = 0; j < numCellsOnSide; j++) {
                this.cells[i][j] = new Cell(i * this.cellSize, j * this.cellSize);
            }
        }

        this.snake = new Snake(numCellsOnSide);
        this.updateCells();
        this.generateApple();
        this.isRunning = true;
    }

    private void generateApple() {
        int rand1, rand2;
        Cell randCell;

        // Keep trying to generate an apple until it's in a position where the snake isn't
        do {
            rand1 = new Random().nextInt(this.cells.length);
            rand2 = new Random().nextInt(this.cells[0].length);
            randCell = this.cells[rand1][rand2];
        } while(randCell.isSnake());

        this.applePos = new Position(rand1, rand2);
    }

    public void run() {
        this.keyPressedInFrame = false;

        this.snake.move();
        this.updateCells();
        this.checkCollisions();
    }

    public void stop() {
        this.isRunning = false;
    }

    private void checkCollisions() {
        Position snakeHeadPos = this.snake.body[0];

        if (this.snakeCollidesWithApple(snakeHeadPos)) {
            this.snake.grow();
            this.generateApple();
        }

        if (this.snakeCollidesWithBorder(snakeHeadPos) || this.snakeCollidesWithItself(snakeHeadPos)) {
            this.stop();
        }
    }

    public boolean snakeCollidesWithApple(Position snakeHeadPos) {
        return snakeHeadPos.getX() == this.applePos.getX() &&
               snakeHeadPos.getY() == this.applePos.getY();
    }

    public boolean snakeCollidesWithBorder(Position snakeHeadPos) {
        int headPosX = snakeHeadPos.getX();
        int headPosY = snakeHeadPos.getY();

        return headPosX < 0 || headPosX > this.cells.length - 1 ||
               headPosY < 0 || headPosY > this.cells[0].length - 1;
    }

    public boolean snakeCollidesWithItself(Position snakeHeadPos) {
        int headPosX = snakeHeadPos.getX();
        int headPosY = snakeHeadPos.getY();
        Position[] snakeBody = this.snake.body;

        // Starts at 1 because there is no need to check if the head collides with itself
        for (int i = 1; i < snakeBody.length; i++) {
            if (headPosX == snakeBody[i].getX() && headPosY == snakeBody[i].getY()) {
                return true;
            }
        }

        return false;
    }

    public void updateCells() {
        // First, reset all cells. Then, set all cells that are part of the snake to true.
        // Separating these functions into two loops increases efficiency because less iterating is done.
        for (Cell[] cellRow : this.cells) {
            for (Cell cell : cellRow) {
                cell.setSnake(false);
            }
        }

        Position[] snakeBody = this.snake.body;
        for (Position snakePos : snakeBody) {
            // Head position is off the screen, so we don't need to set it to true
            if (snakePos == snakeBody[0] && this.snakeCollidesWithBorder(snakePos)) continue;

            Cell snakeCell = this.getCellFromPosition(snakePos);
            snakeCell.setSnake(true);
        }
    }

    public Cell getCellFromPosition(Position pos) {
        int posX = pos.getX();
        int posY = pos.getY();

        return this.cells[posX][posY];
    }

    public void processKeyPress(int keyCode) {
        // Prevents being able to change directions twice in the same frame
        if (this.keyPressedInFrame) return;
        this.keyPressedInFrame = true;

        switch (keyCode) {
            case 87: // w
            case 38: // Up arrow
                if (this.snake.direction != Constants.DIRECTION_DOWN) {
                    this.snake.direction = Constants.DIRECTION_UP;
                }
                break;
            case 68: // d
            case 39: // Right arrow
                if (this.snake.direction != Constants.DIRECTION_LEFT) {
                    this.snake.direction = Constants.DIRECTION_RIGHT;
                }
                break;
            case 83: // s
            case 40: // Down arrow
                if (this.snake.direction != Constants.DIRECTION_UP) {
                    this.snake.direction = Constants.DIRECTION_DOWN;
                }
                break;
            case 65: // a
            case 37: // Left arrow
                if (this.snake.direction != Constants.DIRECTION_RIGHT) {
                    this.snake.direction = Constants.DIRECTION_LEFT;
                }
                break;
        }
    }
}
