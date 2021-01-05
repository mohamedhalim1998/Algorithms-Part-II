package week1.ProgrammingAssignment;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private HashMap<String, ArrayList<Integer>> nouns;
    private ArrayList<String> synsets;
    private int size = 0;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        constructMap(synsets);
        constructGraph(hypernyms);
    }

    private void constructMap(String synsets) {
        this.nouns = new HashMap<>();
        this.synsets = new ArrayList<>();
        In in = new In(synsets);
        String line = in.readLine();
        while (line != null) {
            String[] vals = line.split(",");
            this.synsets.add(vals[1]);
            for (String s : vals[1].split(" ")) {
                ArrayList<Integer> v = this.nouns.getOrDefault(s, new ArrayList<>());
                v.add(Integer.parseInt(vals[0]));
                this.nouns.put(s, v);
            }
            size++;
            line = in.readLine();
        }
    }

    private void constructGraph(String hypernyms) {
        Digraph graph = new Digraph(size);
        In in = new In(hypernyms);
        String line = in.readLine();
        while (line != null) {
            String[] vals = line.split(",");
            for (int i = 1; i < vals.length; i++) {
                graph.addEdge(Integer.parseInt(vals[0]), Integer.parseInt(vals[i]));
            }

            line = in.readLine();
        }
        int count = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0)
                count++;
        }
        if (count > 1) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(graph);
    }

    // returns all week1.ProgrammingAssignment.WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a week1.ProgrammingAssignment.WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        //System.out.println(word + " : " + nouns.get(word));
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB     zxcvbnm,./
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        ArrayList<Integer> a = nouns.get(nounA);
        ArrayList<Integer> b = nouns.get(nounB);

        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        ArrayList<Integer> a = nouns.get(nounA);
        ArrayList<Integer> b = nouns.get(nounB);
        return synsets.get(sap.ancestor(a, b));

    }


    // do unit testing of this class
    public static void main(String[] args) {

    }
}