import java.awt.*;
import javax.swing.*;

public class DigitsSectionUI extends JPanel {
    Font myFont = new Font("Arial", Font.PLAIN, 30);
    DigitsSection digitsSection;

    public DigitsSectionUI(DigitsSection digitsSection) {
        this.digitsSection = digitsSection;
        setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {"C", "+/-", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "del", "0", ".", "="};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(myFont);
            button.setForeground(Color.decode("#FFFFFF"));
            if (label.equals("C") || label.equals("+/-") || label.equals("%")) {
                button.setBackground(Color.decode("#454442"));
            } else if (label.equals("/") || label.equals("*") || label.equals("-") || label.equals("+") || label.equals("=")) {
                button.setBackground(Color.decode("#FF9F09"));
            } else {
                button.setBackground(Color.decode("#636361"));
            }

            button.addActionListener(e -> {
                String buttonText = button.getText();
                digitsSection.addAction(buttonText);
            });
            add(button);
        }
    }
}
