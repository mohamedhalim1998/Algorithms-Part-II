package week1.ProgrammingAssignment;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Arrays;

public class SAP {
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
            throw new IllegalArgumentException();
        int[] disA = bfs(v);
        int[] disB = bfs(w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < disA.length; i++) {
            if (disA[i] != -1 && disB[i] != -1) {
                int sum = disA[i] + disB[i];
                if (sum < min) {
                    min = sum;
                }

            }
        }
        if (min == Integer.MAX_VALUE)
            return -1;
        return min;
    }

    private int[] bfs(int s) {
        boolean[] marked = new boolean[graph.V()];
        int[] dis = new int[graph.V()];
        Arrays.fill(dis, -1);
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        dis[s] = 0;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    dis[w] = dis[v] + 1;
                }
            }
        }
        return dis;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
            throw new IllegalArgumentException();
        int[] disA = bfs(v);
        int[] disB = bfs(w);
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < disA.length; i++) {
            if (disA[i] != -1 && disB[i] != -1) {
                int sum = disA[i] + disB[i];
                if (sum < min) {
                    min = sum;
                    index = i;
                }

            }
        }
        return index;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null || i < 0 || i >= graph.V())
                throw new IllegalArgumentException();
        }
        for (Integer i : w) {
            if (i == null || i < 0 || i >= graph.V())
                throw new IllegalArgumentException();
        }
        int[] disA = bfsSet(v);
        int[] disB = bfsSet(w);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < disA.length; i++) {
            if (disA[i] != -1 && disB[i] != -1) {
                int sum = disA[i] + disB[i];
                if (sum < min) {
                    min = sum;
                }

            }
        }
        if (min == Integer.MAX_VALUE)
            return -1;
        return min;
    }

    private int[] bfsSet(Iterable<Integer> s) {
        boolean[] marked = new boolean[graph.V()];
        int[] dis = new int[graph.V()];
        Arrays.fill(dis, -1);
        Queue<Integer> queue = new Queue<>();
        for (int i : s) {
            queue.enqueue(i);
            marked[i] = true;
            dis[i] = 0;
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    dis[w] = dis[v] + 1;
                }
            }
        }
        return dis;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null || i < 0 || i >= graph.V())
                throw new IllegalArgumentException();
        }
        for (Integer i : w) {
            if (i == null || i < 0 || i >= graph.V())
                throw new IllegalArgumentException();
        }
        int[] disA = bfsSet(v);
        int[] disB = bfsSet(w);
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < disA.length; i++) {
            if (disA[i] != -1 && disB[i] != -1) {
                int sum = disA[i] + disB[i];
                if (sum < min) {
                    min = sum;
                    index = i;
                }

            }
        }
        return index;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}