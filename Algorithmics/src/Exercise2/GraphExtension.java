package Exercise2;

public class GraphExtension {

    public static void estimateTime() {

        long time_prev = System.nanoTime();
        double time;

        for (int i = 12; i < 18; i++) {
            Graph graph = new Graph(i, 0.5);
            Colouring colouring = graph.bestColouring(3);
            graph.show(colouring);
            time = (System.nanoTime() - time_prev) / 1000000000.0;
            System.out.println(i + " points time = " + time);
            time_prev = System.nanoTime();
        }

    }
}
