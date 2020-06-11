package Q1;

public class Question1 {

    public int[] proc1(int[] A, int n) {
        if(n > 0) {
            proc1(A, n-1);
            int x = A[n];
            int i = n - 1;
            while(i >= 0 && A[i] > x) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i+1] = x;
        }
        return A;
    }

    public static void main(String[] args) {
        int[] array =  {
                5, 4, 3, 2, 1
        };
        int n = array.length - 1;

        Question1 q1 = new Question1();

        array = q1.proc1(array, n);

        for(int i : array) {
            System.out.println(i);
        }
    }
}
