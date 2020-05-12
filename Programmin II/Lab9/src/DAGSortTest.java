import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DAGSortTest {

    //Creates a simple DAG and checks if it is sorted properly.
    @Test
    public void simpleSortTest() throws Exception {
        //Create the DAG
        //The DAG has 4 rows (nodes)
        //DAG[0] = {1, 2}
        //DAG[1] = {3}
        //DAG[2] = {3}
        //DAG[3] = {}
        int[][] DAG = {
                {1, 2},
                {3},
                {3},
                {}
        };

        //Holds the inteded output
        int[] intededOrder = new int[] {0, 2, 1, 3};

        //Holds the topologically ordered DAG
        int[] topologicalOrder = new int[DAG.length];

        //Takes a directed acyclic graph as input and returns a topological sort
        try {
            topologicalOrder = DAGSort.sortDAG(DAG);
        }
        catch (CycleDetectedException e) {
            System.out.println("A cycle has been detected. Please input a valid DAG!");
            e.printStackTrace();
            throw new CycleDetectedException();
        }
        catch (InvalidNodeException e) {
            System.out.println("An invalid node has been detected. Check the input DAG!");
            e.printStackTrace();
            throw new InvalidNodeException();
        }
        catch (Exception e) {
            System.out.println("An exception has occurred!");
            e.printStackTrace();
            throw new Exception();
        }

        //Check if the topological ordering is correct
        if(!Arrays.equals(intededOrder, topologicalOrder)) throw new Exception("The topological ordering is not correct!");
    }


    //Creates a directed graph with a cycle
    //Expected to throw exception
    @Test
    public void testExpectedCycleDetectedException() {
        assertThrows(CycleDetectedException.class, () -> {
            //Create a directed graph that loops
            int[][] flawedDAG = {
                    {0, 1},
                    {}
            };
            DAGSort.sortDAG(flawedDAG);
        });
    }

    //Creates a directed graph in which the nodes exceed the
    //maximum size (length-1)
    @Test
    public void testExpectedInvalidNodeExceptionMax() {
        assertThrows(InvalidNodeException.class, () -> {
            int[][] flawedDAG = {
                    {0, 1, 2},
                    {3, 4, 5}
            };
            DAGSort.sortDAG(flawedDAG);
        });
    }

    //Creates a directed graph in which the nodes are lower than the
    //minimum size (0)
    @Test
    public void testExpectedInvalidNodeExceptionMin() {
        assertThrows(InvalidNodeException.class, () -> {
            int[][] flawedDAG = {
                    {1, 2},
                    {3},
                    {-1},
                    {}
            };
            DAGSort.sortDAG(flawedDAG);
        });
    }

    //Checks the output when the input is a graph with just nodes
    //and no direction.
    @Test
    public void nodesWithNoDirection() throws Exception {
        //Create the DAG
        //The DAG has 4 rows (nodes)
        //DAG[0] = {}
        //DAG[1] = {}
        //DAG[2] = {}
        //DAG[3] = {}
        int[][] DAG = {
                {},
                {},
                {},
                {}
        };

        //Holds the inteded output
        int[] intededOrder = new int[] {3, 2, 1, 0};

        //Holds the topologically ordered DAG
        int[] topologicalOrder = new int[DAG.length];

        //Takes a directed acyclic graph as input and returns a topological sort
        try {
            topologicalOrder = DAGSort.sortDAG(DAG);
        }
        catch (CycleDetectedException e) {
            System.out.println("A cycle has been detected. Please input a valid DAG!");
            e.printStackTrace();
            throw new CycleDetectedException();
        }
        catch (InvalidNodeException e) {
            System.out.println("An invalid node has been detected. Check the input DAG!");
            e.printStackTrace();
            throw new InvalidNodeException();
        }
        catch (Exception e) {
            System.out.println("An exception has occurred!");
            e.printStackTrace();
            throw new Exception();
        }

        //Check if the topological ordering is correct
        if(!Arrays.equals(intededOrder, topologicalOrder)) throw new Exception("The topological ordering is not correct!");
    }

    //Passes a null array
    @Test
    public void testExpectedNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            DAGSort.sortDAG(null);
        });
    }

    //Checks the output when the input is a graph with 1 node
    @Test
    public void graphWithOneNode() throws Exception {
        //Create the DAG
        //The DAG has 4 rows (nodes)
        //DAG[0] = {}
        //DAG[1] = {}
        //DAG[2] = {}
        //DAG[3] = {}
        int[][] DAG = {
        };

        //Holds the inteded output
        int[] intededOrder = new int[] {};

        //Holds the topologically ordered DAG
        int[] topologicalOrder = new int[DAG.length];

        //Takes a directed acyclic graph as input and returns a topological sort
        try {
            topologicalOrder = DAGSort.sortDAG(DAG);
        }
        catch (CycleDetectedException e) {
            System.out.println("A cycle has been detected. Please input a valid DAG!");
            e.printStackTrace();
            throw new CycleDetectedException();
        }
        catch (InvalidNodeException e) {
            System.out.println("An invalid node has been detected. Check the input DAG!");
            e.printStackTrace();
            throw new InvalidNodeException();
        }
        catch (Exception e) {
            System.out.println("An exception has occurred!");
            e.printStackTrace();
            throw new Exception();
        }

        //Check if the topological ordering is correct
        if(!Arrays.equals(intededOrder, topologicalOrder)) throw new Exception("The topological ordering is not correct!");
    }
}