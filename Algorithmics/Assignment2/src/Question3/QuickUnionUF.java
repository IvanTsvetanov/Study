package Question3;

public class QuickUnionUF {
    private int[] id;

    public QuickUnionUF(int N) {
        id = new int[N];
        //Populate the graph.
        for (int i = 0; i < N; i++) id[i] = i;
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    public int find(int p) {
        return root(p);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }

    public static void main(String[] args) {
        //Create graph A with 256 vertices numbered from 0 to 255.
        QuickUnionUF graphA = new QuickUnionUF(256);

        int firstRoot = 0;
        int secondRoot = 0;
        int unionCounter = 0;

        //Connect vertices A[i] with A[i+1].
        for (int i = 0; i <= 254; i += 2) {
            firstRoot = graphA.find(i);
            secondRoot = graphA.find(i + 1);
            graphA.union(firstRoot, secondRoot);
            unionCounter++;
        }

        //Connect vertices A[j] with A[j+3].
        for (int j = 0; j <= 200; j += 3) {
            firstRoot = graphA.find(j);
            secondRoot = graphA.find(j + 3);
            graphA.union(firstRoot, secondRoot);
            unionCounter++;
        }

        //Check how many components are there is the resulting graph.
        //The rule is that each union command reduces by 1 the number of components.
        //So to check how many components we have, we need to subtract the number of unions we have made
        //from the total number of starting components.
        System.out.println(unionCounter);
        System.out.println(256 - unionCounter);

        System.out.println(graphA.find(19));
        System.out.println(graphA.find(112));
    }
}