package week1.InterviewQuestions.UndirectedGraphs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class DiameterCenter {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] depth;
    private int s;
    private Graph G;

    public DiameterCenter(Graph G, int s) {
        this.G = G;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        depth = new int[G.V()];
        this.s = s;
        Arrays.fill(edgeTo, -1);
        dfs(G, s, 0);
    }


    private void dfs(Graph g, int v, int d) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w, d + 1);
                edgeTo[w] = v;
                depth[w] = d + 1;
            }
        }
    }

    public Iterable<Integer> diameter() {
        return pathTo(maxDepthIndex());
    }

    public int center() {
        int max = depth[maxDepthIndex()];
        int center = s;
        Iterable<Integer> iterable = diameter();
        for (int i : iterable) {
            Arrays.fill(marked, false);
            Arrays.fill(edgeTo, -1);
            dfs(G, i, 0);
            int temp = depth[maxDepthIndex()];
            if (max > temp) {
                max = temp;
                center = i;
            }
        }
        return center;
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    public int maxDepthIndex() {
        int max = Integer.MIN_VALUE;
        int v = -1;
        for (int i = 0; i < depth.length; i++) {
            if (depth[i] > max) {
                max = depth[i];
                v = i;
            }
        }
        return v;
    }
}
