package week1.InterviewQuestions.UndirectedGraphs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class NonRecursiveDFS {
    private boolean[] marked;
    private int[] edgeTo;

    public NonRecursiveDFS(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Arrays.fill(edgeTo, -1);
        dfs(G, s);
    }

    private void dfs(Graph g, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            marked[s] = true;
            int v = stack.pop();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    stack.push(w);
                    edgeTo[w] = v;
                }
            }
        }
    }
}
