package algorithms;


public class IndiceFijo {

    public static int indiceFijo(int[] a, int ini, int fin) {
        if(ini <= fin) {
            int mid = (ini + fin) / 2;
            System.out.println("ini es " + ini + " fin es " + fin);
            System.out.println("mid es " +  mid + " a[mid] es " + a[mid]);
            int k = a[mid] - mid;
            System.out.println("La dif entre a[mid] y mid es " + k);
            if (k == 0) {
                System.out.println("lo encontre a " + mid);
                return mid;
            } else if (k > 0) {
                System.out.println("voy a izq");
                return indiceFijo(a, ini, mid - 1); // No considero mid pq ya lo revisé
            } else {
                System.out.println("voy a der");
                return indiceFijo(a, mid + 1, fin);
            }
        }
        return -1;
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        int[] a1 = new int[]{-3, -1, 0, 3, 5, 7, 9}; // a1.length = 7, 7 - 1 = 6
        int resA1 = indiceFijo(a1, 0, a1.length - 1); // a1, 0, 6
        System.out.println(resA1); // 3
        int[] a2 = new int[]{-2, -1, 1, 2, 4, 6}; // a2.length = 6, 6 - 1 = 5
        int resA2 = indiceFijo(a2, 0, a2.length - 1); // a2, 0, 5
        System.out.println(resA2); // 4
        int[] a3 = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 13}; // a3.length = 9, 9 - 1 = 8
        int resA3 = indiceFijo(a3, 0, a3.length - 1); // a3, 0, 8
        System.out.println(resA3); // -1
    }
}
