import java.util.ArrayList;

// JANGAN DIEDIT

/* Smell Code   : The Bloater - Long Method (di performOperation()) ✅
 * Reason       : Methodnya melakukan terlalu banyak hal, switch statementnya bisa diextract ke method terpisah
 * Solution     : Extract Method ini menjadi bagian-bagian kecil, seperti buildOperationString(), calculateResult(), dan formatResult(). 
 *  
 * Smell Code   : The Bloater - Large Class ✅
 * Reason       : Tanggung jawabnya terlalu banyak (nyimpen data, operasi hitungan, error handling, display result)
 * Solution     : Extract Class, misalnya CalculatorEngine untuk perhitungan, dan biarkan ArithmeticFunction hanya mengoordinasikan operasi. Ini mengurangi beban pada kelas.
 *  
 */

public class ArithmeticFunction{
    private final ArrayList<Double> operands;
    private final ArrayList<String> operations;
    private final InputSection inputSection;

    public ArithmeticFunction(ArrayList<Double> operands, ArrayList<String> operations, InputSection inputSection) {
        this.operands = operands;
        this.operations = operations;
        this.inputSection = inputSection;
    }

    private double calculateResult() {
        double result = operands.get(0);
        for (int i = 0; i < operations.size(); i++) {
            String operation = operations.get(i);
            double operand = operands.get(i + 1);
            result = applyOperation(result, operand, operation);
        }
        return result;
    }

    private double applyOperation(double a, double b, String op) {
        switch (op) {
            case "/":
                return CalculatorEngine.divide(a, b, inputSection); 
            case "*":
                return CalculatorEngine.multiply(a, b);
            case "+":
                return CalculatorEngine.add(a, b);
            case "-":
                return CalculatorEngine.subtract(a, b);
            default:
                throw new IllegalArgumentException("Invalid operation sign: " + op);
        }
    }


    private String buildOperationString() {
        StringBuilder sb = new StringBuilder("[").append(operands.get(0)).append("]");
        for (int i = 0; i < operations.size(); i++) {
            sb.append(" ").append(operations.get(i)).append(" [").append(operands.get(i + 1)).append("]");
        }
        return sb.toString();
    }

    private String formatResult(double result) {
        if (result % 1 == 0) {
            return String.valueOf((int) result);
        } else {
            return String.valueOf(result);
        }
    }

    public String performOperation() {
        double result = calculateResult();
        String operationString = buildOperationString();

        System.out.println("Performing operation: " + operationString + " = " + result);
        return formatResult(result);
    }


}
