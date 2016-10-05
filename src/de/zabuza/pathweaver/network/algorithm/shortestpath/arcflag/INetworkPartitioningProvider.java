package de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag;

import java.util.Collection;
import java.util.Set;

import de.zabuza.pathweaver.network.Node;

/**
 * Interface for classes that provide network partitioning. It provides methods
 * to divide a network into partitions that cover the whole network.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface INetworkPartitioningProvider {

	/**
	 * Divides a given network into partitions according to the implementing
	 * technique and returns them as collection. The partitions need to cover
	 * the whole network, i.e. every node needs to be contained in exactly one
	 * partition.
	 * 
	 * @return A collection of partitions that cover the whole network
	 */
	public Collection<Set<Node>> getPartitioning();
}
