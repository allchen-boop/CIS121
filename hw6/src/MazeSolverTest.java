import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MazeSolverTest {
    
    //mazes from hw0 tests
    
    private int[][] smallWriteupMaze;
    private int[][] noBlockedCoordMaze;
    private int[][] noPathMaze;
    
    @Before
    public void setupTestMazes() { 
        smallWriteupMaze = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };
     
        noBlockedCoordMaze = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        
        noPathMaze = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
    }

    @Test
    public void shortestPathSmallWriteupMaze() {
     
        Coordinate src = new Coordinate(0, 0);
        Coordinate tgt = new Coordinate(0, 2);
        
        List<Coordinate> shortestPath = new ArrayList<>();
        
        shortestPath.add(src);
        shortestPath.add(new Coordinate(1, 0));
        shortestPath.add(new Coordinate(2, 0));
        shortestPath.add(new Coordinate(2, 1));
        shortestPath.add(new Coordinate(2, 2));
        shortestPath.add(new Coordinate(1, 2));
        shortestPath.add(tgt);

        assertEquals(shortestPath, MazeSolver.getShortestPath(smallWriteupMaze, src, tgt));
    }
    
    @Test
    public void shortestPathSrcEqualsTgt() {
     
        Coordinate src = new Coordinate(1, 0);
        Coordinate tgt = new Coordinate(1, 0);
        
        List<Coordinate> shortestPath = new ArrayList<>();
        shortestPath.add(new Coordinate(1, 0));
       
        assertEquals(1, MazeSolver.getShortestPath(smallWriteupMaze, src, tgt).size());
        assertEquals(shortestPath, MazeSolver.getShortestPath(smallWriteupMaze, src, tgt));
    }

    @Test
    public void noPathFound() {
        
        Coordinate src = new Coordinate(0, 0);
        Coordinate tgt = new Coordinate(0, 2);
        
        assertEquals(0, MazeSolver.getShortestPath(noPathMaze, src, tgt).size());
    }

    @Test
    public void noBlockedCoordMaze() {
        
        Coordinate src = new Coordinate(0, 0);
        Coordinate tgt = new Coordinate(0, 2);
      
        List<Coordinate> shortestPath = new ArrayList<>();
        
        shortestPath.add(src);
        shortestPath.add(new Coordinate(0, 1));
        shortestPath.add(tgt);
        
        assertEquals(shortestPath, MazeSolver.getShortestPath(noBlockedCoordMaze, src, tgt));
    }
}
