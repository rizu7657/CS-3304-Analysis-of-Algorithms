package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SortUtil {

    public static List<Number> mergeSort(List<Number> list) {

        if (list.size() <= 1) {
            return list;
        }

        List<Number> list1 = list.subList(0, (int) Math.ceil(list.size() / 2d));
        List<Number> list2 = list.subList((int) Math.ceil(list.size() / 2d), list.size());

        list1 = mergeSort(list1);
        list2 = mergeSort(list2);

        return merge(list1, list2);
    }

    private static List<Number> merge(List<Number> list1, List<Number> list2) {
        List<Number> sortedList = new ArrayList<>();
        list1 = new ArrayList<>(list1);
        list2 = new ArrayList<>(list2);

        while (!list1.isEmpty() && !list2.isEmpty()) {
            
            if (list1.get(0).doubleValue() > list2.get(0).doubleValue()) {
                sortedList.add(list2.get(0));
                list2.remove(0);
            } else {
                sortedList.add(list1.get(0));
                list1.remove(0);
            }
        }

        while (!list1.isEmpty()) {
            sortedList.add(list1.get(0));
            list1.remove(0);
        }

        while (!list2.isEmpty()) {
            sortedList.add(list2.get(0));
            list2.remove(0);
        }
        return sortedList;
    }

    public static List<Number> selectSort(List<Number> list) {
        List<Number> toSort = new ArrayList<>(list);
        Stack<Number> sorted = new Stack<>();

        while (sorted.size() != list.size()) {
            Number smallest = Short.MAX_VALUE;

            for (Number itemC : toSort) {
                if (itemC.doubleValue() < smallest.doubleValue()) {
                    smallest = itemC.doubleValue();
                }
            }
            toSort.remove(smallest);
            sorted.add(smallest);
        }
        return sorted;
    }
}
