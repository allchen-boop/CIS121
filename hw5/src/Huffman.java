import java.util.HashMap;
import java.util.Map;

/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 */ 
public class Huffman {
    private Map<Character, Integer> alphabet; //frequency map
    private Map<Character, String> encode; //character (letter) encoding map
    private BinaryMinHeap<Integer, Node> pq; //priority queue
    
    private int input;
    private int output;

    private boolean compress; //records if compress has been called

    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     * 1 character
     */ 
    public Huffman(String seed) {
        if (seed == null) {
            throw new IllegalArgumentException("null seed");
        }

        if (seed.isEmpty()) {
            throw new IllegalArgumentException("empty seed");
        }

        alphabet = new HashMap<Character, Integer>();
        encode = new HashMap<Character, String>();
        pq = new BinaryMinHeapImpl<Integer, Node>();
        
        input = 0;
        output = 0;
        compress = false;

        //frequency map from seed string 
        for (int i = 0; i < seed.length(); i++) {
            if (!alphabet.containsKey(seed.charAt(i))) {
                //map doesn't already contain char so frequency is one
                alphabet.put(seed.charAt(i), 1);
            } else {
                //adding up frequencies by iteration through seed
                alphabet.put(seed.charAt(i), alphabet.get(seed.charAt(i)) + 1);
            } 
        }

        if (alphabet.size() == 1) {
            throw new IllegalArgumentException("resulting one character alphabet");
        }

        //creating leaf node for each character in map
        for (Map.Entry<Character, Integer> entry : alphabet.entrySet()) {
            Node curr = new Node(entry.getKey(), entry.getValue(), null, null);
            
            //adding to priority queue
            pq.add(curr.frequency, curr);
        }

        //creating huffman tree and associated encoding map
        build(pq);
        encodeMap(pq.peek().value, new StringBuilder());
    }


    private void build(BinaryMinHeap<Integer, Node> pq) {
        //extract two minimum frequencies from heap until 1 remains
        while (pq.size() > 1) {
            Node first = pq.extractMin().value;
            Node second = pq.extractMin().value;
   
            //add to create new single node
            int merge = first.frequency + second.frequency;

            //create internal node with extracted nodes as left and right children
            Node parent = new Node(null, merge, first, second);

            //insert back in heap
            pq.add(parent.frequency, parent);  
        }
        //remaining node is the root and tree is complete
    }

    private void encodeMap(Node node, StringBuilder string) {
        if (!node.isLeaf()) {
            StringBuilder left = new StringBuilder(string.toString());
            left.append(0);
            encodeMap(node.left, left);

            StringBuilder right = new StringBuilder(string.toString());
            right.append(1);
            encodeMap(node.right, right);
        } else {
            //when we reach the leaf node its encoding is stored in map
            encode.put(node.node, string.toString());
        }
    }
    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     * or has any non-positive frequencies
     */ 
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null) {
            throw new IllegalArgumentException("null alphabet");
        }

        if (alphabet.isEmpty()) {
            throw new IllegalArgumentException("empty alphabet");
        }

        if (alphabet.size() < 2) {
            throw new IllegalArgumentException("alphabet fewer than 2 characters");
        }

        this.alphabet = alphabet;
        encode = new HashMap<Character, String>();
        pq = new BinaryMinHeapImpl<Integer, Node>();
        
        input = 0;
        output = 0;
        compress = false;

        //create node for each character in map
        for (Map.Entry<Character, Integer> entry : alphabet.entrySet()) {
            if (entry.getValue() >= 0) {
                Node curr = new Node(entry.getKey(), entry.getValue(), null, null);
                pq.add(curr.frequency, curr);
            } else {
                throw new IllegalArgumentException("negative frequency");
            }
        }

        build(pq);
        encodeMap(pq.peek().value, new StringBuilder());
    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     * that are not compressible
     */ 
    public String compress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("null input");
        }
        
        if (input.isEmpty()) {
            return "";
        }

        StringBuilder compress = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (!alphabet.containsKey(input.charAt(i))) {
                throw new IllegalArgumentException("input contains non-compressible characters"); 
            } else { //when in the alphabet
                compress.append(encode.get(input.charAt(i)));
            }
        }

        this.compress = true;
        this.input += input.length();
        output += compress.toString().length();
        
        return compress.toString(); 
    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     * matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     * that are NOT 0 or 1, or input contains a sequence of bits that is not decodable
     */  
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("null input");
        }
        
        if (input.isEmpty()) {
            return "";
        }
        
        StringBuilder decompress = new StringBuilder();
        
        Node root = pq.peek().value;
        Node curr = root;
     
        //start from root
        for (int i = 0; i < input.length(); i ++) {
            if (input.charAt(i) == '0') {
                if (curr.left != null) {
                    curr = curr.left;
                }
            } else if (input.charAt(i) == '1') {
                if (curr.right != null) {
                    curr = curr.right;
                }
            } else {
                throw new IllegalArgumentException("input contains characters not 0 or 1"); 
            }

            //when leaf node is reached we've translated a character
            if (curr.isLeaf()) {
                decompress.append(curr.node);
                //going back to the root
                curr = root;
            } else if (i == input.length() - 1) { //haven't reached leaf but traversed input
                throw new IllegalArgumentException("non decodable");
            }
        }
        
        return decompress.toString();
    }

    /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     * calling this method
     */  
    public double compressionRatio() {
        if (compress) {
            return (double) output / ((double) input * 16.0);
        }
        throw new IllegalStateException("no calls have been made to compress");  
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * 
     * The expected encoding length is simply the sum of the length of the encoding of each 
     * character multiplied by the probability that character occurs. 
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */  
    public double expectedEncodingLength() {
        int frequency = 0;
        double expected = 0; 

        //sum all frequencies stored in frequency map 
        for (Map.Entry<Character, Integer> entry : alphabet.entrySet()) {
            frequency += entry.getValue();
        }
        
        //encoding length
        for (Map.Entry<Character, Integer> entry : alphabet.entrySet()) {
            expected += (double) entry.getValue() * 
                    (double) encode.get(entry.getKey()).length() / (double) frequency;
        }
        return expected;
    }

    //helper node class
    class Node implements Comparable <Node> {
        private Integer frequency; //key
        private Character node; //value

        private Node left;
        private Node right;

        
        public Node(Character node, Integer frequency, Node left, Node right) {
            this.frequency = frequency;
            this.node = node;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    
        @Override
        public int compareTo(Node o) {
            if (frequency == o.frequency) {
                return 0;
            } else if (frequency > o.frequency) {
                return 1;
            }
            return -1;
        }
        
        @Override
        public String toString() {
            return ("(" + node + ", " + frequency + " )");
        }
    }
}
