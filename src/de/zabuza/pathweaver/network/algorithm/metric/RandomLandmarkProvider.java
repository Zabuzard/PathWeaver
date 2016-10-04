package de.zabuza.pathweaver.network.algorithm.metric;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;

/**
 * Landmark provider that randomly selects landmarks from a given
 * {@link IPathNetwork}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RandomLandmarkProvider implements ILandmarkProvider<Node> {

	/**
	 * Message which is shown when requesting an amount of landmarks that is not
	 * greater than zero.
	 */
	private static final String LANDMARK_AMOUNT_NEGATIVE = "The given amount must be greater than zero.";
	/**
	 * Message which is shown when requesting more landmarks than are available.
	 */
	private static final String LANDMARK_AMOUNT_UNAVAILABLE = "There must be as many unique landmarks available as asked for.";

	/**
	 * The network to select landmarks from.
	 */
	private final IPathNetwork mNetwork;

	/**
	 * The random number generator used for random landmark selection.
	 */
	private final Random mRandom;

	/**
	 * Creates a new landmark provided that selects landmarks from the given
	 * network.
	 * 
	 * @param network
	 *            The network to select landmarks from
	 */
	public RandomLandmarkProvider(final IPathNetwork network) {
		mNetwork = network;
		mRandom = new Random();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.metric.ILandmarkProvider#
	 * getLandmarks(int)
	 */
	@Override
	public Set<Node> getLandmarks(final int amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException(LANDMARK_AMOUNT_NEGATIVE);
		}
		if (amount > mNetwork.getSize()) {
			throw new IllegalArgumentException(LANDMARK_AMOUNT_UNAVAILABLE);
		}

		HashSet<Node> landmarks = new HashSet<>();
		Object[] nodes = mNetwork.getNodes().toArray();
		int amountOfNodes = nodes.length;

		while (landmarks.size() < amount) {
			int candidateIndex = mRandom.nextInt(amountOfNodes);
			Node candidate = (Node) nodes[candidateIndex];
			landmarks.add(candidate);
		}

		return landmarks;
	}

}
