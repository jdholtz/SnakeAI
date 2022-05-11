package src.screen.components;

import javax.swing.JButton;


public class Button extends JButton {
    public Button(String title, int x, int y, int width, int height) {
        this.setText(title);
        this.setBounds(x, y, width, height);
        this.setFocusable(false); // So focus is still on the JFrame instead of the JButton
    }
}
