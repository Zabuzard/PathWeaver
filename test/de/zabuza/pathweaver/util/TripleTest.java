package de.zabuza.pathweaver.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Triple}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TripleTest {

	/**
	 * Test method for {@link Triple#equals(java.lang.Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsObject() {
		final String firstEntry = "a";
		final Float secondEntry = Float.valueOf(2.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Integer differentThirdEntry = Integer.valueOf(3);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> similarTriple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> differentTriple = new Triple<>(firstEntry, secondEntry,
				differentThirdEntry);

		Assert.assertEquals(triple, similarTriple);
		Assert.assertNotEquals(triple, differentTriple);
	}

	/**
	 * Test method for {@link Triple#getFirst()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetFirst() {
		final String firstEntry = "a";
		final String anotherFirstEntry = "b";
		final Float secondEntry = Float.valueOf(2.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> anotherTriple = new Triple<>(anotherFirstEntry, secondEntry, thirdEntry);

		Assert.assertEquals(firstEntry, triple.getFirst());
		Assert.assertEquals(anotherFirstEntry, anotherTriple.getFirst());
	}

	/**
	 * Test method for {@link Triple#getSecond()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSecond() {
		final String firstEntry = "a";
		final Float secondEntry = Float.valueOf(2.3f);
		final Float anotherSecondEntry = Float.valueOf(4.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> anotherTriple = new Triple<>(firstEntry, anotherSecondEntry, thirdEntry);

		Assert.assertEquals(secondEntry, triple.getSecond());
		Assert.assertEquals(anotherSecondEntry, anotherTriple.getSecond());
	}

	/**
	 * Test method for {@link Triple#getThird()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetThird() {
		final String firstEntry = "a";
		final Float secondEntry = Float.valueOf(2.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Integer anotherThirdEntry = Integer.valueOf(4);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> anotherTriple = new Triple<>(firstEntry, secondEntry, anotherThirdEntry);

		Assert.assertEquals(thirdEntry, triple.getThird());
		Assert.assertEquals(anotherThirdEntry, anotherTriple.getThird());
	}

	/**
	 * Test method for {@link Triple#hashCode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		final String firstEntry = "a";
		final Float secondEntry = Float.valueOf(2.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Integer differentThirdEntry = Integer.valueOf(3);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> similarTriple = new Triple<>(firstEntry, secondEntry, thirdEntry);
		final Triple<String, Float, Integer> differentTriple = new Triple<>(firstEntry, secondEntry,
				differentThirdEntry);

		Assert.assertEquals(triple.hashCode(), similarTriple.hashCode());
		Assert.assertNotEquals(triple.hashCode(), differentTriple.hashCode());
	}

	/**
	 * Test method for
	 * {@link Triple#Triple(java.lang.Object, java.lang.Object, java.lang.Object)}
	 * .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTriple() {
		final String firstEntry = "a";
		final Float secondEntry = Float.valueOf(2.3f);
		final Integer thirdEntry = Integer.valueOf(2);
		final Triple<String, Float, Integer> triple = new Triple<>(firstEntry, secondEntry, thirdEntry);

		Assert.assertEquals(firstEntry, triple.getFirst());
		Assert.assertEquals(secondEntry, triple.getSecond());
		Assert.assertEquals(thirdEntry, triple.getThird());
	}

}
