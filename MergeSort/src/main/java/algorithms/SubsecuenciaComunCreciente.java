package algorithms;

public class SubsecuenciaComunCreciente {

    public static class Resultado {
        private int l;
        private int[] z;

        public Resultado(int l, int[] z) {
            this.l = l;
            this.z = z;
        }

        private String printZ(int[] z) {
            StringBuilder stringZ = new StringBuilder();
            for(int i = 0; i < z.length; i++) {
                stringZ.append(z[i] + " ");
            }
            return stringZ.toString();
        }


        @Override
        public String toString() {
            return "Longitud = " + l + " Subscuencia = " + printZ(z);
        }
    }

    public static Resultado LIS(int[] x) {
        int[][] d = initializeMatrix(2, x.length);
        for (int i = 0; i < x.length; i++) {
            d[0][i] = 1;
            d[1][i] = -1;
        }
        for (int i = 1; i < x.length; i++) {
            for(int j = 0; j < i; j++) {
                if(x[j] <= x[i] && d[0][j] + 1 >= d[0][i]) {
                    d[0][i] = d[0][j] + 1;
                    d[1][i] = j;
                }
            }
        }
        int inx = max_i(d);
        int l = d[0][inx];
        int[] z = initializeVector(l);
        z[l - 1] = x[inx];
        for(int i = l - 2; i >= 0; i--) {
            z[i] = x[d[1][inx]];
            inx = d[1][inx];
        }
        return new Resultado(l, z);
    }

    private static int max_i(int[][] d) {
        int inx = 0;
        int max = 0;
        for(int i = 0; i < d[0].length; i++) {
            if(d[0][i] > max) {
                max = d[0][i];
                inx = i;
            }
        }
        return inx;
    }

    private static int[] initializeVector(int l) {
        return new int[l];
    }

    public static int[][] initializeMatrix(int filas, int columnas) {
        if (filas < 0 || columnas < 0) {
            throw new IllegalArgumentException("filas y columnas deben ser >= 0");
        }
        return new int[filas][columnas]; // ya viene en 0
    }

    // Ejemplo de uso
    public static void main (String[] args) {
        int[] x_test = new int[]{0,4,12,2,10,6,9,13,3,11,7,15};
        Resultado res = LIS(x_test);
        System.out.println(res); // El resultado debería ser: l = 6, z = [0, 2, 6, 9, 11, 15]
    }
}
