import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides access to Dijkstra's algorithm for a weighted graph.
 */
final public class Dijkstra {
    private Dijkstra() {}
    
   

    /**
     * Computes the shortest path between two nodes in a weighted graph.
     * Input graph is guaranteed to be valid and have no negative-weighted edges.
     *
     * @param g   the weighted graph to compute the shortest path on
     * @param src the source node
     * @param tgt the target node
     * @return an empty list if there is no path from {@param src} to {@param tgt}, otherwise an
     * ordered list of vertices in the shortest path from {@param src} to {@param tgt},
     * with the first element being {@param src} and the last element being {@param tgt}.
     * If {@param src} = {@param tgt}, you should return a SINGLETON list.
     */
    public static List<Integer> getShortestPath(Graph g, int src, int tgt) {

        //min priority queue with distance keys and vertices as values
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<>();
        
        Map<Integer, Integer> parent = new HashMap<>();
        
        int [] distance = new int [g.getSize()];
      
        //initializing distances to infinity (notes)
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
       
        distance[src] = 0;
        heap.add(0, src);
        
        //basically just the algorithm from the notes
        while (!heap.isEmpty()) {
            int curr = heap.extractMin().value;
            
            for (int neighbor : g.outNeighbors(curr)) {
                relax(parent, distance, heap, curr, neighbor, g.getWeight(curr, neighbor));
            }
        }
            
        return buildPath(parent, src, tgt); 
    }
    
    //edge relaxation
    private static void relax(Map<Integer, Integer> parent, int [] distance, 
                                BinaryMinHeapImpl<Integer, Integer> heap,
                                int u, int v, int weight) {
        if (distance[v] > distance[u] + weight) {
            
            //decrease key operation
            distance[v] = distance[u] + weight;   
            parent.put(v, u);
            
            if (!heap.containsValue(v)) { 
                heap.add(distance[v], v);
            } 
            heap.decreaseKey(v, distance[v]);
        }  
    }
 
    //same path tracer used in MazeSolver
    private static List<Integer> buildPath(Map<Integer, Integer> parent,
                                            int src, int tgt) {
        
        List<Integer> path = new ArrayList<Integer>();
        
        if (!parent.containsKey(tgt)) {
            if (src == tgt) {
                path.add(src);
            }
            return path;
        }
        
        path.add(tgt);
        
        while (tgt != src) {
            path.add(0, parent.get(tgt));
            tgt = parent.get(tgt);
        }
  
        return path;
    }
 
}
