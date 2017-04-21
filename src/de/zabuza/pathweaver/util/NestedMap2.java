package de.zabuza.pathweaver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Nested hash map which uses two keys in a nested structure for storing values.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 * @param <K1>
 *            Type of the first key
 * @param <K2>
 *            Type of the second key
 * @param <V>
 *            Type of the value
 */
public final class NestedMap2<K1, K2, V> {

	/**
	 * Internal map which stores maps of second keys and values for the first
	 * keys.
	 */
	private final Map<K1, Map<K2, V>> mK1ToK2ToV = new HashMap<>();

	/**
	 * Adds all entries from the given map to this map.
	 * 
	 * @param nestedMap
	 *            The map to add entries from
	 */
	public void addAll(final NestedMap2<K1, K2, V> nestedMap) {
		for (final Triple<K1, K2, V> triple : nestedMap.entrySet()) {
			this.put(triple.getFirst(), triple.getSecond(), triple.getThird());
		}
	}

	/**
	 * Removes all of the mappings from this map. The map will be empty after
	 * this call returns.
	 */
	public void clear() {
		this.mK1ToK2ToV.clear();
	}

	/**
	 * Returns an iterable object which contains all entries of this map.
	 * 
	 * @return An iterable object which contains all entries of this map
	 */
	public Iterable<Triple<K1, K2, V>> entrySet() {
		final ArrayList<Triple<K1, K2, V>> result = new ArrayList<>();
		for (final Entry<K1, Map<K2, V>> entryOuter : this.mK1ToK2ToV.entrySet()) {
			for (final Entry<K2, V> entryInner : entryOuter.getValue().entrySet()) {
				result.add(new Triple<>(entryOuter.getKey(), entryInner.getKey(), entryInner.getValue()));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final NestedMap2<?, ?, ?> other = (NestedMap2<?, ?, ?>) obj;
		if (this.mK1ToK2ToV == null) {
			if (other.mK1ToK2ToV != null)
				return false;
		} else if (!this.mK1ToK2ToV.equals(other.mK1ToK2ToV))
			return false;
		return true;
	}

	/**
	 * Returns the map to which the specified first key is mapped, or
	 * <tt>null</tt> if this map contains no mapping for the first key.
	 * 
	 * @param key1
	 *            The first key
	 * @return The map to which the specified first key is mapped, or
	 *         <tt>null</tt> if this map contains no mapping for the first key.
	 */
	public Map<K2, V> get(final K1 key1) {
		return this.mK1ToK2ToV.get(key1);
	}

	/**
	 * Returns the value to which the specified keys are mapped, or
	 * <tt>null</tt> if this map contains no mapping for the keys.
	 * 
	 * @param key1
	 *            The first key
	 * @param key2
	 *            The second key
	 * @return The value to which the specified keys are mapped, or
	 *         <tt>null</tt> if this map contains no mapping for the keys.
	 */
	public V get(final K1 key1, final K2 key2) {
		final Map<K2, V> k2toV = this.mK1ToK2ToV.get(key1);
		if (k2toV == null) {
			return null;
		}
		return k2toV.get(key2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.mK1ToK2ToV == null) ? 0 : this.mK1ToK2ToV.hashCode());
		return result;
	}

	/**
	 * Returns an iterable object which contains all key entries of this map.
	 * 
	 * @return An iterable object which contains all key entries of this map.
	 */
	public Iterable<Pair<K1, K2>> keys2() {
		return new Iterable<Pair<K1, K2>>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Iterable#iterator()
			 */
			@SuppressWarnings("synthetic-access")
			@Override
			public Iterator<Pair<K1, K2>> iterator() {
				return new Iterator<Pair<K1, K2>>() {
					private Iterator<Entry<K1, Map<K2, V>>> mIterator1;
					private Entry<K1, Map<K2, V>> mIterator1Object;
					private Iterator<K2> mIterator2;

					{
						this.mIterator1 = NestedMap2.this.mK1ToK2ToV.entrySet().iterator();
						if (this.mIterator1.hasNext()) {
							this.mIterator1Object = this.mIterator1.next();
							this.mIterator2 = this.mIterator1Object.getValue().keySet().iterator();
						}
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see java.util.Iterator#hasNext()
					 */
					@Override
					public boolean hasNext() {
						if (this.mIterator1Object == null) {
							return false;
						}
						return this.mIterator2.hasNext();
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see java.util.Iterator#next()
					 */
					@Override
					public Pair<K1, K2> next() {
						if (this.mIterator1Object == null) {
							throw new NoSuchElementException();
						}
						if (!this.mIterator2.hasNext()) {
							if (!this.mIterator1.hasNext()) {
								throw new NoSuchElementException();
							}
							this.mIterator1Object = this.mIterator1.next();
							assert this.mIterator1Object.getValue().size() > 0 : "must contain at least one value";
							this.mIterator2 = this.mIterator1Object.getValue().keySet().iterator();
						}
						return new Pair<>(this.mIterator1Object.getKey(), this.mIterator2.next());
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see java.util.Iterator#remove()
					 */
					@Override
					public void remove() {
						throw new UnsupportedOperationException("not yet implemented");
					}
				};
			}
		};

	}

	/**
	 * Returns a set view of the first keys contained in this map.
	 * 
	 * @return A set view of the first keys contained in this map.
	 */
	public Set<K1> keySet() {
		return this.mK1ToK2ToV.keySet();
	}

	/**
	 * Associates the specified value with the two specified keys in this map.
	 * 
	 * @param key1
	 *            First key
	 * @param key2
	 *            Second key
	 * @param value
	 *            Value to associate
	 * @return The previous value associated with the two keys, or <tt>null</tt>
	 *         if there was no mapping for the keys.
	 */
	public V put(final K1 key1, final K2 key2, final V value) {
		Map<K2, V> k2toV = this.mK1ToK2ToV.get(key1);
		if (k2toV == null) {
			k2toV = new HashMap<>();
			this.mK1ToK2ToV.put(key1, k2toV);
		}
		return k2toV.put(key2, value);
	}

	/**
	 * Removes the mapping for the first key from this map if it is present.
	 * 
	 * @param k1
	 *            The first key
	 * @return The previous value associated with the first key, or
	 *         <tt>null</tt> if there was no mapping for it.
	 */
	public Map<K2, V> remove(final K1 k1) {
		return this.mK1ToK2ToV.remove(k1);
	}

	/**
	 * Removes the mapping for the two keys from this map if it is present.
	 * 
	 * @param k1
	 *            The first key
	 * @param k2
	 *            The second key
	 * @return The previous value associated with the two keys, or <tt>null</tt>
	 *         if there was no mapping for it.
	 */
	public V remove(final K1 k1, final K2 k2) {
		final Map<K2, V> k2ToV = this.mK1ToK2ToV.get(k1);
		if (k2ToV == null) {
			return null;
		}
		return k2ToV.remove(k2);
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * 
	 * @return The number of key-value mappings in this map
	 */
	public int size() {
		int result = 0;
		for (final Entry<K1, Map<K2, V>> entry : this.mK1ToK2ToV.entrySet()) {
			result += entry.getValue().size();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.mK1ToK2ToV.toString();
	}
}