package src.screen;

import javax.swing.JFormattedTextField;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

public class TextField extends JFormattedTextField {
    TextField(NumberFormat format, Label label) {
        super(format);
        this.setBackground(Color.WHITE);
        this.setFont(new Font("TimesRoman", Font.BOLD, 20));

        // Set bounds a little lower than its corresponding label
        this.setBounds(label.getX(), label.getY() + 30, label.getWidth(), label.getHeight());
    }
}
