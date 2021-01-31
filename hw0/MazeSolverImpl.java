public class MazeSolverImpl {

    /**
     * You should write your code within this method. A good rule of thumb, especially with
     * recursive problems like this, is to write your input exception handling within this
     * method and then use helper methods to carry out the actual recursion.
     * <p>
     * How you set up the recursive methods is up to you, but note that since this is a *static*
     * method, all helper methods that it calls must *also* be static. Make them package-private
     * (i.e. without private or public modifiers) so you can test them individually.
     *
     * @param maze See the writeup for more details about the format of the input maze.
     * @param sourceCoord The source (starting) coordinate
     * @param goalCoord The goal (ending) coordinate
     * @return a matrix of the same dimension as the input maze containing the solution
     * path marked with 1's, or null if no path exists. See the writeup for more details.
     * @throws IllegalArgumentException in the following instances:
     * 1. If the maze is null
     * 2. If m * n <= 1 where m and n are the dimensions of the maze
     * 3. If either the source coordinate OR the goal coordinate are out of the matrix bounds.
     * 4. If your source or goal coordinate are on a blocked tile.
     */
    public static int[][] solveMaze(int[][] maze, Coordinate sourceCoord, Coordinate goalCoord) {
        
        if (maze == null) {
            throw new IllegalArgumentException();
        }
        
        //empty or 1 x 1 maze
        if (maze.length == 0 || (maze.length == 1 && maze[0].length == 1)) {
            throw new IllegalArgumentException();
        }
        
        if (sourceCoord.getX() > maze[0].length - 1 || sourceCoord.getX() < 0 ||
                sourceCoord.getY() > maze.length - 1 || sourceCoord.getY() < 0 ||
                goalCoord.getX() > maze[0].length - 1 || goalCoord.getX() < 0 ||
                goalCoord.getY() > maze.length - 1 || goalCoord.getY() < 0) {
            throw new IllegalArgumentException();
        }
        
        if (maze[sourceCoord.getY()][sourceCoord.getX()] == 1 ||
                maze[goalCoord.getY()][goalCoord.getX()] == 1) {
            throw new IllegalArgumentException();
        }
        
        int [][] solvedMaze = new int [maze.length][maze[0].length];
        
        int sourceX = sourceCoord.getX();
        int sourceY = sourceCoord.getY();
        int goalX = goalCoord.getX();
        int goalY = goalCoord.getY();
        
        
        if (pathHelper(maze, sourceX, sourceY, goalX, goalY)) {
            for (int i = 0; i < maze.length; i ++) { 
                for (int j = 0; j < maze[0].length; j ++) {
                    //so that the blocked coordinates don't get marked
                    if (maze [i][j] == 2) {
                        solvedMaze [i][j] = 1;
                    } 
                }  
            }
            return solvedMaze;
        } else {
            return null; 
        }
    }
    
  
    
    //if there's a path to goal and labels solution maze with 0, 1, 2
    private static boolean pathHelper(int[][] maze, int currentX, int currentY,
                                                    int goalX, int goalY) {
        //base cases
        if (currentX > maze[0].length - 1 || currentX < 0 ||
                currentY > maze.length - 1 || currentY < 0) {
            return false;
        }
      
        if (currentX == goalX && currentY == goalY) {
            maze [currentY][currentX] = 2;
            return true;
        }
        
        if (maze[currentY][currentX] == 1) { //blocked
            return false;
        }
        
        if (maze[currentY][currentX] == 2) { //visited
            return false;
        }
        
        //marking tile as part of path
        maze[currentY][currentX] = 2;
       
        if (pathHelper(maze, currentX, currentY + 1, goalX, goalY)) { //down
            return true;
        }
        
        if (pathHelper(maze, currentX, currentY - 1, goalX, goalY)) { //up
            return true;
        } 
        
        if (pathHelper(maze, currentX - 1, currentY, goalX, goalY)) { //left
            return true;
        } 
        
        if (pathHelper(maze, currentX + 1, currentY, goalX, goalY)) { //right
            return true;
        } 
        
        //unmarking as part of path
        maze[currentY][currentX] = 0;    
        return false;
        
    }    
}
