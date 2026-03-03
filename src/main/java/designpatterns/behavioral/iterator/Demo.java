package designpatterns.behavioral.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator Pattern
 *
 * Intent: Provide a way to access the elements of an aggregate object sequentially
 * without exposing its underlying representation.
 *
 * Example: A sorted integer collection that exposes both ascending and descending
 *          iterators without revealing its internal array.
 */

// ── Aggregate ─────────────────────────────────────────────────────────────────

class SortedCollection implements Iterable<Integer> {
    private int[] data;
    private int   size = 0;

    SortedCollection(int capacity) {
        data = new int[capacity];
    }

    void add(int value) {
        if (size == data.length) throw new IllegalStateException("Collection is full");
        data[size++] = value;
        Arrays.sort(data, 0, size);   // keep sorted after each insertion
    }

    public int size() { return size; }

    // ── Ascending iterator (satisfies Iterable → works in for-each) ──────────
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int index = 0;

            public boolean hasNext() { return index < size; }
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                return data[index++];
            }
        };
    }

    // ── Descending iterator ───────────────────────────────────────────────────
    public Iterator<Integer> descendingIterator() {
        return new Iterator<>() {
            private int index = size - 1;

            public boolean hasNext() { return index >= 0; }
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                return data[index--];
            }
        };
    }

    // ── Range iterator ────────────────────────────────────────────────────────
    public Iterator<Integer> rangeIterator(int from, int to) {
        return new Iterator<>() {
            private int index = 0;

            private void advance() {
                while (index < size && data[index] < from) index++;
            }

            { advance(); }  // instance initializer

            public boolean hasNext() { return index < size && data[index] <= to; }
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                return data[index++];
            }
        };
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var col = new SortedCollection(10);
        for (int n : new int[]{5, 2, 8, 1, 9, 3, 7, 4}) {
            col.add(n);
        }

        System.out.print("Ascending  : ");
        for (var n : col) {                         // for-each uses iterator()
            System.out.print(n + " ");
        }

        System.out.print("\nDescending : ");
        var desc = col.descendingIterator();
        while (desc.hasNext()) {
            System.out.print(desc.next() + " ");
        }

        System.out.print("\nRange [3,7]: ");
        var range = col.rangeIterator(3, 7);
        while (range.hasNext()) {
            System.out.print(range.next() + " ");
        }
        System.out.println();
    }
}
