import java.awt.Color;

/*
 * Smell Code   : The Dispensable - Comments
 * Reason       : Terlalu banyak comments di setiap method pada berbagai class di project ini
 * Solution     : Remove comment, dan pastikan method / variable diberikan nama yang jelas 
 */

public class Main {
    static CalculatorFrame frame;
    public static void main(String[] args) {
        frame = new CalculatorFrame("Calculator",1400,1000);
        frame.setBackground(Color.decode("#32322F"));
        frame.setVisible(true);
    } 
}
