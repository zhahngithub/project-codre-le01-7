import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.regex.*;

/*
 * Smell Code   : The Bloater - Long Method
 * Reason       : Logika di dalam actionPerformed (bagian button.addActionListener) terlalu panjang dan mencampur banyak hal sekaligus: mulai dari kontrol UI, parsing ekspresi, hingga evaluasi ilmiah dan aritmatika.
 * Solution     : Extract method seperti: handleClear(), handleDelete(), handleEquals(), handlePercentage(), dan handleSignToggle()
 * 
 * Smell Code   : The Bloater - Large Class
 * Reason       : Kelas DigitsSection bertanggung jawab atas banyak hal: membuat tombol, mengatur tata letak UI, mengelola input, dan bahkan melakukan perhitungan.
 * Solution     : Pisahkan logika perhitungan ke kelas terpisah, seperti CalculatorLogic, sehingga DigitsSection hanya fokus pada UI dan interaksi pengguna.
 * 
 * Smell Code   : Object Oriented Abuser - Switch Statements
 * Reason       : Dalam method seperti applyScientificFunctions, terdapat rantai if-else panjang untuk memeriksa input (misalnya "sin", "cos", "tan"). Ini mirip dengan switch statement dan sulit dipelihara jika fungsi baru ditambahkan.
 * Solution     : Definisikan interface ScientificOperation dengan method apply(), lalu buat kelas terpisah untuk setiap operasi (contoh: SinOperation, CosOperation).
 * 
 * Smell Code   : The Dispensable - Duplicate Code ✅
 * Reason       : Logika untuk mengurai input muncul berulang kali di applyScientificFunctions.
 * Solution     : Extract Method, sehingga bisa di reuse di berbagai tempat
 * 
 * Smell Code   : The Dispensable - Dead Code ✅
 * Reason       : Inisialisasi arraylist operands dan operations tidak pernah digunakan
 * Solution     : Remove Code, hapus bagian inisialisasi kedua arraylist
 */


class DigitsSection extends JPanel {
    Font myFont = new Font("Arial", Font.PLAIN, 30);
    ArithmeticFunction arithmeticFunction;
    ScientificFunction scientificFunction;
    InputSection inputSection;

