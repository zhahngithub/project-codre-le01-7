/*
 * Smell Code   : Large Class
 * Reason       : Memiliki terlalu banyak method abstrak (33 total), mencerminkan terlalu banyak tanggung jawab.
 * Solution     : Bagi menjadi beberapa interface/abstract class sesuai domain (contoh: TrigonometricOps, LogarithmicOps, MemoryOps, dll).
 */

/*
 * Smell Code   : Primitive Obsession ✅ (done in solution package)
 * Reason       : Menggunakan `double` dan `String` untuk representasi scientific number tanpa class khusus.
 * Solution     : Buat class `ScientificNumber` untuk membungkus nilai dan tanda, sehingga lebih fleksibel dan mudah diperluas.
 */

/*
 * Smell Code   : Long Parameter List
 * Reason       : Konstruktor menggunakan dua parameter primitif berpasangan yang bisa dikelompokkan.
 * Solution     : Gunakan object wrapper seperti `ScientificValue` untuk merapikan parameter.
 */

/*
 * Smell Code   : Data Clump
 * Reason       : Kombinasi `double num` dan `String sign` selalu digunakan bersama.
 * Solution     : Bungkus keduanya dalam satu class seperti `ScientificValue`.
 */

/*
 * Smell Code   : Long Parameter List (in constructor)
 * Reason       : Konstruktor menerima lebih dari satu parameter primitif yang berhubungan.
 * Solution     : Gunakan object wrapper seperti `ScientificValue` untuk menyederhanakan konstruktor.
 */

public abstract class ScientificAbstract{
   protected ScientificNumber scientificNumber;
   
   public ScientificAbstract(ScientificNumber scientificNumber){
    this.scientificNumber = scientificNumber;
   }
   
    abstract double square();
    abstract double cube();
    abstract double customPower(PowerComponents powerComponents);
    abstract double tenthPower();
    abstract double fraction();
    abstract double exponential();
    abstract double exponentialPower();
    abstract double sqroot();
    abstract double cubeRoot();
    abstract double customRoot(PowerComponents rootComponents);
    abstract double ln();
    abstract double log();
    abstract double factorial();
    abstract double sin();
    abstract double cos();
    abstract double tan();
    abstract double arcSin();
    abstract double arcCos();
    abstract double arcTan();
    abstract double singleE();
    abstract double rad();
    abstract double sinH();
    abstract double cosH();
    abstract double tanH();
    abstract double arcSinH();
    abstract double arcCosH();
    abstract double arcTanH();
    abstract double mc();
    abstract double mplus();
    abstract double mminus();
    abstract double mread();


}