package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> buggy = new BuggyAList<>();
        AListNoResizing<Integer> noResizing = new AListNoResizing<>();
        buggy.addLast(4);
        noResizing.addLast(4);
        buggy.addLast(5);
        noResizing.addLast(5);
        buggy.addLast(6);
        noResizing.addLast(6);
        assertEquals(noResizing.size(), buggy.size());
        assertEquals(noResizing.removeLast(), buggy.removeLast());
        assertEquals(noResizing.removeLast(), buggy.removeLast());
        assertEquals(noResizing.removeLast(), buggy.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L_buggy = new BuggyAList<>();

        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L_buggy.addLast(randVal);
                assertEquals(L.size(), L_buggy.size());
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_buggy = L_buggy.size();
                assertEquals(L.size(), L_buggy.size());
            } else if (operationNumber == 2) {
                // removelast
                int size = L.size();
                if (size == 0) continue;
                int removed = L.removeLast();
                int removed_buggy = L_buggy.removeLast();
                assertEquals(removed, removed_buggy);
            } else if (operationNumber == 3) {
                // getLast
                int size = L.size();
                if (size == 0) continue;
                int last = L.getLast();
                int last_buggy = L_buggy.getLast();
                assertEquals(last, last_buggy);
            }
        }
    }

    public static void main(String[] args) {
//        randomizedTest();
    }
}
