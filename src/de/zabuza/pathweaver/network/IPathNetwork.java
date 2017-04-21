package de.zabuza.pathweaver.network;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Interface for path networks which consists of nodes and directed edges which
 * connects the nodes. Edges have costs for using them. The network can be used
 * for computing shortest paths and similar tasks.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IPathNetwork {
	/**
	 * Adds an directed edge with a given source and destination node. Also a
	 * cost for using the edge must be given. Be aware that this method allows
	 * adding an edge multiple times.
	 * 
	 * @param source
	 *            The source of the directed edge
	 * @param destination
	 *            The destination of the directed edge
	 * @param cost
	 *            The cost of the edge which must be inside the limit specified
	 *            by
	 *            {@link DirectedWeightedEdge#DirectedWeightedEdge(Node, Node, float)}
	 *            .
	 * @return The edge that was created and added
	 */
	public DirectedWeightedEdge addEdge(final Node source, final Node destination, final float cost);

	/**
	 * Adds the given node to the graph if not already contained.
	 * 
	 * @param node
	 *            The node to add
	 * @return <tt>True</tt> if the node was added, i.e. it was not already
	 *         contained. <tt>False</tt> otherwise.
	 */
	public boolean addNode(final Node node);

	/**
	 * Returns whether the network contains the given node or not.
	 * 
	 * @param nodeId
	 *            The node ID to check
	 * @return <tt>True</tt> if the node is contained in the network,
	 *         <tt>false</tt> otherwise
	 */
	public boolean containsNodeId(final int nodeId);

	/**
	 * Gets the amount of edges the network currently has.
	 * 
	 * @return The amount of edges the network currently has.
	 */
	public int getAmountOfEdges();

	/**
	 * Gets an unmodifiable set of all incoming edges the given destination has
	 * or an <tt>empty set</tt> if there are no.
	 * 
	 * @param destination
	 *            The destination to get its incoming edges from
	 * @return An unmodifiable set of all incoming edges the given destination
	 *         has or an <tt>empty set</tt> if there are no
	 */
	public Set<DirectedWeightedEdge> getIncomingEdges(final Node destination);

	/**
	 * Returns the node with the given id, if it is contained in the network.
	 * 
	 * @param id
	 *            The id of the node to get
	 * @return The node with the given id, if it is contained in the network or
	 *         <tt>null</tt> if that is not the case.
	 */
	public Node getNodeById(final int id);

	/**
	 * Gets all nodes that belong to this network.
	 * 
	 * @return All nodes that belong to this network
	 */
	public Collection<Node> getNodes();

	/**
	 * Gets an unmodifiable set of all outgoing edges the given source has or an
	 * <tt>empty set</tt> if there are no.
	 * 
	 * @param source
	 *            The source to get its outgoing edges from
	 * @return An unmodifiable set of all outgoing edges the given source has or
	 *         an <tt>empty set</tt> if there are no
	 */
	public Set<DirectedWeightedEdge> getOutgoingEdges(final Node source);

	/**
	 * Gets the size of this network, i.e. the amount of nodes.
	 * 
	 * @return The size of this network, i.e. the amount of nodes
	 */
	public int getSize();

	/**
	 * Returns whether the node has the given incoming edge or not.
	 * 
	 * @param destination
	 *            The destination to check
	 * @param incomingEdge
	 *            The incoming edge to check
	 * @return <tt>True</tt> if the node has the incoming edge, <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasIncomingEdge(final Node destination, final DirectedWeightedEdge incomingEdge);

	/**
	 * Returns whether the node has the given outgoing edge or not.
	 * 
	 * @param source
	 *            The source to check
	 * @param outgoingEdge
	 *            The outgoing edge to check
	 * @return <tt>True</tt> if the node has the outgoing edge, <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasOutgoingEdge(final Node source, final DirectedWeightedEdge outgoingEdge);

	/**
	 * Reduces the graph to its largest strongly connected component. Inside
	 * such a component every node is reachable from all others.
	 */
	public void reduceToLargestScc();

	/**
	 * Removes the given edge from the network.
	 * 
	 * @param edge
	 *            The edge to remove
	 * @throws NoSuchElementException
	 *             If the edge is not contained in the network
	 */
	public void removeEdge(final DirectedWeightedEdge edge) throws NoSuchElementException;

	/**
	 * Removes the given node and all its in- and outgoing edges from the
	 * network.
	 * 
	 * @param node
	 *            The node to remove
	 * @throws NoSuchElementException
	 *             If the node is not contained in the network
	 */
	public void removeNode(final Node node) throws NoSuchElementException;

	/**
	 * Reverses the network, i.e. changing the direction of every edge.
	 */
	public void reverse();
}
