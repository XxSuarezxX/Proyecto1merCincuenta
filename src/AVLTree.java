import java.util.ArrayList;
import java.util.List;

class AVLTree  {
    AVLNode root;

    int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    void insert(int isbn, String title, String volume, String publisher, String library, String author, String authorBiography) {
        root = insertRec(root, isbn, title, volume, publisher, library, author, authorBiography);
    }

    AVLNode insertRec(AVLNode node, int isbn, String title, String volume, String publisher, String library, String author, String authorBiography) {
        if (node == null) {
            return new AVLNode(isbn, title, volume, publisher, library, author, authorBiography);
        }

        if (isbn < node.isbn) {
            node.left = insertRec(node.left, isbn, title, volume, publisher, library, author, authorBiography);
        } else if (isbn > node.isbn) {
            node.right = insertRec(node.right, isbn, title, volume, publisher, library, author, authorBiography);
        } else {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && isbn < node.left.isbn) {
            return rotateRight(node);
        }

        if (balance < -1 && isbn > node.right.isbn) {
            return rotateLeft(node);
        }

        if (balance > 1 && isbn > node.left.isbn) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && isbn < node.right.isbn) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    void delete(int isbn) {
        root = deleteRec(root, isbn);
    }

    AVLNode deleteRec(AVLNode root, int isbn) {
        if (root == null) {
            return root;
        }

        if (isbn < root.isbn) {
            root.left = deleteRec(root.left, isbn);
        } else if (isbn > root.isbn) {
            root.right = deleteRec(root.right, isbn);
        } else {
            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                AVLNode temp = getMinNode(root.right);
                root.isbn = temp.isbn;
                root.right = deleteRec(root.right, temp.isbn);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotateLeft(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }
        return root;
    }

    AVLNode getMinNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    int getCopiesByISBN(int isbn) {
        return getCopiesByISBNRec(root, isbn);
    }

    int getCopiesByISBNRec(AVLNode node, int isbn) {
        if (node == null) {
            return 0;
        }
        if (isbn < node.isbn) {
            return getCopiesByISBNRec(node.left, isbn);
        } else if (isbn > node.isbn) {
            return getCopiesByISBNRec(node.right, isbn);
        } else {
            return 1 + getCopiesByISBNRec(node.left, isbn) + getCopiesByISBNRec(node.right, isbn);
        }
    }

    void inOrderTraversalRec(AVLNode node, List<AVLNode> result) {
        if (node != null) {
            inOrderTraversalRec(node.left, result);
            result.add(node);
            inOrderTraversalRec(node.right, result);
        }
    }
}