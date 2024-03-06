package practice.dynamic;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KnapsackProblem {

    public static void main(String[] args) {
        int capacity = 163;
        Integer[] values = {10, 20, 15, 30, 25, 10, 20, 15, 30, 25};
        Integer[] weights = {4, 9, 15, 19, 27, 44, 54, 68, 73, 101};

        StopWatch watch = new StopWatch();
        watch.start();
        solveByBruteForce(capacity, values, weights, new ArrayList<>());
        watch.stop();

        System.out.printf("Brute force took %s microseconds.\n", watch.getTime(TimeUnit.MICROSECONDS));

        watch = new StopWatch();
        watch.start();
        solveWithDynamicalP(capacity, weights.length, values, weights);
        watch.stop();

        System.out.printf("Dynamic Programming took %s microseconds.", watch.getTime(TimeUnit.MICROSECONDS));
    }

    public static void solveByBruteForce(int K, Integer[] values, Integer[] weights, List<List<Item>> combinations) {
        List<Item> options = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            options.add(new Item(i, values[i], weights[i]));
        }

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

        List<Item> highestValueList = new ArrayList<>();
        int highestValue = 0;

        for (List<Item> comb : combinations) {
            int sum = comb.stream().mapToInt(Item::getWeight).sum();

            if (sum >= highestValue) {
                highestValue = sum;
                highestValueList = comb;
            }
            System.out.printf("\nSum of %s with weight %s and weights %s at %s", sum, comb.stream().mapToInt(Item::getWeight).sum(), ArrayUtils.toString(comb.stream().map(Item::getWeight).toList()), ArrayUtils.toString(comb.stream().map(Item::getPosition).toList()));
        }

        System.out.printf("\nBest combination is %s with value %s\n\n", ArrayUtils.toString(highestValueList.stream().map(Item::getWeight).toList()), highestValueList.stream().mapToInt(Item::getValue).sum());
    }

    public static void solveByBruteForce(int K, int n, Integer[] values, Integer[] weights) {
        List<List<Item>> combinations = new ArrayList<>();
        List<Item> options = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            options.add(new Item(k, values[k], weights[k]));
        }

        Integer[] positions = new Integer[n];

        for (int i = 0; i < positions.length; i++) {
            positions[i] = i;
        }

        combinations = generateCombinations(weights, values, K);

        List<Item> highestValueList = new ArrayList<>();
        int highestValue = 0;

        for (List<Item> comb : combinations) {
            int sum = comb.stream().mapToInt(Item::getValue).sum();

            if (sum >= highestValue) {
                highestValue = sum;
                highestValueList = comb;
            }
            System.out.printf("\nSum of %s with weight %s and weights %s at %s", sum, comb.stream().mapToInt(Item::getWeight).sum(), ArrayUtils.toString(comb.stream().map(Item::getWeight).toList()), ArrayUtils.toString(comb.stream().map(Item::getPosition).toList()));
        }

        System.out.printf("\nBest combination is %s with value %s\n\n", ArrayUtils.toString(highestValueList.stream().map(Item::getWeight).toList()), highestValueList.stream().mapToInt(Item::getValue).sum());
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
