package algorithms;

import java.util.concurrent.ThreadLocalRandom;

public final class QuickSelect {

    private QuickSelect() {}

    /** Devuelve el k-ésimo menor (k es 0-index). Modifica el arreglo. */
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("Arreglo vacío");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k fuera de rango");
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int p = lomutoPartitionRandom(a, lo, hi);
            if (k == p) return a[p];
            if (k < p)  hi = p - 1;
            else        lo = p + 1;
        }
        throw new IllegalStateException("No debería suceder");
    }

    /** Partición de Lomuto con pivote aleatorio. Retorna índice final del pivote. */
    private static int lomutoPartitionRandom(int[] a, int lo, int hi) {
        int r = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        swap(a, r, hi);                 // usar a[hi] como pivote
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
