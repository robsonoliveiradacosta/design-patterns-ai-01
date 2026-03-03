package designpatterns.behavioral.strategy;

import java.util.Arrays;

/**
 * Strategy Pattern
 *
 * Intent: Define a family of algorithms, encapsulate each one, and make them
 * interchangeable. Strategy lets the algorithm vary independently from clients
 * that use it.
 *
 * Example: Sorter that can swap sorting algorithms at runtime.
 * Java 25: @FunctionalInterface allows lambda strategies alongside class-based ones.
 */

// ── Strategy (functional interface — enables lambda usage) ────────────────────

@FunctionalInterface
interface SortStrategy {
    void sort(int[] array);

    default String name() { return "Custom Strategy"; }
}

// ── Concrete Strategies ───────────────────────────────────────────────────────

class BubbleSort implements SortStrategy {
    public String name() { return "Bubble Sort  O(n²)"; }

    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = tmp;
                }
            }
        }
    }
}

class SelectionSort implements SortStrategy {
    public String name() { return "Selection Sort O(n²)"; }

    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            int tmp = arr[minIdx]; arr[minIdx] = arr[i]; arr[i] = tmp;
        }
    }
}

class QuickSort implements SortStrategy {
    public String name() { return "Quick Sort   O(n log n)"; }

    public void sort(int[] arr) { quickSort(arr, 0, arr.length - 1); }

    private void quickSort(int[] arr, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = arr[hi], i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (arr[j] <= pivot) { i++; int t = arr[i]; arr[i] = arr[j]; arr[j] = t; }
        }
        int t = arr[i + 1]; arr[i + 1] = arr[hi]; arr[hi] = t;
        int p = i + 1;
        quickSort(arr, lo, p - 1);
        quickSort(arr, p + 1, hi);
    }
}

// ── Context ───────────────────────────────────────────────────────────────────

class Sorter {
    private SortStrategy strategy;

    Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    /** Sort a copy of the array, leaving the original untouched. */
    public int[] sort(int[] array) {
        var copy = Arrays.copyOf(array, array.length);
        strategy.sort(copy);
        return copy;
    }

    public String strategyName() { return strategy.name(); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        int[] data = {64, 34, 25, 12, 22, 11, 90, 47};
        System.out.println("Original      : " + Arrays.toString(data));
        System.out.println();

        var sorter = new Sorter(new BubbleSort());
        System.out.println(sorter.strategyName() + " : " + Arrays.toString(sorter.sort(data)));

        sorter.setStrategy(new SelectionSort());
        System.out.println(sorter.strategyName() + " : " + Arrays.toString(sorter.sort(data)));

        sorter.setStrategy(new QuickSort());
        System.out.println(sorter.strategyName() + " : " + Arrays.toString(sorter.sort(data)));

        // Lambda strategy — uses the built-in Arrays.sort (TimSort)
        sorter.setStrategy(arr -> Arrays.sort(arr));
        System.out.println("Built-in sort        : " + Arrays.toString(sorter.sort(data)));
    }
}
