/**
 * Your implementation of an ArrayList.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        clear();
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        if (++size == backingArray.length) {
            T[] newBacking = (T[])(new Object[2 * size]);
            for (int i = 0; i < size; i++) {
                newBacking[i] = backingArray[i];
            }
            backingArray = newBacking;
        }
        backingArray[index] = data;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T element = backingArray[index];
        for (int i = index + 1; i < size; i++) {
            backingArray[i - 1] = backingArray[i];
        }
        backingArray[size - 1] = null;
        return element;
    }

    @Override
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        return removeAtIndex(size);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[])(new Object[INITIAL_CAPACITY]);
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
