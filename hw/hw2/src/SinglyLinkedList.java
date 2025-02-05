/**
 * Your implementation of a circular singly linked list.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(T data, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index provided.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data);
            head.setNext(head);
            size++;
            return;
        }
        if (index == 0) {
            head.setNext(new LinkedListNode<T>(head.getData(), head.getNext()));
            head.setData(data);
            size++;
            return;
        }
        LinkedListNode<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        current.setNext(new LinkedListNode<T>(data, current.getNext()));
        size++;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(data, 0);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(data, size);
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index provided.");
        }
        if (index == 0) {
            if (size == 1) {
                T value = head.getData();
                head = null;
                size--;
                return value;
            }
            T value = head.getData();
            T first = head.getNext().getData();
            head.setData(first);
            head.setNext(head.getNext().getNext());
            size--;
            return value;
        }
        LinkedListNode<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        T value = current.getNext().getData();
        current.setNext(current.getNext().getNext());
        size--;
        return value;
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        return removeAtIndex(size - 1);
    }

    @Override
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        }
        Integer last = null;
        LinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(data)) {
                last = i;
            }
            current = current.getNext();
        }
        if (last == null) {
            return null;
        }
        return removeAtIndex(last);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index provided.");
        }
        LinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        LinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}