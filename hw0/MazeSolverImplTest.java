import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MazeSolverImplTest {

    private int[][] smallWriteupMaze;
    private int[][] bigWriteupMaze;
    private int[][] noBlockedCoordMaze;
    private int[][] noPathMaze;
    private int[][] invalidDimensionMaze;
    private int[][] solutionPath;
    private int[][] nullMaze;
    private int[][] emptyMaze;
    

    @Before
    public void setupTestMazes() {
        smallWriteupMaze = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };

        bigWriteupMaze = new int[][]{
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 1, 1, 0, 1, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
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
        
        invalidDimensionMaze = new int[][] {
                {1},
        };
        
        emptyMaze = new int[][] {
        };

    }

    @Test
    public void testReturnsSmallMazeSolutionPathInWriteup() {
        int[][] solutionPath = {
                {1, 1, 1, 0},
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 0, 0}
        };
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(0, 0), new Coordinate(0, 2)));
    }

    @Test
    public void testReturnsBigMazeSolutionPathInWriteup() {
        int[][] bigWriteupSolution = new int[][]{
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int[][] returnedPath = MazeSolverImpl.solveMaze(bigWriteupMaze, new Coordinate(2, 0),
                new Coordinate(4, 0));
        assertArrayEquals(bigWriteupSolution, returnedPath);
    }

    /*
      Note: the above tests are the ones included in the writeup and NOT exhaustive. The autograder
      uses other test cases not listed above. Please thoroughly read all stub files, including
      CoordinateTest.java!

      For help with creating test cases, please see this link:
      https://www.seas.upenn.edu/~cis121/current/testing_guide.html
     */

    @Test
    public void testNoBlockedCoords() {
        int[][] solutionPath = {
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
        };
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(noBlockedCoordMaze,
                new Coordinate(0, 0), new Coordinate(0, 3)));
    }
    
    @Test
    public void testNoPath() {
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(noPathMaze,
                new Coordinate(0, 0), new Coordinate(0, 3)));
    }
    
    @Test
    public void testSameSourceAndGoal() {
        int[][] solutionPath = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(noBlockedCoordMaze,
                new Coordinate(0, 0), new Coordinate(0, 0)));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testNullMaze() {
        MazeSolverImpl.solveMaze(nullMaze, new Coordinate(0,0), new Coordinate(0, 3));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSourceOutOfBounds() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(10, 10), new Coordinate(0, 0));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testOutOfBoundsNegativeSourceCoord() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(-1, -1), new Coordinate(0, 0));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testGoalOutOfBounds() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(0, 0), new Coordinate(10, 10));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testOutOfBoundsNegativeGoalCoord() {
        MazeSolverImpl.solveMaze(noBlockedCoordMaze, new Coordinate(0, 0), new Coordinate(-1, -1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSourceAndGoalOutOfBounds() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(-1, 6), new Coordinate(6, -1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSourceOnBlockedTile() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(0, 1), new Coordinate(0, 0));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testGoalOnBlockedTile() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(0,0), new Coordinate(3, 2));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSourceAndGoalOnBlockedTile() {
        MazeSolverImpl.solveMaze(smallWriteupMaze, new Coordinate(0,1), new Coordinate(0, 1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testInvalidMazeDimensions() {
        MazeSolverImpl.solveMaze(invalidDimensionMaze, new Coordinate(0,0), new Coordinate(0, 0));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testEmptyMaze() {
        MazeSolverImpl.solveMaze(emptyMaze, new Coordinate(0,0), new Coordinate(0,0));
    }

}
