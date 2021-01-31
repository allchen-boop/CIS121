import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.Test;



public class BinaryMinHeapImplTest {
    

    @Test
    public void createIntHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void createStringHeap() {
        BinaryMinHeapImpl<Integer, String> heap = new BinaryMinHeapImpl<Integer, String>();
        
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void size() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertEquals(0, heap.size());
        
        heap.add(1, 1);
        assertEquals(1, heap.size());
        
        heap.add(2, 2);
        assertEquals(2, heap.size());
    }
    
    @Test
    public void empty() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void notEmpty() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        assertFalse(heap.isEmpty());
    }

 
    @Test
    public void containsIntValue() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();

        heap.add(1, 1);
        assertTrue(heap.containsValue(1));
    }
    
    @Test
    public void containsStringValue() {
        BinaryMinHeapImpl<Integer, String> heap = new BinaryMinHeapImpl<Integer, String>();

        heap.add(1, "1");
        assertTrue(heap.containsValue("1"));
    }
    
    @Test
    public void containsNullValue() {
        BinaryMinHeapImpl<Integer, String> heap = new BinaryMinHeapImpl<Integer, String>();
        
        heap.add(1, null);
        assertTrue(heap.containsValue(null));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void addNullKey()  {
        BinaryMinHeapImpl<Integer, String> heap = new BinaryMinHeapImpl<Integer, String>();
        
        heap.add(null, null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void addValAlreadyInHeap()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();

        heap.add(1, 1);
        assertTrue(heap.containsValue(1));
        
        heap.add(2, 1);
    }
    
    @Test
    public void addKeyAlreadyInHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        assertEquals(2, heap.size());
        
        heap.add(2, 3);
        assertEquals(3, heap.size());
    }
    
    @Test
    public void add()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        heap.add(3, 3);
        heap.add(4, 4);
        assertEquals(4, heap.size());
    }
    
    @Test (expected = NoSuchElementException.class)
    public void decreaseKeyValNotInHeap()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();

        heap.decreaseKey(1, 1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void decreaseNullNewKey()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.decreaseKey(1, null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void decreaseGreaterNewKey()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        heap.decreaseKey(2, 3);
    }
    
    @Test
    public void decreaseKeyOnce()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(2, 2);
        heap.decreaseKey(2, 1);
        assertEquals(1, heap.size());
    }
    
    @Test
    public void multipleCallsToDecreaseKey()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        heap.add(3, 3);
        
        heap.decreaseKey(2, 1);
        heap.decreaseKey(3, 1);
    }
    
    @Test
    public void decreaseKeyNoChange()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        heap.decreaseKey(1, 1);
    }

    
    @Test (expected = NoSuchElementException.class)
    public void peekEmptyHeap()  {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.peek();
    }
    
    @Test
    public void peek() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(2, 2);
        heap.add(1, 1);
        heap.add(3, 3);

        int peek = heap.peek().value;
        assertEquals(1, peek);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void extractMinEmptyHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.extractMin();
    }
    
    @Test 
    public void extractMin() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(2, 2);
        heap.add(1, 1);
        heap.add(3, 3);
        
        int peek = heap.peek().value;
        
        assertEquals(1, peek);
        assertEquals(3, heap.size());
        
        heap.extractMin();
        assertFalse(heap.containsValue(1));
        assertEquals(2, heap.size());
        
        int extractPeek = heap.peek().value;
        assertEquals(2, extractPeek);
        assertEquals(2, heap.size());
    }
    
    @Test 
    public void extractMinAll() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        
        heap.add(2, 2);
        heap.add(1, 1);
        heap.add(3, 3);
        
        int peek = heap.peek().value;
        
        assertEquals(1, peek);
        assertEquals(3, heap.size());
        
        heap.extractMin();
        assertFalse(heap.containsValue(1));
        assertEquals(2, heap.size());

        heap.extractMin();
        assertFalse(heap.containsValue(2));
        assertEquals(1, heap.size()); 

        heap.extractMin(); 
        assertFalse(heap.containsValue(3)); 
        assertEquals(0, heap.size());
    }
 
    
    
    @Test
    public void setInt() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        HashSet<Integer> set = new HashSet<Integer>();
        
        heap.add(1, 1);
        heap.add(2, 2);
        heap.add(3, 3);
        
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(set, heap.values());
    }
    
    @Test
    public void setString() {
        BinaryMinHeapImpl<Integer, String> heap = new BinaryMinHeapImpl<Integer, String>();
        HashSet<String> set = new HashSet<String>();
        
        heap.add(1, "1");
        heap.add(2, "2");
        heap.add(3, "3");

        set.add("1");
        set.add("2");
        set.add("3");
        
        assertEquals(set, heap.values());
    }
}

