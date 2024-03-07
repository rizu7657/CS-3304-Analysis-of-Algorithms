package practice.dynamic;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KnapsackProblem {

    public static void main(String[] args) {
        int capacity = 163;
        Integer[] values = {4, 9, 15, 19, 27, 44, 54, 68, 73, 101};
        Integer[] weights = {4, 9, 15, 19, 27, 44, 54, 68, 73, 101};

        StopWatch watch = new StopWatch();
        watch.start();
        solveByBruteForce(capacity, values, weights,false);
        watch.stop();

        System.out.printf("Brute force took %s microseconds.\n", watch.getTime(TimeUnit.MICROSECONDS));

        watch = new StopWatch();
        watch.start();
        solveWithDynamicalP(capacity, weights.length, values, weights);
        watch.stop();

        System.out.printf("Dynamic Programming took %s microseconds.", watch.getTime(TimeUnit.MICROSECONDS));
    }

    public static void solveByBruteForce(int K, Integer[] values, Integer[] weights, boolean useGptMethod) {
        List<List<Item>> combinations = new ArrayList<>();
        List<Item> options = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            options.add(new Item(i, values[i], weights[i]));
        }

        if (useGptMethod){
            combinations = generateCombinations(weights, values, K);

        } else {

            for (int i = 0; i < options.size(); i++) {

                for (int j = 0; j < options.size(); j++) {
                    List<Item> combination = new ArrayList<>();

                    for (Item option : options) {

                        if ((combination.stream().mapToInt(Item::getWeight).sum() + option.weight) > K) {
                            continue;
                        }
                        combination.add(option);
                    }
                    combinations.add(combination);
                    Item temp = options.remove(0);
                    options.add(temp); // move front item to last position
                }
                Collections.shuffle(options);
            }
        }

        List<Item> highestValueList = new ArrayList<>();
        int highestValue = 0;

        for (List<Item> comb : combinations) {
            int sum = comb.stream().mapToInt(Item::getValue).sum();

            if (sum >= highestValue) {
                highestValue = sum;
                highestValueList = comb;
            }
            System.out.printf("\nSum of %s with weight %s and weights %s at %s", sum,
                    comb.stream().mapToInt(Item::getWeight).sum(),
                    ArrayUtils.toString(comb.stream().map(Item::getWeight).toList()),
                    ArrayUtils.toString(comb.stream().map(Item::getPosition).toList()));

            if (highestValue == K) {
                break;
            }
        }

        System.out.printf("\nBest combination is %s with values %s with weights %s\n\n",
                highestValueList.stream().mapToInt(Item::getValue).sum(),
                ArrayUtils.toString(highestValueList.stream().map(Item::getValue).toList()),
                highestValueList.stream().map(Item::getWeight).toList());
    }

    public static List<List<Item>> generateCombinations(Integer[] weights, Integer[] values, int limit) {
        List<List<Item>> result = new ArrayList<>();
        generateCombinationsHelper(weights, values, limit, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generateCombinationsHelper(Integer[] weights, Integer[] values, int limit, int index, List<Item> currentCombination, List<List<Item>> result) {
        if (index == weights.length) {
            // Base case: we've reached the end of the weights array
            if (totalWeight(currentCombination) <= limit) {
                result.add(new ArrayList<>(currentCombination));
            }
            return;
        }

        // Include current item
        currentCombination.add(new Item(index, values[index], weights[index]));
        generateCombinationsHelper(weights, values, limit, index + 1, currentCombination, result);
        currentCombination.remove(currentCombination.size() - 1); // Backtrack

        // Exclude current item
        generateCombinationsHelper(weights, values, limit, index + 1, currentCombination, result);
    }

    private static int totalWeight(List<Item> combination) {
        int total = 0;
        for (Item item : combination) {
            total += item.weight;
        }
        return total;
    }

    public static void solveWithDynamicalP(int K, int n, Integer[] values, Integer[] weights) {
        // Populate base cases
        int[][] mat = new int[n + 1][K + 1];
        for (int r = 0; r < K + 1; r++) {
            mat[0][r] = 0;
        }
        for (int c = 0; c < n + 1; c++) {
            mat[c][0] = 0;
        }

        // Main logic
        for (int item = 1; item <= n; item++) {
            for (int capacity = 1; capacity <= K; capacity++) {
                int maxValWithoutCurr = mat[item - 1][capacity]; // This is guaranteed to exist
                int maxValWithCurr = 0; // We initialize this value to 0

                int weightOfCurr = weights[item - 1]; // We use item -1 to account for the extra row at the top
                if (capacity >= weightOfCurr) { // We check if the knapsack can fit the current item
                    maxValWithCurr = values[item - 1]; // If so, maxValWithCurr is at least the value of the current item

                    int remainingCapacity = capacity - weightOfCurr; // remainingCapacity must be at least 0
                    maxValWithCurr += mat[item - 1][remainingCapacity]; // Add the maximum value obtainable with the remaining capacity
                }

                mat[item][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr); // Pick the larger of the two
            }
        }

        System.out.println(mat[n][K]); // Final answer
        for (int i = 0; i < mat.length; i++) {
            System.out.printf("item %s: %s\n", i == 0 ? "0" : i, Arrays.toString(mat[i]));
        }
    }

    private static class Item {
        private int position;
        private int value;
        private int weight;

        public Item(int position, int value, int weight) {
            this.position = position;
            this.value = value;
            this.weight = weight;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
