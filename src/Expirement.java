import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Expirement {

	private LinkedList<Integer> list;

	private Vertex[] verticies;

	public Expirement(Graph g, int source, int target, Map<Integer, Edge> map) {
		list = new LinkedList<Integer>();
		int n = g.size();
		int gates = 0;
		for (int key : map.keySet()) {
			g.expandGraph(key, map.get(key), n);
			gates++;
		}
		verticies = new Vertex[(gates + 1) * n];
		for (int i = 0; i < g.size(); i++) {
			verticies[i] = new Vertex(i);
		}
		Dijkstra(source, target + n * gates, g);
	}

	public void Dijkstra(int source, int target, Graph graph) {
		int u = target;
		dijkstras(graph, source);
		while (verticies[u].getPi() != -1) {
			list.add(0, u);
			u = verticies[u].getPi();
		}
		list.add(0, u);
	}

	public void dijkstras(Graph graph, int source) {
		ArrayList<Integer> q = new ArrayList<Integer>();
		for (int v = 0; v < graph.size(); v++) {
			q.add(v);
		}
		verticies[source].setDistanceFromSource(0);
		while (!q.isEmpty()) {
			int u = findMinDistance(q);
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

	public int findMinDistance(ArrayList<Integer> Q) {
		Integer u = Q.get(0);
		for (int v : Q) {
			if (verticies[v].getDistanceFromSource() < verticies[u].getDistanceFromSource()) {
				u = v;
			}
		}
		return u;
	}

	public LinkedList<Integer> getList() {
		return list;
	}
}
