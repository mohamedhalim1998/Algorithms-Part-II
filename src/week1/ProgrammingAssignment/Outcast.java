package week1.ProgrammingAssignment;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a week1.ProgrammingAssignment.WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of week1.ProgrammingAssignment.WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        int sum = 0;
        int i = 0;
        for (String a : nouns) {
            for (String b : nouns) {
                sum += wordNet.distance(a, b);
            }
            if (sum > max) {
                max = sum;
                index = i;
            }
            i++;
            sum = 0;
        }
        return nouns[index];
    }

    // see test client below
    public static void main(String[] args) {

    }
}