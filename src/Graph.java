import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Graph {

	/**
	 * A map that connects vertices, represented by integers, to the distance
	 * between neighbors
	 */
	private Map<Integer, Double>[] neighbors;
	
	private int sizeOfPlane;

	private Map<Integer, Double>[][] plane;
	
	public void makePlanes(int sizeOfMap){
		sizeOfPlane = size();
		Map<Integer, Double>[] big = new HashMap [(int) (size()*Math.pow(2, sizeOfMap))];
		plane = new HashMap[(int) Math.pow(2, sizeOfMap)][size()];
		for(int i = 0; i < big.length; i++){
			big[i] = new HashMap<Integer, Double>();
			plane[i / size()][i % size()] =  new HashMap<Integer, Double>();
		}
		for(int i = 0; i < big.length; i++){
			for(int j : neighbors[i % size()].keySet()){
				big[i].put(j + (i / size()) * size(), neighbors[i % size()].get(j));
				plane[i / size()][i % size()].put(j + (i / size()) * size(), neighbors[i % size()].get(j));
			}
		}
		neighbors = big;
	}

	public int getSizeOfPlane() {
		return sizeOfPlane;
	}

	public void addNeighbor(Edge requirement, int plane) {
		neighbors[requirement.getStart() + sizeOfPlane * plane].put(requirement.getEnd() + sizeOfPlane * plane, requirement.getLength());
		neighbors[requirement.getEnd() + sizeOfPlane * plane].put(requirement.getStart() + sizeOfPlane * plane, requirement.getLength());
	}
	
	public void addNeighbor(Edge requirement, int planeA, int planeB, int keyOfConnection) {
		neighbors[keyOfConnection + planeA * sizeOfPlane].put(keyOfConnection + planeB * sizeOfPlane, 0.0);
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < neighbors.length; i++){
			Object[] n = neighbors[i].keySet().toArray();
			int[] np = new int[n.length];
			for(int j = 0; j < n.length; j++){
				np[j] = (int) n[j];
			}
			Arrays.sort(np);
			s += i + " -> " + Arrays.toString(np);
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

	public Map<Integer, Double>[] getPlane(int planeIndex){
		return plane[planeIndex];
	}
	
	public int getNumerOfPlanes(){
		return plane.length;
	}
	
}
