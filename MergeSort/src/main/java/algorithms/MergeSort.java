package algorithms;

public final class MergeSort {

    private MergeSort() {}

    /** Ordena ascendente (estable). */
    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        sort(a, 0, a.length - 1, buf);
    }

    private static void sort(int[] a, int lo, int hi, int[] buf) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid, buf);
        sort(a, mid + 1, hi, buf);
        // Early-exit: ya ordenado el corte
        if (a[mid] <= a[mid + 1]) return;
        merge(a, lo, mid, hi, buf);
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf) {
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            if (a[i] <= a[j]) {
                buf[k++] = a[i++];
            } else {
                buf[k++] = a[j++];
            }
        }
        while (i <= mid) buf[k++] = a[i++];
        while (j <= hi)  buf[k++] = a[j++];
        // copiar de vuelta al original
        System.arraycopy(buf, 0, a, lo, k);
    }
}
