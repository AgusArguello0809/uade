package algorithms;
import java.util.*;

public class Dijkstra {
    static class Edge {
        int to;
        int w;
        Edge(int to, int w) { this.to = to; this.w = w; }
    }

    /**
     * @param n   cantidad de nodos [0..n-1]
     * @param g   grafo como lista de adyacencia: g[u] = lista de aristas (u -> v, w)
     * @param src nodo fuente
     * @return    par (dist, parent): dist[v] = distancia mínima; parent[v] para reconstruir camino
     */
    public static Result dijkstra(int n, List<List<Edge>> g, int src) {
        final int INF = Integer.MAX_VALUE / 4;
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        // (distancia, nodo)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, src});

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (visited[u]) continue;      // descartamos entradas “viejas”
            visited[u] = true;

            for (Edge e : g.get(u)) {
                if (d + e.w < dist[e.to]) {
                    dist[e.to] = d + e.w;
                    parent[e.to] = u;
                    pq.add(new int[]{dist[e.to], e.to});
                }
            }
        }
        return new Result(dist, parent);
    }

    // Helper para reconstruir camino src -> t
    public static List<Integer> pathTo(int t, int[] parent) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int cur = t; cur != -1; cur = parent[cur]) path.addFirst(cur);
        return path;
    }

    public static class Result {
        public final int[] dist;
        public final int[] parent;
        public Result(int[] dist, int[] parent) {
            this.dist = dist; this.parent = parent;
        }
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        int n = 5;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        // aristas dirigidas (u -> v, w). Si es no dirigido, agregá la inversa.
        g.get(0).add(new Edge(1, 2));
        g.get(0).add(new Edge(2, 5));
        g.get(1).add(new Edge(2, 1));
        g.get(1).add(new Edge(3, 2));
        g.get(2).add(new Edge(3, 1));
        g.get(3).add(new Edge(4, 3));

        Result r = dijkstra(n, g, 0);
        System.out.println("distancias: " + Arrays.toString(r.dist));
        System.out.println("camino 0->4: " + pathTo(4, r.parent));
    }
}
