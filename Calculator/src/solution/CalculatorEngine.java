// JANGAN DIEDIT

public class CalculatorEngine {
    public static double add(double num1, double num2) {
        return num1 + num2;
    }

    public static double subtract(double num1, double num2) {
        return num1 - num2;
    }

    public static double multiply(double num1, double num2) {
        return num1 * num2;
    }

    public static double divide(double num1, double num2, InputSection inputSection) {
        if (num2 == 0) {
            inputSection.setInputField("NaN");
            throw new ArithmeticException("Cannot divide by zero");
        }
        return num1 / num2;
    }

}
