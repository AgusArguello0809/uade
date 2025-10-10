package algorithms;
import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {

    // Busca todas las ocurrencias; devolvemos índices de comienzo en T
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        if (m == 0) { matches.add(0); return matches; }
        if (n < m) return matches;

        int[] bad = buildBadChar(pattern);   // última aparición por char
        int[] gs  = buildGoodSuffix(pattern); // saltos por good-suffix

        int s = 0; // desplazamiento del patrón sobre el texto
        while (s <= n - m) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }
            if (j < 0) {
                matches.add(s);
                s += gs[0]; // salto cuando hubo match completo
            } else {
                char c = text.charAt(s + j);
                int last = c < 256 ? bad[c] : -1; // aquí tratamos ASCII; fuera de eso => -1
                int bcShift = j - last;
                int gsShift = gs[j];
                s += Math.max(1, Math.max(bcShift, gsShift));
            }
        }
        return matches;
    }

    // --- Bad Character: última aparición de cada char (ASCII 0..255) ---
    private static int[] buildBadChar(String pattern) {
        int[] last = new int[256];
        for (int i = 0; i < last.length; i++) last[i] = -1;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c < 256) last[c] = i; // si no es ASCII, lo ignoramos (queda -1)
        }
        return last;
    }

    // --- Good Suffix: implementación estándar con tablas suffix y prefix ---
    // Referencia: tabla de desplazamientos donde gs[j] = cuánto mover si falla en j
    private static int[] buildGoodSuffix(String pattern) {
        int m = pattern.length();
        int[] gs = new int[m];         // desplazamientos
        int[] suffix = new int[m];     // suffix[k] = inicio del sufijo de longitud k que coincide
        boolean[] prefix = new boolean[m]; // prefix[k] = true si sufijo de long k es también prefijo

        for (int i = 0; i < m; i++) {
            gs[i] = m; // default: si no se puede usar nada, saltar m
        }

        // Precompute suffix/prefix
        // Para cada i, calculamos la mayor k tal que P[i-k+1..i] == P[m-k..m-1]
        for (int i = 0; i < m - 1; i++) { // hasta m-2; el sufijo completo se trata aparte
            int k = 0;
            while (i - k >= 0 && pattern.charAt(i - k) == pattern.charAt(m - 1 - k)) {
                k++;
                suffix[k] = i - k + 1; // inicio donde aparece ese sufijo
            }
            if (i - k + 1 == 0) {
                // si el prefijo de longitud k == sufijo de longitud k
                prefix[k] = true;
            }
        }

        // Completar desplazamientos:
        // Caso 1: si existe otra ocurrencia del sufijo P[j+1..m-1]
        for (int j = 0; j < m - 1; j++) {
            int k = m - 1 - j; // longitud del sufijo bueno
            if (suffix[k] != 0 || prefix[k]) {
                // mover para alinear esa otra ocurrencia
                gs[j] = (suffix[k] != 0) ? (j - suffix[k] + 1) : (m - k);
            }
        }

        // Caso 2: fallback para match completo
        // gs[0] debe ser el salto cuando se encontró una coincidencia completa
        // Regla: mover para alinear el mayor prefijo que sea sufijo del patrón
        int move = m; // por defecto
        for (int k = 1; k < m; k++) {
            if (prefix[k]) {
                move = m - k;
                break;
            }
        }
        gs[0] = move;

        return gs;
    }

    // --- Demo simple ---
    public static void main(String[] args) {
        String text = "ABAAABCDABCDAABCDABCD";
        String pattern = "ABCD";
        System.out.println(search(text, pattern)); // Ej: [4, 9, 14, 18]
    }
}
