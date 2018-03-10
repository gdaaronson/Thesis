import java.util.ArrayList;

public class Graph {

	/**
	 * A list of edges in all planes
	 */
	private ArrayList<ArrayList<Edge>> edgePlane;

	/**
	 * A list of vertices in all planes
	 */
	private ArrayList<ArrayList<Vertex>> vertPlane;

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public ArrayList<ArrayList<Vertex>> getVertPlane() {
		return vertPlane;
	}

	public ArrayList<Vertex> getVertOnAllPlanes(Vertex v){
		ArrayList<Vertex> vPlane = new ArrayList<>();
		for (ArrayList<Vertex> vertices: vertPlane){
			for (Vertex vertex: vertices){
				if(v.getName().equals(vertex.getName())){
					vPlane.add(searchThis(vertex));
				}
			}
		}
		return vPlane;
	}

	public void makeEdge(String start, String end){
		edgePlane.get(extractPlane(start)).add(new Edge(searchThis(getVertexFromPlane(start)), searchThis(getVertexFromPlane(end)), 0.0));
	}

	private int extractPlane(String vertex){
		return Integer.valueOf(vertex.substring(vertex.indexOf('_')+1));
	}

	public Edge getEdgeAll(String start, String end){
		for(ArrayList<Edge> edges: edgePlane) {
			for (Edge e : edges) {
				if (start.equals(e.getStart().getFullName()) && end.equals(e.getEnd().getFullName())) {
					return e;
				}
			}
		}
		return null;
	}

	public Edge getEdgeAll(Vertex start, Vertex end){
		for(ArrayList<Edge> edges: edgePlane) {
			for (Edge e : edges) {
				if (searchThis(start).eq(e.getStart()) && searchThis(end).eq(e.getEnd())) {
					return e;
				}
			}
		}
		return null;
	}

	public Vertex getVertex(String key) {
		for (Vertex v: vert){
			if (v.getName().equals(key)){
					return v;
				}
			}
		return null;
	}

	public Vertex getVertexFromPlane(String key) {
		for (ArrayList<Vertex> vertices: vertPlane){
			for (Vertex vertex : vertices){
				if (vertex.getFullName().equals(key)){
					return searchThis(vertex);
				}
			}
		}
		return null;
	}

	public Vertex searchThis(Vertex v){
		for (ArrayList<Vertex> vertices: vertPlane){
			for (Vertex vertex: vertices){
				if(v.eq(vertex)){
					return vertex;
				}
			}
		}
		return null;
	}

	public void updateVertex(Vertex v, double distance, Vertex prev){
		searchThis(v).setPrevious(searchThis(prev));
		searchThis(v).setDistanceFromSource(distance);

	}

	private void vertToPlanes(int planeIndex){
		ArrayList<Vertex> copy = new ArrayList<>();
		for(Vertex v : vert){
			copy.add(v.copy(planeIndex));
		}
		vertPlane.add(copy);
	}

	private void edgeToPlanes(int planeIndex){
		ArrayList<Edge> copy = new ArrayList<>();
		for(Edge e : edges){
			copy.add(new Edge(e.getStart().copy(planeIndex),e.getEnd().copy(planeIndex), e.getLength()));
		}
		edgePlane.add(copy);

	}

	private ArrayList<Vertex> vert;

	private ArrayList<Edge> edges;

	/**
	 * This generates a graph for the logic of the game
	 * @param poi should be a list of places/tasks/objects that are needed on the run
	 * @param distance at the start of the game, put the time that it would take do go from one poi to another, with the
	 *                 the starting location in the first index and the ending location index in the second array
	 */
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

	private String toString(ArrayList<Edge> edge) {
		String[] g = new String[vertNum()];
		int vertIndex;
		for(Edge e : edge) {
			if (e.getLength() != 0 && e.getLength() != Double.POSITIVE_INFINITY) {
				vertIndex = vertPlane.get(e.getStart().getPlane()).indexOf(getVertexFromPlane(e.getStart().getFullName()));
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

	private String toStringAll(ArrayList<Edge> edge) {
		String[] g = new String[vertNum()];
		int vertIndex;
		for(Edge e : edge) {
			if (e.getLength() != Double.POSITIVE_INFINITY) {
				vertIndex = vertPlane.get(e.getStart().getPlane()).indexOf(getVertexFromPlane(e.getStart().getFullName()));
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

	public String toStringAll(){
		String g = "";
		for(ArrayList<Edge> edge : edgePlane){
			g += toStringAll(edge);
		}
		return g;
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

	public ArrayList<Vertex> getConnectedVert(Vertex vertex) {
		ArrayList<Vertex> near = new ArrayList<>();
		for (ArrayList<Edge> edges : edgePlane){
			for (Edge edge : edges){
				if (edge.getStart().eq(vertex) && edge.getLength() != Double.POSITIVE_INFINITY){
					near.add(edge.getEnd());
				}
			}
		}
		return near;
	}
}