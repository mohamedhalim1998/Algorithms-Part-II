import edu.princeton.cs.algs4.StdOut;
import week3.ProgrammingAssignment.BaseballElimination;

public class Main {
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams4.in");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
