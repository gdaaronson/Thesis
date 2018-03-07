import java.util.ArrayList;

public class Graph {

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * A list of vertices in the default plane
	 */
	private ArrayList<Vertex> vert;

	/**
	 * A list of edges in all planes
	 */
	private ArrayList<ArrayList<Edge>> edgePlane;

	/**
	 * A list of vertices in all planes
	 */
	private ArrayList<ArrayList<Vertex>> vertPlane;

	/**
	 * A list of edges in the default plane
	 */
	private ArrayList<Edge> edges;


	//TODO might make a list for return instead
	public Edge getEdge(String name){
		for(Edge e : edges){
			if (e.getEdge(name) != null){
				return e;
			}
		}
		return null;
	}


	//TODO might need to change to a list that gets all neightbrs regargless of vertPlane
	public Vertex getVertex(String key) {
		for (Vertex map : vert){
			if (map.getName().equals(key)){
				return map;
			}
		}
		return null;
	}

	public Vertex getVertex(String key, int plane) {
		for (Vertex map : vertPlane.get(plane)){
			if (map.getName().equals(key)){
				return map;
			}
		}
		return null;
	}


	public ArrayList<Vertex> getVert() {
		return vert;
	}

	public void vertToPlanes(int planeIndex){
		ArrayList<Vertex> copy = new ArrayList<>();
		for(Vertex v : vert){
			copy.add(v.copy(planeIndex));
		}
		vertPlane.add(copy);
	}

	public void edgeToPlanes(int planeIndex){
		ArrayList<Edge> copy = new ArrayList<>();
		for(Edge e : edges){
			copy.add(new Edge(e.getStart().copy(planeIndex),e.getEnd().copy(planeIndex), e.getLength()));
		}
		edgePlane.add(copy);
	}

	public Graph(String[] poi, double[][] distance) {
		vert = new ArrayList<>();
		edges = new ArrayList<>();
		for(int i = 0; i < poi.length; i++){
			vert.add(new Vertex(poi[i]));
		}
		for(int i = 0; i < distance.length; i++){
			for(int j = 0; j < distance[i].length; j++) {
				edges.add(new Edge(vert.get(i), vert.get(j), distance[i][j]));
			}
		}
		vertPlane = new ArrayList<>();
		edgePlane = new ArrayList<>();

	}

	public void makePlanes(int numberOfPlanes) {
		for (int planeIndex = 0; planeIndex < numberOfPlanes; planeIndex++) {
			vertToPlanes(planeIndex);
			edgeToPlanes(planeIndex);
		}
	}

	/** The number of vertices that a graph has */
	public int vertNum() {
		return  vert.size();
	}

	/** The number of planes of existance*/
	public int planeNum(){
		return vertPlane.size();
	}

	public String toString(ArrayList<Edge> edge) {
		String[] g = new String[vertNum()];
		int vertIndex;
		for(Edge e : edge) {
			if (e.getLength() != 0 && e.getLength() != Double.POSITIVE_INFINITY) {
				vertIndex = vert.indexOf(getVertex(e.getStart().getName()));
				if (g[vertIndex] == null || g[vertIndex].isEmpty()) {
					g[vertIndex] = e.getStart().getFullName() + "->" + e.getEnd().getFullName() + ":" + e.getLength();
				} else {
					g[vertIndex] += "," + e.getEnd().getFullName() + ":" + e.getLength();
				}
			}
		}
		String graph = "";
		for(String s : g) {
			graph += s + "\n";
		}
		return graph;
	}

	public String toStringFull(){
		String g = "";
		for(ArrayList<Edge> edge : edgePlane){
			g += toString(edge);
		}
		return g;
	}

	@Override
	public String toString(){
		String[] g = new String[vertNum()];
		int vertIndex;
		for(Edge e : edges) {
			if (e.getLength() != 0 && e.getLength() != Double.POSITIVE_INFINITY) {
				vertIndex = vert.indexOf(e.getStart());
				if (g[vertIndex] == null || g[vertIndex].isEmpty()) {
					g[vertIndex] = e.getStart().getName() + "->" + e.getEnd().getName() + ":" + e.getLength();
				} else {
					g[vertIndex] += "," + e.getEnd().getName() + ":" + e.getLength();
				}
			}
		}
		String graph = "";
		for(String s : g) {
			graph += s + "\n";
		}
		return graph;
	}
}