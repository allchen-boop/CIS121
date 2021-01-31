import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class HuffmanTest {
    
    @Test
    public void testHuffmanSimplePiazza() {
        String seed = "abbccc";
        Huffman simpleHuff = new Huffman(seed);
        String compressed = simpleHuff.compress("abc");
        assertEquals(5, compressed.length());
        assertEquals("abc", simpleHuff.decompress(compressed));
        assertEquals(5.0 / 48, simpleHuff.compressionRatio(), .00001);
        assertEquals(1.5, simpleHuff.expectedEncodingLength(), .00001);
    }


    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanNullSeed() {
        String seed = null;
        Huffman huffman = new Huffman(seed);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanEmptySeed() {
        String seed = "";
        Huffman huffman = new Huffman(seed);
    }
    
    @Test  (expected = IllegalArgumentException.class)
    public void createHuffmanOneCharacterSeed() {
        String seed = "a";
        Huffman huffman = new Huffman(seed);
    }
    
    @Test  (expected = IllegalArgumentException.class)
    public void createHuffmanResultingOneCharacterAlphabet() {
        String seed = "aaaaaaa";
        Huffman huffman = new Huffman(seed);
    }
    
    @Test 
    public void createHuffmanValidSeed() {
        String seed = "aaabb";
        Huffman huffman = new Huffman(seed);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanNullAlphabet() {
        Map<Character, Integer> alphabet = null;
        Huffman huffman = new Huffman(alphabet);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanEmptyAlphabet() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        Huffman huffman = new Huffman(alphabet);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanAlphabetFewerThanTwoCharacters() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        alphabet.put('a', 1);
        Huffman huffman = new Huffman(alphabet);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createHuffmanNegativeFrequencies() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        alphabet.put('a', -1);
        alphabet.put('b', 1);
        Huffman huffman = new Huffman(alphabet);
    }
    
    @Test 
    public void createHuffmanValidAlphabet() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        alphabet.put('a', 1);
        alphabet.put('b', 1);
        alphabet.put('c', 1);
        Huffman huffman = new Huffman(alphabet);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void compressNullInput() {
        String seed = "abc";
        Huffman huffman = new Huffman(seed);
        
        String input = null;
        huffman.compress(input);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void compressNonCompressibleInput() {
        String seed = "abc";
        Huffman huffman = new Huffman(seed);
        
        String input = "abcd";
        huffman.compress(input);
    }
    
    
    @Test
    public void compressEmptyInput() {
        String seed = "abc";
        Huffman huffman = new Huffman(seed);
        
        String input = "";
        String compressed = huffman.compress(input);
        
        assertEquals(0, compressed.length());
        assertEquals("", compressed); 
    }
    
    @Test
    public void compressOneCharacterInput() {
        String seed = "aaabb";
        Huffman huffman = new Huffman(seed);
        
        String input = "a";
        String compressed = huffman.compress(input);
        
        assertEquals(1, compressed.length());
        assertEquals("1", compressed); 
    }
    
    @Test
    public void compressTwoCharacters() {
        String seed = "aaabb";
        Huffman huffman = new Huffman(seed);
        
        String input = "ab";
        String compressed = huffman.compress(input);
        
        assertEquals(2, compressed.length());
        assertEquals("10", compressed); 
    }
    
    @Test
    public void compressLargeSeedOneCharacterInput() {
        String seed = "aaabbccd";
        Huffman huffman = new Huffman(seed);

        String a = huffman.compress("a");
        assertEquals("0", a); 

        String d = huffman.compress("d");
        assertEquals(3, d.length()); 
        
    }
  
    
    @Test
    public void testCompressFrequencyMap() {
        HashMap<Character, Integer> alphabet = new HashMap<Character, Integer>();
        alphabet.put('a', 2);
        alphabet.put('b', 1);
        alphabet.put('c', 4);
        alphabet.put('d', 2);
        alphabet.put('e', 2);
        alphabet.put('f', 1);
        
        Huffman huffman = new Huffman(alphabet);
        
        String a = huffman.compress("a");
        assertEquals(3, a.length()); 
        
        String b = huffman.compress("b");
        assertEquals(3, b.length());
        
        String c = huffman.compress("c");
        assertEquals(2, c.length()); 
        
        String d = huffman.compress("d");
        assertEquals(3, d.length());
        
        String e = huffman.compress("e");
        assertEquals(2, e.length());
        
        String f = huffman.compress("f");
        assertEquals(3, f.length());
    }

    @Test (expected = IllegalArgumentException.class)
    public void decompressNullInput() {
        String seed = "aaabb";
        Huffman huffman = new Huffman(seed);
        
        String input = null;
        huffman.decompress(input);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void decompressInputContainsInvalidCharacters() {
        String seed = "aaabb";
        Huffman huffman = new Huffman(seed);
        
        String input = "12";
        huffman.decompress(input);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void decompressNonDecodableInput() {
        String seed = "aabc";
        Huffman huffman = new Huffman(seed);
        
        String a = huffman.compress("a");
        assertEquals("0", a); 
        
        String b = huffman.compress("b");
        assertEquals("10", b); 
        
        String c = huffman.compress("c");
        assertEquals("11", c); 
        
        String input = "111";
        huffman.decompress(input); 
   
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void decompressNonDecodableSingeFrequencyInput() {
        String seed = "abc";
        Huffman huffman = new Huffman(seed);
        
        String input = "01";
        huffman.decompress(input); 
    }
   
    
    @Test
    public void decompressEmptyInput() {
        String seed = "aabbcc";
        Huffman huffman = new Huffman(seed);
        
        String input = "";
        String decompressed = huffman.decompress(input);
        
        assertEquals(input, decompressed);
    }
    
    @Test
    public void decompressCompressed() {
        String seed = "aabbcc";
        Huffman huffman = new Huffman(seed);
        
        String input = "abc";
        String compressed = huffman.compress(input);
        String decompressed = huffman.decompress(compressed);
        
        assertEquals(input, decompressed);
    }
    
    @Test
    public void decompressVariedInputs() {
        String seed = "abbc";
        Huffman huffman = new Huffman(seed);

        assertEquals("a", huffman.decompress("10"));
        assertEquals("b", huffman.decompress("0"));
        assertEquals("c", huffman.decompress("11"));
        
        assertEquals("ab", huffman.decompress("100"));
        assertEquals("bc", huffman.decompress("011"));
        assertEquals("ac", huffman.decompress("1011"));
        

        assertEquals("abc", huffman.decompress("10011"));
        assertEquals("cba", huffman.decompress("11010"));
        assertEquals("bbb", huffman.decompress("000"));
        
        assertEquals("aacb", huffman.decompress("1010110"));
        assertEquals("ccbbaa", huffman.decompress("1111001010"));
        
    }
    
    @Test
    public void decompressVariedFrequencies() {
        String seed = "aaabbcd";
        Huffman huffman = new Huffman(seed);

        assertEquals("a", huffman.decompress("0"));
        assertEquals("b", huffman.decompress("10"));
        assertEquals("c", huffman.decompress("110"));
        assertEquals("d", huffman.decompress("111")); 
    }
    
    

    @Test (expected = IllegalStateException.class) 
    public void compressionRatioNoCallsToCompress() {
        String seed = "aabbcc";
        Huffman huffman = new Huffman(seed);
        
        huffman.compressionRatio();
    }
    
    @Test 
    public void compressionRatio() {
        String seed = "aabbccdd";
        Huffman huffman = new Huffman(seed);
        
        String input = "aabcd";
        huffman.compress(input);
        
        assertEquals(10.0 / (5.0 * 16), huffman.compressionRatio(), 0.00001);
    }
    
    @Test 
    public void compressionRatioMultipleCompressionCalls() {
        String seed = "aabbccdd";
        Huffman huffman = new Huffman(seed);
        
        huffman.compress("ab");
        huffman.compress("ab");
        huffman.compress("ab");

        assertEquals(12.0 / (6.0 * 16), huffman.compressionRatio(), 0.00001);
    }
    
    @Test 
    public void expectedEncodingLengthDiffFrequencies() {
        String seed = "aab";
        Huffman huffman = new Huffman(seed);
  
        assertEquals(1.0, huffman.expectedEncodingLength(), 0.00001);
    }
}
