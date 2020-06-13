package Q2;

import java.util.Arrays;

public class Solution {
    //Returns the bigger of the two input integers.
    static int getMax(int a, int b) {
        return (a > b) ? a : b;
    }

    // Returns the maximum value
    static int calculateMaxValue(int maxWeight, int weights[], int values[], int length)
    {
        int table[][] = new int[length + 1][maxWeight + 1];

        // Build the table in a bottom up manner
        for (int i = 0; i <= length; i++) {
            for (int j = 0; j <= maxWeight; j++) {

                if (i == 0 || j == 0)
                    table[i][j] = 0;

                else if (weights[i - 1] <= j)
                    table[i][j] = getMax(
                            values[i - 1] + table[i - 1][j - weights[i - 1]],
                            table[i - 1][j]);
                else
                    table[i][j] = table[i - 1][j];
            }
        }

        return table[length][maxWeight];
    }

    public static void main(String args[])
    {
        // Gives the input for solving the problem
        // The input number must be sorted (ascending order)
        int values[] = new int[] { 1, 4, 5, 7 };
        int weights[] = new int[] { 1, 3, 4, 5 };
        int maxWeight = 7;

        // Calculates the maximum value
        int maxValue = calculateMaxValue(maxWeight, weights, values, values.length);

        // Displays the maximum value
        System.out.println("Max value: " + maxValue);
    }
}
