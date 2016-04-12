import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Expirement {

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
	private Vertex[] verticies;

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
	public Expirement(Graph g, int source, int target, Map<Integer, Edge> map) {
		list = new LinkedList<Integer>();
		listMod = new LinkedList<Integer>();
		int n = g.size();
		g.makePlanes(map.size());
		verticies = new Vertex[g.size()];
		for (int i = 0; i < g.size(); i++) {
			verticies[i] = new Vertex(i);
		}
		Edge[] mapIndex = new Edge[map.size()];
		int index = 0;
		for (int key : map.keySet()) {
			mapIndex[index] = map.get(key);
			mapIndex[index].setIndex(key);
			index++;
		}
		for (int j = 0; j < g.getNumerOfPlanes(); j++) {
			for (int i = 0; i < mapIndex.length; i++) {
				if (((1 << i) & j) != 0) {
					g.addNeighbor(mapIndex[i], j);
					g.addNeighbor(mapIndex[i], j - (1 << i), j, mapIndex[i].getIndex());
				}
			}
		}
		graph = g;
		dijkstras(g, source);
		double minDistance = Double.POSITIVE_INFINITY;
		int indexOfVertex = -1;
		for(int i = target; i < g.size(); i+=n){
			if(verticies[i].getDistanceFromSource() < minDistance){
				minDistance = verticies[i].getDistanceFromSource();
				indexOfVertex = i;
			}
		}
		dijkstra(indexOfVertex);
		extractListMod(g);
	}

	/**
	 * Dijkstra's Algorithm
	 * 
	 * @param source
	 *            The starting location
	 * @param target
	 *            The ending location
	 * @param graph
	 *            The graph
	 */
	public void dijkstra(int target) {
		while (verticies[target].getPi() != -1) {
			list.add(0, target);
			target = verticies[target].getPi();
		}
		list.add(0, target);
	}

	/**
	 * A sub-method for Dijkstra's Algorithm
	 * 
	 * @param graph
	 *            The graph
	 * @param source
	 *            The starting location
	 */
	public void dijkstras(Graph graph, int source) {
		ArrayList<Integer> q = new ArrayList<Integer>();
		for (int v = 0; v < graph.size(); v++) {
			q.add(v);
		}
		verticies[source].setDistanceFromSource(0);
		while (!q.isEmpty()) {
			int u = findClosestVertex(q);
			q.remove(new Integer(u));
			for (int v : graph.getNeighbors(u).keySet()) {
				double alt = verticies[u].getDistanceFromSource() + graph.getNeighbors(u).get(v);
				if (alt < verticies[v].getDistanceFromSource()) {
					verticies[v].setDistanceFromSource(alt);
					verticies[v].setPi(u);
				}
			}
		}
	}

	/**
	 * This finds the human readable output
	 * 
	 * @param g
	 *            The graph of the game
	 */
	public void extractListMod(Graph g) {
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
			if (verticies[v].getDistanceFromSource() < verticies[u].getDistanceFromSource()) {
				u = v;
			}
		}
		return u;
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
}
