package algorithms;

public final class QuickSort {

    private QuickSort() {}

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = hoarePartition(a, lo, hi);
            // Recurro primero el lado más chico (limita profundidad a ~log n)
            if (p - lo < hi - (p + 1)) {
                quickSort(a, lo, p);
                lo = p + 1;          // tail-call elimination del lado grande
            } else {
                quickSort(a, p + 1, hi);
                hi = p;              // tail-call elimination del otro lado
            }
        }
    }

    // Hoare partition con pivote = mediana de tres (valor)
    private static int hoarePartition(int[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        int pivot = medianOfThreeValue(a[lo], a[mid], a[hi]);

        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do { i++; } while (a[i] < pivot);
            do { j--; } while (a[j] > pivot);
            if (i >= j) return j;
            swap(a, i, j);
        }
    }

    // Devuelve la mediana (por valor) de tres enteros
    private static int medianOfThreeValue(int x, int y, int z) {
        if (x < y) {
            if (y < z) return y;       // x < y < z
            return (x < z) ? z : x;    // x < z <= y  o  z <= x < y
        } else { // x >= y
            if (x < z) return x;       // y <= x < z
            return (y < z) ? z : y;    // y < z <= x  o  z <= y <= x
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
