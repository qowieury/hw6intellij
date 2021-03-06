public class BSTree extends BTreePrinter {

    Node root;

    public BSTree() {

    }

    public BSTree(Node root) {
        this.root = root;
        this.root.parent = null; // To make sure the root has no parent
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Empty tree!!!");
        } else {
            super.printTree(root);
        }
    }

    public Node find(int search_key) {
        return find(root, search_key);
    }

    public static Node find(Node node, int search_key) {
        if (search_key == node.key) {
            return node;
        } else if ((search_key < node.key) && (node.left != null)) {
            return find(node.left, search_key);
        } else if ((search_key > node.key) && (node.right != null)) {
            return find(node.right, search_key);
        } else {
            return null;
        }
    }

    public Node findMin() {
        return findMin(root);
    }

    public static Node findMin(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }

    public Node findMax() {
        return findMax(root);
    }

    public static Node findMax(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return findMax(node.right);
        }
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
        } else {
            insert(root, key);
        }
    }

    public static void insert(Node node, int key) {
        if (key == node.key) {
            System.out.println("Duplicated key!!!");
        } else if (key < node.key) {
            if (node.left == null) {
                node.left = new Node(key);
                node.left.parent = node;
            } else {
                insert(node.left, key);
            }
        } else if (node.right == null) {
            node.right = new Node(key);
            node.right.parent = node;
        } else {
            insert(node.right, key);
        }
    }





    public void singleRotateFromLeft(Node y) {
        if (y != null) { //check mull node
            Node x = y.left;
            Node b = x.right;

            y.left = b;
            if(b != null){ //sometimes x.right doesnt exit
                b.parent = y;
            }
            x.right = y;
            x.parent = y.parent;
            y.parent = x;
            if (x.parent == null) { //if this node "x" is root node update root
                root = x;
            }else{
                if (x.parent.key < x.key) { //check for way of parent link to child
                    x.parent.right = x;
                } else {
                    x.parent.left = x;
                }
            }



        }
    }

    public void singleRotateFromRight(Node y) {
        if (y != null) {//check mull node
            Node x = y.right;
            Node b = x.left;

            y.right = b;
            if(b != null){//sometimes x.right doesnt exit
                b.parent = y;
            }
            x.left = y;
            x.parent = y.parent;
            y.parent = x;
            if (x.parent == null) {//if this node "x" is root node update root
                root = x;
            }else{
                if (x.parent.key < x.key) {//check for way of parent link to child
                    x.parent.right = x;
                } else {
                    x.parent.left = x;
                }
            }


        }
    }

    public void doubleRotateFromLeft(Node y) { //rotate inner left
        singleRotateFromRight(y.left);
        singleRotateFromLeft(y);
    }

    public void doubleRotateFromRight(Node y) { //rotate inner right
        singleRotateFromLeft(y.right);
        singleRotateFromRight(y);
    }

    // You should have "root node deletion" in this function
    public void delete(int key) {
        if (root == null) {
            System.out.println("Empty Tree!!!");
        } else if (root.key == key) { // Delete root node
            if ((root.left == null) && (root.right == null)) { // Case 1 (leaf node)
                root = null;
            } else if ((root.left != null) && (root.right != null)) { // Case 3 (full node)
                Node minRightSubTree = findMin(root.right); // find min from the right subtree
                Node temp = new Node(minRightSubTree.key);
                replace(root, temp);
                root = temp;
                // recursively delete the node from the right subtree
                delete(root.right, minRightSubTree.key);
            } else if (root.left != null) { // Case 2 (single child, left child)
                root = root.left; // promote the left child to the root
            } else { // Case 2 (single child, right child)
                root = root.right; // promote the right child to the root
            }
        } else { // Delete non-root node
            delete(root, key);
        }
    }

    // this function should delete only non-root node
    public static void delete(Node node, int key) {
        if (node == null) {
            System.out.println("Key not found!!!");
        } else if (node.key > key) {
            delete(node.left, key);
        } else if (node.key < key) {
            delete(node.right, key);
        } else if (node.key == key) { // Node to be deleted is found
            if ((node.left == null) && (node.right == null)) { // Case 1(leaf node)
                if (node.key < node.parent.key) {
                    node.parent.left = null; // disown the left child
                } else {
                    node.parent.right = null; // disown the right child
                }
            } else if ((node.left != null) && (node.right != null)) { // Case 3 (full nodes)
                Node minRightSubTree = findMin(node.right); // find min from the right subtree
                Node temp = new Node(minRightSubTree.key);
                replace(node, temp);
                // recursively delete the node start from the right subtree
                delete(node.right, minRightSubTree.key);
            } else {// Case 2 (single child)
                Node childNode;
                if (node.left != null) {  // only the left child
                    childNode = node.left;
                } else { // only the right child
                    childNode = node.right;
                }
                childNode.parent = node.parent;
                if (node.parent.key > node.key) {
                    node.parent.left = childNode;
                } else {
                    node.parent.right = childNode;
                }
            }
        }
    }

    // Replace node1 with a new node2
    // node2 must be created using "new Node(key)" before calling this function
    // This function is optional, you do not have to use it
    public static void replace(Node node1, Node node2) {
        if ((node1.left != null) && (node1.left != node2)) {
            node2.left = node1.left;
            node1.left.parent = node2;
        }
        if ((node1.right != null) && (node1.right != node2)) {
            node2.right = node1.right;
            node1.right.parent = node2;
        }
        if ((node1.parent != null) && (node1.parent != node2)) {
            node2.parent = node1.parent;
            if (node1.parent.key > node1.key) {
                node1.parent.left = node2;
            } else {
                node1.parent.right = node2;
            }
        }
    }


    public static boolean isMergeable(Node r1, Node r2) {
        if (r1 == null || r2 == null) { //when r1 or r2 is empty ,it can merge
            return true;
        }

        if (r1.right == null && r2.left == null) { // when r1 is max value of tree and r2 is min value of tree
            if (r1.key < r2.key) {  //if r1 < r2 it can merge
                return true;
            } else {
                return false;
            }
        } else if (r1.right == null && r2.left != null) { //if r1 cannot go right to get max , and r2 still go left
            return isMergeable(r1, r2.left); //then r2 go left to get minvalue
        } else {
            return isMergeable(r1.right, r2); //otherwise r1 can go right
        }

    }

    public static Node mergeWithRoot(Node r1, Node r2, Node t) {
        if (isMergeable(r1, r2)) { //if r1 and r2 can merge
            t.left = r1;
            t.right = r2;
            if (r1 != null) r1.parent = t;
            if (r2 != null) r2.parent = t;
            return t;
        } else {
            System.out.println("All nodes in T1 must be smaller than all nodes from T2");
            return null;
        }
    }

    public void merge(BSTree tree2) {
        if (isMergeable(this.root, tree2.root)) { //if it can merge

            Node newRoot = new Node(findMax(this.root).key); //create new temporary root with max value of tree
            delete(findMax(this.root).key);
            newRoot.left = this.root;
            newRoot.right = tree2.root;
            root = newRoot;


        } else {
            System.out.println("All nodes in T1 must be smaller than all nodes from T2");
        }

    }

    public NodeList split(int key) {
        return split(this.root, key);
    }

    public static NodeList split(Node r, int key) {
        NodeList list = new NodeList();
        if (r == null) { //if empty , no anything happend
            return list;
        } else if (key < r.key) { //if key < root.key split left side
            list = split(r.left, key);
            Node r3 = mergeWithRoot(list.r2, r.right, r);
            list.r2 = r3;
            return list;
        } else { // key>=root.key // split right side
            list = split(r.right, key);
            Node r4 = mergeWithRoot(r.left, list.r1, r);
            list.r1 = r4;
            return list;
        }
    }
}
