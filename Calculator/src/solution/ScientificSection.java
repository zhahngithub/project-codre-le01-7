import javax.swing.*;
import java.awt.*;

/*
 * Smell Code   : Duplicate Code ✅
 * Reason       : Proses pembuatan dan styling tombol dilakukan berulang dalam loop.
 * Solution     : Ekstrak method `createStyledButton(String label)` untuk modularitas.
 */

/*
 * Smell Code   : Primitive Obsession ✅
 * Reason       : Penggunaan `String` sebagai representasi tipe fungsi (misalnya "mplus", "powerTen").
 * Solution     : Gunakan `enum` FunctionType untuk meningkatkan type safety dan readability.
 */

/*
 * Smell Code   : Long Method ✅
 * Reason       : Method `applyUnaryFunction()` mengandung banyak logika pemrosesan dalam satu blok switch-case.
 * Solution     : Refactor menjadi method terpisah per fungsi (misal: `handleSquare()`, `handleCube()`).
 */

/*
 * Smell Code   : Feature Envy ✅
 * Reason       : Banyak logika pengolahan angka dilakukan di class ini, padahal harusnya dilakukan oleh `ScientificFunction`.
 * Solution     : Serahkan semua kalkulasi ke `ScientificFunction`, `ScientificCalculatorEngine`, atau class domain lainnya.
 */

/*
 * Smell Code   : Data Clumps  ✅
 * Reason       : Kombinasi `double num` dan `String functionName` sering digunakan bersamaan.
 * Solution     : Bungkus dalam class seperti `ScientificCommand` untuk menyederhanakan parameter.
 */

/*
 * Smell Code   : Dead Code ✅
 * Reason       : Beberapa case (seperti "mread") tidak menjalankan logika aktual, hanya print ke console.
 * Solution     : Implementasikan fungsionalitas sebenarnya atau hapus jika tidak dibutuhkan.
 */

public class ScientificSection extends JPanel {

    Font myFont = new Font("Arial", Font.PLAIN, 30);
    InputSection inputSection;
    private boolean showInverseFunctions = false;

    public ScientificSection(InputSection inputSection) {
        this.inputSection = inputSection;
        if (inputSection == null) {
            System.out.println("InputSection object is null!");
        } else {
            System.out.println("InputSection object is not null.");
        }
        setLayout(new GridLayout(5, 5, 5, 5));


        String[] scientificButtonLabels = {
                "(", ")", "mc", "m+", "m-",
                "mr", "2nd", "x²", "x³", "X^y",
                "e^x", "10^x", "1/x", "√", "3√","x√y",
                "LN(x)", "log", "x!", "sin", "cos",
                "tan", "e", "EE", "Rad", "sinh",
                "cosh", "tanh", "π", "rand"};


        String[] renderNewButtons={
            "arcSin","arcCos","arcTan",
            "arcSiH","arcCoH","arcTaH"};

        for (String label : scientificButtonLabels) {
            JButton button = createStyledButton(label);
            button.addActionListener(e -> {
                String buttonText = button.getText();
                switch (buttonText) {
                    case "(":
                        inputSection.updateInputField(buttonText);
                        break;
                    case ")":
                        inputSection.updateInputField(buttonText);
                        break;
                    case "mc":
                        inputSection.resetMemory();
                        break;
                    case "m+":
                        applyUnaryFunction(FunctionType.M_PLUS);
                        break;
                    case "m-":
                        double numberToRemove=Double.parseDouble(inputSection.getInputFieldText());
                        inputSection.subtractFromMemory(numberToRemove);
                        break;
                    case "mr":
                       inputSection.getMemoryValue();
         
                        break;
                    case "x²":
                        applyUnaryFunction(FunctionType.SQUARE);
                        break;
                    case "x³":
                        applyUnaryFunction(FunctionType.CUBE);
                        break;
                    case "√":
                        applyUnaryFunction(FunctionType.SQROOT);
                        break;
                    case "2nd":
                        showInverseFunctions=!showInverseFunctions;
                        for(int i=0 ; i<renderNewButtons.length; i++){
                            JButton secondButton=(JButton) getComponent(scientificButtonLabels.length-1-i);
                            secondButton.setText(showInverseFunctions ? renderNewButtons[i] : scientificButtonLabels[scientificButtonLabels.length - 1 - i]);
                        }
                        break;
                    case "3√":
                        applyUnaryFunction(FunctionType.CUBEROOT);
                        break;
                    case "1/x":
                        applyUnaryFunction(FunctionType.FRACTION);
                        break;
                    case "e":
                        double result = 2.718281828459045;
                        inputSection.updateInputField(String.valueOf(result));
                        break;
                    case "LN(x)":
                        inputSection.updateInputField("ln");
                        break;
                    case "log":
                        inputSection.updateInputField(buttonText);
                        break;
                    case "x!":
                        applyUnaryFunction(FunctionType.FACTORIAL);
                        break;
                    case "sin":
                    case "cos":
                    case "tan":
                        inputSection.updateInputField(buttonText);
                        break;
                    case "tanh":
                        inputSection.updateInputField("tah");
                        break;
                    case "sinh":
                        inputSection.updateInputField("sih");
                        break;
                    case "cosh":
                        inputSection.updateInputField("coh");
                        break;
                    
                    case "arcSin":
                    case "arcCos":
                    case "arcTan":
                    case "arcSiH":
                    case "arcCoH":
                    case "arcTaH":
                        inputSection.updateInputField(buttonText);
                        break;
                    case "π":
                        double result2 = Math.PI;
                        inputSection.setInputField(String.valueOf(result2));
                        break;
                    case "x√y":
                        inputSection.updateInputField("√");
                        break;
                    case "X^y":
                        inputSection.updateInputField("^");
                        break;
                    // Add cases for other scientific functions here
                    case "10^x":
                        applyUnaryFunction(FunctionType.POWER_TEN);
                        break;
                    case "e^x":
                        applyUnaryFunction(FunctionType.EXPONENTIAL_POWER);
                        break;
                    case "EE":
                        inputSection.updateInputField("E");
                        break;
                    default:
                        break;
                }
            });
            add(button);
        }
    }

    private void applyUnaryFunction(FunctionType type) {
        String inputText = inputSection.getInputFieldText();
        if (inputText.isEmpty() || !inputText.matches("[-+]?\\d*(\\.\\d+)?")) {
            return;
        }
        double num = Double.parseDouble(inputSection.getInputFieldText());
        ScientificFunction scientificFunction = new ScientificFunction(new ScientificNumber(num, type.name()), inputSection);
        double result = scientificFunction.execute(type);
        inputSection.setInputField(String.valueOf(result));
    }

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        button.setFont(myFont);
        button.setBackground(Color.decode("#454442"));
        button.setForeground(Color.decode("#FFFFFF"));
        return button;
    }

}
