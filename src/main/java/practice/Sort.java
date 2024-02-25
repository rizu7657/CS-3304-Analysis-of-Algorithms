package practice;

import collections.SortUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Sort {

    private static final int[] LIST = {36, 25, 44, 2, 8, 88, 11};

    public static void main(String[] args) {

        List<Number> sortedBySelection = SortUtil.selectSort(Arrays.stream(LIST).boxed().collect(Collectors.toList()));
        List<Number> sortedByMerge = SortUtil.mergeSort(Arrays.stream(LIST).boxed().collect(Collectors.toList()));

        System.out.println(sortedByMerge);
    }
}
