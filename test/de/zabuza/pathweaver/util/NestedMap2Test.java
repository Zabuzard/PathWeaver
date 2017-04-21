package de.zabuza.pathweaver.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link NestedMap2}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class NestedMap2Test {

	/**
	 * Test method for {@link NestedMap2#addAll(NestedMap2)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAddAll() {
		final NestedMap2<Integer, String, Boolean> dataMap = new NestedMap2<>();
		dataMap.put(Integer.valueOf(1), "a", Boolean.TRUE);
		dataMap.put(Integer.valueOf(1), "b", Boolean.FALSE);

		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(2), "a", Boolean.TRUE);
		map.put(Integer.valueOf(1), "a", Boolean.FALSE);

		map.addAll(dataMap);

		Assert.assertEquals(3, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(Integer.valueOf(1), "b"));
		Assert.assertEquals(Boolean.TRUE, map.get(Integer.valueOf(1), "a"));
		Assert.assertEquals(Boolean.TRUE, map.get(Integer.valueOf(2), "a"));
	}

	/**
	 * Test method for {@link NestedMap2#clear()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testClear() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		Assert.assertEquals(2, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());

		map.put(Integer.valueOf(1), "c", Boolean.TRUE);
		Assert.assertEquals(1, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());
	}

	/**
	 * Test method for {@link NestedMap2#entrySet()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEntrySet() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		for (final Triple<Integer, String, Boolean> entry : map.entrySet()) {
			if (entry.getFirst().equals(new Integer(1))) {
				Assert.assertEquals(new Integer(1), entry.getFirst());
				Assert.assertEquals("a", entry.getSecond());
				Assert.assertEquals(Boolean.TRUE, entry.getThird());
			} else {
				Assert.assertEquals(new Integer(2), entry.getFirst());
				Assert.assertEquals("b", entry.getSecond());
				Assert.assertEquals(Boolean.FALSE, entry.getThird());
			}
		}
	}

	/**
	 * Test method for {@link NestedMap2#equals(Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsObject() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		final NestedMap2<Integer, String, Boolean> similarMap = new NestedMap2<>();
		similarMap.put(Integer.valueOf(1), "a", Boolean.TRUE);
		similarMap.put(Integer.valueOf(2), "b", Boolean.FALSE);

		final NestedMap2<Integer, String, Boolean> differentMap = new NestedMap2<>();
		differentMap.put(Integer.valueOf(1), "a", Boolean.TRUE);
		differentMap.put(Integer.valueOf(2), "c", Boolean.FALSE);

		Assert.assertEquals(map, similarMap);
		Assert.assertNotEquals(map, differentMap);
	}

	/**
	 * Test method for {@link NestedMap2#get(Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetK1() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(1), "b", Boolean.FALSE);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(Integer.valueOf(1)).get("a"));
		Assert.assertEquals(Boolean.FALSE, map.get(Integer.valueOf(1)).get("b"));

		map.put(Integer.valueOf(1), "a", Boolean.FALSE);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(Integer.valueOf(1)).get("a"));
	}

	/**
	 * Test method for {@link NestedMap2#get(Object, Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetK1K2() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(1), "b", Boolean.FALSE);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(Integer.valueOf(1), "a"));

		map.put(Integer.valueOf(1), "a", Boolean.FALSE);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(Integer.valueOf(1), "a"));
	}

	/**
	 * Test method for {@link NestedMap2#hashCode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		final NestedMap2<Integer, String, Boolean> similarMap = new NestedMap2<>();
		similarMap.put(Integer.valueOf(1), "a", Boolean.TRUE);
		similarMap.put(Integer.valueOf(2), "b", Boolean.FALSE);

		final NestedMap2<Integer, String, Boolean> differentMap = new NestedMap2<>();
		differentMap.put(Integer.valueOf(1), "a", Boolean.TRUE);
		differentMap.put(Integer.valueOf(2), "c", Boolean.FALSE);

		Assert.assertEquals(map.hashCode(), similarMap.hashCode());
		Assert.assertNotEquals(map.hashCode(), differentMap.hashCode());
	}

	/**
	 * Test method for {@link NestedMap2#keys2()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKeys2() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);
		map.put(Integer.valueOf(1), "c", Boolean.FALSE);

		for (final Pair<Integer, String> key : map.keys2()) {
			final Boolean value = map.get(key.getFirst(), key.getSecond());
			if (key.getFirst().equals(new Integer(1)) && key.getSecond().equals("a")) {
				Assert.assertEquals(Boolean.TRUE, value);
			} else if (key.getFirst().equals(new Integer(1)) && key.getSecond().equals("c")) {
				Assert.assertEquals(Boolean.FALSE, value);
			} else {
				Assert.assertEquals(Boolean.FALSE, value);
			}
		}
	}

	/**
	 * Test method for {@link NestedMap2#keySet()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKeySet() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);
		map.put(Integer.valueOf(1), "c", Boolean.FALSE);

		for (final Integer key : map.keySet()) {
			final Map<String, Boolean> entry = map.get(key);
			if (key.equals(new Integer(1))) {
				Assert.assertEquals(2, entry.size());
				Assert.assertEquals(Boolean.TRUE, entry.get("a"));
				Assert.assertEquals(Boolean.FALSE, entry.get("c"));
			} else {
				Assert.assertEquals(1, entry.size());
				Assert.assertEquals(Boolean.FALSE, entry.get("b"));
			}
		}
	}

	/**
	 * Test method for {@link NestedMap2#put(Object, Object, Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPut() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(Integer.valueOf(1), "a"));

		map.put(Integer.valueOf(1), "a", Boolean.FALSE);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(Integer.valueOf(1), "a"));
	}

	/**
	 * Test method for {@link NestedMap2#remove(Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRemoveK1() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);
		map.put(Integer.valueOf(1), "c", Boolean.FALSE);

		Assert.assertEquals(3, map.size());
		map.remove(new Integer(1));
		Assert.assertEquals(1, map.size());
		map.remove(new Integer(2));
		Assert.assertEquals(0, map.size());
	}

	/**
	 * Test method for {@link NestedMap2#remove(Object, Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRemoveK1K2() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);
		map.put(Integer.valueOf(1), "c", Boolean.FALSE);

		Assert.assertEquals(3, map.size());
		map.remove(new Integer(1), "a");
		Assert.assertEquals(2, map.size());
		map.remove(new Integer(1), "c");
		Assert.assertEquals(1, map.size());
		map.remove(new Integer(1), "c");
		Assert.assertEquals(1, map.size());
		map.remove(new Integer(2), "b");
		Assert.assertEquals(0, map.size());
	}

	/**
	 * Test method for {@link NestedMap2#size()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSize() {
		final NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(Integer.valueOf(1), "a", Boolean.TRUE);
		map.put(Integer.valueOf(2), "b", Boolean.FALSE);

		Assert.assertEquals(2, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());

		map.put(Integer.valueOf(1), "c", Boolean.TRUE);
		Assert.assertEquals(1, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());
	}

}
