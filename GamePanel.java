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
        this.game.drawGrid(g);
    }
}
