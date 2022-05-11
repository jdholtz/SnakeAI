package src.screen.panels;

import src.game.Constants;
import src.game.Game;
import src.game.Position;
import src.screen.components.Button;

import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;


public class GamePanel extends Panel {
    public Game game;

    protected final Timer timer;

    private final JButton speedButton;

    GamePanel() {
        this.speedButton = new Button("1X", Constants.SCREEN_WIDTH / 2, 20, 50, 40);
        this.speedButton.addActionListener(this);
        this.add(this.speedButton);

        this.timer = new Timer(Constants.FRAME_INTERVAL, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.speedButton) {
            // If the speed button is pressed
            this.changeSpeed();
        } else {
            this.repaint();
            this.revalidate(); // Needed to redraw every frame
        }
    }

    @Override
    public void processKeyPress(int keyCode) {
        if (keyCode == 80) { // p
            this.pause();
        } else if (keyCode == 75) { // k
            this.game.stop();
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
        this.drawScore(g);
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

    private void drawScore(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 32));

        int score = this.game.snake.getLength() - 3; // Starting length is 3
        int x = (int) (Constants.SCREEN_WIDTH / 1.3);
        int y = Constants.SCREEN_HEIGHT / 16;
        super.drawString("Score: " + score, g, x, y);
    }
}
