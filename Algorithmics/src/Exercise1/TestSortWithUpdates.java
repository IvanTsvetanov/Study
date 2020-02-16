package Exercise1;

import java.util.Arrays;

public class TestSortWithUpdates {
    public static void main(String[] args) {
        int N = 1000;
        //CREATE MORE VARIABLES TO STORE THE LENGTHS OF THE ARRAYS
        int smallerN = 5000;
        int evenSmallerN = 10000;
        int biggerN = 50000;

        double[] data = new double[N];
        //CREATE MORE ARRAYS WITH THE MODIFIED SIZES
        double[] smallerData = new double[smallerN];
        double[] evenSmallerData = new double[evenSmallerN];
        double[] biggerData = new double[biggerN];

        for (int i = 0; i < N; i++) {
            // Math.random returns a double type number greater than or equal to 0.0 and less than 1.0
            data[i] = Math.random();
        }
        //POPULATE THE NEWLY CREATED ARRAYS
        for(int i = 0; i < smallerN; i++) {
            smallerData[i] = Math.random();
        }

        for(int i = 0; i < evenSmallerN; i++) {
            evenSmallerData[i] = Math.random();
        }

        for(int i = 0; i < biggerN; i++) {
            biggerData[i] = Math.random();
        }

        //Put all the data from the original array in these ones
        double[] data1 = (double[])data.clone();
        double[] data2 = (double[])data.clone();
        double[] data3 = (double[])data.clone();

        //CREATE 3 DATA CONTAINERS FOR EACH OF THE NEWLY CREATED ARRAYS
        double[] smallerData1 = (double[])smallerData.clone();
        double[] smallerData2 = (double[])smallerData.clone();
        double[] smallerData3 = (double[])smallerData.clone();

        double[] evenSmallerData1 = (double[])evenSmallerData.clone();
        double[] evenSmallerData2 = (double[])evenSmallerData.clone();
        double[] evenSmallerData3 = (double[])evenSmallerData.clone();

        double[] biggerData1 = (double[])biggerData.clone();
        double[] biggerData2 = (double[])biggerData.clone();
        double[] biggerData3 = (double[])biggerData.clone();

        double time;

        //region evenSmallerData
        //CALLS THE THREE METHODS FOR EVEN-SMALLER-DATA[]
        long time_prev_evensmaller = System.nanoTime();

        //Calls the first method
        InsertionSort(evenSmallerData1);
        time = (System.nanoTime()-time_prev_evensmaller)/1000000000.0;
        System.out.println("Insertion Sort\nTime= " + time);
        time_prev_evensmaller = System.nanoTime();

        //Calls the second method
        ShellSort(evenSmallerData2);
        time = (System.nanoTime()-time_prev_evensmaller)/1000000000.0;
        System.out.println("Shell Sort\nTime= " + time);
        time_prev_evensmaller = System.nanoTime();

        //Calls the third method
        Arrays.sort(evenSmallerData3);
        time = (System.nanoTime()-time_prev_evensmaller)/1000000000.0;
        System.out.println("Quick Sort\nTime= " + time);

        //Display all the info
        System.out.println("EVEN-SMALLER-DATA");
        System.out.println("\tPresorted\tInsertion\t\t Shell\t\t Quick");
        for (int i=0; i<evenSmallerData.length; i++)
            System.out.println(evenSmallerData[i] + " " + evenSmallerData1[i] + " " + evenSmallerData2[i] + " " + evenSmallerData3[i]);
        //endregion

        //region smallerData

        //CALLS THE THREE METHODS FOR SMALLER-DATA[]
        long time_prev_smaller = System.nanoTime();

        //Calls the first method
        InsertionSort(smallerData1);
        time = (System.nanoTime()-time_prev_smaller)/1000000000.0;
        System.out.println("Insertion Sort\nTime= " + time);
        time_prev_smaller = System.nanoTime();

        //Calls the second method
        ShellSort(smallerData2);
        time = (System.nanoTime()-time_prev_smaller)/1000000000.0;
        System.out.println("Shell Sort\nTime= " + time);
        time_prev_smaller = System.nanoTime();

        //Calls the third method
        Arrays.sort(smallerData3);
        time = (System.nanoTime()-time_prev_smaller)/1000000000.0;
        System.out.println("Quick Sort\nTime= " + time);

        //Display all the info
        System.out.println("SMALLER-DATA");
        System.out.println("\tPresorted\tInsertion\t\t Shell\t\t Quick");
        for (int i=0; i<smallerData.length; i++)
            System.out.println(smallerData[i] + " " + smallerData1[i] + " " + smallerData2[i] + " " + smallerData3[i]);
        //endregion

        //region normalData

        //Contains the current value of the most precise available system timer
        long time_prev = System.nanoTime();

        //Calls the first method
        InsertionSort(data1);
        time = (System.nanoTime()-time_prev)/1000000000.0;
        System.out.println("Insertion Sort\nTime= " + time);
        time_prev = System.nanoTime();

        //Calls the second method
        ShellSort(data2);
        time = (System.nanoTime()-time_prev)/1000000000.0;
        System.out.println("Shell Sort\nTime= " + time);
        time_prev = System.nanoTime();

        //Calls the third method
        Arrays.sort(data3);
        time = (System.nanoTime()-time_prev)/1000000000.0;
        System.out.println("Quick Sort\nTime= " + time);


        //Display all the info
        System.out.println("NORMAL-DATA");
        System.out.println("\tPresorted\tInsertion\t\t Shell\t\t Quick");
        for (int i=0; i<data.length; i++)
            System.out.println(data[i] + " " + data1[i] + " " + data2[i] + " " + data3[i]);
        //endregion

        //region biggerData

        //CALLS THE THREE METHODS FOR EVEN-SMALLER-DATA[]
        long time_prev_bigger = System.nanoTime();

        //Calls the first method
        InsertionSort(biggerData1);
        time = (System.nanoTime()-time_prev_bigger)/1000000000.0;
        System.out.println("Insertion Sort\nTime= " + time);
        time_prev_bigger = System.nanoTime();

        //Calls the second method
        ShellSort(biggerData2);
        time = (System.nanoTime()-time_prev_bigger)/1000000000.0;
        System.out.println("Shell Sort\nTime= " + time);
        time_prev_bigger = System.nanoTime();

        //Calls the third method
        Arrays.sort(biggerData3);
        time = (System.nanoTime()-time_prev_bigger)/1000000000.0;
        System.out.println("Quick Sort\nTime= " + time);

        //Display all the info
        System.out.println("BIGGER-DATA");
        System.out.println("\tPresorted\tInsertion\t\t Shell\t\t Quick");
        for (int i=0; i<biggerData.length; i++)
            System.out.println(biggerData[i] + " " + biggerData1[i] + " " + biggerData2[i] + " " + biggerData3[i]);
        //endregion
    }


