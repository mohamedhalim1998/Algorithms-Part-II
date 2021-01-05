import week1.ProgrammingAssignment.WordNet;

public class Main {
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("g.in", "a.in");
        System.out.println(wordNet.distance("a", "a"));
//        week1.ProgrammingAssignment.WordNet wordNet = new week1.ProgrammingAssignment.WordNet("synsets.in", "hypernyms.in");
//        week1.ProgrammingAssignment.Outcast outcast = new week1.ProgrammingAssignment.Outcast(wordNet);
//        String[] arr = StdIn.readLine().split(" ");
//        System.out.println(outcast.outcast(arr));
//        week1.ProgrammingAssignment.SAP sap = new week1.ProgrammingAssignment.SAP(new Digraph(new In("f.in")));
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}
