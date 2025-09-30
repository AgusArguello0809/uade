package algorithms;
import java.util.*;

public class Kruskal {
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class DSU {
        int[] parent, rank;
        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]); // path compression
            return parent[x];
        }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

    static class Result {
        int totalWeight;
        List<Edge> mstEdges;
        Result(int w, List<Edge> es) { totalWeight = w; mstEdges = es; }
    }

    public static Result kruskal(int n, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.w));
        DSU dsu = new DSU(n);
        List<Edge> mst = new ArrayList<>();
        int total = 0;

        for (Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                mst.add(e);
                total += e.w;
                if (mst.size() == n - 1) break;
            }
        }
        return new Result(total, mst);
    }

    public static void main(String[] args) {
        int n = 5;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0,1,2));
        edges.add(new Edge(0,2,5));
        edges.add(new Edge(1,2,1));
        edges.add(new Edge(1,3,2));
        edges.add(new Edge(2,3,1));
        edges.add(new Edge(3,4,3));

        Result r = kruskal(n, edges);
        System.out.println("Peso total MST = " + r.totalWeight);
        for (Edge e : r.mstEdges) {
            System.out.println(e.u + " - " + e.v + " (" + e.w + ")");
        }
    }
}

