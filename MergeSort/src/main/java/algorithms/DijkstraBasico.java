package algorithms;

import java.util.Arrays;
import java.util.List;

public class DijkstraBasico {
    static class Edge {
        int to, w;
        Edge(int to, int w){ this.to = to; this.w = w; }
    }

    public static int[] dijkstraBasico(int n, List<List<Edge>> g, int src) {
        final int INF = Integer.MAX_VALUE / 4;
        int[] dist = new int[n];
        boolean[] used = new boolean[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int it = 0; it < n; it++) {
            // elegir el no visitado con menor dist (O(V))
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!used[v] && (u == -1 || dist[v] < dist[u])) u = v;
            }
            if (u == -1 || dist[u] == INF) break;
            used[u] = true;

            // relajar sus aristas
            for (Edge e : g.get(u)) {
                if (dist[u] + e.w < dist[e.to]) {
                    dist[e.to] = dist[u] + e.w;
                }
            }
        }
        return dist;
    }
}
