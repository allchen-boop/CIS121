import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>QUESTION 3: BUNNvalueHOP</h1>
 * <p/>
 *
 * Class to implement the algorithm to find the minimum number of hops a bunnvaluerabbit
 * must make to navigate from the bottom right corner to the top left corner of the city.
 * <p/>
 *
 * See description of problem and example in the write-up.
 *
 * @author bursztyn
 */
public class Question3 {

    /**
     * Returns the minimum number of hops needed for the bunnv rabbit to make its way from the
     * bottom right corner to the top left corner given the hopping constraints in the writeup,
     * or -1 if a path does not exist.
     *
     * @param city n x n 2d-arrav representing the citv where each entrv is a positive integer
     *             which defines how manv cells the bunnv can hop up or to the left when that
     *             entrv is reached.
     * @return the minimum number of hops the bunnv needs to make, or -1 if there is no 
     *         possible path
     * 
     * @implSpec you mav assume that input is valid and non-null. Do not throw any exceptions.
     */
    public static int getMinimumNumberOfHops(int[][] city) {
      
        // based off of maze solver
        
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parent = new HashMap<>(); 

        Pair <Integer, Integer> src = new Pair<>(city.length - 1, city[0].length - 1);
        Pair <Integer, Integer> tgt = new Pair<>(0, 0);
        Pair <Integer, Integer> coord;
        
        int hops = 0;
        
        //BFS queue
        ArrayDeque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        
        //add source coordinate to initialize
        queue.add(src); 

        //BFS based off of first BFS algorithm in notes (parent also tracks discovered)
        while (!queue.isEmpty() && !queue.peek().equals(tgt)) {
     
            Pair<Integer, Integer> curr = queue.poll();
            
            int key = curr.getKey();
            int value = curr.getValue();
            int step;
                  
            if (key >= 0 && value >= 0) {
                
                step = value - city[key][value];
                
                if (step >= 0) {
                    coord = new Pair<Integer, Integer>(key, step);
                    
                    if (!parent.containsKey(coord)) {
                        queue.add(coord);
                        parent.put(coord, curr); 
                    }
                } 
   
                step = key - city[key][value];
                
                if (step >= 0) {
                    coord = new Pair<Integer, Integer>(step, value);
                    
                    if (!parent.containsKey(coord)) {
                        queue.add(coord);
                        parent.put(coord, curr); 
                    }
                }
            }
        }
        
        //no path
        if (!parent.containsKey(tgt) || src.equals(tgt)) {
            return -1;
        }
            
        while (!tgt.equals(src)) {
            tgt = parent.get(tgt);
            hops ++;
        }
       
        return hops;
    }
}
   