import java.util.LinkedList;

public class TopologicalSort {

	/** An inner class for book keeping */
	private class Vertex {

		/** The color of the enum */
		Color color;

		/** The initial time that a this vertex is visited */
		int d;

		/** The time when the vertex turns black */
		int f;

		/** The identification for a vertex */
		int id;

		/** The identification of the previously visited vertex */
		int pi;

		/**
		 * The constructor for the vertex
		 * 
		 * @param color
		 *            Color of the vertex
		 * @param pi
		 *            The previous vertex
		 */
		public Vertex(Color color, int pi) {
			this.pi = pi;
			this.color = color;
		}
	}

	/** A list that contains the order in which things must be done */
	private LinkedList<Integer> list;

	/** The time for keeping track of when a vertex is started and finished */
	private int time;

	/** A list of all the vertices */
	private Vertex[] verticies;

	/**
	 * The constructor for Topological Sort
	 * 
	 * @param g
	 *            The graph to sort
	 */
	public TopologicalSort(Graph g) {
		list = new LinkedList<>();
		DFS(g);
	}

	/**
	 * The actual sorting process begins here
	 * 
	 * @param g
	 *            The graph to sort
	 */
	public void DFS(Graph g) {
		verticies = new Vertex[g.size()];
		for (int i = 0; i < g.size(); i++) {
			verticies[i] = new Vertex(Color.WHITE, -1);
		}
		time = 0;
		for (int i = 0; i < g.size(); i++) {
			if (verticies[i].color == Color.WHITE) {
				DFSVisit(g, i);
			}
		}
	}

	/**
	 * A recursive sub-method that analyzes what happens when a specific vertex
	 * is visited
	 * 
	 * @param g
	 *            The graph that is being sorted
	 * @param u
	 *            The vertex that is being visited
	 */
	private void DFSVisit(Graph g, int u) {
		time++;
		verticies[u].d = time;
		verticies[u].color = Color.GRAY;
		for (int v : g.getNeighbors(u).keySet()) {
			if (verticies[v].color == Color.WHITE) {
				verticies[v].pi = u;
				DFSVisit(g, v);
			}
		}
		verticies[u].color = Color.BLACK;
		time++;
		verticies[u].f = time;
		list.add(0, u);
	}

	/** A getter for the list of the order in which vertices are completed */
	public LinkedList<Integer> getList() {
		return list;
	}

}
