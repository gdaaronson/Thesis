import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra {

	/**
	 * Given the array index, this tells the distance to that vertex from the given
	 * source
	 */
	private double[] distanceFromSource;

	/** This contains the path from the source to the target */
	private LinkedList<Integer> path;

	/** Given the array index, this tells the last place visited */
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
		int u = target;
		dijkstras(graph, source);
		path = new LinkedList<Integer>();
		while (predecessor[u] != -1) {
			path.add(0, u);
			u = predecessor[u];
		}
		path.add(0, u);
	}

	/**
	 * The heart of the program, this method finds the path
	 * 
	 * @param graph
	 *            the graph where this takes place
	 * @param source
	 *            where the start is
	 */
	public void dijkstras(Graph graph, int source) {
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
			int u = findMinDistance(q);
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

	/**
	 * Finds the minimum distance away from a vertex given a list of its
	 * neighbors
	 * 
	 * @param Q
	 *            the list of neighbors
	 */
	public int findMinDistance(ArrayList<Integer> Q) {
		Integer u = Q.get(0);
		for (int v : Q) {
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
}
