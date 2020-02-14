import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        double a = 0.0421818;
        double b = 0.081605999;

        if(a > b) System.out.println("a > b");
        else System.out.println("a < b");


        System.out.println( BigDecimal.valueOf(3.951E-4).toPlainString());
        /*System.out.println( BigDecimal.valueOf(7.705E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(5.82399E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(9.94299E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(8.007E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(7.759E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(7.545E-4).toPlainString());
        System.out.println( BigDecimal.valueOf(5.541E-4).toPlainString());*/

    }
}
