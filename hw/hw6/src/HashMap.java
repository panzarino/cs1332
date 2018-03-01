import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Zachary Panzarino
 * @userid zpanzarino3
 * @GTID 903305160
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (size + 1 / ((double) table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int index = Math.abs(key.hashCode()) % table.length;
        MapEntry<K, V> position = table[index];
        if (position == null) {
            table[index] = new MapEntry<K, V>(key, value);
            size++;
            return null;
        }
        for (MapEntry<K, V> i = position; i != null; i = i.getNext()) {
            if (i.getKey().equals(key)) {
                V temp = i.getValue();
                i.setKey(key);
                i.setValue(value);
                size++;
                return temp;
            }
        }
        table[index] = new MapEntry<K, V>(key, value, position);
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        MapEntry<K, V> position = table[index];
        if (position.getKey().equals(key)) {
            V value = position.getValue();
            table[index] = position.getNext();
            size--;
            return value;
        }
        for (
            MapEntry<K, V> i = position; i.getNext() != null; i = i.getNext()
        ) {
            MapEntry<K, V> next = i.getNext();
            if (next.getKey().equals(key)) {
                V value = next.getValue();
                i.setNext(next.getNext());
                size--;
                return value;
            }
        }
        throw new NoSuchElementException("No value exists for that key.");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        MapEntry<K, V> position = table[Math.abs(key.hashCode()) % table.length];
        for (MapEntry<K, V> i = position; i != null; i = i.getNext()) {
            if (i.getKey().equals(key)) {
                return i.getValue();
            }
        }
        throw new NoSuchElementException("No value exists for that key.");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        MapEntry<K, V> position = table[Math.abs(key.hashCode()) % table.length];
        for (MapEntry<K, V> i = position; i != null; i = i.getNext()) {
            if (i.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (MapEntry<K, V> entry : table) {
            for (MapEntry<K, V> i = entry; i != null; i = i.getNext()) {
                set.add(i.getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<V>(size);
        for (MapEntry<K, V> entry : table) {
            for (MapEntry<K, V> i = entry; i != null; i = i.getNext()) {
                list.add(i.getValue());
            }
        }
        return list;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException(
                "New length must be greater than current table length."
            );
        }
        MapEntry<K, V>[] newTable = new MapEntry[length];
        for (MapEntry<K, V> entry : table) {
            for (MapEntry<K, V> i = entry; i != null; i = i.getNext()) {
                K key = i.getKey();
                V value = i.getValue();
                int index = Math.abs(key.hashCode()) % newTable.length;
                MapEntry<K, V> position = newTable[index];
                newTable[index] = new MapEntry<K, V>(key, value, position);
            }
        }
        table = newTable;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
