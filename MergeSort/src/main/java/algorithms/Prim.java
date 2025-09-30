package algorithms;
import java.util.*;

public class Prim {
    static class Edge { int to, w; Edge(int to, int w){ this.to = to; this.w = w; } }

    static class Result {
        int totalWeight;
        int[] parent;
        Result(int w, int[] p) { totalWeight = w; parent = p; }
    }

    // Grafo como lista de adyacencia: g[u] = (v, w)
    public static Result prim(int n, List<List<Edge>> g, int start) {
        final int INF = Integer.MAX_VALUE / 4;
        int[] key = new int[n];
        int[] parent = new int[n];
        boolean[] inMST = new boolean[n];
        Arrays.fill(key, INF);
        Arrays.fill(parent, -1);
        key[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, start}); // (key, node)

        int total = 0;
        int picked = 0;

        while (!pq.isEmpty() && picked < n) {
            int[] cur = pq.poll();
            int k = cur[0], u = cur[1];
            if (inMST[u]) continue;
            inMST[u] = true;
            total += k;
            picked++;

            for (Edge e : g.get(u)) {
                if (!inMST[e.to] && e.w < key[e.to]) {
                    key[e.to] = e.w;
                    parent[e.to] = u;
                    pq.add(new int[]{key[e.to], e.to});
                }
            }
        }
        return new Result(total, parent);
    }

    public static void main(String[] args) {
        int n = 5;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        // grafo no dirigido
        BiEdge(g, 0, 1, 2);
        BiEdge(g, 0, 2, 5);
        BiEdge(g, 1, 2, 1);
        BiEdge(g, 1, 3, 2);
        BiEdge(g, 2, 3, 1);
        BiEdge(g, 3, 4, 3);

        Result r = prim(n, g, 0);
        System.out.println("Peso total MST = " + r.totalWeight);
        System.out.println("parent = " + Arrays.toString(r.parent));
    }

    static void BiEdge(List<List<Edge>> g, int u, int v, int w) {
        g.get(u).add(new Edge(v, w));
        g.get(v).add(new Edge(u, w));
    }
}

