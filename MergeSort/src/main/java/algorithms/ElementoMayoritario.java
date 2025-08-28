package algorithms;

import java.util.OptionalInt;

public class ElementoMayoritario {

    public static OptionalInt findMajority(int[] a) {
        if (a == null || a.length == 0) return OptionalInt.empty();
        int cand = solve(a, 0, a.length - 1);
        if (cand == Integer.MIN_VALUE) return OptionalInt.empty();

        int occ = countInRange(a, 0, a.length - 1, cand);
        return (occ > a.length / 2) ? OptionalInt.of(cand) : OptionalInt.empty();
    }

    // Devuelve el candidato mayoritario en [l..r] si existe, o Integer.MIN_VALUE si no.
    private static int solve(int[] a, int l, int r) {
        if (l == r) return a[l];

        int m = (l + r) >>> 1; // división por 2
        int left = solve(a, l, m);
        int right = solve(a, m + 1, r);

        if (left == right) return left;

        int leftCount = (left == Integer.MIN_VALUE) ? 0 : countInRange(a, l, r, left);
        int rightCount = (right == Integer.MIN_VALUE) ? 0 : countInRange(a, l, r, right);
        int len = r - l + 1;

        if (leftCount > len / 2) return left;
        if (rightCount > len / 2) return right;
        return Integer.MIN_VALUE;
    }

    private static int countInRange(int[] a, int l, int r, int val) {
        if (val == Integer.MIN_VALUE) return 0;
        int c = 0;
        for (int i = l; i <= r; i++) if (a[i] == val) c++;
        return c;
    }

    // Demo rápida
    public static void main(String[] args) {
        int[] arr1 = {2,2,1,1,1,2,2};        // mayoritario = 2
        int[] arr2 = {3,3,4};                 // mayoritario = 3
        int[] arr3 = {1,1,1,2,3,1,4,1,1};     // mayoritario = 1

        System.out.println(findMajority(arr1)); // OptionalInt[2]
        System.out.println(findMajority(arr2)); // OptionalInt[3]
        System.out.println(findMajority(arr3)); // OptionalInt[1]
    }
}
