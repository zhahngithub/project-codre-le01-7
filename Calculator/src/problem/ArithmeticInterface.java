/*
 * Smell Code   : Abstraction Smell - Unnecessary Abstraction ✅ (done deleted in solution package)
 * Reason       : Interface ini mendefinisikan operasi aritmatika dasar (add, subtract, multiply, divide), tetapi dalam konteks kalkulator, operasi ini sudah jelas dan tidak memerlukan abstraksi.
 * Solution     : Hapus interface ini dan langsung implementasikan method-nya di kelas
*/

public interface ArithmeticInterface {

    //This is the default implementation of Arithmetic Operations
    double add(double a, double b);
    double subtract(double a, double b);
    double multiply(double a, double b);
    double divide(double a, double b);
    

}
