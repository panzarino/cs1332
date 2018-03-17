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
        AVLNode<T> newNode = new AVLNode<T>(data);
        if (root == null) {
            root = newNode;
            size++;
            setHeightBalance(root);
        } else {
            addHelper(root, newNode);
        }
    }

    /**
     * Recursively adds data in the correct location and balances tree
     * @param previous the previous node in recursive calls
     * @param node the current node in recursive calls
     */
    private void addHelper(AVLNode<T> previous, AVLNode<T> node) {
        int compared = previous.getData().compareTo(node.getData());
        if (compared == 0) {
            return;
        }
        if (compared < 0) {
            if (previous.getRight() != null) {
                addHelper(previous.getRight(), node);
            } else {
                previous.setRight(node);
                size++;
            }
        } else {
            if (node.getLeft() != null) {
                addHelper(previous.getLeft(), node);
            } else {
                previous.setLeft(node);
                size++;
            }
        }
        setHeightBalance(previous);
        balance(previous);
    }

    /**
     * Balances a node in the tree
     * @param node node to be balanced
     */
    private void balance(AVLNode<T> node) {
        int balance = node.getBalanceFactor();
        if (balance > 1) {
            if (node.getLeft() != null) {
                if (node.getLeft().getBalanceFactor() >= 0) {
                    rightRotate(node);
                } else {
                    leftRotate(node.getLeft());
                    rightRotate(node);
                }
            }
        } else if (balance < -1) {
            if (node.getRight() != null) {
                if (node.getRight().getBalanceFactor() <= 0) {
                    leftRotate(node);
                } else {
                    rightRotate(node.getRight());
                    leftRotate(node);
                }
            }
        }
    }

    /**
     * Performs a right rotation
     * @param node node to start rotation from
     */
    private void rightRotate(AVLNode<T> node) {
        AVLNode<T> newNode = new AVLNode<T>(node.getData());
        AVLNode<T> left = node.getLeft();
        newNode.setRight(node.getRight());
        newNode.setLeft(left.getRight());
        node.setData(left.getData());
        node.setRight(newNode);
        node.setLeft(left.getLeft());
        left.setLeft(null);
        setHeightBalanceSub(node);
    }

    /**
     * Performs a left rotation
     * @param node node to start rotation from
     */
    private void leftRotate(AVLNode<T> node) {
        AVLNode<T> newNode = new AVLNode<T>(node.getData());
        AVLNode<T> right = node.getRight();
        newNode.setLeft(node.getLeft());
        newNode.setRight(right.getLeft());
        node.setData(right.getData());
        node.setLeft(newNode);
        node.setRight(right.getRight());
        right.setRight(null);
        setHeightBalanceSub(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return removeHelper(data, root, null);
    }

    /**
     * Recursively removes data from the tree
     * @param data the data to remove from the tree
     * @param node the current node in recursive calls
     * @param parent the previous node in recursive calls
     * @return the data removed from the tree
     */
    private T removeHelper(T data, AVLNode<T> node, AVLNode<T> parent) {
        if (node == null) {
            throw new NoSuchElementException(
                "Element could not be found in tree."
            );
        }
        int compared = data.compareTo(node.getData());
        if (compared == 0) {
            AVLNode<T> left = node.getLeft();
            AVLNode<T> right = node.getRight();
            T value = node.getData();
            if (left != null && right != null) {
                if (left.getRight() == null) {
                    node.setData(left.getData());
                    node.setLeft(null);
                } else {
                    node.setData(predecessor(left));
                }
            } else {
                if (parent == null) {
                    if (left == null && right == null) {
                        root = null;
                    } else if (left != null) {
                        root = left;
                    } else {
                        root = right;
                    }
                } else {
                    if (parent.getLeft() != null
                            && data.compareTo(parent.getLeft().getData()) == 0) {
                        if (left == null && right == null) {
                            parent.setLeft(null);
                        } else if (left != null) {
                            parent.setLeft(left);
                        } else {
                            parent.setLeft(right);
                        }
                    }
                    if (parent.getRight() != null
                            && data.compareTo(parent.getRight().getData()) == 0) {
                        if (left == null && right == null) {
                            parent.setRight(null);
                        } else if (left != null) {
                            parent.setRight(left);
                        } else {
                            parent.setRight(right);
                        }
                    }
                }
            }
            size--;
            setHeightBalance(parent);
            balance(parent);
            return value;
        }
        if (compared > 0) {
            T value = removeHelper(data, node.getRight(), node);
            setHeightBalance(parent);
            balance(parent);
            return value;
        } else {
            T value = removeHelper(data, node.getLeft(), node);
            setHeightBalance(parent);
            balance(parent);
            return value;
        }
    }

    /**
     * Recursively finds and removes the predecessor for the remove method
     * @param node the node to search at
     * @return the predecessor data
     */
    private T predecessor(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        if (right.getRight() == null) {
            node.setRight(right.getLeft());
            return right.getData();
        }
        return predecessor(right);
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
        return secondLargestHelper(root, root.getData());
    }

    /**
     * Recursively finds the second largest element in the tree
     * @param node the current node in recursive calls
     * @param data the previous value in recursive calls
     * @return the second largest element in the tree
     */
    private T secondLargestHelper(AVLNode<T> node, T data) {
        if (node.getRight() != null) {
            return secondLargestHelper(node.getRight(), node.getData());
        }
        return data;
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
        return root.getHeight();
    }

    /**
     * Sets the height and balance factor for a node
     * @param node the node to set data for
     */
    private void setHeightBalance(AVLNode<T> node) {
        int leftHeight = (node.getLeft() != null) ?
            node.getLeft().getHeight() : -1;
        int rightHeight = (node.getRight() != null) ?
            node.getRight().getHeight() : -1;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Sets the height and balance factor for a subtree
     * @param node the current node in recursive calls
     */
    private void setHeightBalanceSub(AVLNode<T> node) {
        if (root.getLeft() != null) {
            setHeightBalanceSub(node.getLeft());
        }
        if (root.getRight() != null) {
            setHeightBalanceSub(node.getRight());
        }
        setHeightBalance(node);
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
