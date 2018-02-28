import java.util.List;
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
        table = (MapEntry<K, V>[]) (new Object[initialCapacity]);
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
        int index = key.hashCode() % table.length;
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
            if (i.getNext() == null) {
                i.setNext(new MapEntry<K, V>(key, value));
                size++;
                return null;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {

    }

    @Override
    public V get(K key) {

    }

    @Override
    public boolean containsKey(K key) {

    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {

    }

    @Override
    public List<V> values() {

    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < table.length) {
            throw new IllegalArgumentException(
                "New length must be greater than current table length."
            );
        }
        MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) (new Object[length]);
        for (MapEntry<K, V> entry : table) {
            K key = entry.getKey();
            V value = entry.getValue();
            int index = key.hashCode() % newTable.length;
            MapEntry<K, V> position = newTable[index];
            if (position == null) {
                newTable[index] = new MapEntry<K, V>(key, value);
                size++;
            }
            while (position.getNext() != null) {
                position = position.getNext();
            }
            position.setNext(new MapEntry<K, V>(key, value));
        }
        table = newTable;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
