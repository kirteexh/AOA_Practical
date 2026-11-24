import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BPlusTree {

    static abstract class Node {
        boolean isLeaf;
        int maxKeys;

        Node(boolean isLeaf, int maxKeys) {
            this.isLeaf = isLeaf;
            this.maxKeys = maxKeys;
        }

        abstract boolean isOverflow();
    }

    static class InternalNode extends Node {
        List<Integer> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        InternalNode(int maxKeys) {
            super(false, maxKeys);
        }

        @Override
        boolean isOverflow() {
            return keys.size() > maxKeys;
        }
    }

    static class LeafNode extends Node {
        List<Integer> keys = new ArrayList<>();
        LeafNode next = null;

        LeafNode(int maxKeys) {
            super(true, maxKeys);
        }

        @Override
        boolean isOverflow() {
            return keys.size() > maxKeys;
        }
    }

    private Node root;
    private final int maxKeys;

    public BPlusTree(int order) {
        this.maxKeys = order - 1;
        this.root = new LeafNode(maxKeys);
    }

    public boolean search(int key) {
        LeafNode leaf = findLeafNode(key);
        return leaf.keys.contains(key);
    }

    private LeafNode findLeafNode(int key) {
        Node curr = root;
        while (!curr.isLeaf) {
            InternalNode internal = (InternalNode) curr;
            int idx = 0;
            while (idx < internal.keys.size() && key >= internal.keys.get(idx)) {
                idx++;
            }
            curr = internal.children.get(idx);
        }
        return (LeafNode) curr;
    }

    public void insert(int key) {
        SplitResult res = insertRec(root, key);
        if (res != null) {
            InternalNode newRoot = new InternalNode(maxKeys);
            newRoot.keys.add(res.key);
            newRoot.children.add(res.left);
            newRoot.children.add(res.right);
            root = newRoot;
        }
    }

    static class SplitResult {
        int key;
        Node left, right;
        SplitResult(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    private SplitResult insertRec(Node curr, int key) {
        if (curr.isLeaf) {
            LeafNode leaf = (LeafNode) curr;
            int pos = Collections.binarySearch(leaf.keys, key);
            if (pos >= 0) return null;
            leaf.keys.add(-(pos + 1), key);
            if (leaf.isOverflow()) {
                LeafNode right = new LeafNode(maxKeys);
                int mid = leaf.keys.size() / 2;
                right.keys.addAll(leaf.keys.subList(mid, leaf.keys.size()));
                leaf.keys.subList(mid, leaf.keys.size()).clear();
                right.next = leaf.next;
                leaf.next = right;
                return new SplitResult(right.keys.get(0), leaf, right);
            }
            return null;
        } else {
            InternalNode internal = (InternalNode) curr;
            int idx = 0;
            while (idx < internal.keys.size() && key >= internal.keys.get(idx)) {
                idx++;
            }
            SplitResult split = insertRec(internal.children.get(idx), key);
            if (split != null) {
                internal.keys.add(idx, split.key);
                internal.children.set(idx, split.left);
                internal.children.add(idx + 1, split.right);
                if (internal.isOverflow()) {
                    InternalNode right = new InternalNode(maxKeys);
                    int mid = internal.keys.size() / 2;
                    int promo = internal.keys.get(mid);
                    right.keys.addAll(internal.keys.subList(mid + 1, internal.keys.size()));
                    right.children.addAll(internal.children.subList(mid + 1, internal.children.size()));
                    internal.keys.subList(mid, internal.keys.size()).clear();
                    internal.children.subList(mid + 1, internal.children.size()).clear();
                    return new SplitResult(promo, internal, right);
                }
            }
            return null;
        }
    }

    public void printLeaves() {
        Node curr = root;
        while (!curr.isLeaf) {
            curr = ((InternalNode) curr).children.get(0);
        }
        LeafNode leaf = (LeafNode) curr;
        while (leaf != null) {
            for (int k : leaf.keys) {
                System.out.print(k + " ");
            }
            leaf = leaf.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order of B+ Tree: ");
        int order = sc.hasNextInt() ? sc.nextInt() : 4;
        BPlusTree tree = new BPlusTree(order);

        System.out.print("Enter number of keys to insert: ");
        int n = sc.hasNextInt() ? sc.nextInt() : 0;
        if (n > 0) {
            System.out.println("Enter " + n + " keys:");
            for (int i = 0; i < n; i++) {
                tree.insert(sc.nextInt());
            }
        } else {
            int[] keys = {5, 15, 25, 35, 45, 55, 40, 30, 20};
            for (int k : keys) {
                tree.insert(k);
            }
        }

        System.out.print("Leaf Traversal: ");
        tree.printLeaves();

        System.out.print("Enter key to search: ");
        int target = sc.hasNextInt() ? sc.nextInt() : 25;
        System.out.println("Key " + target + (tree.search(target) ? " Found" : " Not Found"));
        sc.close();
    }
}
