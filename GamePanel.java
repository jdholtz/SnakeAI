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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.game.isRunning) {
            this.game.run();
            this.repaint();
            this.revalidate(); // Needed to redraw every frame
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid(g);
    }

    public void drawGrid(Graphics g) {
        for (int i = this.game.cellSize; i <= Constants.SCREEN_WIDTH; i += this.game.cellSize) {
            // Draw vertical lines
            g.drawLine(i, 0, i, Constants.SCREEN_HEIGHT);
            // Draw horizontal lines
            g.drawLine(0, i, Constants.SCREEN_WIDTH, i);
        }

        this.colorGrid(g);
    }

    private void colorGrid(Graphics g) {
        int cellSize = this.game.cellSize;

        // Color cell that has the apple
        Position applePos = this.game.applePos;
        g.setColor(Color.RED);
        g.fillOval(applePos.getX() * cellSize, applePos.getY() * cellSize, cellSize, cellSize);

        // Color every cell that has the snake
        for (Position snakePos : this.game.snake.body) {
            g.setColor(Color.GREEN);
            g.fillRect(snakePos.getX() * cellSize, snakePos.getY() * cellSize, cellSize, cellSize);
        }
    }
}
