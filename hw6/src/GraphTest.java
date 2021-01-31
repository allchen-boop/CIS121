import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class GraphTest {

    @Test (expected = IllegalArgumentException.class)
    public void graphZeroVertices() {
        new Graph(0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void graphNegativeVertices() {
        new Graph(-1);
    }
    
    @Test
    public void getSize() {
        Graph graph = new Graph(3);
        assertEquals(3, graph.getSize());
    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void hasEdgeInvalidVertices() {
        Graph graph = new Graph(3);
        graph.hasEdge(-1, 10);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void hasEdgeVertexEqualsSize() {
        Graph graph = new Graph(3);
        graph.hasEdge(0, 3);
    }
    
    @Test
    public void hasEdgeFalse() {
        Graph graph = new Graph(3);
        assertFalse(graph.hasEdge(0, 1));
    }
    
    @Test
    public void hasEdgeTrue() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 0);
        
        assertTrue(graph.hasEdge(0, 1));
    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void getWeightInvalidVertices() {
        Graph graph = new Graph(3);
        graph.getWeight(-1, 10);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void getWeightNoEdge() {
        Graph graph = new Graph(3);
        
        graph.getWeight(1, 0); 
        assertFalse(graph.hasEdge(1, 0));
    }
    
    @Test
    public void getWeight() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 2);
        
        assertEquals(2, graph.getWeight(0, 1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeSameVertices() {
        Graph graph = new Graph(3);
        graph.addEdge(1, 1, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeInvalidVertices() {
        Graph graph = new Graph(5);
        graph.addEdge(-1, 10, 1);
    }
    
    @Test
    public void testAddEdgeFalse() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        
        assertFalse(graph.addEdge(0, 1, 1));
        assertTrue(graph.hasEdge(0, 1));
    }
    
    @Test
    public void testAddEdgeTrue() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.addEdge(1, 2, 1));
        assertTrue(graph.hasEdge(1, 2));
    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void outNeighborsInvalidVertex() {
        Graph graph = new Graph(3);
        graph.outNeighbors(10);
    }
 
    @Test
    public void noOutNeighbors() {
        Graph graph = new Graph(3);
        assertEquals(0, graph.outNeighbors(0).size());
    }
    
    @Test
    public void outNeighbors() {
        Graph graph = new Graph(3);
        
        graph.addEdge(0, 1, 0);
        graph.addEdge(0, 2, 0);
        
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        
        assertEquals(2, graph.outNeighbors(0).size());
        assertEquals(set, graph.outNeighbors(0));
    }
    
}
