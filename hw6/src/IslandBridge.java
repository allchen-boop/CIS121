import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

final public class IslandBridge {

    private IslandBridge() {
        
    }

    /**
     * See question details in the write-up. Input is guaranteed to be valid.
     *
     * @param g the input graph representing the islands as vertices and bridges as directed edges
     * @param x the starting node
     * @return true, if no matter how you navigate through the one-way bridges from node x,
     * there is always a way to drive back to node x, and false otherwise.
     * @implSpec This method should run in worst-case O(n + m) time.
     */
    

    public static boolean allNavigable(Graph g, int x) {
        Graph reversed = reverse(g, g.getSize());

        return checkDFS(dfs(g, x), dfs(reversed, x));
    }
    

    private static boolean checkDFS(List<Integer> regular, List<Integer> reversed) {
        
        //if there's a differing reachable island between the DFS traversals then false
        for (int element : regular) {
            if (!reversed.contains(element)) {
                return false;
            }
        }
        return true;
    }

    //based off of kosaraju using DFS twice for SCC
    private static List<Integer> dfs(Graph g, int src) {
 
        //DFS uses stack instead of queue
        Stack<Integer> queue = new Stack<>();
        
        List<Integer> path = new ArrayList<>();
        
        //add source coordinate
        queue.push(src); 
        path.add(src);

        //basically BFS but with stack (from notes)
        while (!queue.isEmpty()) {
            int curr = queue.pop();

            for (int neighbor : g.outNeighbors(curr)) {
                if (!path.contains(neighbor)) {
                    queue.push(neighbor);
                    path.add(neighbor); 
                }
            }
        }
        //don't need to trace path since we only care about what islands are reached
        return path;
    }

    //reverse the direction of every edge to  see if island is reachable both ways
    private static Graph reverse(Graph g, int n) {
        Graph reverse = new Graph(n);
     
        while (n > 0) {
            for (int neighbor : g.outNeighbors(n - 1)) {
                reverse.addEdge(neighbor, n - 1, 0);
            }
            n--;
        }
        return reverse;
    }
}

        
        
     
