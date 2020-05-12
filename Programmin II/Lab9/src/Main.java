public class Main {
    public static void main(String[] args) {
        int[][] a = new int[0][];



        int[] b = new int[a.length];

        try {
            b = DAGSort.sortDAG(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b.length);
        for(int i : b) {
            System.out.println(i);
        }
    }
}
