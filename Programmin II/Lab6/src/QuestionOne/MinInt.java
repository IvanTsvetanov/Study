package QuestionOne;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.recursion.MinimumInArray;

import java.util.Arrays;

/*
Finds the smallest number in an array of integers (recursively).
 */

public class MinInt implements MinimumInArray {

    public static void main(String[] numbers){
        //Creates the array and displays the minimum element in it.
        int[] arr = {24,52,74,9,34,23,64,34};
        MinInt minInt = new MinInt();
        System.out.println("Minimum is: " + minInt.findMin(arr));
        System.out.println("Minimum is: " + minInt.findMinAux(5, arr));
    }

    //Returns the minimum element in the array.
    public int findMin(int[] array){
        //If there is one element in the array - return it.
        if(array.length == 1)
            return array[0];

        //Recursive case
        else {
            //Recreate the new array at every call, in order to go through all the elements.
            //Then return the minimum value.
            int[] newArray = Arrays.copyOf(array, array.length-1);
            return Math.min(array[array.length-1], findMin(newArray));
        }
    }

    //Returns the minimum element from the index parameter onwards.
    public int findMinAux(int index, int[] array) {
        //If there is one element, after the index, in the array - return it.
        if (index == array.length - 1) {
            return array[index];
        }

        //Recursively check the values in the array (after the index),
        //and then return the smallest one.
        int minimumValue = findMinAux( index + 1, array);

        if (array[index] < minimumValue)
            return array[index];
        else
            return minimumValue;
    }
}
