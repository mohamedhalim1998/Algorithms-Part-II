package week3.ProgrammingAssignment;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BaseballElimination {
    private ArrayList<Team> list;
    private HashMap<String, Team> teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        constructTeams(filename);
        checkEliminationAll();
    }

    private void constructTeams(String filename) {
        teams = new HashMap<>();
        list = new ArrayList<>();
        In in = new In(filename);
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            String name = in.readString();
            int win = in.readInt();
            int loss = in.readInt();
            int left = in.readInt();
            Team team = new Team(i, name, win, loss, left);
            for (int j = 0; j < n; j++) {
                team.addAgainst(in.readInt());
            }
            teams.put(name, team);
            list.add(team);
        }
    }

    private void checkEliminationAll() {
        trivialElimination();
        ArrayList<Team> remain = getRemainTeams();
        nonTrivialElimination(remain);
    }


    private void trivialElimination() {
        int maxWin = calculateMaxWin();
        for (Team team : list) {
            checkTrivialElimination(team);
        }
    }

    private int calculateMaxWin() {
        int max = Integer.MIN_VALUE;
        for (Team team : list) {
            max = Math.max(max, team.wins);
        }
        return max;
    }

    private void checkTrivialElimination(Team team) {
        for (Team t : list) {
            if (team.wins + team.remain < t.wins) {
                team.isEliminated = true;
                team.eliminatedBy = new ArrayList<>();
                team.eliminatedBy.add(t.name);
                return;
            }
        }
    }

    private ArrayList<Team> getRemainTeams() {
        ArrayList<Team> remain = new ArrayList<>();
        for (Team team : list) {
            if (!team.isEliminated) {
                remain.add(team);
            }
        }
        return remain;
    }

    private void nonTrivialElimination(ArrayList<Team> remain) {
        for (int i = 0; i < remain.size(); i++) {
            checkNonTrivialElimination(i, remain);
        }
    }

    private void checkNonTrivialElimination(int teamIndex, ArrayList<Team> remain) {
        Team team = remain.get(teamIndex);
        int matches = nCr(remain.size() - 1, 2);
        int n = remain.size();
        int v = 1 + matches + n;
        FlowNetwork flow = createFlowNetwork(remain, team, matches, n, v);
        FordFulkerson fordFulkerson = new FordFulkerson(flow, 0, v - 1);
        for (FlowEdge edge : flow.adj(0)) {
            if (edge.flow() < edge.capacity()) {
                team.isEliminated = true;
                team.eliminatedBy = new ArrayList<>();
                for (int i = matches + 1, j = 0; i < v - 1; i++, j++) {
                    if (j == teamIndex) {
                        j++;
                    }
                    if (fordFulkerson.inCut(i)) {
                        team.eliminatedBy.add(remain.get(j).name);
                    }
                }
                return;
            }
        }
    }

    private FlowNetwork createFlowNetwork(ArrayList<Team> remain, Team team, int matches, int n, int v) {
        FlowNetwork flow = new FlowNetwork(v);
        Vertex[] vertices = new Vertex[matches];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!remain.get(i).equals(team) && !remain.get(j).equals(team))
                    vertices[index++] = new Vertex(index, remain.get(i).index, remain.get(j).index);
            }
        }
        // from s to matches
        for (Vertex vertex : vertices) {
            flow.addEdge(new FlowEdge(0, vertex.index, list.get(vertex.team1).against.get(vertex.team2)));
        }
        // from matches to teams
        for (int i = 0, j = matches + 1; i < remain.size(); i++) {
            Team t = remain.get(i);
            if (!t.equals(team)) {
                for (Vertex vertex : vertices) {
                    if (vertex.team1 == t.index || vertex.team2 == t.index)
                        flow.addEdge(new FlowEdge(vertex.index, j, Double.POSITIVE_INFINITY));
                }
                j++;
            }
        }
        // from teams to t
        for (int i = 0, j = matches + 1; i < remain.size(); i++) {
            Team t = remain.get(i);
            if (!t.equals(team)) {
                flow.addEdge(new FlowEdge(j, v - 1, team.remain + team.wins - t.wins));
                j++;
            }
        }
        return flow;
    }

    private static int nCr(int n, int r) {
        int ans = 1;
        for (int k = 0; k < r; k++) {
            ans = ans * (n - k) / (k + 1);
        }

        return ans;
    }

    // number of teams
    public int numberOfTeams() {
        return list.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team).wins;
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team).losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team).remain;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1)) {
            throw new IllegalArgumentException();
        }
        if (!teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team1).against.get(teams.get(team2).index);
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team).isEliminated;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teams.get(team).eliminatedBy;
    }

    private static class Team {
        int index;
        String name;
        int wins, losses, remain;
        boolean isEliminated;
        ArrayList<String> eliminatedBy;
        ArrayList<Integer> against;

        public Team(int index, String name, int wins, int losses, int remain) {
            this.index = index;
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            this.remain = remain;
            this.against = new ArrayList<>();
            this.isEliminated = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Team team = (Team) o;
            return index == team.index &&
                    Objects.equals(name, team.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, name, wins, losses, remain, isEliminated, eliminatedBy, against);
        }

        public void addAgainst(int n) {
            against.add(n);
        }

        @Override
        public String toString() {
            return String.format("Team{%s}", name);
        }
    }

    private static class Vertex {
        int index;
        int team1, team2;

        public Vertex(int index, int team1, int team2) {
            this.index = index;
            this.team1 = team1;
            this.team2 = team2;
        }

        public int other(int team) {
            return team == team1 ? team2 : team1;
        }
    }
}
