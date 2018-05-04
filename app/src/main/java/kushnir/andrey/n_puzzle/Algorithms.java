package kushnir.andrey.n_puzzle;

import java.util.*;


/**
 * Created by Андрей on 29.04.2018.
 */

public class Algorithms {

    static private final int POSSIBLE_MOVES[][] = {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6},
            {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {6, 4, 8}, {7, 5}};

    static private final int N_POSSIBLE_MOVES[] = {2, 3, 2, 3, 4, 3, 2, 3, 2};

    static private final String FIN = "12345678#";

    static public ArrayList<String> pathStrings;

    static private int minMoves(String start) {
        String current = "", to = "";
        Set<String> used = new HashSet<>();
        Map<String, Integer> moves = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        Map<String, String> parents = new HashMap<>();
        pathStrings = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();

        q.add(start);
        used.add(start);
        moves.put(start, 0);
        parents.put(start, "top");
        while (!q.isEmpty()) {
            current = q.peek();
            if (current.contentEquals(FIN))
                break;
            q.remove();
            int n = current.indexOf('#');
            for (int i = 0; i < N_POSSIBLE_MOVES[n]; ++i) {
                tmp.setLength(0);
                tmp.append(current);
                tmp.setCharAt(n, tmp.charAt(POSSIBLE_MOVES[n][i]));
                tmp.setCharAt(POSSIBLE_MOVES[n][i], '#');
                to = tmp.toString();
                if (!used.contains(to)) {
                    used.add(to);
                    q.add(to);
                    moves.put(to, moves.get(current) + 1);
                    parents.put(to, current);
                }
            }
        }

        for (String v = parents.get(to); !v.equals("top"); v = parents.get(v)) {
            pathStrings.add(v);
        }
        Collections.reverse(pathStrings);

        return moves.get(FIN);
    }

    static private String convertToString(List<Place> places, int boardSize) {
        StringBuilder sb = new StringBuilder(FIN.length());
        for (Place p: places) {
            sb.append(p.getTile().number());
        }
        return sb.toString();
    }

    static int calcMinMoves(List<Place> places, int boardSize) {
        String start = convertToString(places, boardSize);
        return minMoves(start);
    }

    static ArrayList<Integer> getShortestPath() {
        ArrayList<Integer> path = new ArrayList<>(pathStrings.size());
        for (int i = 1; i < pathStrings.size(); i++) {
            path.add(pathStrings.get(i).indexOf('#'));
        }
        return path;
    }


}
