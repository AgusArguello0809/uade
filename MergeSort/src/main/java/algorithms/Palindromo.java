package algorithms;

public class Palindromo {
    public static boolean isPalindrome(int[] a) {
        return isPalindrome(a, 0, a.length - 1);
    }

    private static boolean isPalindrome(int[] a, int l, int r) {
        if (l >= r) return true;              // caso base
        if (a[l] != a[r]) return false;       // fallo
        return isPalindrome(a, l + 1, r - 1); // recursión
    }

    // Demo
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 2, 1};   // true
        int[] arr2 = {1, 2, 3, 4, 5};   // false
        int[] arr3 = {7, 7, 7, 7};      // true
        int[] arr4 = {1};               // true

        System.out.println(isPalindrome(arr1));
        System.out.println(isPalindrome(arr2));
        System.out.println(isPalindrome(arr3));
        System.out.println(isPalindrome(arr4));
    }
}
