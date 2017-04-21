package de.zabuza.pathweaver.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

import de.zabuza.pathweaver.network.algorithm.scc.ISccComputation;
import de.zabuza.pathweaver.network.algorithm.scc.StronglyConnectedComponent;
import de.zabuza.pathweaver.network.algorithm.scc.TarjanNonRecursiveSccComputation;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A path network which consists of nodes and directed edges which connects the
 * nodes. Edges have costs for using them. The network can be used for computing
 * shortest paths and similar tasks.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class PathNetwork implements IPathNetwork {
	/**
	 * Message for the exception thrown when the given nodes where not added to
	 * the network previously though needed.
	 */
	private static final String EXCEPTION_NODE_NOT_ADDED = "The given nodes must already be added to the network before using this operation.";
	/**
	 * The current amount of edges the network has.
	 */
	private int mAmountOfEdges;

	/**
	 * The current amount of nodes the network has.
	 */
	private int mAmountOfNodes;

	/**
	 * The set of nodes the network currently has, accessible by their IDs.
	 */
	private final HashMap<Integer, Node> mIdToNodes;

	/**
	 * Maps nodes to their incoming edges.
	 */
	private HashMap<Node, HashSet<DirectedWeightedEdge>> mNodeToIncomingEdges;

	/**
	 * Maps nodes to their outgoing edges.
	 */
	private HashMap<Node, HashSet<DirectedWeightedEdge>> mNodeToOutgoingEdges;

	/**
	 * Creates a new empty path network.
	 */
	public PathNetwork() {
		this.mAmountOfNodes = 0;
		this.mAmountOfEdges = 0;
		this.mIdToNodes = new HashMap<>();
		this.mNodeToOutgoingEdges = new HashMap<>();
		this.mNodeToIncomingEdges = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#addEdge(de.zabuza.pathweaver.
	 * network.Node, de.zabuza.pathweaver.network.Node, float)
	 */
	@Override
	public DirectedWeightedEdge addEdge(final Node source, final Node destination, final float cost) {
		if (!containsNodeId(source.getId()) || !containsNodeId(destination.getId())) {
			throw new IllegalArgumentException(EXCEPTION_NODE_NOT_ADDED);
		}
		final DirectedWeightedEdge edge = new DirectedWeightedEdge(source, destination, cost);
		HashSet<DirectedWeightedEdge> outgoingEdges = this.mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			outgoingEdges = new LinkedHashSet<>();
		}
		outgoingEdges.add(edge);
		this.mNodeToOutgoingEdges.put(source, outgoingEdges);

		HashSet<DirectedWeightedEdge> incomingEdges = this.mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			incomingEdges = new LinkedHashSet<>();
		}
		incomingEdges.add(edge);
		this.mNodeToIncomingEdges.put(destination, incomingEdges);

		this.mAmountOfEdges++;

		return edge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#addNode(de.zabuza.pathweaver.
	 * network.Node)
	 */
	@Override
	public boolean addNode(final Node node) {
		final Node previousElement = this.mIdToNodes.get(Integer.valueOf(node.getId()));
		final boolean getsAdded = previousElement == null;
		if (getsAdded) {
			this.mIdToNodes.put(Integer.valueOf(node.getId()), node);
			this.mAmountOfNodes++;
			assert this.mAmountOfNodes == this.mIdToNodes.size();
		}
		return getsAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#containsNodeId(int)
	 */
	@Override
	public boolean containsNodeId(final int nodeId) {
		return this.mIdToNodes.containsKey(Integer.valueOf(nodeId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getAmountOfEdges()
	 */
	@Override
	public int getAmountOfEdges() {
		return this.mAmountOfEdges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#getIncomingEdges(de.zabuza.
	 * pathweaver.network.Node)
	 */
	@Override
	public Set<DirectedWeightedEdge> getIncomingEdges(final Node destination) {
		final HashSet<DirectedWeightedEdge> incomingEdges = this.mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(incomingEdges);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getNodeById(int)
	 */
	@Override
	public Node getNodeById(final int id) {
		return this.mIdToNodes.get(Integer.valueOf(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getNodes()
	 */
	@Override
	public Collection<Node> getNodes() {
		return this.mIdToNodes.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#getOutgoingEdges(de.zabuza.
	 * pathweaver.network.Node)
	 */
	@Override
	public Set<DirectedWeightedEdge> getOutgoingEdges(final Node source) {
		final HashSet<DirectedWeightedEdge> outgoingEdges = this.mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(outgoingEdges);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getSize()
	 */
	@Override
	public int getSize() {
		return this.mAmountOfNodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#hasIncomingEdge(de.zabuza.
	 * pathweaver.network.Node, de.zabuza.pathweaver.network.IncomingEdge)
	 */
	@Override
	public boolean hasIncomingEdge(final Node destination, final DirectedWeightedEdge incomingEdge) {
		final HashSet<DirectedWeightedEdge> incomingEdges = this.mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return false;
		}
		return incomingEdges.contains(incomingEdge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#hasOutgoingEdge(de.zabuza.
	 * pathweaver.network.Node, de.zabuza.pathweaver.network.OutgoingEdge)
	 */
	@Override
	public boolean hasOutgoingEdge(final Node source, final DirectedWeightedEdge outgoingEdge) {
		final HashSet<DirectedWeightedEdge> outgoingEdges = this.mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return false;
		}
		return outgoingEdges.contains(outgoingEdge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#reduceToLargestScc()
	 */
	@Override
	public void reduceToLargestScc() {
		final ISccComputation sccComputation = new TarjanNonRecursiveSccComputation(this);
		final StronglyConnectedComponent largestScc = sccComputation.getLargestScc();
		final Set<Node> largestSccNodes = largestScc.getNodes();
		final LinkedList<Node> nodesToRemove = new LinkedList<>();
		for (final Node node : getNodes()) {
			if (largestSccNodes.contains(node)) {
				continue;
			}
			nodesToRemove.add(node);
		}
		for (final Node node : nodesToRemove) {
			removeNode(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#removeEdge(de.zabuza.pathweaver
	 * .network.DirectedWeightedEdge)
	 */
	@Override
	public void removeEdge(final DirectedWeightedEdge edge) throws NoSuchElementException {
		if (!this.mNodeToOutgoingEdges.containsKey(edge.getSource())
				|| !this.mNodeToIncomingEdges.containsKey(edge.getDestination())) {
			throw new NoSuchElementException();
		}

		this.mNodeToOutgoingEdges.get(edge.getSource()).remove(edge);
		this.mNodeToIncomingEdges.get(edge.getDestination()).remove(edge);
		this.mAmountOfEdges--;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#removeNode(de.zabuza.pathweaver
	 * .network.Node)
	 */
	@Override
	public void removeNode(final Node node) throws NoSuchElementException {
		if (!this.mIdToNodes.containsKey(Integer.valueOf(node.getId()))) {
			throw new NoSuchElementException();
		}

		// First remove all in- and outgoing edges
		final Set<DirectedWeightedEdge> outgoingEdges = getOutgoingEdges(node);
		for (final DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			// Remove this edge from destinations incoming edges
			final Node destination = outgoingEdge.getDestination();
			this.mNodeToIncomingEdges.get(destination).remove(outgoingEdge);
			this.mAmountOfEdges--;
		}
		final Set<DirectedWeightedEdge> incomingEdges = getIncomingEdges(node);
		for (final DirectedWeightedEdge incomingEdge : incomingEdges) {
			// Remove this edge from sources outgoing edges
			final Node source = incomingEdge.getSource();
			this.mNodeToOutgoingEdges.get(source).remove(incomingEdge);
			this.mAmountOfEdges--;
		}

		// At this point no other node has a link to the node to delete
		// Now delete the node and its links
		this.mNodeToIncomingEdges.remove(node);
		this.mNodeToOutgoingEdges.remove(node);
		this.mIdToNodes.remove(Integer.valueOf(node.getId()));
		this.mAmountOfNodes--;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#reverse()
	 */
	@Override
	public void reverse() {
		// Iterate every edge exactly one time and reverse it
		for (final HashSet<DirectedWeightedEdge> edges : this.mNodeToOutgoingEdges.values()) {
			for (final DirectedWeightedEdge edge : edges) {
				edge.reverse();
			}
		}

		// Exchange the internal edge maps
		final HashMap<Node, HashSet<DirectedWeightedEdge>> tmpNodeToIncomingEdges = this.mNodeToIncomingEdges;
		this.mNodeToIncomingEdges = this.mNodeToOutgoingEdges;
		this.mNodeToOutgoingEdges = tmpNodeToIncomingEdges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Network[");
		builder.append("#nodes=" + this.mAmountOfNodes);
		builder.append(",#edges=" + this.mAmountOfEdges);
		builder.append(",edges={");
		for (final Entry<Node, HashSet<DirectedWeightedEdge>> entry : this.mNodeToOutgoingEdges.entrySet()) {
			final Node source = entry.getKey();
			for (final DirectedWeightedEdge outgoingEdge : entry.getValue()) {
				final Node destination = outgoingEdge.getDestination();
				final float cost = outgoingEdge.getCost();
				builder.append("(" + source + "-" + cost + "->" + destination + "),");
			}
		}
		if (!this.mNodeToOutgoingEdges.isEmpty()) {
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("}");
		builder.append("]");

		return builder.toString();
	}
}
