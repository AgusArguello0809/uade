package algorithms;

public class EjercicioPropuestoSubstring {

    public static class Substring{
        int bestLen;
        int start;
        int endPos;
        String subString;

        public Substring(int bestLen, int start, int endPos, String subString) {
            this.bestLen = bestLen;
            this.start = start;
            this.endPos = endPos;
            this.subString = subString;
        }

        @Override
        public String toString() {
            return "bestLen = " + bestLen + " start = " + start + " endPos = " + endPos + " Substring = " + subString;
        }
    }

    public static Substring LCSubstring(String x, String y) {
        int[][] A = initializeMatrix(x.length() + 1, y.length() + 1);
        int bestLen = 0;
        int endPos = 0;
        for(int i = 1; i <= x.length(); i ++) {
            for(int j = 1; j <= y.length(); j ++) {
                if(x.charAt(i - 1) == y.charAt(j - 1)) {
                    A[i][j] = A[i - 1][j - 1] + 1;
                    if(A[i][j] > bestLen) {
                        bestLen = A[i][j];
                        endPos = i;
                    }
                } else {
                    A[i][j] = 0;
                }
            }
        }
        if (bestLen == 0)
            return new Substring(0, 0, 0, "");
        int start = endPos - bestLen + 1;
        return new Substring(bestLen, start - 1, endPos - 1, x.substring(start - 1, endPos));
    }

    public static int[][] initializeMatrix(int filas, int columnas) {
        if (filas < 0 || columnas < 0) {
            throw new IllegalArgumentException("filas y columnas deben ser >= 0");
        }
        return new int[filas][columnas]; // ya viene en 0
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        String x = "substring";
        String y = "string";
        Substring res = LCSubstring(x, y);
        System.out.println(res.toString());
    }
}
