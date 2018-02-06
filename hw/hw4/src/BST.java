import java.util.Collection;
import java.util.List;

/**
 * Your implementation of a binary search tree.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add elements from a null collection.");
        }
        for (T item: data) {
            add(item);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("A null value cannot be added to the tree.");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Recursively adds data in the correct location
     * @param data the data to add to the tree
     * @param current the current node in recursive calls
     */
    private void addHelper(T data, BSTNode<T> current) {
        int compared = data.compareTo(current.getData());
        if (compared == 0) {
            return;
        }
        if (compared > 0) {
            if (current.getRight() != null) {
                addHelper(data, current.getRight());
            } else {
                current.setRight(new BSTNode<T>(data));
                size++;
            }
        } else {
            if (current.getLeft() != null) {
                addHelper(data, current.getLeft());
            } else {
                current.setLeft(new BSTNode<T>(data));
                size++;
            }
        }
    }

    @Override
    public T remove(T data) {

    }

    @Override
    public T get(T data) {

    }

    @Override
    public boolean contains(T data) {

    }

    @Override
    public List<T> preorder() {

    }

    @Override
    public List<T> postorder() {

    }

    @Override
    public List<T> inorder() {

    }

    @Override
    public List<T> levelorder() {

    }

    @Override
    public int distanceBetween(T data1, T data2) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int height() {

    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
