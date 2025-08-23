package algorithms;

public class MaxPartialSum {

    public static final class Result {
        public final int start;   // índice de inicio (inclusive)
        public final int end;     // índice de fin (inclusive)
        public final long sum;    // suma del subarreglo

        public Result(int start, int end, long sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "Result{start=" + start + ", end=" + end + ", sum=" + sum + "}";
        }
    }

    public static Result maxPartialSum(int[] a) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("array vacío");
        return mps(a, 0, a.length - 1);
    }

    private static Result mps(int[] a, int ini, int fin) {
        if (ini == fin) {
            return new Result(ini, fin, a[ini]);
        }
        int mid = ini + (fin - ini) / 2;

        Result izq = mps(a, ini, mid);
        Result der = mps(a, mid + 1, fin);
        Result mix = cruzandoCentro(a, ini, mid, fin);

        return max(izq, der, mix);
    }

    // Calcula el mejor subarreglo que cruza el centro:
    // mejor suffix en [ini..mid] + mejor prefix en [mid+1..fin]
    private static Result cruzandoCentro(int[] a, int ini, int mid, int fin) {
        long mejorSumIzq = Long.MIN_VALUE;
        long suma = 0;
        int mejorIni = mid;

        for (int i = mid; i >= ini; i--) {
            suma += a[i];
            if (suma > mejorSumIzq) {
                mejorSumIzq = suma;
                mejorIni = i;
            }
        }

        long mejorSumDer = Long.MIN_VALUE;
        suma = 0;
        int mejorFin = mid + 1;

        for (int j = mid + 1; j <= fin; j++) {
            suma += a[j];
            if (suma > mejorSumDer) {
                mejorSumDer = suma;
                mejorFin = j;
            }
        }

        return new Result(mejorIni, mejorFin, mejorSumIzq + mejorSumDer);
    }

    private static Result max(Result a, Result b, Result c) {
        Result best = a;
        if (b.sum > best.sum) best = b;
        if (c.sum > best.sum) best = c;
        return best;
    }

    // Pequeña demo
    public static void main(String[] args) {
        // 1) Máximo en la IZQUIERDA (totalmente en [ini..mid])
        int[] casoIzq = {4, -1, 2, 1,  -5, -2, -3, -4}; // mid=3
        Result r1 = maxPartialSum(casoIzq);
        System.out.println("IZQ → " + r1 + "  // esperado: start=0, end=3, sum=6 ([4,-1,2,1])");

        // 2) Máximo en la DERECHA (totalmente en [mid+1..fin])
        int[] casoDer = {-5, -2, -3, -1,   3, -1, 4, 2}; // mid=3, mejor [4..7] = 8
        Result r2 = maxPartialSum(casoDer);
        System.out.println("DER → " + r2 + "  // esperado: start=4, end=7, sum=8 ([3,-1,4,2])");

        // 3) Máximo CRUZANDO el centro (parte izq + parte der)
        int[] casoMix = {-2, 1, -3, 4, -1, 2, 1, -5, 4}; // mid=4, mejor [3..6] = 6
        Result r3 = maxPartialSum(casoMix);
        System.out.println("MIX → " + r3 + "  // esperado: start=3, end=6, sum=6 ([4,-1,2,1])");
    }
}
