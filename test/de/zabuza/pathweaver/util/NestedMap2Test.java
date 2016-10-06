package de.zabuza.pathweaver.util;

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
	@Test
	public void testAddAll() {
		NestedMap2<Integer, String, Boolean> dataMap = new NestedMap2<>();
		dataMap.put(1, "a", true);
		dataMap.put(1, "b", false);

		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(2, "a", true);
		map.put(1, "a", false);

		map.addAll(dataMap);

		Assert.assertEquals(3, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(1, "b"));
		Assert.assertEquals(Boolean.TRUE, map.get(1, "a"));
		Assert.assertEquals(Boolean.TRUE, map.get(2, "a"));
	}

	/**
	 * Test method for {@link NestedMap2#clear()}.
	 */
	@Test
	public void testClear() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		Assert.assertEquals(2, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());

		map.put(1, "c", true);
		Assert.assertEquals(1, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());
	}

	/**
	 * Test method for {@link NestedMap2#entrySet()}.
	 */
	@Test
	public void testEntrySet() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link NestedMap2#equals(Object)}.
	 */
	@Test
	public void testEqualsObject() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		NestedMap2<Integer, String, Boolean> similarMap = new NestedMap2<>();
		similarMap.put(1, "a", true);
		similarMap.put(2, "b", false);

		NestedMap2<Integer, String, Boolean> differentMap = new NestedMap2<>();
		differentMap.put(1, "a", true);
		differentMap.put(2, "c", false);

		Assert.assertEquals(map, similarMap);
		Assert.assertNotEquals(map, differentMap);
	}

	/**
	 * Test method for {@link NestedMap2#get(Object)}.
	 */
	@Test
	public void testGetK1() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(1, "b", false);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(1).get("a"));
		Assert.assertEquals(Boolean.FALSE, map.get(1).get("b"));

		map.put(1, "a", false);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(1).get("a"));
	}

	/**
	 * Test method for {@link NestedMap2#get(Object, Object)}.
	 */
	@Test
	public void testGetK1K2() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(1, "a"));

		map.put(1, "a", false);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(1, "a"));
	}

	/**
	 * Test method for {@link NestedMap2#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		NestedMap2<Integer, String, Boolean> similarMap = new NestedMap2<>();
		similarMap.put(1, "a", true);
		similarMap.put(2, "b", false);

		NestedMap2<Integer, String, Boolean> differentMap = new NestedMap2<>();
		differentMap.put(1, "a", true);
		differentMap.put(2, "c", false);

		Assert.assertEquals(map.hashCode(), similarMap.hashCode());
		Assert.assertNotEquals(map.hashCode(), differentMap.hashCode());
	}

	/**
	 * Test method for {@link NestedMap2#keys2()}.
	 */
	@Test
	public void testKeys2() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link NestedMap2#keySet()}.
	 */
	@Test
	public void testKeySet() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link NestedMap2#put(Object, Object, Object)}.
	 */
	@Test
	public void testPut() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.TRUE, map.get(1, "a"));

		map.put(1, "a", false);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Boolean.FALSE, map.get(1, "a"));
	}

	/**
	 * Test method for {@link NestedMap2#remove(Object)}.
	 */
	@Test
	public void testRemoveK1() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link NestedMap2#remove(Object, Object)}.
	 */
	@Test
	public void testRemoveK1K2() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link NestedMap2#size()}.
	 */
	@Test
	public void testSize() {
		NestedMap2<Integer, String, Boolean> map = new NestedMap2<>();
		map.put(1, "a", true);
		map.put(2, "b", false);

		Assert.assertEquals(2, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());

		map.put(1, "c", true);
		Assert.assertEquals(1, map.size());
		map.clear();
		Assert.assertEquals(0, map.size());
	}

}
