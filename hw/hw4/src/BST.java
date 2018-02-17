import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

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
            throw new IllegalArgumentException(
                "Can not add elements from a null collection."
            );
        }
        for (T item: data) {
            add(item);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "A null value cannot be added to the tree."
            );
        }
        if (size == 0) {
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
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data.");
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
    private T removeHelper(T data, BSTNode<T> node, BSTNode<T> parent) {
        if (node == null) {
            throw new NoSuchElementException(
                "Element could not be found in tree."
            );
        }
        int compared = data.compareTo(node.getData());
        if (compared == 0) {
            BSTNode<T> left = node.getLeft();
            BSTNode<T> right = node.getRight();
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
                }
                if (data.compareTo(parent.getLeft().getData()) == 0) {
                    if (left == null && right == null) {
                        parent.setLeft(null);
                    } else if (left != null) {
                        parent.setLeft(left);
                    } else {
                        parent.setLeft(right);
                    }
                }
                if (data.compareTo(parent.getRight().getData()) == 0) {
                    if (left == null && right == null) {
                        parent.setRight(null);
                    } else if (left != null) {
                        parent.setRight(left);
                    } else {
                        parent.setRight(right);
                    }
                }
            }
            size--;
            return value;
        }
        if (compared > 0) {
            return removeHelper(data, node.getRight(), node);
        } else {
            return removeHelper(data, node.getLeft(), node);
        }
    }

    /**
     * Recursively finds and removes the predecessor for the remove method
     * @param node the node to search at
     * @return the predecessor data
     */
    private T predecessor(BSTNode<T> node) {
        BSTNode<T> right = node.getRight();
        if (right.getRight() == null) {
            node.setRight(null);
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
    private T getHelper(T data, BSTNode<T> node) {
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
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        List<T> list = new ArrayList<T>(size);
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.poll();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    @Override
    public int distanceBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        BSTNode<T> common;
        if (data1.compareTo(data2) > 0) {
            common = commonAncestor(data1, data2, root);
        } else {
            common = commonAncestor(data2, data1, root);
        }
        return distance(data1, common, 0) + distance(data2, common, 0);
    }

    /**
     * Recursively the deepest common ancestor of two values
     * @param greater the greater of the values
     * @param smaller the lesser of the values
     * @param node the current node in recursive calls
     * @return the common ancestor
     */
    private BSTNode<T> commonAncestor(T greater, T smaller, BSTNode<T> node) {
        int comparedGreater = greater.compareTo(node.getData());
        int comparedSmaller = smaller.compareTo(node.getData());
        if (comparedGreater > 0 && comparedSmaller > 0) {
            return commonAncestor(greater, smaller, node.getRight());
        } else if (comparedGreater < 0 && comparedSmaller < 0) {
            return commonAncestor(greater, smaller, node.getLeft());
        } else {
            return node;
        }
    }

    /**
     * Recursively calculate the distance between a root and a value
     * @param value the value to search for
     * @param node the current node in recursive calls
     * @param sum the current sum to add to
     * @return the distance between the root and the value
     */
    private int distance(T value, BSTNode<T> node, int sum) {
        if (node == null) {
            throw new NoSuchElementException(
                "Could not find the value " + value + " in the tree."
            );
        }
        int compared = value.compareTo(node.getData());
        if (compared == 0) {
            return sum;
        } else if (compared > 0) {
            return distance(value, node.getRight(), sum + 1);
        } else {
            return distance(value, node.getLeft(), sum + 1);
        }
    }

    @Override
    public void clear() {
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
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getRight() == null && node.getLeft() == null) {
            return 0;
        }
        return Math.max(
            heightHelper(node.getLeft()), heightHelper(node.getRight())
        );
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
