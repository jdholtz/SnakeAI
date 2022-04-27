package src.screen;

import src.ai.GameAI;
import src.game.Constants;
import src.game.Game;
import src.game.Position;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GamePanel extends JPanel implements ActionListener {
    public Game game;

    private final GameAI gameAI;
    private final JButton speedButton;
    private final Timer timer;
    private boolean isPaused = false;

    GamePanel() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // Add the speed button onto the panel
        this.speedButton = new JButton("1X");
        this.speedButton.setBounds(500, 20, 50, 40);
        this.speedButton.addActionListener(this);
        this.add(this.speedButton);
        this.speedButton.setFocusable(false); // So focus is still on the JFrame instead of the JButton

        this.gameAI = new GameAI();
        this.game = this.gameAI.getActiveGame();

        this.timer = new Timer(Constants.FRAME_INTERVAL, this);
        timer.start();
    }

    public void pause() {
        this.isPaused = !this.isPaused;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (this.isPaused) {
            return;
        }

        if (actionEvent.getSource() == this.speedButton) {
            // If the speed button is pressed
            this.changeSpeed();
        } else {
            this.gameAI.run();
            this.game = this.gameAI.getActiveGame();

            this.repaint();
            this.revalidate(); // Needed to redraw every frame
        }
    }

    private void changeSpeed() {
        String text = this.speedButton.getText();

        switch (text) {
            case "1X":
                this.timer.setDelay(Constants.FRAME_INTERVAL / 2);
                this.speedButton.setText("2X");
                break;
            case "2X":
                this.timer.setDelay(Constants.FRAME_INTERVAL / 4);
                this.speedButton.setText("4X");
                break;
            default:
                this.timer.setDelay(Constants.FRAME_INTERVAL);
                this.speedButton.setText("1X");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid(g);
        this.displayStats(g);

        if (!this.game.isRunning) {
            this.drawGameOverScreen(g);
        }
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
            if (snakePos == this.game.snake.body[0]) {
                // Color the head a different color
                g.setColor(new Color(5, 77, 4));
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(snakePos.getX() * cellSize, snakePos.getY() * cellSize, cellSize, cellSize);
        }
    }

    private void displayStats(Graphics g) {
        int score = this.game.snake.getLength() - 3;
        String scoreMessage = "Score: " + score;
        g.setFont(new Font("TimesRoman", Font.BOLD, 32));

        // Align the score display correctly
        FontMetrics fm = g.getFontMetrics();
        double scoreX = ((Constants.SCREEN_WIDTH - fm.stringWidth(scoreMessage)) / 1.25);

        g.drawString("Generation " + this.gameAI.numGeneration, Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 20);
        g.drawString(scoreMessage, (int) scoreX, Constants.SCREEN_HEIGHT / 20);
    }

    private void drawGameOverScreen(Graphics g) {
        this.repaint();
        this.revalidate();
        String gameOverMessage = "Game Over";

        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 64));

        // Get x value to center text
        FontMetrics fm = g.getFontMetrics();
        int gameOverX = ((Constants.SCREEN_WIDTH - fm.stringWidth(gameOverMessage)) / 2);

        g.drawString(gameOverMessage, gameOverX, Constants.SCREEN_HEIGHT / 2);
    }
}
