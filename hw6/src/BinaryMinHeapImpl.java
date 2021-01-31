import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    private ArrayList<Entry<Key, V>> heap;
    private HashMap<V, Integer> map; //maps values in heap to their index
    
    
    //constructor
    public BinaryMinHeapImpl() {
        heap = new ArrayList<Entry<Key, V>>();
        map = new HashMap<>();
    }
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.size() == 0;
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public boolean containsValue(V value) {
        return map.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        
        if (containsValue(value)) {
            throw new IllegalArgumentException("value already contained in heap");
        }

        //add to end of heap
        heap.add(new Entry<Key, V>(key, value));
 
        //updates map from add- stores value with last index
        map.put(value, size() - 1);
        
        //to satisfy min heap requirements
        traverse(size() - 1);
    }
    


    /**
     * {@inheritDoc}
     */ 
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException("value not in heap");
        }
        
        if (newKey == null) {
            throw new IllegalArgumentException("new key is null");
        }
 
        //find entry of given value
        int index = map.get(value);
        Entry<Key, V> entry = heap.get(index);

        //checking if newKey > key(value)
        if (newKey.compareTo(entry.key) > 0) {
            throw new IllegalArgumentException("new key is greater than key (value)");
        }
        
        //set the new key in heap
        heap.set(index, new Entry<Key, V>(newKey, value));

        traverse(index);  
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public Entry<Key, V> peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty heap");
        }
        return heap.get(0); 
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public Entry<Key, V> extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty heap");
        }
        
        //if not empty remove min from map and heap
        Entry<Key, V> extract = heap.remove(0);
        map.remove(extract.value);
        
        // after initial extraction heap now has one element
        if (size() == 1) {
            
            //removing and updating heap and map
            extractMinHelper(); 
        }
   
        //still not empty after removing
        if (!isEmpty()) {
            extractMinHelper();
            minHeapify(0);
        } 
        return extract;
    }
    
    private void extractMinHelper() {
        Entry <Key, V> root = heap.remove(size() - 1);
        
        //last or only element is made root
        heap.add(0, root); 
        
        //updates last value as root index in map
        map.put(root.value, 0);
    }


    private void minHeapify(int num) {
        int left = 2 * num + 1; //left child
        int right = 2 * num + 2; //right child
        
        int min = num;
        
        //finding index of min value
        if (left < size() && heap.get(left).key.compareTo(heap.get(min).key) < 0) {
            min = left;
        }
        
        if (right < size() && heap.get(right).key.compareTo(heap.get(min).key) < 0) {
            min = right;
        }
        
        //swap index with smallest child and recurse
        if (min != num) {
            swap(num, min);
            minHeapify(min);
        }
    }
    
    private void swap(int x, int y) {
        Entry<Key, V> entry = heap.get(x);
        
        //swap entries in heap
        heap.set(x, heap.get(y));
        heap.set(y, entry);
        
        //update map
        map.put(heap.get(x).value, x);
        map.put(heap.get(y).value, y);
  
    }
    
    
    //traverse to satisfy min heap to top
    private void traverse(int num) {
        //comparing current node with parent
        while (num != 0 && (heap.get(num).key.compareTo(heap.get((num - 1) / 2).key) < 0)) {
            swap(num, (num - 1) / 2);
            //sets current as parent
            num = (num - 1) / 2;
        }
    }
    

    /**
     * {@inheritDoc}
     */ 
    @Override
    public Set<V> values() {
        return map.keySet();
    }


}
