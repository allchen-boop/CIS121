import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


/**
 * <h1>QUESTION 1: FLIGHT SCHEDULES</h1>
 * <p/>
 *
 * Class to implement the algorithm to determine a possible flight schedule given the conditions.
 * <p/>
 *
 * See description of problem and example in the write-up.
 *
 * @author bursztyn
 */
public class Question1 {

    /**
     * Given the number of flights to schedule and conditions for departure, returns one possible
     * departure schedule.
     *
     * @param numFlights the number of flights waiting to take off
     * @param conditions the list of condition pairs (no duplicate pairs may exist)
     * @return one possible valid flight departure ordering, or an empty list if none exists
     * 
     * @implSpec you may assume that input is valid (well formatted, mx2 2d-array) and non-null.
     * Do not throw any exceptions.
     */
    public static List<Integer> getFlightDepartureSchedule(int numFlights, int[][] conditions) {
        // basically maze solver

        // list of each flight's dependent flights (adjacency list)
        List<ArrayList<Integer>> dependencies = new ArrayList<>();
    
        // number of flights needed to take off before flight 
        int [] flights = new int [numFlights];
        
        ArrayList<Integer> schedule = new ArrayList<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        
        // initializing graph
        for (int i = 0; i < numFlights; i ++) {
            dependencies.add(new ArrayList<>());
        }
        
        // adding dependent flights
        for (int [] condition : conditions) {
            int dependentFlight = condition[0];
            int flight = condition[1];
            
            dependencies.get(flight).add(dependentFlight);
            
            // increment number of dependent flights
            flights[dependentFlight] = flights[dependentFlight] + 1;
        }
        
        int i = 0;
        
        // flights with no dependencies are added to BFS queue
        while (i < numFlights) {
            if (flights[i] == 0) {
                queue.add(i);
            }
            i ++;
        }
       
        // BFS
        while (!queue.isEmpty()) {     
            int curr = queue.poll();
            
            for (int element : dependencies.get(curr)) {
                if (flights[element]-- == 1) {
                    queue.add(element);
                }
            }
            schedule.add(curr);
        }
        
        if (schedule.size() != numFlights) { 
            schedule = new ArrayList<Integer>();
        } 
        
        return schedule;
    }
}