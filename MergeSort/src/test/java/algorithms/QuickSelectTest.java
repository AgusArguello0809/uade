package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class QuickSelectTest {

    // --- Helpers ---

    private static int[] sortedCopy(int[] a) {
        int[] c = Arrays.copyOf(a, a.length);
        Arrays.sort(c);
        return c;
    }

    /**
     * Verifica para un arreglo dado que select(a, k) devuelve el valor correcto
     * (comparando contra Arrays.sort) y que cumple la propiedad de rango:
     * #(< val) <= k < #(< = val).
     */
    private static void checkAllKs(int[] base) {
        int[] expectedSorted = sortedCopy(base);
        for (int k = 0; k < base.length; k++) {
            int[] a = Arrays.copyOf(base, base.length);
            int val = QuickSelect.select(a, k);

            // 1) Igual al oráculo
            assertEquals(expectedSorted[k], val, "k=" + k);

            // 2) Propiedad de rango (no exige que el array quede ordenado)
            int lt = 0, le = 0;
            for (int x : a) {
                if (x < val) lt++;
                if (x <= val) le++;
            }
            assertTrue(lt <= k && k < le, "rank property failed for k=" + k);
        }
    }

    // --- Tests ---

    @Test
    void nullArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(null, 0));
    }

    @Test
    void emptyArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0));
    }

    @Test
    void kOutOfRangeThrows() {
        int[] a = {1,2,3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, -1));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, 3));
    }

    @Test
    void singleElement() {
        int[] a = {42};
        assertEquals(42, QuickSelect.select(a, 0));
    }

    @Test
    void smallFixedArrays() {
        checkAllKs(new int[]{3, 1});
        checkAllKs(new int[]{3, 1, 2});
        checkAllKs(new int[]{5, -1, 5, 0});
        checkAllKs(new int[]{9, 8, 7, 6, 5});
    }

    @Test
    void allEqualValues() {
        int[] a = new int[1000];
        Arrays.fill(a, 7);
        checkAllKs(a);
    }

    @Test
    void manyDuplicates() {
        int[] a = new int[5000];
        Random r = new Random(1);
        for (int i = 0; i < a.length; i++) a[i] = r.nextInt(10); // solo 10 valores
        checkAllKs(a);
    }

    @Test
    void negativesAndRange() {
        checkAllKs(new int[]{-5, 0, 100, -5, 7, -1, 7, 7, 2});
    }

    @Test
    void randomizedVariousSizes() {
        Random rnd = new Random(12345);
        int[] sizes = {1, 2, 3, 5, 10, 100, 5_000};
        for (int n : sizes) {
            for (int t = 0; t < 5; t++) {
                int[] a = new int[n];
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(200_001) - 100_000;
                checkAllKs(a);
            }
        }
    }
}
