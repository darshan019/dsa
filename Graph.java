import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int[][] matrix1 = {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 1 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 0, 0 },
                { 1, 0, 1, 0, 1 }
        };

        int[][] matrix2 = {
                { 1, 0, 1 },
                { 0, 1, 0 },
                { 1, 0, 1 }
        };

        int[][] matrix3 = {
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        // System.out.println("Matrix 1 islands: " + graph.numberOfIslands(matrix1));
        // System.out.println("Matrix 2 islands: " + graph.numberOfIslands(matrix2));
        // System.out.println("Matrix 3 islands: " + graph.numberOfIslands(matrix3));
        int[][] arr = graph.convertToDistance(matrix2);

        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    public void dfsOfGraph(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis, ArrayList<Integer> li) {
        li.add(node);
        vis[node] = true;

        for (int it : adj.get(node)) {
            if (!vis[node])
                dfsOfGraph(it, adj, vis, li);
        }
    }

    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        ArrayList<Integer> bfs = new ArrayList<>();

        vis[0] = true;
        q.add(0);

        while (!q.isEmpty()) {
            int node = q.poll();
            bfs.add(node);

            for (Integer it : adj.get(node)) {
                if (!vis[it]) {
                    q.add(it);
                    vis[it] = true;
                }
            }
        }

        return bfs;
    }

    public int numberOfProvinces(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        int count = 0;

        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                count++;
                dfsOfGraph(i, adj, vis, new ArrayList<>());
            }
        }

        return count;
    }

    public int numberOfIslands(int[][] matrix) {
        int count = 0;
        List<Pair> lands = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1)
                    lands.add(new Pair(i, j));
            }
        }

        for (Pair pair : lands) {
            if (matrix[pair.first][pair.second] != -1) {
                count++;
                dfsOnIslands(matrix, pair);
            }
        }

        return count;
    }

    public void dfsOnIslands(int[][] matrix, Pair pair) {
        if (pair.first < 0 || pair.second < 0)
            return;
        if (pair.first >= matrix.length || pair.second >= matrix[0].length)
            return;

        if (matrix[pair.first][pair.second] != 1)
            return;

        matrix[pair.first][pair.second] = -1;

        dfsOnIslands(matrix, new Pair(pair.first - 1, pair.second));
        dfsOnIslands(matrix, new Pair(pair.first + 1, pair.second));
        dfsOnIslands(matrix, new Pair(pair.first, pair.second - 1));
        dfsOnIslands(matrix, new Pair(pair.first, pair.second + 1));
        dfsOnIslands(matrix, new Pair(pair.first - 1, pair.second - 1));
        dfsOnIslands(matrix, new Pair(pair.first - 1, pair.second + 1));
        dfsOnIslands(matrix, new Pair(pair.first + 1, pair.second - 1));
        dfsOnIslands(matrix, new Pair(pair.first + 1, pair.second + 1));
    }

    public int rottingOranges(int[][] grid) {
        Queue<Triple> q = new LinkedList<>();
        int fresh = 0;

        // collect all rotten oranges and count fresh ones
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    q.add(new Triple(i, j, 0)); // row, col, time
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        int minutes = 0;
        while (!q.isEmpty()) {
            Triple orange = q.poll();
            minutes = Math.max(minutes, orange.three);

            // 4-directional neighbors
            if (orange.one + 1 < grid.length && grid[orange.one + 1][orange.two] == 1) {
                grid[orange.one + 1][orange.two] = 2;
                fresh--;
                q.offer(new Triple(orange.one + 1, orange.two, orange.three + 1));
            }
            if (orange.one - 1 >= 0 && grid[orange.one - 1][orange.two] == 1) {
                grid[orange.one - 1][orange.two] = 2;
                fresh--;
                q.offer(new Triple(orange.one - 1, orange.two, orange.three + 1));
            }
            if (orange.two + 1 < grid[0].length && grid[orange.one][orange.two + 1] == 1) {
                grid[orange.one][orange.two + 1] = 2;
                fresh--;
                q.offer(new Triple(orange.one, orange.two + 1, orange.three + 1));
            }
            if (orange.two - 1 >= 0 && grid[orange.one][orange.two - 1] == 1) {
                grid[orange.one][orange.two - 1] = 2;
                fresh--;
                q.offer(new Triple(orange.one, orange.two - 1, orange.three + 1));
            }
        }

        return fresh == 0 ? minutes : -1;
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        Queue<Pair> q = new LinkedList<>();
        int initialColor = image[sr][sc];
        q.add(new Pair(sr, sc));
        image[sr][sc] = color;

        while (!q.isEmpty()) {
            Pair curr = q.poll();
            image[curr.first][curr.second] = color;

            if (curr.first + 1 < image.length && image[curr.first + 1][curr.second] == initialColor)
                q.offer(new Pair(curr.first + 1, curr.second));
            if (curr.first - 1 >= 0 && image[curr.first - 1][curr.second] == initialColor)
                q.offer(new Pair(curr.first - 1, curr.second));
            if (curr.second + 1 < image[0].length && image[curr.first][curr.second + 1] == initialColor)
                q.offer(new Pair(curr.first, curr.second + 1));
            if (curr.second - 1 >= 0 && image[curr.first][curr.second - 1] == initialColor)
                q.offer(new Pair(curr.first, curr.second - 1));
        }

        return image;
    }

    public boolean isCycle(int[][] grid) {
        boolean[] vis = new boolean[grid.length];

        for (int i = 0; i < grid.length; i++) {
            if (!vis[i]) {
                if (detectCycle(i, grid, vis))
                    return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private boolean detectCycleDFS(int src, int parent, boolean[] vis, int[][] grid) {
        vis[src] = true;
        for (int i = 0; i < grid[src].length; i++) {
            int adj = grid[src][i];
            if (!vis[adj]) {
                if (detectCycleDFS(adj, src, vis, grid))
                    return true;
            } else if (adj != parent)
                return true;
        }
        return false;
    }

    private boolean detectCycle(int src, int[][] grid, boolean[] vis) {
        vis[src] = true;
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(src, -1));

        while (!q.isEmpty()) {
            int node = q.peek().first;
            int parent = q.peek().second;
            q.remove();

            for (int i = 0; i < grid[node].length; i++) {
                int adj = grid[node][i];
                if (!vis[adj]) {
                    vis[adj] = true;
                    q.add(new Pair(adj, node));
                } else if (adj != parent)
                    return true;
            }
        }

        return false;
    }

    public int[][] convertToDistance(int[][] grid) {
        boolean[][] vis = new boolean[grid.length][grid[0].length];
        int[][] res = new int[grid.length][grid[0].length];
        bfsDist(grid, vis, res);
        return res;
    }

    public void bfsDist(int[][] grid, boolean[][] vis, int[][] res) {
        Queue<Triple> q = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1)
                    q.add(new Triple(i, j, 0));
            }
        }

        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        while (!q.isEmpty()) {
            Triple curr = q.peek();
            int row = curr.one;
            int col = curr.two;
            int dist = curr.three;
            q.remove();

            vis[row][col] = true;
            res[row][col] = dist;

            for (int[] arr : dirs) {
                int i = arr[0], j = arr[1];
                if (row + i < grid.length && col + j < grid[0].length && row + i >= 0 && col + j >= 0
                        && !vis[row + i][col + j] && grid[row + i][col + j] != 1) {
                    vis[row + i][col + j] = true;
                    q.add(new Triple(row + i, col + j, dist + 1));

                }
            }
        }
    }

    public int numOfEnclaves(int[][] grid) {
        int res = 0;
        boolean[][] vis = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            dfsOfEnclaves(grid, i, 0, vis); // first col
            dfsOfEnclaves(grid, i, grid[0].length - 1, vis); // last col
        }

        // First and last row
        for (int j = 0; j < grid[0].length; j++) {
            dfsOfEnclaves(grid, 0, j, vis); // first row
            dfsOfEnclaves(grid, grid.length - 1, j, vis); // last row
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !vis[i][j])
                    res++;
            }
        }

        return res;
    }

    private void dfsOfEnclaves(int[][] grid, int row, int col, boolean[][] vis) {
        if (row >= grid.length || row < 0 || col < 0 || col >= grid[0].length || vis[row][col] || grid[row][col] == 0)
            return;

        vis[row][col] = true;
        dfsOfEnclaves(grid, row + 1, col, vis);
        dfsOfEnclaves(grid, row - 1, col, vis);
        dfsOfEnclaves(grid, row, col + 1, vis);
        dfsOfEnclaves(grid, row, col - 1, vis);
    }

}

class Triple {
    int one, two, three;

    Triple(int a, int b, int c) {
        one = a;
        two = b;
        three = c;
    }

}

class Pair {
    int first, second;

    Pair(int a, int b) {
        first = a;
        second = b;
    }
}