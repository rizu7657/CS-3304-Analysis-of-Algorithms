package assignment.u5;

import java.util.Arrays;

public class KnapsackProblem {
    public static void main(String[] args) {
        int W = 20;
        int n = 16;
        int[] values = {10, 5, 30, 8, 12, 30, 50, 10, 2, 10, 40, 80, 100, 25, 10, 5};
        int[] weights = {1, 4, 6, 2, 5, 10, 8, 3, 9, 1, 4, 2, 5, 8, 9, 1};

        /**
         * Use a matrix table to store the results to not calculate them again
         * Make the matrix one row and column more to account for not selected 0.
         * The rows represents the items and the columns the capacity or weight
         **/
        int[][] matrix = new int[n + 1][W + 1];

        // Start at 1 because of extra row and column
        for (int item = 1; item <= n; item++) {

            for (int capacity = 1; capacity <= W; capacity++) {
                int maxValWithoutCurr = matrix[item - 1][capacity];

                // Initialize the variable
                int maxValWithCurr = 0;
                int weightOfCurr = weights[item - 1];

                //Evaluate whether the knapsack can take the current item
                if (capacity >= weightOfCurr) {
                    maxValWithCurr = values[item - 1];

                    int remainingCapacity = capacity - weightOfCurr;
                    maxValWithCurr += matrix[item - 1][remainingCapacity];
                }

                // Choose the larger value of the two items
                matrix[item][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr);
            }
        }

        System.out.println("Total value: " + matrix[n][W]); // Final answer
        System.out.println("Total weight: " + W); // Final answer

        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("item %s: %s\n", i, Arrays.toString(matrix[i]));
        }
    }
}
