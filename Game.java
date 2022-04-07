import java.util.Random;
import java.util.TimerTask;

public class Game extends TimerTask {
    public final Cell[][] cells;
    public final int cellSize;
    private final Snake snake;
    private Position applePos;
    private boolean isRunning;

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

        this.snake = new Snake();
        this.generateApple();
        this.isRunning = true;
    }

    private void generateApple() {
        int rand1, rand2;
        Cell randCell;

        do {
            rand1 = new Random().nextInt(this.cells.length);
            rand2 = new Random().nextInt(this.cells[0].length);
            randCell = this.cells[rand1][rand2];
        } while(randCell.isSnake());

        this.applePos = new Position(rand1, rand2);
    }

    public void run() {
        if (this.isRunning) {
            this.snake.move();
            this.checkCollisions();
            this.updateCells();
        }
    }

    private void checkCollisions() {
        Position snakeHeadPos = this.snake.body[this.snake.body.length - 1];

        if (this.snakeCollidesWithApple(snakeHeadPos)) {
            this.generateApple();
            System.out.println("Ate apple");
        }

        if (this.snakeCollidesWithBorder(snakeHeadPos)) {
            this.isRunning = false;
            System.out.println("Game over");
        }
    }

    private boolean snakeCollidesWithApple(Position snakeHeadPos) {
        return snakeHeadPos.getX() == this.applePos.getX() &&
               snakeHeadPos.getY() == this.applePos.getY();
    }

    private boolean snakeCollidesWithBorder(Position snakeHeadPos) {
        int headPosX = snakeHeadPos.getX();
        int headPosY = snakeHeadPos.getY();

        return headPosX < 0 || headPosX > this.cells.length - 1 ||
               headPosY < 0 || headPosY > this.cells.length - 1;
    }

    public void updateCells() {
        for (Cell[] cellRow : this.cells) {
            for (Cell cell : cellRow) {
                int cellX = cell.getPosition().getX();
                int cellY = cell.getPosition().getY();
                int appleX = this.applePos.getX() * this.cellSize;
                int appleY = this.applePos.getY() * this.cellSize;

                // TODO: Refactor to set cells to true in generation
                if (cellX == appleX && cellY == appleY) {
                    cell.setApple(true);
                } else {
                    cell.setApple(false);
                }

                for (Position snakePos : this.snake.body) {
                    int snakeX = snakePos.getX() * this.cellSize;
                    int snakeY = snakePos.getY() * this.cellSize;

                    if (cellX == snakeX && cellY == snakeY) {
                        cell.setSnake(true);
                    } else {
                        cell.setSnake(false);
                    }
                }
            }
        }
    }

    public void processKeyPress(int keyCode) { // TODO: Change name?
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
