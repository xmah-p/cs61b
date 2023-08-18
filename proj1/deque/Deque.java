package deque;

public interface Deque<T> {
    // assume item is never null
    void addFirst(T item);

    void addLast(T item);

    default boolean isEmpty() {
        return size() == 0;
    }

    int size();

    // separated by space, end with a new line
    void printDeque();

    // for these 3 methods, if no such item exists, return null
    T removeFirst();

    T removeLast();

    T get(int index);
}
