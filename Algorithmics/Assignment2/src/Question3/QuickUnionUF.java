package Question3;

public class QuickUnionUF {
    private int[] id;
    //Number of elements in subtree.
    private int[] size;

    public QuickUnionUF(int N) {
        id = new int[N];
        size = new int[N];
        //Populate the graph.
        for (int i = 0; i < N; i++) {
            id[i] = i;
            //At the start, none of the elements have a subtree.
            size[i] = 1;
        }
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

    //We modify the union to:
    //merge the smaller tree into the larger tree, and
    //keep the size[] array updated.
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        if (size[i] < size[j]) {
            id[i] = j;
            size[j] += size[i];
        } else {
            id[j] = i;
            size[i] += size[j];
        }
    }

    public static void main(String[] args) {
        //Create graph A with 256 vertices numbered from 0 to 255.
        QuickUnionUF graphA = new QuickUnionUF(256);

        int firstElement = 0;
        int secondElement = 0;

        //Connect vertices A[i] with A[i+1].
        for (int i = 0; i <= 254; i += 2) {
            firstElement = graphA.find(i);
            secondElement = graphA.find(i + 1);
            graphA.union(firstElement, secondElement);
        }

        //Connect vertices A[j] with A[j+3].
        for (int j = 0; j <= 200; j += 3) {
            firstElement = graphA.find(j);
            secondElement = graphA.find(j + 3);
            graphA.union(firstElement, secondElement);
        }

        System.out.println(graphA.find(19));
        System.out.println(graphA.find(112));
    }
}