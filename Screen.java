import javax.swing.JFrame;

public class Screen {
    private final JFrame mainScreen;

    public Screen (int numCells) {
        mainScreen = new JFrame("Snake");
        mainScreen.add(new GamePanel(numCells));

        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.pack();
        mainScreen.setResizable(false);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setVisible(true);
    }
}