    public DigitsSection(InputSection inputSection) {
        this.inputSection = inputSection;
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
                if (buttonText.equals("C")) {
                    inputSection.deleteInputField();
                } else if (buttonText.equals("del")) {
                    inputSection.removeCurrentText();
                } else if (buttonText.equals("=")) {
                    String input = inputSection.getInputFieldText();
                    char sign = extractSign(input);
                    if (sign == ' ') {
                        applyScientificFunctions();
                    } else {
                        String[] operands = input.split(Pattern.quote(String.valueOf(sign)));
                        if (sign == '√') {
                            double num1 = Double.parseDouble(operands[0]);
                            double num2 = Double.parseDouble(operands[1]);
                            ScientificFunction scientificFunction=new ScientificFunction(num1, "√",inputSection);
                            double result = scientificFunction.customRoot(new PowerComponents(num2, num1));
                            inputSection.setInputField(String.valueOf(result));
                        } else if (sign == '^') { 
                            double num1 = Double.parseDouble(operands[0]);
                            double num2 = Double.parseDouble(operands[1]);
                            ScientificFunction scientificFunction=new ScientificFunction(num1, "^",inputSection);
                            double result=scientificFunction.customPower(new PowerComponents(num1, num2));
                            inputSection.setInputField(String.valueOf(result));
                        } else if (sign == 'E') {
                            double num1 = Double.parseDouble(operands[0]);
                            double num2 = Double.parseDouble(operands[1]);
                            double result = num1 * (Math.pow(10, num2));
                            inputSection.setInputField(String.valueOf(result));
                        } else {
                            String[] split = input.split("(?=[-+*/()])|(?<=[-+*/()])");
                            ArrayList<Double> operandsList = new ArrayList<>();
                            ArrayList<String> operationsList = new ArrayList<>();

                            for (String token : split) {                 
                                try {
                                    if (token.matches("sin\\d*|cos\\d*|tan\\d*|log\\d*|ln\\d*")) {
                                    String functionName=token.substring(0,3).trim();
                                    String numericPart = token.substring(3).trim(); 
                                    double numericValue =Double.parseDouble(numericPart);
                                    double number=performScientificAction(functionName,numericValue,inputSection);
                                    operandsList.add(number);
                                }
                            
                                double number = Double.parseDouble(token);
                                operandsList.add(number);
                                    
                                } catch (NumberFormatException error) {
                                    if (token.equals("+")) {
                                        operationsList.add(token);
                                    }
                                    else if (token.equals("-")) {
                                        operationsList.add(token);
                                    }
                                    else if (token.equals("/")) {
                                        operationsList.add(token);
                                    }
                                    else if (token.equals("*")) {
                                        operationsList.add(token);
                                    }
                                 
                                    
                                }
                            }
                            ArithmeticFunction arithmeticFunction = new ArithmeticFunction(operandsList, operationsList, inputSection);
                            String result = arithmeticFunction.performOperation();
                            inputSection.setInputField(result);
                            
                        }
                    }
                } else if (buttonText.equals("%")) {
                    String input = inputSection.getInputFieldText();
                    char sign = extractSign(input);
                    String[] operands = input.split("[" + sign + "]");
                    double num = Double.parseDouble(operands[0]);
                    double result = num / 100.0;
                    inputSection.setInputField(String.valueOf(result));
                } else if (buttonText.equals("+/-")) {
                    String input = inputSection.getInputFieldText();
                    double number = Double.parseDouble(input);
                    double result = number * -1;
                    inputSection.setInputField(String.valueOf(result));
                } else {
                    inputSection.updateInputField(buttonText);
                }
            });
            add(button);
        }
    }

    private char extractSign(String input) {
        String newInput = removeBrackets(input);
        System.out.println(newInput);
        char sign = ' ';
        for (char c : newInput.toCharArray()) {
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '√' || c == '^' || c == 'E') {
                sign = c;
                break;
            }
        }
        return sign;
    }

    public String removeBrackets(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || c == '.' || c == '-' || c == '+' || c == '*' || c == '/' || c == '%' || c == '√' || c == '^' || c == 'E') {
                result.append(c);
            }
        }
        return result.toString();
    }

    private void applyScientificFunctions() {
        String inputText = inputSection.getInputFieldText();
        double result = 0;

        if (inputText.startsWith("sin")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "sin");
            result = scientificFunction.sin();
        }else if(inputText.startsWith("arcSin")){
            scientificFunction = getScientificFunctionFromInput(inputText, "arcSin");
            result = scientificFunction.arcSin();
        }   else if (inputText.startsWith("cos")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "cos");
            result = scientificFunction.cos();
        } else if(inputText.startsWith("arcCos")){
            scientificFunction = getScientificFunctionFromInput(inputText, "arcCos");
            result = scientificFunction.arcCos();
        } else if (inputText.startsWith("tan")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "tan");
            result = scientificFunction.tan();
        } else if(inputText.startsWith("arcTan")){
            scientificFunction = getScientificFunctionFromInput(inputText, "arcTan");
            result = scientificFunction.arcTan();
        } else if (inputText.startsWith("log")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "log");
            result = scientificFunction.log();
        } else if (inputText.startsWith("ln")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "ln");
            result = scientificFunction.ln();
        } else if (inputText.startsWith("sih")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "sinh");
            result = scientificFunction.sinH();
        } else if (inputText.startsWith("arcSiH")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "arcSinh");
            result = scientificFunction.arcSinH();
        }  else if (inputText.startsWith("coh")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "cosh");
            result = scientificFunction.cosH();
        } else if (inputText.startsWith("arcCoH")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "arcCosh");
            result = scientificFunction.arcCosH();
        } 
           else if (inputText.startsWith("tah")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "tanh");
            result = scientificFunction.tanH();
        } else if (inputText.startsWith("arcTaH")) {
            scientificFunction = getScientificFunctionFromInput(inputText, "arcTanH");
            result = scientificFunction.arcTanH();
        } 
         else if (inputText.startsWith("√")) {
            String numericPart = inputText.substring(1); 
            double num = Double.parseDouble(numericPart);
            result = Math.sqrt(num);
        }

        inputSection.setInputField(String.valueOf(result));
    }
    private static double performScientificAction(String functionName, double numericValue, InputSection inputSection) {
        double result = 0;
        switch (functionName) {
            case "sin":
                ScientificFunction sinFunction = new ScientificFunction(numericValue, "sin", inputSection);
                result = sinFunction.sin();
                break;
            case "cos":
                ScientificFunction cosFunction = new ScientificFunction(numericValue, "cos", inputSection);
                result = cosFunction.cos();
                break;
            case "tan":
                ScientificFunction tanFunction = new ScientificFunction(numericValue, "tan", inputSection);
                result = tanFunction.tan();
                break;
            case "log":
                ScientificFunction logFunction = new ScientificFunction(numericValue, "log", inputSection);
                result = logFunction.log();
                break;
            case "ln":
                ScientificFunction lnFunction = new ScientificFunction(numericValue, "ln", inputSection);
                result = lnFunction.ln();
                break;
            default:
                throw new IllegalArgumentException("Unsupported scientific function: " + functionName);
        }
        return result;
    }
    
    private ScientificFunction getScientificFunctionFromInput(String inputText, String functionName){
        Integer strLen = functionName.length();
        String numericPart = inputText.substring(strLen);
        double num = Double.parseDouble(numericPart);
        return new ScientificFunction(num, functionName, inputSection);
    }
}