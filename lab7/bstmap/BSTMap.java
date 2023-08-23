package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    Node root;

    public BSTMap() {

    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(Node n, K key) {
        if (n == null) return false;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) return containsKey(n.left, key);
        else if (cmp > 0) return containsKey(n.right, key);
        return true;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) return get(n.left, key);
        if (cmp > 0) return get(n.right, key);
        return n.val;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        return n == null ? 0 : n.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {
        if (n == null) return new Node(key, value, 1);
        int cmp = key.compareTo(n.key);
        if (cmp < 0) n.left = put(n.left, key, value);
        else if (cmp > 0) n.right = put(n.right, key, value);
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node n) {
        if (n == null) return;
        System.out.print("{ " + n.key + ", " + n.val + " }");
        printInOrder(n.left);
        printInOrder(n.right);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySet(root, set);
        return set;
    }

    private void keySet(Node n, Set<K> set) {
        if (n == null) return;
        set.add(n.key);
        keySet(n.left, set);
        keySet(n.right, set);
    }

    @Override
    public V remove(K key) {
        V ret = get(key);
        root = remove(root, key);
        return ret;
    }

    @Override
    public V remove(K key, V value) {
        V ret = get(key);
        if (ret != value) return null;
        root = remove(root, key);
        return ret;
    }

    private Node remove(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) n.left = remove(n.left, key);
        else if (cmp > 0) n.right = remove(n.right, key);
        else {
            if (n.left == null) return n.right;
            if (n.right == null) return n.left;

            Node t = n.right;
            while (t.left != null) {
                t = t.left;
            }
            n.key = t.key;
            n.val = t.val;
            n.right = remove(n.right, t.key);
        }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        K key;
        V val;
        int size;

        Node left, right;

        Node(K k, V v, int sz) {
            key = k;
            val = v;
            size = sz;
            left = right = null;
        }
    }
}
