import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {

	/** An inner class for book keeping */
	private class Vertex {

		/** The color of the enum */
		Color color;

		/** The distance from the source */
		double d;

		/** The predecessor vertex */
		int pi;

		/**
		 * The constructor of the vertex
		 * 
		 * @param color
		 *            Color
		 * @param d
		 *            Distance from the source
		 * @param pi
		 *            Predecessor vertex
		 */
		Vertex(Color color, double d, int pi) {
			this.color = color;
			this.d = d;
			this.pi = pi;
		}
	}

	/** The source for the graph */
	private int source;

	/** A list of all the vertices */
	private Vertex[] vertices;

	/**
	 * The constructor for breadth first search
	 * 
	 * @param g
	 *            The graph
	 * @param source
	 *            The source
	 */
	public BreadthFirstSearch(Graph g, int source) {
		this.source = source;
		vertices = new Vertex[g.size()];
		for (int i = 0; i < g.size(); i++) {
			vertices[i] = new Vertex(Color.WHITE, Double.POSITIVE_INFINITY, -1);
		}
		vertices[source] = new Vertex(Color.GRAY, 0, -1);
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(source);
		while (!q.isEmpty()) {
			int u = q.remove();
			for (int neighbor : g.getNeighbors(u).keySet()) {
				Vertex v = vertices[neighbor];
				if (v.color == Color.WHITE) {
					v.color = Color.GRAY;
					v.d = vertices[u].d + 1;
					v.pi = u;
					q.add(neighbor);
				}
			}
			vertices[u].color = Color.BLACK;
		}
	}

	/**
	 * Returns the path given a destination
	 * 
	 * @param destination
	 *            The destination
	 * 
	 */
	public List<Integer> getPath(int destination) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		for (int v = destination; vertices[v].pi != -1; v = vertices[v].pi) {
			path.addFirst(v);
		}
		path.addFirst(source);
		return path;
	}
}
