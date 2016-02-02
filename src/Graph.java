import java.util.Map;

public class Graph {

	/**
	 * A map that connects vertices, represented by integers, to the distance
	 * between neighbors
	 */
	private Map<Integer, Double>[] neighbors;

	/**
	 * The constructor which sets the neighbors
	 * 
	 * @param neighbors
	 *            the list of vertices which are one degree away
	 */
	public Graph(Map<Integer, Double>[] neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * Getter for the map given a vertex
	 * 
	 * @param v
	 *            the key of the vertex
	 */
	public Map<Integer, Double> getNeighbors(int v) {
		return neighbors[v];
	}

	/**
	 * Setter for the map of neighbors 
	 * 
	 * @param neighbors
	 *            the set of all neighbors
	 */
	public void setNeighbors(Map<Integer, Double>[] neighbors) {
		this.neighbors = neighbors;
	}

	/** The number of vertices that a graph has */
	public int size() {
		return neighbors.length;
	}

}
