import uk.ac.soton.ecs.comp1206.labtestlibrary.datastructure.Tree;
import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.recursion.MinimumInTree;

//Find the minimum integer value in a tree (recursively).
public class MinTree implements MinimumInTree{

    public static void main(String[] args){
        //Creates a tree and displays the minimum value in it.
        Tree tree = new Tree( 24,
                new Tree( 45,
                        null ,
                        new Tree(8, null , null) ) ,
                new Tree ( 17,
                        new Tree (74 , null , null ) ,
                        null ) );
        MinTree minTree = new MinTree();
        System.out.println("Minimum is: " + minTree.findMin(tree));
    }

    //Returns the minimum value in a tree.
    public int findMin(Tree tree){
        //Ignore the null values
        if (tree == null)
            return Integer.MAX_VALUE;

        //For each of the values of the parent and its children nodes, get the minimum value.
        int minValue = tree.getVal();
        int leftMinValue = findMin(tree.left());
        int rightMinValue = findMin(tree.right());

        if (leftMinValue < minValue)
            minValue = leftMinValue;
        if (rightMinValue < minValue)
            minValue = rightMinValue;
        return minValue;
    }

}
