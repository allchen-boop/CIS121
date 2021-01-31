import java.util.List;

/**
 * <h1>QUESTION 5: ODD SIZED FAMILY TREES</h1>
 * <p/>
 *
 * Class to implement the algorithm to determine the number of odd sized sub-trees in a given
 * family tree.
 * <p/>
 *
 * See description of problem and example in the write-up.
 *
 * @author bursztyn
 */
public class Question5 {
    
    // storing subtrees of each node
    private static int [] subtreeSizes;

    /**
     * Returns the number of odd sized sub-trees in a given family tree.
     *
     * @param family an adjacency list of the family tree
     * @param root the favorite number of the root of the family tree
     * @return the number of odd sized sub-trees
     * 
     * @implSpec you may assume that input is valid and non-null. Do not throw any exceptions.
     */
    public static int getNumberOfOddSubtrees(List<List<Integer>> family, int root) {
        // based off that one algorithm hw problem cause i just finished a regrade for it :0
        
        int oddSubtrees = 0;
        int treeSize = family.size();
        int i = 0;
                
        subtreeSizes = new int[treeSize];
        
        // all nodes have subtree size 1 (at least)
        for (int j = 0; j < treeSize; j ++) {
            subtreeSizes[j] = 1;
        }
        
        subtreeNodes(family, root);
           
        // go through entire tree to count the odd elements of the size array
        while (i < treeSize) {
            if (subtreeSizes[i] % 2 == 1) {
                oddSubtrees ++;
            }
            
            i ++;
        }

        return oddSubtrees;
    }

    
    // kinda based off recursive dfs from notes
    private static int subtreeNodes(List<List<Integer>> family, int node) {
        
        boolean [] visited = new boolean[family.size()];
        visited[node] = true;
        
        for (int curr : family.get(node)) { 
            if (!visited[curr]) {
                visited[curr] = true;System.out.println(visited[curr]);
                subtreeSizes[node] += subtreeNodes(family, curr);
            }
        }
       
        return subtreeSizes[node];
    }

}