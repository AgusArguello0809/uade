package algorithms;
import java.util.function.LongToDoubleFunction;

public class EjercicioPropuesto1DyC {

    // Precondición: existe algún i >= 1 tal que f(i) <= 0.
    public static long firstNonPositive(LongToDoubleFunction f) {
        long L = 0;     // centinela (no evaluamos f(0))
        long R = 1;
        double vR = f.applyAsDouble(R);

        // Fase exponencial: duplicar R hasta encontrar f(R) <= 0
        while (vR > 0.0) {
            L = R;
            if (R > Long.MAX_VALUE / 2) {
                throw new IllegalStateException("No se encontró i con f(i) <= 0 (o overflow).");
            }
            R <<= 1; // R *= 2
            vR = f.applyAsDouble(R);
        }

        // Ahora: f(L) > 0 y f(R) <= 0, solución en (L, R]
        // Búsqueda binaria del primer i con f(i) <= 0
        while (L + 1 < R) {
            long mid = L + ((R - L) >>> 1); // evita overflow
            double vm = f.applyAsDouble(mid);
            if (vm <= 0.0) {
                R = mid;
            } else {
                L = mid;
            }
        }
        return R; // primer índice con f(i) <= 0
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        LongToDoubleFunction f1 = i -> 10.0 - 3.0 * i; // f(1)=7, f(4)=-2 -> n=4
        System.out.println(firstNonPositive(f1)); // imprime 4

        LongToDoubleFunction f2 = i -> -i * i; // ya es <=0 desde i=1 -> n=1
        System.out.println(firstNonPositive(f2)); // imprime 1
    }
}
