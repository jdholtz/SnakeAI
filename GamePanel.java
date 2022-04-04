import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel {
    private final int screenWidth = 1000;
    private final int screenHeight = 1000;
    private final Cell[][] cells;
    private final int cellSize;

    GamePanel(int numberOfCells) {
        // Number of cells should be a perfect square so a square grid can be made
        int numCellsOnSide = (int) Math.sqrt(numberOfCells);
        this.cellSize = screenWidth / numCellsOnSide;

        // Initialize cells on the board
        this.cells = new Cell[numCellsOnSide][numCellsOnSide];
        for (int i = 0; i < numCellsOnSide; i++) {
            for (int j = 0; j < numCellsOnSide; j++) {
                this.cells[i][j] = new Cell(i * this.cellSize, j * this.cellSize);
            }
        }

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid(g);
    }


    private void drawGrid(Graphics g) {
        // Draw horizontal lines
        for (int i = cellSize; i <= screenWidth; i += cellSize) {
            g.drawLine(i, 0, i, screenHeight);
            g.drawLine(0, i, screenWidth, i);
        }

        this.colorGrid(g);
    }

    private void colorGrid(Graphics g) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                Position cellPos = cell.getPosition();

                if (cell.isApple()) {
                    g.setColor(Color.RED);
                    g.fillOval(cellPos.getX(), cellPos.getY(), this.cellSize, this.cellSize);
                } else if (cell.isSnake()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(cellPos.getX(), cellPos.getY(), this.cellSize, this.cellSize);
                }
            }
        }
    }
}
