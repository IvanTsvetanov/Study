package  Question3;

public class TestClass {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components


    public TestClass(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }


    public int count() {
        return count;
    }


    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public static void main(String[] args) {
        int n = 256;
        TestClass graphA = new TestClass(n);
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