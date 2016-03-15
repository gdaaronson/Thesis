import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Expirement {

	private LinkedList<Integer> list;
	
	private LinkedList<Integer> listMod;

	private Vertex[] verticies;

	private Graph graph;

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
			System.out.println("key(" + index + ") = " + key);
			mapIndex[index] = map.get(key);
			mapIndex[index].setIndex(key);
			index++;
		}

		for (int j = 0; j < g.getNumerOfPlanes(); j++) {
			for (int i = 0; i < mapIndex.length; i++) {
				if(((1 << i) & j) != 0){
					g.addNeighbor(mapIndex[i], j);
					g.addNeighbor(mapIndex[i], j -(1 << i), j, mapIndex[i].getIndex());
				}
			}
		}
		
		System.out.println(g);
		graph = g;
		Dijkstra(source, target + g.size() - n, g);
		extractListMod(g);
	}

	public void extractListMod(Graph g) {
		int last = -1;
		for(Integer i: list){
			if(last != i % g.getSizeOfPlane()){
				listMod.add(i % g.getSizeOfPlane());
			}
			last = i % g.getSizeOfPlane();
		}
	}

	public LinkedList<Integer> getListMod() {
		return listMod;
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

	public Graph getGraph() {
		return graph;
	}
}
