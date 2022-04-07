import java.awt.Color;
import java.awt.Graphics;
import java.util.TimerTask;

public class Game extends TimerTask {
    public final Cell[][] cells;
    public final int cellSize;
    private final Snake snake;

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
    }

    public void run() {
        this.snake.move();
        this.updateCells();
    }

    public void updateCells() {
        for (Cell[] cellRow : this.cells) {
            for (Cell cell : cellRow) {
                Position cellPos = cell.getPosition();

                for (Position snakePos : this.snake.body) {
                    int snakeX = snakePos.getX() * this.cellSize;
                    int snakeY = snakePos.getY() * this.cellSize;

                    if (cellPos.getX() == snakeX && cellPos.getY() == snakeY) {
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
            default: break;
        }
        System.out.println(this.snake.direction);
    }
}
