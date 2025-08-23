package algorithms;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class QuickSortTest {

    /** Compara contra Arrays.sort (oráculo) */
    private static void checkAgainstJdk(int[] a) {
        int[] expected = Arrays.copyOf(a, a.length); // snapshot
        Arrays.sort(expected);                        // oráculo
        QuickSort.sort(a);                          // SUT (System Under Test)
        assertArrayEquals(expected, a);               // mismo contenido
    }

    @Test
    void nullIsNoop() {
        assertDoesNotThrow(() -> QuickSort.sort(null));
    }

    @Test
    void emptyArray() {
        checkAgainstJdk(new int[]{});
    }

    @Test
    void singleElement() {
        checkAgainstJdk(new int[]{42});
    }

    @Test
    void alreadySorted() {
        checkAgainstJdk(new int[]{-5, -1, 0, 0, 3, 9});
    }

    @Test
    void reverseSorted() {
        checkAgainstJdk(new int[]{9, 7, 5, 3, 1, 0, -1});
    }

    @Test
    void allEqualValues() {
        int[] a = new int[1000];
        Arrays.fill(a, 123);
        checkAgainstJdk(a);
    }

    @Test
    void manyDuplicates() {
        int[] a = new int[5000];
        Random r = new Random(1);
        for (int i = 0; i < a.length; i++) a[i] = r.nextInt(10); // solo 10 valores
        checkAgainstJdk(a);
    }

    @Test
    void negativesAndRange() {
        int[] a = {-1000, 500, 0, -1, 999, -1000, 500, 1, -2};
        checkAgainstJdk(a);
    }

    @Test
    void zigZagPattern() {
        int n = 2000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? i : -i;
        checkAgainstJdk(a);
    }

    @Test
    void randomizedManySizes() {
        int[] sizes = {0, 1, 2, 3, 10, 100, 1_000, 50_000};
        Random rnd = new Random(12345);
        for (int n : sizes) {
            for (int t = 0; t < 5; t++) {
                int[] a = new int[n];
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(200_001) - 100_000;
                checkAgainstJdk(a);
            }
        }
    }

    @Test
    void idempotent() {
        int[] a = {3, 2, 1, 4, 5, 0};
        QuickSort.sort(a);
        int[] once = Arrays.copyOf(a, a.length);
        QuickSort.sort(a);
        assertArrayEquals(once, a); // sort(sort(a)) == sort(a)
    }
}