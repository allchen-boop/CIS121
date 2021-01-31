import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>QUESTION 2: ROOMMATE MATCHING</h1>
 * <p/>
 *
 * Class to implement the algorithm to select roommates from a 2d-array of pairs.
 * <p/>
 *
 * See description of problem and example in the write-up.
 *
 * @author bursztyn
 */
public class Question2 {

    /**
     * Returns the set of roommates who mutually requested each other.
     *
     * @param requests the 2d-array of pairs of requests of the form (student, requested roommate).
     *                 Note: a student cannot request themself. 
     * @return a set of pairs of roommates who mutually requested each other
     * 
     * @implSpec you may assume that input is valid and non-null. Do not throw any exceptions.
     */
    public static Set<Pair<String, String>> findMatches(String[][] requests) {
  
        Set<Pair<String, String>> roommates = new HashSet<>();
        Pair<String, String> match = new Pair<>(null, null);
        
        // alphabetically sorts requests array (first roommate in pair)
        Arrays.sort(requests, new Comparator<String[]>() {
           
            public int compare(String[] one, String[] two) {      
                return one[0].compareTo(two[0]);
            }         
        });
        

        int numRequests = requests.length - 1; 
        int i = 0;
        
        while (i <= numRequests) {
            int search = binarySearch(requests, requests[i][1], 0, numRequests);
            
            if (search == -1 || search >= numRequests) {
                i ++;
            } else {
                String first = requests[search][0];
                String second = requests[search][1];
                
                if (first.equals(requests[i][1])) {
                    if (second.equals(requests[i][0])) {     
                        match = new Pair<>(first, second);
                        roommates.add(match);
                        
                        // dealing with duplicate adding
                        requests[i][1] = "";
                    }
                    
                    i ++;
                }
            }
        }
        
        return roommates;
    }
    
    // binary search (from notes)
    private static int binarySearch(String[][] requests, String request, int lo, int hi) {
        int mid = (lo + hi) / 2;
        String midRequest = requests[mid][0];
        
        if (lo > hi) {
            return -1;
        } 

        if (request.equals(midRequest)) {
            return mid;
        } 
        
        if (request.compareTo(midRequest) > 0) {
            return binarySearch(requests, request, mid + 1, hi);
        } 
        
        return binarySearch(requests, request, lo, mid - 1);
    }
}