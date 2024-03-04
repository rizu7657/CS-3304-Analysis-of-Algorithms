package practice.dynamic;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class fibonacciExecutionTime {

    private static long[] Values;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is n when calculating the fibonacci: ");
        int n = scanner.nextInt();
        Values = new long[n];
        Arrays.fill(Values, 0);
        Values[0] = 1;
        Values[1] = 1;

        StopWatch watch = new StopWatch();
        watch.start();
        long tresult = fibrtable(n);
        watch.stop();
        System.out.printf("Table took %s milliseconds%n", watch.getTime(TimeUnit.MILLISECONDS));
        System.out.println(Arrays.toString(Values));

        watch = new StopWatch();
        watch.start();
        int rresult = fibr(n);
        watch.stop();
        System.out.printf("Recursive took %s milliseconds%n", watch.getTime(TimeUnit.MILLISECONDS));

        watch = new StopWatch();
        watch.start();
        long iresult = fibi(n);
        watch.stop();
        System.out.printf("Bottom up took %s milliseconds%n", watch.getTime(TimeUnit.MILLISECONDS));

        assert tresult == rresult && rresult == iresult : "Answer is incorrect somewhere, they don't compute to the same result";
    }

    private static long fibrtable(int n) {

        if (n <= 2) {
            return 1;
        }

        if (Values[n - 1] == 0) {
            Values[n - 1] = fibrtable(n-1) + fibrtable(n-2);
        }
        return Values[n - 1];
    }

    private static int fibr(int n) {
        assert n > 0 : "n out of range";

        if (n <= 2) {
            return 1;
        }
        return fibr(n - 1) + fibr(n - 2);
    }

    private static long fibi(int n) {
        assert (n > 0) && (n <= 91) : "n out of range";

        long curr, prev, past;

        if (n == 1 || n == 2) {
            return 1;
        }

        curr = prev = 1;

        for (int i = 3; i <= n; i++) {
            past = prev;
            prev = curr;
            curr = past + prev;
        }
        return curr;
    }
}
