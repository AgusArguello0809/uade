package algorithms;

public class OrdenadoEstricto {
    public static boolean ordenado(int [] a, int fin) {
        if (fin == 0) {
            return true;
        }
        if (a[fin - 1] < a[fin]) {
            return ordenado(a, fin - 1);
        } else {
            return false;
        }
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        int[] a1 = new int[]{0, 1, 2, 3, 4};
        boolean resA1 = ordenado(a1, a1.length - 1);
        System.out.println(resA1);
        int[] a2 = new int[]{5, 2};
        boolean resA2 = ordenado(a2, a2.length - 1);
        System.out.println(resA2);
        int [] a3 = new int[]{1};
        boolean resA3 = ordenado(a3, a3.length - 1);
        System.out.println(resA3);
    }
}
