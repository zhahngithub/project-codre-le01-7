/*
 * Smell Code   : Large Class
 * Reason       : Mengimplementasi lebih dari 30 method, menunjukkan class terlalu gemuk.
 * Solution     : Ekstrak class menjadi beberapa turunan berdasarkan domain seperti PowerFunction, TrigonometryFunction, dsb.
 */

/*
 * Smell Code   : Duplicate Code ✅
 * Reason       : Konversi derajat ke radian diulang berkali-kali di sin(), cos(), tan(), dsb.
 * Solution     : Buat method utilitas internal atau gunakan `degreeToRadian(num)` secara konsisten.
 */

/*
 * Smell Code   : Dead Code ✅
 * Reason       : Method `doubleE()` dan `rand()` hanya return 0 tanpa makna fungsional.
 * Solution     : Hapus jika tidak diperlukan atau lempar `UnsupportedOperationException`.
 */

/*
 * Smell Code   : Primitive Obsession
 * Reason       : Masih memakai `double` dan `String` untuk data saintifik kompleks.
 * Solution     : Gunakan class khusus sebagai representasi nilai numerik.
 */

/*
 * Smell Code   : Long Method ✅
 * Reason       : Method `factorial()` memiliki logika dan validasi dalam satu tempat.
 * Solution     : Ekstrak validasi negatif ke method terpisah untuk readability.
 */

/*
 * Smell Code   : Data Clumps ✅ (done in solution package)
 * Reason       : Kombinasi `double base` dan `double exponent` sering digunakan bersama.
 * Solution     : Gunakan class `ExponentOperation` atau sejenisnya.
 */

public class ScientificFunction extends ScientificAbstract {
    InputSection inputSection;

    public ScientificFunction(ScientificNumber scientificNumber, InputSection inputSection) {
        super(scientificNumber);
        this.inputSection=inputSection;
    }

    public double execute(FunctionType type) {
    switch (type) {
        case M_PLUS: return mplus();
        case M_MINUS: return mminus();
        case SQUARE: return square();
        case CUBE: return cube();
        case SQROOT: return sqroot();
        case CUBEROOT: return cubeRoot();
        case FRACTION: return fraction();
        case FACTORIAL: return factorial();
        case PI: return Math.PI;
        case POWER_TEN: return tenthPower();
        case EXPONENTIAL_POWER: return exponentialPower();
        case M_CLEAR: return mc();
        case M_READ: return mread();
        default:
            throw new UnsupportedOperationException("Unknown function type: " + type);
    }
}

    // Method to calculate square of a number
    @Override
    double square() {
        return scientificNumber.getNum() * scientificNumber.getNum();
    }

    // Method to calculate cube of a number
    @Override
    double cube() {
        return scientificNumber.getNum() * scientificNumber.getNum() * scientificNumber.getNum();
    }

    // Method to calculate custom power of a number
    @Override
    double customPower(PowerComponents powerComponents) {
        return Math.pow(powerComponents.getBase(), powerComponents.getExponent());
    }

    // Method to calculate tenth power of a number
    @Override
    double tenthPower() {
        return Math.pow(10, scientificNumber.getNum());
    }

    // Method to calculate reciprocal of a number
    @Override
    double fraction() {
        return 1 / scientificNumber.getNum();
    }

    // Method to return Euler's number (e)
    @Override
    double exponential() {
        return 2.718281828459045;
    }

    // Method to calculate exponential power
    @Override
    double exponentialPower() {
        return Math.pow(2.718281828459045, scientificNumber.getNum());
    }

    // Method to calculate square root of a number
    @Override
    double sqroot() {
        return Math.sqrt(scientificNumber.getNum());
    }

    // Method to calculate cube root of a number
    @Override
    double cubeRoot() {
        return Math.cbrt(scientificNumber.getNum());
    }

    // Method to calculate custom root of a number
    @Override
    double customRoot(PowerComponents rootComponents) {
        return Math.pow(rootComponents.getBase(), 1.0 / rootComponents.getExponent());
    }

    // Method to calculate natural logarithm of a number
    @Override
    double ln() {
        return Math.log(scientificNumber.getNum());
    }