    public static void InsertionSort(double[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i]<data[i-1]) {
                double temp = data[i];
                int j = i;
                do {
                    data[j] = data[j-1];
                    j--;
                } while (j>0 && data[j-1] > temp);
                data[j] = temp;
            }
        }
    }

    public  static void ShellSort(double[] a) {
        int increment = a.length / 3 + 1;

        // Sort by insertion sort at diminishing increments.
        while ( increment > 1 )
        {
            for ( int start = 0; start < increment; start++ )
                insertSort(a, start, increment );

            increment = increment / 3 + 1;
        }

        // Do a final pass with an increment of 1.
        // (This has to be outside the previous loop because
        // the formula above for calculating the next
        // increment will keep generating 1 repeatedly.)
        insertSort(a, 0, 1 );
    }

    public static void insertSort(double[] a, int start, int increment ) {
        int j, k;
        double temp;

        for ( int i = start + increment; i < a.length; i += increment )
        {
            j = i;
            k = j - increment;
            if ( a[j] < a[k] )
            {
                // Shift all previous entries down by the current
                // increment until the proper place is found.
                temp = a[j];
                do
                {
                    a[j] = a[k];
                    j = k;
                    k = j - increment;
                } while ( j != start && a[k] > temp );
                a[j] = temp;
            }
        }
    }

    public static double median(double[] a) {
        Arrays.sort(a);
        return a[a.length/2];
    }
}
