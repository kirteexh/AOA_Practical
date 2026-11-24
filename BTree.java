import java.util.Scanner;

public class BTree {

    static class BTreeNode {
        int[] keys;
        int t;
        BTreeNode[] children;
        int n;
        boolean isLeaf;

        public BTreeNode(int t, boolean isLeaf) {
            this.t = t;
            this.isLeaf = isLeaf;
            this.keys = new int[2 * t - 1];
            this.children = new BTreeNode[2 * t];
            this.n = 0;
        }

        public void traverse() {
            int i;
            for (i = 0; i < n; i++) {
                if (!isLeaf && children[i] != null) {
                    children[i].traverse();
                }
                System.out.print(keys[i] + " ");
            }
            if (!isLeaf && children[i] != null) {
                children[i].traverse();
            }
        }

        public BTreeNode search(int key) {
            int i = 0;
            while (i < n && key > keys[i]) {
                i++;
            }
            if (i < n && keys[i] == key) {
                return this;
            }
            if (isLeaf) {
                return null;
            }
            return children[i].search(key);
        }

        public void insertNonFull(int key) {
            int i = n - 1;
            if (isLeaf) {
                while (i >= 0 && keys[i] > key) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                keys[i + 1] = key;
                n++;
            } else {
                while (i >= 0 && keys[i] > key) {
                    i--;
                }
                i++;
                if (children[i].n == 2 * t - 1) {
                    splitChild(i, children[i]);
                    if (keys[i] < key) {
                        i++;
                    }
                }
                children[i].insertNonFull(key);
            }
        }

        public void splitChild(int i, BTreeNode y) {
            BTreeNode z = new BTreeNode(y.t, y.isLeaf);
            z.n = t - 1;

            for (int j = 0; j < t - 1; j++) {
                z.keys[j] = y.keys[j + t];
            }
            if (!y.isLeaf) {
                for (int j = 0; j < t; j++) {
                    z.children[j] = y.children[j + t];
                }
            }
            y.n = t - 1;

            for (int j = n; j >= i + 1; j--) {
                children[j + 1] = children[j];
            }
            children[i + 1] = z;

            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }
            keys[i] = y.keys[t - 1];
            n++;
        }
    }

    BTreeNode root;
    int t;

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
        }
        System.out.println();
    }

    public boolean search(int key) {
        return root != null && root.search(key) != null;
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                s.splitChild(0, root);
                int i = 0;
                if (s.keys[0] < key) {
                    i++;
                }
                s.children[i].insertNonFull(key);
                root = s;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter minimum degree t: ");
        int t = sc.hasNextInt() ? sc.nextInt() : 3;
        BTree tree = new BTree(t);

        System.out.print("Enter number of keys to insert: ");
        int n = sc.hasNextInt() ? sc.nextInt() : 0;
        if (n > 0) {
            System.out.println("Enter " + n + " keys:");
            for (int i = 0; i < n; i++) {
                tree.insert(sc.nextInt());
            }
        } else {
            int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
            for (int k : keys) {
                tree.insert(k);
            }
        }

        System.out.print("Inorder Traversal: ");
        tree.traverse();

        System.out.print("Enter key to search: ");
        int target = sc.hasNextInt() ? sc.nextInt() : 6;
        System.out.println("Key " + target + (tree.search(target) ? " Found" : " Not Found"));
        sc.close();
    }
}
