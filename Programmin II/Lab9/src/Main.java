public class Main {
    public static void main(String[] args) {
        int[][] a = {
                {1, 2},
                {3},
                {3},
                {}
        };

        int[] b = new int[a.length];

        try {
            b = DAGSort.sortDAG(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i : b) {
            System.out.println(i);
        }
    }
}
