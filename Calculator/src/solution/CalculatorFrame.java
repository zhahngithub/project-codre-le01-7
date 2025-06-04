import java.awt.*;
import javax.swing.*;

// JANGAN DIEDIT

public class CalculatorFrame extends JFrame {

    public CalculatorFrame(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());

        InputSection inputSection = new InputSection();
        inputSection.setPreferredSize(new Dimension(width, height / 5));
        mainPanel.add(inputSection, BorderLayout.NORTH);

        JPanel sectionsPanel = new JPanel(new GridLayout(1, 0));
        ScientificSection scientificSection = new ScientificSection(inputSection);
        sectionsPanel.add(scientificSection);
        DigitsSection digitsSection = new DigitsSection(inputSection);
        DigitsSectionUI digitsSectionUI = new DigitsSectionUI(digitsSection);
        sectionsPanel.add(digitsSectionUI);
        mainPanel.add(sectionsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

}
