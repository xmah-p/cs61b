package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private final Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> comp) {
        this.comp = comp;
    }

    public T max() {
        if (isEmpty()) return null;
        T max_val = this.get(0);
        for (T t : this) {
            if (comp.compare(max_val, t) < 0) max_val = t;
        }
        return max_val;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) return null;
        T max_val = this.get(0);
        for (T t : this)
            if (c.compare(max_val, t) < 0) max_val = t;
        return max_val;
    }

//    private static class IntComparator implements Comparator<Integer> {
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            return o1 - o2;
//        }
//    }
//
//    public static Comparator getIntComp() {
//        return new IntComparator();
//    }
}
