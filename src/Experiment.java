import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Experiment {

	/** The graph of the game */
	private Graph graph;

	/** The list of vertices in the order they are visited */
	private LinkedList<Integer> list;

	/**
	 * The list of vertices that are modified to give the order in terms of the
	 * original output. This is for the human readable output
	 */
	private LinkedList<Integer> listMod;

	/** The list of vertices */
	private Vertex[] vertices;

	/**
	 * The constructor for the class
	 * 
	 * @param g
	 *            The graph
	 * @param source
	 *            The start of the game
	 * @param target
	 *            The end of the game
	 * @param map
	 *            The map which contains the requirements of the game
	 */
	public Experiment(Graph g, int source, int target, Map<Integer, Edge> map) {
		// Initialization
		list = new LinkedList<Integer>();
		listMod = new LinkedList<Integer>();
		int n = g.size();
		g.makePlanes(map.size());
		vertices = new Vertex[g.size()];
		for (int i = 0; i < g.size(); i++) {
			vertices[i] = new Vertex(i);
		}
		// Put the keys in sequential order
		Edge[] mapIndex = new Edge[map.size()];
		int index = 0;
		for (int key : map.keySet()) {
			mapIndex[index] = map.get(key);
			mapIndex[index].setIndex(key);
			index++;
		}
		// Create all planes of existence
		for (int j = 0; j < g.getNumerOfPlanes(); j++) {
			for (int i = 0; i < mapIndex.length; i++) {
				if (((1 << i) & j) != 0) {
					g.addNeighbor(mapIndex[i], j);
					g.addNeighbor(mapIndex[i], j - (1 << i), j, mapIndex[i].getIndex());
				}
			}
		}
		graph = g;
		// Call Dijkstra's Algorithm
		searchFrom(g, source);
		double minDistance = Double.POSITIVE_INFINITY;
		int indexOfVertex = -1;
		// Find the shortest path of all the possible destinations
		for (int i = target; i < g.size(); i += n) {
			if (vertices[i].getDistanceFromSource() < minDistance) {
				minDistance = vertices[i].getDistanceFromSource();
				indexOfVertex = i;
			}
		}
		findPathTo(indexOfVertex);
		extractRoute(g);
	}

	/**
	 * This finds the human readable output
	 * 
	 * @param g
	 *            The graph of the game
	 */
	public void extractRoute(Graph g) {
		for (Integer i : list) {
			listMod.add(i % g.getSizeOfPlane());
		}
	}

	/**
	 * Returns the vertex in the q with the lowest distance from the source
	 * 
	 * 
	 * @param q
	 *            the list of neighbors
	 */
	public int findClosestVertex(ArrayList<Integer> q) {
		Integer u = q.get(0);
		for (int v : q) {
			if (vertices[v].getDistanceFromSource() < vertices[u].getDistanceFromSource()) {
				u = v;
			}
		}
		return u;
	}

	/**
	 * Part of Dijkstra's Algorithm which creates the path
	 * 
	 * @param target
	 *            The ending location
	 */
	public void findPathTo(int target) {
		while (vertices[target].getPi() != -1) {
			list.add(0, target);
			target = vertices[target].getPi();
		}
		list.add(0, target);
	}

	/** Getter for the graph */
	public Graph getGraph() {
		return graph;
	}

	/** Getter for the list */
	public LinkedList<Integer> getList() {
		return list;
	}

	/** Getter for the human readable output */
	public LinkedList<Integer> getListMod() {
		return listMod;
	}

	/**
	 * Part of Dijkstra's Algorithm which calculates distances from the source
	 * 
	 * @param graph
	 *            The graph
	 * @param source
	 *            The starting location
	 */
	public void searchFrom(Graph graph, int source) {
		ArrayList<Integer> q = new ArrayList<Integer>();
		for (int v = 0; v < graph.size(); v++) {
			q.add(v);
		}
		vertices[source].setDistanceFromSource(0);
		while (!q.isEmpty()) {
			int u = findClosestVertex(q);
			q.remove(new Integer(u));
			for (int v : graph.getNeighbors(u).keySet()) {
				double alt = vertices[u].getDistanceFromSource() + graph.getNeighbors(u).get(v);
				if (alt < vertices[v].getDistanceFromSource()) {
					vertices[v].setDistanceFromSource(alt);
					vertices[v].setPi(u);
				}
			}
		}
	}
}
