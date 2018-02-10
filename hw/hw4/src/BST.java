import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

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
    public BST() { }

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
     * @param node the current node in recursive calls
     */
    private void addHelper(T data, BSTNode<T> node) {
        int compared = data.compareTo(node.getData());
        if (compared == 0) {
            return;
        }
        if (compared > 0) {
            if (node.getRight() != null) {
                addHelper(data, node.getRight());
            } else {
                node.setRight(new BSTNode<T>(data));
                size++;
            }
        } else {
            if (node.getLeft() != null) {
                addHelper(data, node.getLeft());
            } else {
                node.setLeft(new BSTNode<T>(data));
                size++;
            }
        }
    }

    @Override
    public T remove(T data) {

    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data.");
        }
        return getHelper(data, root);
    }

    /**
     * Recursively searches for value in the tree
     * @param data the data to search for in the tree
     * @param node the current node in recursive calls
     * @return the data in the tree equal to the data parameter
     */
    private T getHelper(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Element could not be found in tree.");
        }
        if (node.getData().equals(data)) {
            return node.getData();
        }
        int compared = data.compareTo(node.getData());
        if (compared > 0) {
            return getHelper(data, node.getRight());
        } else {
            return getHelper(data, node.getLeft());
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data.");
        }
        return containsHelper(data, root);
    }

    /**
     * Recursively searches to see if the tree contains a value
     * @param data the data to search for in the tree
     * @param node the current node in recursive calls
     * @return whether or not the data is contained within the tree
     */
    private boolean containsHelper(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        }
        if (node.getData().equals(data)) {
            return true;
        }
        int compared = data.compareTo(node.getData());
        if (compared > 0) {
            return containsHelper(data, node.getRight());
        } else {
            return containsHelper(data, node.getLeft());
        }
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<T>(size);
        preorderHelper(list, root);
        return list;
    }

    /**
     * Recursively populates a list in a preorder traversal
     * @param list the list to add elements to
     * @param node the current node in recursive calls
     */
    private void preorderHelper(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        preorderHelper(list, node.getLeft());
        preorderHelper(list, node.getRight());
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<T>(size);
        postorderHelper(list, root);
        return list;
    }

    /**
     * Recursively populates a list in a postorder traversal
     * @param list the list to add elements to
     * @param node the current node in recursive calls
     */
    private void postorderHelper(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        postorderHelper(list, node.getLeft());
        postorderHelper(list, node.getRight());
        list.add(node.getData());
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<T>(size);
        inorderHelper(list, root);
        return list;
    }

    /**
     * Recursively populates a list in an inorder traversal
     * @param list the list to add elements to
     * @param node the current node in recursive calls
     */
    private void inorderHelper(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        inorderHelper(list, node.getLeft());
        list.add(node.getData());
        inorderHelper(list, node.getRight());
    }

    @Override
    public List<T> levelorder() {

    }

    @Override
    public int distanceBetween(T data1, T data2) {

    }

    @Override
    public void clear() {
        root = null;
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
