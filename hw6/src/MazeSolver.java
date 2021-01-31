import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayDeque;

final public class MazeSolver {

    public MazeSolver() {}

    /**
     * Returns a list of coordinates on the shortest path from {@code src} to {@code tgt}
     * by executing a breadth-first search on the graph represented by a 2D-array of size m x n.
     * <p>
     * Input {@code maze} guaranteed to be a non-empty and valid matrix.
     * Input {@code src} and {@code tgt} are guaranteed to be valid, in-bounds, and not blocked.
     * <p>
     * Do NOT modify this method header.
     *
     * @param maze the input maze, which is a 2D-array of size m x n
     * @param src The starting Coordinate of the path on the matrix
     * @param tgt The target Coordinate of the path on the matrix
     * @return an empty list if there is no path from {@param src} to {@param tgt}, otherwise an
     * ordered list of vertices in the shortest path from {@param src} to {@param tgt},
     * with the first element being {@param src} and the last element being {@param tgt}.
     * If {@param src} = {@param tgt}, you should return a SINGLETON list.
     * @implSpec This method should run in worst-case O(m x n) time.
     */

    public static List<Coordinate> getShortestPath(int[][] maze, Coordinate src, Coordinate tgt) {

        //tracks parent (mapping previous coordinate)
        Map<Coordinate, Coordinate> parent = new HashMap<>();

        //BFS queue
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        
        //add source coordinate to initialize
        queue.add(src); 

        //BFS based off of first BFS algorithm in notes (parent also tracks discovered)
        while (!queue.isEmpty() && !queue.peek().equals(tgt)) {
            Coordinate curr = queue.poll();

            for (Coordinate neighbor : neighbors(maze, curr.getX(), curr.getY())) {
               
                //if coordinate hasn't been visited
                if (!parent.containsKey(neighbor)) {
                    queue.add(neighbor);

                    //storing mapping between current and previously visited
                    parent.put(neighbor, curr);
                }
            }
        }
        return buildPath(parent, src, tgt);
    }
    

    private static List<Coordinate> buildPath(Map<Coordinate, Coordinate> parent,
                                                Coordinate src, Coordinate tgt) {
        
        List<Coordinate> path = new ArrayList<>();

        //no path
        if (!parent.containsKey(tgt)) {
            if (src.equals(tgt)) {
                path.add(src);
            }
            return path;
        }
        
        //tracing path from target to source
        path.add(tgt);
            
        while (tgt != src) {
            //adding in reverse
            path.add(0, parent.get(tgt));
            tgt = parent.get(tgt);
        }
        return path;
    }
    
    
    //want to do the same thing as Graph outNeighbors but modified for coordinates
    private static List<Coordinate> neighbors(int[][] maze, int x, int y) { 
        
        List<Coordinate> neighbors = new ArrayList<>();

        //checking space above
        if (y > 0 && validNeighbor(y - 1, x, maze)) {
            neighbors.add(new Coordinate(x, y - 1));
        }
        
        //checking space to the left
        if (x > 0 && validNeighbor(y, x - 1, maze)) {
            neighbors.add(new Coordinate(x - 1, y));
        }

        //checking space below
        if (y < maze.length - 1 && validNeighbor(y + 1, x, maze)) {
            neighbors.add(new Coordinate(x, y + 1));
        }
  
        //checking space to the right
        if (x < maze[0].length - 1 && validNeighbor(y, x + 1, maze)) {
            neighbors.add(new Coordinate(x + 1, y));
        } 
        
        return neighbors;
    }
       
    
    private static boolean validNeighbor(int x, int y, int [][] maze) {
        return maze[x][y] == 0;
    }
    
}

    