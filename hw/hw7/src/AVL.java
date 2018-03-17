import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null.");
        }
        for (T x : data) {
            add(x);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            root = new AVLNode<>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Recursively adds data in the correct location
     * @param data the data to be added to the tree
     * @param node the current node in recursive calls
     */
    private void addHelper(T data, AVLNode<T> node) {
        int compared = data.compareTo(node.getData());
        if (compared == 0) {
            return;
        }
        if (compared > 0) {
            if (node.getRight() != null) {
                addHelper(data, node.getRight());
            } else {
                node.setRight(new AVLNode<T>(data));
                size++;
                // rebalance the tree
            }
        } else {
            if (node.getLeft() != null) {
                addHelper(data, node.getLeft());
            } else {
                node.setLeft(new AVLNode<T>(data));
                size++;
                // rebalance the tree
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
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException(
                    "Element could not be found in tree."
            );
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
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        int compared = data.compareTo(node.getData());
        if (compared == 0) {
            return true;
        } else if (compared > 0) {
            return containsHelper(data, node.getRight());
        } else {
            return containsHelper(data, node.getLeft());
        }
    }


    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public T getSecondLargest() {
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AVL)) {
            return false;
        }
        AVL other = (AVL) obj;
        return equalsHelper(root, other.getRoot());
    }

    /**
     * Recursively checks if two trees are equal
     * @param a first tree to check
     * @param b second tree to check
     * @return whether the two trees are equal
     */
    private boolean equalsHelper(AVLNode a, AVLNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            return a.getData().equals(b.getData())
                && equalsHelper(a.getLeft(), b.getLeft())
                && equalsHelper(a.getRight(), b.getRight());
        }
        return false;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Recursively calculate the height of a node
     * @param node the current node in recursive calls
     * @return the height of the node
     */
    private int heightHelper(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getRight() == null && node.getLeft() == null) {
            return 0;
        }
        return Math.max(
            heightHelper(node.getLeft()), heightHelper(node.getRight())
        ) + 1;
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
