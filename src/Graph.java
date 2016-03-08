import java.util.HashMap;
import java.util.Map;

public class Graph {

	/**
	 * A map that connects vertices, represented by integers, to the distance
	 * between neighbors
	 */
	private Map<Integer, Double>[] neighbors;
	
	public void expandGraph(int key, Edge gate, int originalSize){
		int size = neighbors.length;
		Map<Integer, Double>[] big= new HashMap [originalSize + size];
		for(int i = 0; i < size; i++){
			big[i] = new HashMap<Integer, Double>();
			big[i + originalSize] = new HashMap<Integer, Double>();
			for(int j : neighbors[i].keySet()){
				big[i].put(j, neighbors[i].get(j));
				big[i + originalSize].put(j + originalSize, neighbors[i].get(j));
			}
		}
		neighbors = big;
		addNeighbor(new Edge(gate.getStart() + size, gate.getEnd() + size, gate.getLength()));
		addNeighbor(new Edge(key + size - originalSize, key + size, 0));
	}

	public void addNeighbor(Edge requirement) {
		neighbors[requirement.getStart()].put(requirement.getEnd(), requirement.getLength()); 
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < neighbors.length; i++){
			s += i + " -> " + neighbors[i].keySet();
			s += "\n";
		}
		return s;
	}
	
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
	
	public Map<Integer, Double>[] getNeighbors() {
		return neighbors;
	}

	public void remove(Map<Integer, Double> neighbors) {
		neighbors.remove(neighbors.keySet());
	}

	public void remove(Map<Integer, Integer>[] requirement) {
		for(int i = 0; i < neighbors.length; i++){
			for(int j = 0; j < requirement.length; j++){
			}
		}
	}

}
