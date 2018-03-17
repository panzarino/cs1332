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
        root = addHelper(data, root);
    }

    /**
     * Recursively adds data in the correct location and balances tree
     * @param data the data to be added
     * @param node the current node in recursive calls
     * @return the new tree
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else {
            int compared = data.compareTo(node.getData());
            if (compared < 0) {
                node.setLeft(addHelper(data, node.getLeft()));
            } else if (compared > 0) {
                node.setRight(addHelper(data, node.getRight()));
            }
        }
        return balance(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        AVLNode<T> empty = new AVLNode<>(null);
        root = removeHelper(data, root, empty);
        return empty.getData();
    }

    /**
     * Recursively remove data from tree
     * @param data data to remove from tree
     * @param node current node in recursive call
     * @param empty empty node that holds data before deletion
     * @return removed node
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> empty) {
        if (node == null) {
            throw new NoSuchElementException(
                "Element could not be found in tree."
            );
        }
        int compared = data.compareTo(node.getData());
        AVLNode<T> left = node.getLeft();
        AVLNode<T> right = node.getRight();
        if (compared < 0) {
            node.setLeft(removeHelper(data, left, empty));
        } else if (compared > 0) {
            node.setRight(removeHelper(data, right, empty));
        } else {
            size--;
            empty.setData(node.getData());
            if (right == null && left == null) {
                setHeightBalance(node);
                return null;
            } else if (right == null) {
                setHeightBalance(node);
                return left;
            } else if (left == null) {
                setHeightBalance(node);
                return right;
            } else {
                AVLNode<T> blank = new AVLNode<>(null);
                node.setRight(successor(right, blank));
                node.setData(blank.getData());
            }
        }
        return balance(node);
    }

    /**
     * Recursively finds and removes successor from tree
     * @param node current node in recursive calls
     * @param blank node to store successor value
     * @return tree without successor
     */
    private AVLNode<T> successor(AVLNode<T> node, AVLNode<T> blank) {
        if (node.getLeft() == null) {
            blank.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successor(node.getLeft(), blank));
            setHeightBalance(node);
        }
        return balance(node);
    }

    /**
     * Balances a node in the tree
     * @param node node to be balanced
     * @return updated tree
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        setHeightBalance(node);
        int balanceFactor = node.getBalanceFactor();

        if (balanceFactor > 1) { // right rotation
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            return rightRotate(node);
        } else if (balanceFactor < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            return leftRotate(node);
        }
        return node;
    }

    /**
     * Performs a right rotation
     * @param node node to start rotation from
     * @return node to be shifted
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        setHeightBalance(node);
        setHeightBalance(left);
        return left;
    }

    /**
     * Performs a left rotation
     * @param node node to start rotation from
     * @return node to be shifted
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        right.setLeft(node);
        setHeightBalance(node);
        setHeightBalance(right);
        return right;
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
        if (size < 2) {
            throw new NoSuchElementException("Not enough elements in tree.");
        }
        return secondLargestHelper(root);
    }

    /**
     * Recursively finds the second largest element in the tree
     * @param node the current node in recursive calls
     * @return the second largest element in the tree
     */
    private T secondLargestHelper(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        AVLNode<T> right = node.getRight();
        if (size == 2) {
            if (right != null) {
                return left.getData();
            }
            return node.getData();
        }
        AVLNode<T> rightRight = right.getRight();
        AVLNode<T> rightLeft = right.getLeft();
        if (rightRight == null) {
            if (rightLeft != null) {
                return rightLeft.getData();
            }
            return node.getData();
        }
        return secondLargestHelper(right);
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
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Sets the height and balance factor for a node
     * @param node the node to set data for
     */
    private void setHeightBalance(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        AVLNode<T> right = node.getRight();
        if (left == null && right == null) {
            node.setHeight(0);
            node.setBalanceFactor(0);
        } else if (left == null) {
            node.setHeight(right.getHeight() + 1);
            node.setBalanceFactor(-1 - right.getHeight());
        } else if (right == null) {
            node.setHeight(left.getHeight() + 1);
            node.setBalanceFactor(left.getHeight() + 1);
        } else {
            node.setHeight(Math.max(left.getHeight(),
                    right.getHeight()) + 1);
            node.setBalanceFactor(left.getHeight()
                    - right.getHeight());
        }
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
