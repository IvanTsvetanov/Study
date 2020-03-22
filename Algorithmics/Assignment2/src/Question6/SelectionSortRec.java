package Question6;

public class SelectionSortRec {
    //Points from which index the input array will be sorted.
    private static int index = 0;

    //Helper method.
    //Used to return the minimum element of the input array.
    static int minIndex(int a[], int i, int j)
    {
        //Check if there is only one element left.
        //If so - return it.
        //(This is the base case)
        if (i == j) return i;

        else {
            //Find the smallest element of the remaining ones.
            int k = minIndex(a, i + 1, j);

            //Checks which element is smaller - the current one or the remaining
            //and then returns the smaller of the two.
            return (a[i] < a[k]) ? i : k;
        }
    }

    //Recursive selection sort.
    static void recursiveSelectionSort(int a[])
    {
        //Get the length of the input array.
        int n = a.length;

        //If the array is of size 1 -> return.
        //(This is the base case)
        if (index == n) return;

        else {
            //Getting the minimum index of the array via our recursive helper function.
            int k = minIndex(a, index, n - 1);

            //Swapping when index and minimum index are not same.
            if (k != index) {
                int temp = a[k];
                a[k] = a[index];
                a[index] = temp;
            }
            //Move the index up, as the array is being sorted.
            index++;
            //Recursively calling selection sort function.
            recursiveSelectionSort(a);
        }
    }

    public static void main(String args[])
    {
        //Create the array.
        int arr[] = {4, 1, 2, 2, 12, 0};

        //Call the funcion.
        recursiveSelectionSort(arr);

        //Print out the sorted array.
        for (int i = 0; i< arr.length; i++)
            System.out.print(arr[i] + " ");
    }
}
