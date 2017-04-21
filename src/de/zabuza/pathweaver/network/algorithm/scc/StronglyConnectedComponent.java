package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.zabuza.pathweaver.network.Node;

/**
 * Class for strongly connected components of directed graphs.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class StronglyConnectedComponent {
	/**
	 * Message which is shown when trying to modify the SCC though the root node
	 * has already been set.
	 */
	private final static String ROOT_SET_METHOD_UNSUPPORTED = "If root node is set, SCC may not be modified.";
	/**
	 * A set of all nodes that belong to this SCC.
	 */
	private final HashSet<Node> mNodes;
	/**
	 * The root node of this SCC.
	 */
	private Node mRootNode;

	/**
	 * Creates a new empty strongly connected component.
	 */
	public StronglyConnectedComponent() {
		this.mNodes = new HashSet<>();
	}

	/**
	 * Adds a given node to the SCC.
	 * 
	 * @param node
	 *            The node to add
	 * @throws UnsupportedOperationException
	 *             If the root node was already set, since the SCC may not be
	 *             modified after this
	 */
	public void addNode(final Node node) throws UnsupportedOperationException {
		if (this.mRootNode != null) {
			throw new UnsupportedOperationException(ROOT_SET_METHOD_UNSUPPORTED);
		}
		boolean wasAlreadyContained = this.mNodes.add(node);
		assert wasAlreadyContained;
	}

	/**
	 * Gets an unmodifiable set of all nodes that belong to this SCC.
	 * 
	 * @return An unmodifiable set of all nodes that belong to this SCC
	 */
	public Set<Node> getNodes() {
		return Collections.unmodifiableSet(this.mNodes);
	}

	/**
	 * Gets the root node of this SCC. If a root node was set, the SCC may not
	 * be modified.
	 * 
	 * @return The root node of this SCC
	 */
	public Node getRootNode() {
		return this.mRootNode;
	}

	/**
	 * Gets the size of the SCC which is the amount of nodes.
	 * 
	 * @return The size of the SCC which is the amount of nodes
	 */
	public int getSize() {
		return this.mNodes.size();
	}

	/**
	 * Sets the root node.
	 * 
	 * @param rootNode
	 *            The root node to set
	 * @throws UnsupportedOperationException
	 *             If the root node was already set, since the SCC may not be
	 *             modified after this
	 */
	public void setRootNode(final Node rootNode) throws UnsupportedOperationException {
		if (this.mRootNode != null) {
			throw new UnsupportedOperationException(ROOT_SET_METHOD_UNSUPPORTED);
		}
		this.mRootNode = rootNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.mNodes.toString();
	}

}
