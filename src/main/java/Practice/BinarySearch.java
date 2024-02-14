import java.util.*;


public class BinarySearch {
    public static void main(String[] args) {
        Set<Double> numbers = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            numbers.add(Math.ceil(Math.random() * 1000));
        }
        List<Double> sortedNumbers = numbers.stream().sorted().toList();

        System.out.println("A list of million random sorted numbers from 1 - 100 are created. Which value would you like to search:");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        System.out.println("You entered integer " + a);

        boolean found = binarySearch(sortedNumbers, a);
        System.out.printf("Value %s was %s.\n", a, found ? "found" : "not found");
    }

    private static boolean binarySearch(List<Double> numbers, int searchedValue) {
        if (numbers.size() == 1) {
            return numbers.get(0).equals(searchedValue);
        }

        Double middleValue = numbers.get((int) Math.ceil(numbers.size() / 2));

        if (searchedValue > middleValue) {
            return binarySearch(numbers.subList((int) Math.ceil(numbers.size() / 2), numbers.size()), searchedValue);
        } else if (searchedValue < middleValue) {
            return binarySearch(numbers.subList(0, (int) Math.floor(numbers.size() / 2)), searchedValue);
        } else {
            return true;
        }
    }
}