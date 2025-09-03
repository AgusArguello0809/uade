package algorithms;
import java.util.*;

public class EjercicioPropuesto1PD {

    public static class Result {
        public final int minCost;
        public final List<Integer> path; // puestos visitados: [1, ..., n]

        public Result(int minCost, List<Integer> path) {
            this.minCost = minCost;
            this.path = path;
        }

        @Override public String toString() {
            return "minCost=" + minCost + ", path=" + path;
        }
    }

    /**
     * Calcula el costo mínimo para ir del puesto 1 al puesto n.
     *
     * @param f matriz 1-indexada de costos; usar f[i][j] solo si i<j.
     *          Si un tramo no existe, insertar f[i][j] = INF (o un valor muy grande).
     * @return Result con costo mínimo y ruta (lista de puestos).
     *
     * Complejidad: O(n^2) tiempo, O(n) memoria (además de la matriz f).
     */
    public static Result minCostCanoas(int[][] f) {
        int n = f.length - 1;          // asumimos f de tamaño (n+1) x (n+1) y 1-indexado
        final int INF = 1_000_000_000; // “infinito” seguro para sumas

        int[] dp = new int[n + 1];
        int[] prev = new int[n + 1];

        Arrays.fill(dp, INF);
        Arrays.fill(prev, -1);

        dp[1] = 0;
        prev[1] = -1;

        for (int j = 2; j <= n; j++) {
            int best = INF;
            int bestPrev = -1;

            for (int i = 1; i < j; i++) {
                int cij = f[i][j];      // costo directo i->j
                if (cij >= INF) continue; // tramo inexistente/invalidado
                if (dp[i] >= INF) continue; // no se puede llegar a i

                int cand = dp[i] + cij;
                if (cand < best) {
                    best = cand;
                    bestPrev = i;
                }
            }

            if (best == INF) {
                throw new IllegalArgumentException("No se puede llegar al puesto " + j);
            }

            dp[j] = best;
            prev[j] = bestPrev;
        }

        // reconstruir ruta 1..n
        LinkedList<Integer> path = new LinkedList<>();
        for (int k = n; k != -1; k = prev[k]) path.addFirst(k);

        return new Result(dp[n], path);
    }

    // Demo mínima
    public static void main(String[] args) {
        final int INF = 1_000_000_000;
        int n = 5;
        int[][] f = new int[n + 1][n + 1]; // 1-indexado

        // Inicializamos tod a INF (tramo inexistente)
        for (int i = 0; i <= n; i++) Arrays.fill(f[i], INF);

        // Definimos algunos costos directos i->j (i<j)
        f[1][2] = 4;   f[1][3] = 15;  f[1][4] = 30;  f[1][5] = 20;
        f[2][3] = 3;   f[2][4] = 50;  f[2][5] = 30;
        f[3][4] = 2;   f[3][5] = 4;
        f[4][5] = 100;

        Result r = minCostCanoas(f);
        System.out.println(r); // esperado: minCost=11, path=[1, 2, 3, 5]
    }
}
