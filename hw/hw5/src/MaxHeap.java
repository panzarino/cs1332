import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        clear();
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The initial array before the Build Heap algorithm takes place should
     * contain the data in the same order as it appears in the ArrayList.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        size = data.size();
        backingArray = (T[]) (new Comparable[size * 2 + 1]);
        for (int i = 0; i < size; i++) {
            T value = data.get(i);
            if (value == null) {
                throw new IllegalArgumentException(
                    "Data in collection cannot be null."
                );
            }
            backingArray[i + 1] = value;
        }
        for (int i = data.size() / 2; i > 0; i--) {
            removeHelper(i);
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (size + 1 == backingArray.length) {
            T[] newBacking = (T[]) (new Comparable[2 * backingArray.length]);
            for (int i = 0; i < backingArray.length; i++) {
                newBacking[i] = backingArray[i];
            }
            backingArray = newBacking;
        }
        backingArray[++size] = item;
        addHelper(size);
    }

    /**
     * Recursively heapify current backingArray
     * @param index the index of the array in recursive calls
     */
    private void addHelper(int index) {
        int half = index / 2;
        if (half < 1) {
            return;
        }
        if (backingArray[half].compareTo(backingArray[index]) < 0) {
            swap(index, half);
            addHelper(half);
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "Cannot remove element if heap is empty."
            );
        }
        T value = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size--] = null;
        removeHelper(1);
        return value;
    }

    /**
     * Recursively sorts the list on remove
     * @param index the index of the array in recursive calls
     */
    private void removeHelper(int index) {
        int child = 2 * index;
        if (child > size || backingArray[child] == null) {
            return;
        }
        if (child + 1 > size
                || backingArray[child + 1] == null
                || backingArray[child].compareTo(
                        backingArray[child + 1]
                    ) > 0) {
            removeSwap(index, child);
        } else {
            removeSwap(index, child + 1);
        }
    }

    /**
     * Swap elements in remove method if applicable
     * @param index the current index
     * @param child the current child index
     */
    private void removeSwap(int index, int child) {
        if (backingArray[index].compareTo(backingArray[child]) < 0) {
            swap(index, child);
            removeHelper(child);
        }
    }

    /**
     * Swap two elements in the backingArray
     * @param a the first index to swap
     * @param b the second index to swap
     */
    private void swap(int a, int b) {
        T temp = backingArray[a];
        backingArray[a] = backingArray[b];
        backingArray[b] = temp;
    }

    @Override
    public boolean isEmpty() {
        // DO NOT MODIFY THIS METHOD!
        return size == 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}
