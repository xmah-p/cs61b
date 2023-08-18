package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private T[] arr;
    private int size = 0;
    private int capacity = 8;
    private Index bg, ed;

    public ArrayDeque() {
        arr = (T[]) new Object[8];
        bg = new Index(0);
        ed = new Index(0);
    }

    // For arrays of length 16 or more, usage factor should always be at least 25%
    private void reserve(int cap) {
        T[] old = arr;
        arr = (T[]) new Object[cap];
        int j = 0;
        for (Index i = new Index(bg.get()); j < size; i.inc()) {
            arr[j++] = old[i.get()];
        }
        bg = new Index(0);
        ed = new Index(size);
    }

    @Override
    public void addFirst(T item) {
        if (size * 2 > capacity) {
            reserve(capacity * 2);
            capacity *= 2;
        }
        size++;
        arr[bg.dec()] = item;
    }

    @Override
    public void addLast(T item) {
        if (size * 2 > capacity) {
            reserve(capacity * 2);
            capacity *= 2;
        }
        size++;
        arr[ed.get()] = item;
        ed.inc();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T t : this) {
            System.out.print(t);
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        if (capacity >= 16 && size * 4 < capacity) {
            reserve(capacity / 2);
            capacity /= 2;
        }
        size--;
        T deleted = arr[bg.get()];
        arr[bg.get()] = null;
        bg.inc();
        return deleted;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        if (capacity >= 16 && size * 4 < capacity) {
            reserve(capacity / 2);
            capacity /= 2;
        }
        size--;
        ed.dec();
        T deleted = arr[ed.get()];
        arr[ed.get()] = null;
        return deleted;
    }

    @Override
    public T get(int index) {
        checkValidIndex(index);
        return arr[new Index(index + bg.get()).get()];
    }

    private void checkValidIndex(int index) {
        if (index >= 0 && index < size) return;
        throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for deque of size " + size);
    }

    @Override
    public Iterator<T> iterator() {
        return new ADIterator();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deque)) return false;
        Deque od = (Deque) o;
        if (size != od.size()) return false;
        for (int i = 0; i != size; ++i) {
            if (!get(i).equals(od.get(i))) return false;
        }
        return true;
    }

    // Index of arr, ModCapacityResidueClass
    private class Index {
        int n;

        public Index(int i) {
            n = i % capacity;
        }

        public int get() {
            return n;
        }

        public int inc() {
            n++;
            if (n == capacity) n = 0;
            return n;
        }

        public int dec() {
            n--;
            if (n < 0) n = capacity - 1;
            return n;
        }

        public int add(int rhs) {
            int t = n + rhs;
            if (t >= capacity) t -= capacity;
            return t;
        }
    }

    private class ADIterator implements Iterator<T> {
        int pos = bg.get();
        Index cur = new Index(pos);

        ADIterator() {
        }

        @Override
        public boolean hasNext() {
            return pos >= bg.get() && pos < ed.get() + size;
        }

        @Override
        public T next() {
            T ret = arr[cur.get()];
            pos = cur.inc();
            return ret;
        }
    }
}
