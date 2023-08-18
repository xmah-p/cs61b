package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements  Deque<T>, Iterable<T> {
    private class Node {
        T data;
        Node prev, next;

        Node() {}

        Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;
    private int size = 0;

    public LinkedListDeque() {
        head = new Node();
        head.prev = head.next = head;
    }

    private class LLIterator implements Iterator<T> {
        private int pos = 0;
        private Node curr = head.next;

        LLIterator() {}

        LLIterator(int pos) {
            checkValidIndex(pos);
            this.pos = pos;
            curr = getNode(pos);
        }

        @Override
        public boolean hasNext() {
            return pos >= 0 && pos < size;
        }

        @Override
        public T next() {
            curr = curr.next;
            ++pos;
            return curr.prev.data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LLIterator();
    }

    private void checkValidIndex(int index) {
        if (index >= 0 && index < size) return;
        throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for deque of size " + size);
    }

    private Node getNode(int index) {
        checkValidIndex(index);
        Node p = head;
        if (index + index < size) {
            while (index-- > -1) p = p.next;
        } else {
            while (index++ < size) p = p.prev;
        }
        return p;
    }

    private Node getNodeRecursive(int index) {
        checkValidIndex(index);
        if (index == 0) return head.next;
        if (index == size - 1) return head.prev;
        if (index + index < size)
            return getNodeRecursive(index - 1).next;
        else return getNodeRecursive(index + 1).prev;
    }

    @Override
    public void addFirst(T item) {
        ++size;
        Node q = new Node(item, head, head.next);
        head.next.prev = head.next = q;
    }

    @Override
    public void addLast(T item) {
        ++size;
        Node q = new Node(item, head.prev, head);
        head.prev.next = head.prev = q;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (Node p = head.next; p != head; p = p.next) {
            System.out.print(p.data + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) return null;  // throw new NoSuchElementException("Empty deque");
        --size;
        Node deleted = head.next;
        Node q = deleted.next;
        head.next = q;
        q.prev = head;
        deleted.prev = deleted.next = null;
        return deleted.data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        --size;
        Node deleted = head.prev;
        Node q = deleted.prev;
        q.next = head;
        head.prev = q;
        deleted.prev = deleted.next = null;
        return deleted.data;
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    public T getRecursive(int index) {
        return getNodeRecursive(index).data;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedListDeque)) return false;
        LinkedListDeque<T> od = (LinkedListDeque<T>) o;
        if (size != od.size()) return false;
        Node q = od.head.next;
        for (Node p = head.next; p != head && q != od.head; p = p.next, q = q.next) {
            if (!p.data.equals(q.data)) return false;
        }
        return true;
    }
}
