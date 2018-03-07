import java.util.ArrayList;
import java.util.ArrayList;

public class Dijkstra {


	public ArrayList<String> getPathNames(){
		ArrayList<String> names = new ArrayList<>();
		for(Vertex v: path){
			names.add(v.getName());
		}
		return names;
	}

	/** This contains the path from the source to the target */
	private ArrayList<Vertex> path;

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
	public Dijkstra(Vertex source, Vertex target, Graph graph) {
		searchFrom(graph, source);
		Vertex u = target;
		path = new ArrayList<>();
		while (u.getPrevious() != null) {
			path.add(0, u);
			u = u.getPrevious();
		}
		path.add(0, u);
	}

	/**
	 * Returns the vertex in the q with the lowest distance from the source
	 * 
	 * @param q
	 *            the list of neighbors
	 */
	public Vertex findClosestVertex(ArrayList<Vertex> q) {
		Vertex u = q.get(0);
		for (Vertex v : q) {
			if (v.getDistanceFromSource() < u.getDistanceFromSource()) {
				u = v;
			}
		}
		return u;
	}

	/** Getter for the path */
	public ArrayList<Vertex> getPath() {
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
	public void searchFrom(Graph graph, Vertex source) {
		ArrayList<Vertex> q = new ArrayList<>();
		for (Vertex v : graph.getVert()) {
			v.setDistanceFromSource(Double.POSITIVE_INFINITY);
			v.setPrevious(null);
			q.add(v);
		}
		source.setDistanceFromSource(0);
		while (!q.isEmpty()) {
			Vertex u = findClosestVertex(q);
			q.remove(u);
			for (Vertex v : graph.getVert()) {
				double alt = u.getDistanceFromSource() + graph.getEdge(u.getName() + "->" + v.getName()).getLength();
				if (alt < v.getDistanceFromSource()) {
					v.setDistanceFromSource(alt);
					v.setPrevious(u);
				}
			}
		}
	}
}
