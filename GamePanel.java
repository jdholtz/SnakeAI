import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GamePanel extends JPanel implements ActionListener {
    public final Game game;

    GamePanel() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        this.game = new Game();

        Timer timer = new Timer(Constants.FRAME_INTERVAL, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        this.game.run();
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid(g);
    }

    public void drawGrid(Graphics g) {
        // Draw horizontal lines
        for (int i = this.game.cellSize; i <= Constants.SCREEN_WIDTH; i += this.game.cellSize) {
            g.drawLine(i, 0, i, Constants.SCREEN_HEIGHT);
            g.drawLine(0, i, Constants.SCREEN_WIDTH, i);
        }

        this.colorGrid(g);
    }

    private void colorGrid(Graphics g) {
        for (Cell[] cellRow : this.game.cells) {
            for (Cell cell : cellRow) {
                Position cellPos = cell.getPosition();
                int cellSize = this.game.cellSize;

                if (cell.isApple()) {
                    g.setColor(Color.RED);
                    g.fillOval(cellPos.getX(), cellPos.getY(), cellSize, cellSize);
                } else if (cell.isSnake()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(cellPos.getX(), cellPos.getY(), cellSize, cellSize);
                }
            }
        }
    }
}
