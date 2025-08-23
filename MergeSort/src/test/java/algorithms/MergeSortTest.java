package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;


public class MergeSortTest {

    private static void checkAgainstJdk(int[] a) {
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);           // arreglo esperado
        MergeSort.sort(a);             // tu implementación
        assertArrayEquals(expected, a);  // deben coincidir
    }

    @Test
    void nullIsNoop() {
        assertDoesNotThrow(() -> MergeSort.sort(null));
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
        int[] a = {1,2,3,4,5,6,7,8,9};
        checkAgainstJdk(a);
    }

    @Test
    void reverseSorted() {
        int[] a = {9,8,7,6,5,4,3,2,1,0};
        checkAgainstJdk(a);
    }

    @Test
    void negativesAndDuplicates() {
        int[] a = {5, -1, 3, 3, 0, -1, 2, 2, 2, 10, -5};
        checkAgainstJdk(a);
    }

    @Test
    void randomizedSmallMedium() {
        Random rnd = new Random(123456);
        int[] sizes = {0,1,2,3,5,10,30,100,1000};
        for (int n : sizes) {
            for (int t = 0; t < 50; t++) {
                int[] a = new int[n];
                for (int i = 0; i < n; i++) {
                    // rango con duplicados y negativos
                    a[i] = rnd.nextInt(2001) - 1000;
                }
                checkAgainstJdk(a);
            }
        }
    }
}
