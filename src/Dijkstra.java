import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra {

	/**
	 * Given the array index, this tells the distance to that vertex from the
	 * given source
	 */
	private double[] distanceFromSource;

	/** This contains the path from the source to the target */
	private LinkedList<Integer> path;

	/**
	 * Given the array index, this tells the last vertex visited alogn the
	 * shortest path from the source
	 */
	private int[] predecessor;

	/**
	 * Finds the shortest path between two points
	 * 
	 * @param source
	 *            where the path begins
	 * @param target
	 *            where the path ends
	 * @param graph
	 *            the graph where these are located
	 */
	public Dijkstra(int source, int target, Graph graph) {
		searchFrom(graph, source);
		int u = target;
		path = new LinkedList<Integer>();
		while (predecessor[u] != -1) {
			path.add(0, u);
			u = predecessor[u];
		}
		path.add(0, u);
	}

	/**
	 * Returns the vertex in the q with the lowest distance from the source
	 * 
	 * @param q
	 *            the list of neighbors
	 */
	public int findClosestVertex(ArrayList<Integer> q) {
		Integer u = q.get(0);
		for (int v : q) {
			if (distanceFromSource[v] < distanceFromSource[u]) {
				u = v;
			}
		}
		return u;
	}

	/** Getter for the path */
	public LinkedList<Integer> getPath() {
		return path;
	}

	/**
	 * The heart of the program, this method finds the path
	 * 
	 * @param graph
	 *            the graph where this takes place
	 * @param source
	 *            where the start is
	 */
	public void searchFrom(Graph graph, int source) {
		ArrayList<Integer> q = new ArrayList<Integer>();
		distanceFromSource = new double[graph.size()];
		predecessor = new int[graph.size()];
		for (int v = 0; v < graph.size(); v++) {
			distanceFromSource[v] = Double.POSITIVE_INFINITY;
			predecessor[v] = -1;
			q.add(v);
		}
		distanceFromSource[source] = 0;
		while (!q.isEmpty()) {
			int u = findClosestVertex(q);
			q.remove(new Integer(u));
			for (int v : graph.getNeighbors(u).keySet()) {
				double alt = distanceFromSource[u] + graph.getNeighbors(u).get(v);
				if (alt < distanceFromSource[v]) {
					distanceFromSource[v] = alt;
					predecessor[v] = u;
				}
			}
		}
	}
}
