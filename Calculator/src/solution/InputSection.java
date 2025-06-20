import javax.swing.*;
import java.awt.*;

public class InputSection extends JPanel {

    private final JTextField inputField;

    public double memoryValue=0;

    public InputSection() {
        setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        inputField.setFont(new Font("Arial", Font.PLAIN, 50));
        inputField.setBackground(Color.decode("#32322F"));
        inputField.setForeground(Color.decode("#FFFFFF"));
        add(inputField, BorderLayout.CENTER);
    }
    public void updateInputField(String text) {
        String currentText = inputField.getText();
        if (currentText.equals("0")) {
            inputField.setText(text);
        } else {
            inputField.setText(currentText + text);
        }
    }
    public void deleteInputField() {
        inputField.setText(""); 
    }
    
    public void removeCurrentText(){
        String currentText=inputField.getText();
        if(!currentText.isEmpty()){
            inputField.setText(currentText.substring(0, currentText.length()-1));
        }
    }

    public String getInputFieldText(){
        return inputField.getText();

    }

    public void setInputField(String text){
        inputField.setText(text);
    }

    public double resetMemory(){
       memoryValue=0;
       return memoryValue;
    }

    public double setMemoryValue(double num){
        memoryValue = num;
        setInputField(Double.toString(memoryValue)); 
        return memoryValue;        
    }

    public double getMemoryValue(){
        setInputField(String.valueOf(memoryValue));
        System.out.println(memoryValue);
        return memoryValue;
    }

    public double subtractFromMemory(double num) { 
        double result = memoryValue - num;
        memoryValue = result; 
        setInputField(String.valueOf(memoryValue));
        return result;
    }
}