import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


public class DijkstraTest {

    @Test
    public void directedNoPath() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
         
        assertEquals(0, Dijkstra.getShortestPath(graph, 0, 3).size());
    }
    
    @Test
    public void undirectedNoPath() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 0, 1);
        
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 0, 1);
        
        assertEquals(0, Dijkstra.getShortestPath(graph, 0, 3).size());
    }
    
    @Test
    public void srcEqualsTgt() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
 
        List<Integer> path = new ArrayList<>();
        path.add(1);
        
        assertEquals(path, Dijkstra.getShortestPath(graph, 1, 1));
        assertEquals(1, Dijkstra.getShortestPath(graph, 1, 1).size());
    }

    @Test
    public void shortestPathDirected() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        
        List<Integer> path = new ArrayList<>();
        path.add(0);
        path.add(1);
        
        assertEquals(path, Dijkstra.getShortestPath(graph, 0, 1));
    }
    
    @Test
    public void shortestPathUndirected() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 0, 1);
        
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 1, 1);
        
        List<Integer> path = new ArrayList<>();
        path.add(0);
        path.add(1);
        
        assertEquals(path, Dijkstra.getShortestPath(graph, 0, 1));
    }
    
    
    @Test
    public void shortestPathDiffWeights() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(0, 3, 1);
        graph.addEdge(2, 3, 0);
        
        List<Integer> path = new ArrayList<>();
        path.add(0);
        path.add(1);
        path.add(2);
        
        assertEquals(path, Dijkstra.getShortestPath(graph, 0, 2));
        
    }
  
    
    @Test
    public void dijkstraVisualizerDirected() {
        Graph graph = new Graph(8);
        graph.addEdge(0, 3, 8);
        graph.addEdge(3, 7, 9);
        graph.addEdge(7, 6, 8);
        graph.addEdge(6, 2, 3);
        graph.addEdge(2, 4, 3);
        graph.addEdge(2, 6, 3);
        graph.addEdge(4, 6, 8);
        graph.addEdge(5, 3, 3);
        graph.addEdge(5, 1, 4);
        
        assertEquals(0, Dijkstra.getShortestPath(graph, 2, 7).size());
        
        
        List<Integer> path02 = new ArrayList<>();
        path02.add(0);
        path02.add(3);
        path02.add(7);
        path02.add(6);
        path02.add(2);
        
        assertEquals(path02, Dijkstra.getShortestPath(graph, 0, 2));
        
        List<Integer> path24 = new ArrayList<>();
        path24.add(2);
        path24.add(4);
        
        assertEquals(path24, Dijkstra.getShortestPath(graph, 2, 4));
    }
    
    @Test
    public void dijkstraVisualizerUndirected() {
        Graph graph = new Graph(8);
        graph.addEdge(0, 3, 4);
        graph.addEdge(3, 0, 4);
        
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 0, 1);
        
        graph.addEdge(1, 3, 8);
        graph.addEdge(3, 1, 8);
        
        graph.addEdge(3, 5, 6);
        graph.addEdge(5, 3, 6);
        
        graph.addEdge(1, 5, 6);
        graph.addEdge(5, 1, 6);
        
        graph.addEdge(2, 5, 3);
        graph.addEdge(5, 2, 3);
        
        graph.addEdge(5, 7, 2);
        graph.addEdge(7, 5, 2);
        
        graph.addEdge(4, 7, 9);
        graph.addEdge(7, 4, 9);
        
        assertEquals(0, Dijkstra.getShortestPath(graph, 0, 6).size());
        
        
        List<Integer> path04 = new ArrayList<>();
        path04.add(0);
        path04.add(1);
        path04.add(5);
        path04.add(7);
        path04.add(4);
        
        assertEquals(path04, Dijkstra.getShortestPath(graph, 0, 4));
        
        List<Integer> path31 = new ArrayList<>();
        path31.add(3);
        path31.add(0);
        path31.add(1);
        
        assertEquals(path31, Dijkstra.getShortestPath(graph, 3, 1));
    }
         
}