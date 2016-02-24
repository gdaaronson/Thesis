import java.util.LinkedList;

public class TopologicalSort {

	public enum Color {
		BLACK, GRAY, WHITE
	}

	private class Vertex {
		Color color;
		int d, f, id, pi;

		public Vertex(Color color, int pi) {
			this.pi = pi;
			this.color = color;
		}
	}

	private LinkedList<Integer> list;

	private int time;

	private Vertex[] verticies;

	public TopologicalSort(Graph g) {
		list = new LinkedList<Integer>();
		DFS(g);
	}

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
		list.add(u);
	}

	public LinkedList<Integer> getList() {
		return list;
	}

}
