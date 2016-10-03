package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.List;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Interface for algorithm which solve strongly connected component computation
 * tasks in {@link PathNetwork}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ISccComputation {
	/**
	 * Gets the largest strongly connected component of the network specified by
	 * {@link #getPathNetwork()}. There is no condition on how ties should get
	 * broken if there are multiple largest SCCs.
	 * 
	 * @return The largest SCC of the graph
	 */
	public StronglyConnectedComponent getLargestScc();

	/**
	 * Gets the path network this object works on.
	 * 
	 * @return The path network this object works on
	 */
	public IPathNetwork getPathNetwork();

	/**
	 * Gets a list of all strongly connected component of the network specified
	 * by {@link #getPathNetwork()}. Each node must be contained in exactly one
	 * SCC.
	 * 
	 * @return A list of all SCCs of the graph
	 */
	public List<StronglyConnectedComponent> getSccs();
}
