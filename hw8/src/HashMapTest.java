import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.junit.Test;


public class HashMapTest {
    
    // from writeup
    static class MockHashObject {
        private final int hashCode;
        
        public MockHashObject(int hashCode) {
            this.hashCode = hashCode;
        }
        
        @Override
        public int hashCode() { 
            return hashCode; 
        }
    }
    
    @Test
    public void testCollisionWritUp() {
        HashMap<Object, String> map = new HashMap<Object, String>();
        
        MockHashObject obj1 = new MockHashObject(5);
        MockHashObject obj2 = new MockHashObject(5);
        
        map.put(obj1, "foo");
        map.put(obj2, "bar");
        
        assertTrue(map.containsKey(obj1));
        assertTrue(map.containsKey(obj2));
        
        assertTrue(map.containsValue("foo"));
        assertTrue(map.containsValue("bar"));
    }
  
    
    @Test
    public void testGet() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        map.put(obj, "foo");
        assertEquals("foo", map.get(obj));
    }
    
    @Test
    public void testGetNull() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        assertNull(map.get(obj));
    }

    @Test
    public void testGetNullKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
       
        MockHashObject obj = null;
        map.put(obj, null);
        assertNull(map.get(obj));
    }

    
    @Test
    public void testContainsKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        map.put(obj, "foo");
        
        assertTrue(map.containsKey(obj));
        assertFalse(map.containsKey(null));
    }
    
    @Test
    public void testContainsNullKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = null;
        map.put(obj, "foo");
        
        assertTrue(map.containsKey(obj));
        assertTrue(map.containsKey(null));
    }
    
    @Test
    public void testContainsKeyFalse() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        assertFalse(map.containsKey(obj));
        assertFalse(map.containsKey(null));
    }
    
    @Test
    public void testPut() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        assertNull(map.put(obj, "foo"));
        assertEquals(1, map.size());
        assertTrue(map.containsKey(obj));
 
    }
    
    @Test
    public void testPutMulitple() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
       
        assertNull(map.put(obj1, "foo"));
        assertEquals(1, map.size());

        assertEquals("foo", map.put(obj1, "bar"));
        assertEquals(1, map.size());
        assertTrue(map.containsKey(obj1));
        assertTrue(map.containsValue("bar"));
        assertFalse(map.containsValue("foo"));
        
        assertNull(map.put(obj2, "bruh"));
        assertEquals(2, map.size());
        assertTrue(map.containsKey(obj2));
        assertTrue(map.containsValue("bruh"));
    }
    
    @Test
    public void testPutNoKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
        
        assertNull(map.put(obj1, "foo"));
        assertEquals(1, map.size());
        assertFalse(map.containsKey(obj2));
        assertTrue(map.containsValue("foo"));
    }
    
    @Test
    public void testResize() {
        HashMap<Object, String> map = new HashMap<Object, String>(4);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());

        map.put(obj1, "foo");
        map.put(obj2, "bar");
       
        assertEquals("foo", map.get(obj1));
        assertEquals("bar", map.get(obj2));
     
        MockHashObject obj3 = new MockHashObject(map.hashCode());
        MockHashObject obj4 = new MockHashObject(map.hashCode());
        
        map.put(obj3, "harry");
        map.put(obj4, "styles");
        
        assertEquals("foo", map.get(obj1));
        assertEquals("bar", map.get(obj2));
        assertEquals("harry", map.get(obj3));
        assertEquals("styles", map.get(obj4));
        
        map.resize(16);
        assertEquals(4, map.size());
        assertEquals(12, map.getThreshold());
        
    }
    
    @Test
    public void testMaxResize() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
        MockHashObject obj3 = new MockHashObject(map.hashCode());
        MockHashObject obj4 = new MockHashObject(map.hashCode());

        map.put(obj1, "foo");
        map.put(obj2, "bar");
        map.put(obj3, "harry");
        map.put(obj4, "styles");
       
        assertEquals("foo", map.get(obj1));
        assertEquals("bar", map.get(obj2));
        assertEquals("harry", map.get(obj3));
        assertEquals("styles", map.get(obj4));
        
        // max capacity
        map.resize(1 << 30);
        assertEquals(4, map.size());
        assertEquals(Integer.MAX_VALUE, map.getThreshold());
        
    }
    
    @Test
    public void testRemove() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
  
        map.put(obj, "foo");
        assertEquals(1, map.size());
        assertEquals("foo", map.remove(obj));
        assertEquals(0, map.size());
    }
    
    @Test
    public void testRemoveNullKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
  
        map.put(obj1, null);
        assertEquals(1, map.size());
        
        assertNull(map.remove(obj2));
        assertEquals(1, map.size());
        
        assertNull(map.remove(obj1));
        assertEquals(0, map.size());
    }
    
    @Test
    public void testRemoveNoKey() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        assertNull(map.remove(obj1));
        
    }
    
    @Test
    public void testRemoveSame() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
        
        map.put(obj1, "foo");
        map.put(obj2, "foo");
  
        assertEquals("foo", map.remove(obj1));
        assertEquals("foo", map.remove(obj2));
        assertNull(map.remove(obj1));
    }
    
  
    @Test
    public void testRemoveDuplicatePut() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        map.put(obj, "foo");
        map.put(obj, "bar");
        
        assertEquals("bar", map.get(obj));
        assertFalse(map.containsValue("foo"));
        assertEquals(1, map.size());
        
        assertEquals("bar", map.remove(obj));
        assertFalse(map.containsKey(obj));
        assertEquals(0, map.size());
    }

    
    @Test
    public void testContainsValue() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        map.put(obj, "foo");
        assertTrue(map.containsValue("foo"));
    }
    
    @Test
    public void testContainsValueFalse() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        assertFalse(map.containsValue("foo"));
    }
    
    @Test
    public void testContainsNullValue() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        map.put(obj, null);
        assertTrue(map.containsValue(null));
    }
    
    @Test
    public void testNullKeyAndValue() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = null;
        
        map.put(obj1, "foo");
        map.put(obj2, null);
        
        assertTrue(map.containsKey(obj1));
        assertTrue(map.containsKey(obj2));
        
        assertTrue(map.containsValue("foo"));
        assertTrue(map.containsValue(null));
    }
   
    
    @Test
    public void testClear() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        MockHashObject obj = new MockHashObject(map.hashCode());

        map.put(obj, "foo");
        assertEquals(1, map.size());
        
        map.clear();
        assertEquals(0, map.size());
        assertFalse(map.containsKey(obj));
    }
    
    @Test
    public void testClearLarge() {
        HashMap<Object, Integer> map = new HashMap<Object, Integer>(5);
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        for (int i = 0; i < 50; i ++) {
            map.put(obj, i);
        }
        
        map.clear();
        assertEquals(0, map.size());
    }
    

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() {
        HashMap<Object, String> map = new HashMap<Object, String>(5);
        
        MockHashObject obj = new MockHashObject(map.hashCode());
        Iterator<Entry<Object, String>> iter = map.entryIterator();
        
        map.put(obj, "foo");
        iter.next();
        iter.next();
    }
    
    @Test
    public void testIterator() {
        HashMap<Object, Object> map = new HashMap<Object, Object>(5);
        MockHashObject obj = new MockHashObject(map.hashCode());
        
        HashMap.Entry<Object, Object> entry = new HashMap.Entry<Object, Object>(obj, "foo", null);
        
        map.put(entry.getKey(), entry.getValue());
        
        Iterator<Entry<Object, Object>> iter = map.entryIterator();
        
        assertTrue(iter.hasNext());
        assertEquals(entry, iter.next());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testIteratorLarge() {
        HashMap<Object, Object> map = new HashMap<Object, Object>(5);
        
        MockHashObject obj1 = new MockHashObject(map.hashCode());
        MockHashObject obj2 = new MockHashObject(map.hashCode());
        MockHashObject obj3 = new MockHashObject(map.hashCode());
        MockHashObject obj4 = new MockHashObject(map.hashCode());
        
        HashMap.Entry<Object, Object> entry1 = new HashMap.Entry<Object, Object>(obj1, "foo", null);
        HashMap.Entry<Object, Object> entry2 = new HashMap.Entry<Object, Object>(obj2, "bar", null);
        HashMap.Entry<Object, Object> entry3 =  new HashMap.Entry<Object, Object>(obj3, "uh", null);
        HashMap.Entry<Object, Object> entry4 =  new HashMap.Entry<Object, Object>(obj4, "um", null);
        
        map.put(entry1.getKey(), entry1.getValue());
        map.put(entry2.getKey(), entry2.getValue());
        map.put(entry3.getKey(), entry3.getValue());
        map.put(entry4.getKey(), entry4.getValue());
        
        Iterator<Entry<Object, Object>> iter = map.entryIterator();
        
        assertTrue(iter.hasNext());
        assertEquals(entry4, iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(entry3, iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(entry2, iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(entry1, iter.next());
        
        assertFalse(iter.hasNext());
    
    }
       
}