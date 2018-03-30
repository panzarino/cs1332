import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class Sorting {

    /*

    DELETE THIS FUNCTION

     */

    private static <T> void printArray(T[] arr) {
        for (T x : arr) {
            System.out.print(x);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        for (int x = 0; x < arr.length - 1; x++) {
            boolean swap = false;
            for (int i = 0; i < arr.length - x - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swap = true;
                }
            }
            if (!swap) {
                return;
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        for (int x = 1; x < arr.length; x++) {
            T value = arr[x];
            int i;
            for (
                i = x - 1;
                i >= 0 && comparator.compare(arr[i], value) > 0;
                i--
            ) {
                arr[i + 1] = arr[i];
            }
            arr[i + 1] = value;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        for (int x = 0; x < arr.length - 1; x++) {
            int min = x;
            for (int i = x + 1; i < arr.length; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    min = x;
                }
            }
            swap(arr, x, min);
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        mergeSortHelper(arr, 0, arr.length - 1, comparator);
    }

    /**
     * Recursively performs a merge sort
     * @param arr array to operate on
     * @param left leftmost index for iteration
     * @param right rightmost index for iteration
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void mergeSortHelper(
        T[] arr, int left, int right, Comparator<T> comparator
    ) {
        if (left < right) {
            int half = (left + right) / 2;
            mergeSortHelper(arr, left, half, comparator);
            mergeSortHelper(arr, half + 1, right, comparator);
            merge(arr, left, half, right, comparator);
        }
    }

    /**
     * Merges two halves of the array
     * @param arr array to operate on
     * @param left leftmost index for merging
     * @param half index halfway between left and right
     * @param right rightmost index for merging
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to merge
     */
    private static <T> void merge(
        T[] arr, int left, int half, int right, Comparator<T> comparator
    ) {
        int leftSize = half - left + 1;
        int rightSize = right - half;
        T[] leftArray = (T[]) (new Object[leftSize]);
        T[] rightArray = (T[]) (new Object[rightSize]);
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int i = 0; i < rightSize; i++) {
            rightArray[i] = arr[half + i + 1];
        }
        int leftIndex = 0;
        int rightIndex = 0;
        int i = left;
        while (leftIndex < leftSize && rightIndex < rightSize) {
            if (comparator.compare(leftArray[leftIndex],
                rightArray[rightIndex]) <= 0) {
                arr[i++] = leftArray[leftIndex++];
            } else {
                arr[i++] = rightArray[rightIndex++];
            }
        }
        while (leftIndex < leftSize) {
            arr[i++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightSize) {
            arr[i++] = rightArray[rightIndex++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {

    }

    /**
     * Swaps two elements in an array
     * @param <T> data type to swap
     * @param arr the array to perform the operation on
     * @param pos1 index of the first element
     * @param pos2 index of the second element
     */
    private static <T> void swap(T[] arr, int pos1, int pos2) {
        T temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
}
