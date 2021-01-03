import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import week1.InterviewQuestions.UndirectedGraphs.DiameterCenter;

public class Main {
    public static void main(String[] args) {
        Graph G = new Graph(new In("g.in"));
        DiameterCenter d = new DiameterCenter(G, 0);
        System.out.println(d.center());
    }
}
