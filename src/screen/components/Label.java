package src.screen.components;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;


public class Label extends JLabel {
    public Label(String text, int x, int y, int width, int height) {
        this.setText(text);
        this.setBounds(x, y, width, height);
        this.setFont(new Font("TimesRoman", Font.BOLD, 20));
        this.setForeground(Color.WHITE);
    }
}
