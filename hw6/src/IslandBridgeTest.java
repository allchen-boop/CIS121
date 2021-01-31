import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class IslandBridgeTest {

    @Test
    public void exampleFalse() {
        Graph graph = new Graph(4);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 1, 0);
        
        assertFalse(IslandBridge.allNavigable(graph, 0));
    }
    
    @Test
    public void exampleTrue() {        
        Graph graph = new Graph(4);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 0, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 1, 0);
        
        assertTrue(IslandBridge.allNavigable(graph, 0));
        assertTrue(IslandBridge.allNavigable(graph, 3));
    }
    
    @Test
    public void singleIslandTrue() {
        Graph graph = new Graph(4);
        assertTrue(IslandBridge.allNavigable(graph, 0));
    }
    
    
    @Test
    public void onlyOutgoingBridges() {
        Graph graph = new Graph(4);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(0, 1, 0);
        graph.addEdge(0, 2, 0);
 
        assertFalse(IslandBridge.allNavigable(graph, 0));
    }
    
    
    @Test
    public void undirectedGraph() {
        Graph graph = new Graph(4);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 0, 0);
        
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 1, 0);
        
        graph.addEdge(2, 0, 0);
        graph.addEdge(0, 2, 0);
        
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 2, 0);
        
        graph.addEdge(3, 1, 0);
        graph.addEdge(1, 3, 0);
        
        assertTrue(IslandBridge.allNavigable(graph, 0));
    }
    
    
    @Test
    public void graphNotStronglyConnected() {
        Graph graph = new Graph(4);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(3, 1, 0);
        
        assertTrue(IslandBridge.allNavigable(graph, 1));   
        assertTrue(IslandBridge.allNavigable(graph, 2));   
        assertTrue(IslandBridge.allNavigable(graph, 3));   
    }
    
    @Test
    public void visualizerGraph() {
        Graph graph = new Graph(8);
        
        graph.addEdge(0, 3, 0);
        graph.addEdge(1, 3, 0);
        graph.addEdge(0, 4, 0);
        graph.addEdge(4, 2, 0);
        graph.addEdge(2, 0, 0);
        graph.addEdge(4, 7, 0);
        graph.addEdge(2, 6, 0);
        graph.addEdge(6, 7, 0);
        
        
        assertFalse(IslandBridge.allNavigable(graph, 1));
        assertFalse(IslandBridge.allNavigable(graph, 6));
    }

}