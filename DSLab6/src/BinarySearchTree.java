import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> {

    private Node<E> root;

    private static class Node<E> {
        private E value;
        private Node<E> left;
        private Node<E> right;

        public Node(E value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    // Order: 1. This method is the most crucial because its the foundation. it allows us to begin building the BST.
    public boolean add(E item) {
        if (root == null) {
            root = new Node<>(item);
            return true;
        }

        Node<E> current = root;
        while (true) {
            int cmp = item.compareTo(current.value);
            if (cmp == 0) {
                return false; // Item already exists
            } else if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node<>(item);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(item);
                    return true;
                }
                current = current.right;
            }
        }
    }
    
    //Order: 2. ths method allows the user to search for a value after it has been inserted in the tree.
    public boolean find(E item) {
        Node<E> current = root;
        while (current != null) {
            int cmp = item.compareTo(current.value);
            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    //Order: 3. It enables the user to remove specific values from a tree.
    public boolean remove(E item) {
        Node<E> parent = null;
        Node<E> current = root;

        while (current != null) {
            int cmp = item.compareTo(current.value);
            if (cmp == 0) {
                if (current.left == null || current.right == null) {
                    Node<E> newCurrent = current.left != null ? current.left : current.right;
                    if (parent == null) {
                        root = newCurrent;
                    } else if (current == parent.left) {
                        parent.left = newCurrent;
                    } else {
                        parent.right = newCurrent;
                    }
                } else {
                    Node<E> successorParent = current;
                    Node<E> successor = current.right;
                    while (successor.left != null) {
                        successorParent = successor;
                        successor = successor.left;
                    }
                    current.value = successor.value;
                    if (successor == successorParent.left) {
                        successorParent.left = successor.right;
                    } else {
                        successorParent.right = successor.right;
                    }
                }
                return true;
            } else if (cmp < 0) {
                parent = current;
                current = current.left;
            } else {
                parent = current;
                current = current.right;
            }
        }
        return false;
    }
    
    //Order: 4. this enables us to get the parent node of a given node.
    public E getParent(E item) {
        Node<E> current = root;
        Node<E> parent = null;

        while (current != null) {
            int cmp = item.compareTo(current.value);
            if (cmp == 0) {
                return parent != null ? parent.value : null;
            } else if (cmp < 0) {
                parent = current;
                current = current.left;
            } else {
                parent = current;
                current = current.right;
            }
        }
        return null;
    }
    
    
    public ArrayList<E> getAllDescendants(E item) {
        ArrayList<E> descendants = new ArrayList<>();
        Node<E> current = root;

        while (current != null) {
            int cmp = item.compareTo(current.value);
            if (cmp == 0) {
                if (current.left != null) {
                    descendants.addAll(getAllDescendants(current.left.value));
                }
                if (current.right != null) {
                    descendants.addAll(getAllDescendants(current.right.value));
                }
                return descendants;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return descendants;
    }

    public E getMax() {
        Node<E> current = root;

        while (current.right != null) {
            current = current.right;
        }

        return current.value;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node<E> node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int getLevel(E item) {
        return getLevel(root, item, 0);
    }

    private int getLevel(Node<E> node, E item, int level) {
        if (node == null) {
            return -1;
        }

        if (item.compareTo(node.value) == 0) {
            return level;
        }

        int leftLevel = getLevel(node.left, item, level + 1);
        if (leftLevel != -1) {
            return leftLevel;
        }

        int rightLevel = getLevel(node.right, item, level + 1);
        if (rightLevel != -1) {
            return rightLevel;
        }

        return -1;
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }
    
    public ArrayList<E> bfs() {
        ArrayList<E> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<E> current = queue.remove();
            result.add(current.value);

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }

        return result;
    }

    public boolean isIdentical(Node<E> anotherTree) {
        return isIdentical(root, anotherTree);
    }

    private boolean isIdentical(Node<E> node1, Node<E> node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 == null || node2 == null) {
            return false;
        }

        return (node1.value.compareTo(node2.value) == 0) &&
               isIdentical(node1.left, node2.left) &&
               isIdentical(node1.right, node2.right);
    }

    public int numLeaves() {
        return numLeaves(root);
    }

    private int numLeaves(Node<E> node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        }

        return numLeaves(node.left) + numLeaves(node.right);
    }

    public int numInternal() {
        return numInternal(root);
    }

    private int numInternal(Node<E> node) {
        if (node == null || (node.left == null && node.right == null)) {
            return 0;
        }

        return 1 + numInternal(node.left) + numInternal(node.right);
    }

    // Order: 16. it allows the user to clear the entire tree.
    public void clear() {
        root = null;
    }
}