    // Method to calculate base-10 logarithm of a number
    @Override
    double log() {
        return Math.log10(scientificNumber.getNum());
    }

    // Method to calculate factorial of a number
    private void validateNonNegativeInteger(double number) {
        if (number < 0) {
            inputSection.setInputField("NaN");
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        if (number != Math.floor(number)) {
             if (inputSection != null) {
                inputSection.setInputField("NaN");
            }
            throw new IllegalArgumentException("Factorial is only defined for non-negative integers.");
        }
    }

    @Override
    double factorial() {
        validateNonNegativeInteger(scientificNumber.getNum());
        double result = 1;
        for (int i = 2; i <= scientificNumber.getNum(); i++) {
        result *= i;
    }
        return result;
    }

    // Method to calculate sine of an angle in degrees
    @Override
    double sin() {
        return Math.sin(degreeToRadian(scientificNumber.getNum()));
    }

    // Method to calculate cosine of an angle in degrees
    @Override
    double cos() {
        return Math.cos(degreeToRadian(scientificNumber.getNum()));
    }

    // Method to calculate tangent of an angle in degrees
    @Override
    double tan() {
        if(scientificNumber.getNum()==90){
            return Double.NaN;
        }
        return Math.tan(degreeToRadian(scientificNumber.getNum()));
    }

    @Override
    double arcSin(){
        return Math.asin(degreeToRadian(scientificNumber.getNum()));
    }
    @Override
    double arcCos() {
        return Math.acos(degreeToRadian(scientificNumber.getNum()));
    }
    @Override
    double arcTan() {
        return Math.atan(degreeToRadian(scientificNumber.getNum()));
    }



    // Method to calculate hyperbolic sine of an angle in degrees
    @Override
    double sinH() {
        double resultInDegrees = Math.toDegrees(Math.sinh(scientificNumber.getNum()));
        return resultInDegrees;
    }

    // Method to calculate hyperbolic cosine of an angle in degrees
    @Override
    double cosH() {
        double resultInDegrees = Math.toDegrees(Math.cosh(scientificNumber.getNum()));
        return resultInDegrees;
    }

    // Method to calculate hyperbolic tangent of an angle in degrees
    @Override
    double tanH() {
        double resultInDegrees = Math.toDegrees(Math.tanh(scientificNumber.getNum()));
        return resultInDegrees;
    }

    @Override
    double arcTanH() {
        double resultInRadians=degreeToRadian(scientificNumber.getNum());
        return inverse(resultInRadians, "atanh");
    }

    @Override
    double arcSinH() {
        double resultInRadians=degreeToRadian(scientificNumber.getNum());
        return inverse(resultInRadians, "asinh");
    }
    @Override
    double arcCosH() {
        double resultInRadians=degreeToRadian(scientificNumber.getNum());
        return inverse(resultInRadians, "acosh");
    }

    // Method to return Euler's number (e)
    @Override
    double singleE() {
        return 2.718281828459045;
    }

    // Method to convert degrees to radians
    @Override
    double rad() {
        return Math.toRadians(scientificNumber.getNum());
    }

    @Override
    double mc(){
        return inputSection.resetMemory();
    }

    @Override
    double mread(){
        return inputSection.getMemoryValue();
    }

    @Override
    double mplus(){
        return inputSection.setMemoryValue(scientificNumber.getNum());
    }

    @Override
    double mminus(){ 
        return inputSection.subtractFromMemory(scientificNumber.getNum());
    }

    // Method to convert degrees to radians
    public double degreeToRadian(double num) {
        double result = (num * Math.PI) / 180;
        return result;
    }

    public double inverse(double num, String type) {
        double result = 0;
        if (type.equals("asinh")) {
            result = Math.log(num + Math.sqrt((num * num) + 1));
        } else if (type.equals("acosh")) {
            result = Math.log(num + Math.sqrt((num * num) - 1));
        } else if (type.equals("atanh")) {
            result = 0.5 * Math.log((1 + num) / (1 - num));
        }
        return result;
    }
      
}
