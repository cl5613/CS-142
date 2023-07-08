package sort.stu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Perform an out of place merge sort on a native array of integers.
 *
 * merge_sort
 * best case = O(n*log*n)
 * worst case = O(n*log*n)
 *
 * @author Chen Lin
 */
public class MergeSort {
    /**
     * Split the array on the left side.
     *
     * @param data the full array of data
     * @return the left half side of the data
     */
    private static ArrayList<Integer> splitLeft(ArrayList<Integer> data) {
        ArrayList<Integer> left = new ArrayList<>();
        for (int i = 0; i < data.size() / 2; ++i) {
            left.add(data.get(i));
        }
        return left;
    }

    /**
     * Split the array on the right side.
     *
     * @param data the full array of data
     * @return the right half side of the data
     */
    private static ArrayList<Integer> splitRight(ArrayList<Integer> data) {
        ArrayList<Integer> right = new ArrayList<>();
        for (int i = data.size() / 2; i < data.size(); ++i) {
            right.add(data.get(i));
        }
        return right;
    }

    /**
     * Merges two sorted arrays, left and right, into a combined sorted array.
     *
     * @param left  a sorted array
     * @param right a sorted array
     * @return one combined sorted array
     */
    private static ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right) {

        ArrayList<Integer> result = new ArrayList<>();
        int left_Index = 0;
        int right_Index = 0;

        while (left_Index < left.size() && right_Index < right.size()) {
            if (left.get(left_Index) <= right.get(right_Index)) {
                result.add(left.get(left_Index));
                ++left_Index;
            } else {
                result.add(right.get(right_Index));
                ++right_Index;
            }
        }

        for (int i = left_Index; i < left.size(); ++i) {
            result.add(left.get(i));
        }
        for (int i = right_Index; i < right.size(); ++i) {
            result.add(right.get(i));
        }
     return result;
    }

    /**
     * Performs a merge sort and returns a newly sorted array
     *
     * @param data the data to be sorted (a native array)
     * @return a sorted array
     */

    private static ArrayList<Integer> mergeSort(ArrayList<Integer> data) {
        if (data.size() < 2) {
            return data;
        } else {
            ArrayList<Integer> left = splitLeft(data);
            ArrayList<Integer> right = splitRight(data);
            return merge(mergeSort(left), mergeSort(right));
        }
    }

    /**
     * Test function for mergeSort.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[][] DATA = {
                {},
                {0},
                {0, 1},
                {1, 0},
                {0, 1, 2},
                {0, 2, 1},
                {1, 0, 2},
                {1, 2, 0},
                {2, 0, 1},
                {2, 1, 0},
                {9, 5, 2, 6, 3, 8, 1, 4, 0, 7}
        };

        for (int[] data : DATA) {
            // create two lists of the data
            List<Integer> sortData = Arrays.stream(data).boxed().collect(Collectors.toList());
            List<Integer> sorted = Arrays.stream(data).boxed().collect(Collectors.toList());
            // merge sort is not in place and returns a new sorted list
            sortData = mergeSort((ArrayList<Integer>) sortData);
            // use built in sort to compare against
            Collections.sort(sorted);
            // show the results of the comparison
            System.out.print("mergeSort: " + Arrays.stream(data).boxed().collect(Collectors.toList()) +
                    " result: " + sortData);
            System.out.println(sortData.equals(sorted) ? " OK" : " FAIL");
        }
    }
}
