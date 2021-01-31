import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>QUESTION 4: AIRPORT CODE GAME -- OFF BY ONE!</h1>
 * <p/>
 *
 * Class to implement the algorithm to find the minimum number of hops a bunny rabbit
 * must make to navigate from the bottom right corner to the top left corner of the city.
 * <p/>
 *
 * See description of problem and example in the write-up.
 *
 * @author bursztyn
 */
public class Question4 {

    /**
     * Solves the "Airport Code Change" game. See complete problem description above.
     * <p/>
     * 
     * You may assume that all codes contain only UPPERCASE alphabetic (A-Z) characters.
     *
     * @param code1 the 3 letter airport code you want to start from
     * @param code2 the 3 letter airport code you want to end at
     * @param codes the set of 3 letter airport codes you can use to get from code1 -> code2
     * @return the smallest chain of airport codes to get from {@code code1 -> code2}. {@code code1}
     *         should be the first element, and {@code code2} should be the last. If no valid
     *         solution exists, return {@code null}. If {@code code1.equals(code2)}, return an empty
     *         list.
     *
     * @implSpec you may assume that all inputs are valid. Do not throw any exceptions.
     */
    public static List<String> getSmallestChain(String code1, String code2, Set<String> codes) {
        
        // litch raelly maze solver luv xx 
        
        Map<String, String> parent = new HashMap<>();

        //BFS queue
        ArrayDeque<String> queue = new ArrayDeque<>();
        
        //add source coordinate to initialize
        queue.add(code1); 

        //BFS based off of first BFS algorithm in notes (parent also tracks discovered)
        while (!queue.isEmpty() && !queue.peek().equals(code2)) {
            
            String curr = queue.poll();
            
            for (String code : codes) {
                if (!parent.containsKey(code)) {
                    if (oneLetterDiff(code, curr)) {
                        queue.add(code);
                        parent.put(code, curr);  
                    }
                }
            }
        }
        
        return buildPath(parent, code1, code2);
    }
    
    private static boolean oneLetterDiff(String code, String curr) {
       
        int i = 0;
        int diff = 0;
        
        while (i < code.length()) {
            if (code.charAt(i) != curr.charAt(i)) {
                diff ++;
            }
            
            i ++;   
        }
        
        return diff == 1;
    }

    //  exact same from maze solver hehe
    private static LinkedList<String> buildPath(Map<String, String> parent,
                                                String code1, String code2) {
        
        LinkedList<String> path = new LinkedList<>();

        //no path
        if (!parent.containsKey(code2)) {
            if (code1.equals(code2)) {
                return path;
            }
            return null;
        }
        
        //tracing path from target to source
        path.add(code2);
            
        while (!code2.equals(code1)) {
            //adding in reverse
            path.addFirst(parent.get(code2));
            code2 = parent.get(code2);
        }
        return path;
    }    
}