public class Main {

    public static void main(String[] args) {

        double[] array = new double[100];

        for(int i = 0; i < 10; i++){
            array[i] = Math.random();
        }

        double[] array1 = (double[])array.clone();
        for(double a : array1) {
            System.out.println(a);
        }

    }
}
