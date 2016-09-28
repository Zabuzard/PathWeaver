package de.zabuza.pathweaver.util;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ReverseIterator}.
 * 
 * @author Zabuza
 *
 */
public class ReverseIteratorTest {

	/**
	 * Test method for {@link ReverseIterator#hasNext()}.
	 */
	@Test
	public void testHasNext() {
		List<Integer> list = new LinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
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
	@Test
	public void testNext() {
		List<Integer> list = new LinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
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
	@Test
	public void testReverseIterator() {
		List<Integer> list = new LinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
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
