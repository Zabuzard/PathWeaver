package de.zabuza.pathweaver.util;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ReverseIterator}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ReverseIteratorTest {

	/**
	 * Test method for {@link ReverseIterator#hasNext()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHasNext() {
		List<Integer> list = new LinkedList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		ReverseIterator<Integer> iter = new ReverseIterator<>(list);

		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(3), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(2), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(1), iter.next());
		Assert.assertFalse(iter.hasNext());
	}

	/**
	 * Test method for {@link ReverseIterator#next()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNext() {
		List<Integer> list = new LinkedList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		ReverseIterator<Integer> iter = new ReverseIterator<>(list);

		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(3), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(2), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(1), iter.next());
		Assert.assertFalse(iter.hasNext());
	}

	/**
	 * Test method for {@link ReverseIterator#ReverseIterator(List)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReverseIterator() {
		List<Integer> list = new LinkedList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		ReverseIterator<Integer> iter = new ReverseIterator<>(list);

		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(3), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(2), iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(new Integer(1), iter.next());
		Assert.assertFalse(iter.hasNext());
	}

}
