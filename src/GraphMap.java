import java.util.ArrayList;
import java.util.HashMap;

public class GraphMap {

	/**
	 * A list of edges in all planes
	 */
	private HashMap<String, Edge> edgePlane;

	/**
	 * A list of vertices in all planes
	 */
	private HashMap<String, Vertex> vertPlane;

	public HashMap<String, Vertex> getVertPlane() {
		return vertPlane;
	}

	public ArrayList<Vertex> getVertOnAllPlanes(Vertex v){
		ArrayList<Vertex> vertices = new ArrayList<>();
		for (int i = 0; i < planeNum()/vertNum(); i++) {
			vertices.add(vertPlane.get(v.getName() + "_" + i));
		}
		return vertices;
	}

	public void makeEdge(String start, String end){
		edgePlane.put(start + "->" + end, new Edge(getVertexFromPlane(start), getVertexFromPlane(end), 0.0));
	}

	public void makeEdge(String start, String end, double length){
		edgePlane.put(start + "->" + end, new Edge(getVertexFromPlane(start), getVertexFromPlane(end), length));
	}

	public Edge getEdgeAll(String start, String end){
		return edgePlane.get(start + "->" + end);
	}

	public double getEdgeLength(Vertex start, Vertex end){
		String edgeName = start.getFullName() + "->" + end.getFullName();
		if (!edgePlane.containsKey(edgeName)){
			edgePlane.put(edgeName, new Edge(start,end,Double.POSITIVE_INFINITY));
		}
		return edgePlane.get(edgeName).getLength();
	}

	public double distanceFromSource(Vertex vet){
		if (vertPlane.containsKey(vet.getFullName())){
			return vertPlane.get(vet.getFullName()).getDistanceFromSource();
		}
		return Double.POSITIVE_INFINITY;
	}

	public void setDistanceFromSource(Vertex vet, double distance){
		if (vertPlane.containsKey(vet.getFullName())){
			vertPlane.get(vet.getFullName()).setDistanceFromSource(distance);
		}
	}

	public void setDistanceFromSource(String vet, double distance){
		if (vertPlane.containsKey(vet)){
			vertPlane.get(vet).setDistanceFromSource(distance);
		}
	}

	public void setPrev(String vet, String prev){
		if (vertPlane.containsKey(vet)){
			vertPlane.get(vet).setPrevious(vertPlane.get(prev));
		}
	}

	public Vertex getVertexFromPlane(String key) {
		return vertPlane.get(key);
	}

	public Vertex getVertexFromPlane(Vertex v){
		return vertPlane.get(v.getFullName());
	}

	public void updateVertex(Vertex v, double distance, Vertex prev){
		getVertexFromPlane(v).setPrevious(getVertexFromPlane(prev));
		getVertexFromPlane(v).setDistanceFromSource(distance);
	}

	private void vertToPlanes(int planeIndex){
		for(String v : vert.keySet()){
			vertPlane.put(v + "_" + planeIndex, vert.get(v).copy(planeIndex));
		}
	}

	private void edgeToPlanes(int planeIndex){
		for(String e : edges.keySet()){
			edgePlane.put(edges.get(e).getStart().getName() + "_" + planeIndex + "->" + edges.get(e).getEnd().getName() + "_" + planeIndex, new Edge(edges.get(e).getStart().copy(planeIndex),edges.get(e).getEnd().copy(planeIndex), edges.get(e).getLength()));
		}
	}

	private HashMap<String, Vertex> vert;

	private HashMap<String, Edge> edges;

	/**
	 * This generates a graph for the logic of the game
	 * @param poi should be a list of places/tasks/objects that are needed on the run
	 * @param distance at the start of the game, put the time that it would take do go from one poi to another, with the
	 *                 the starting location in the first index and the ending location index in the second array
	 */
	public GraphMap(String[] poi, double[][] distance) {
		vert = new HashMap<>();
		edges = new HashMap<>();
		for(int i = 0; i < poi.length; i++){
			vert.put(poi[i],new Vertex(poi[i]));
		}
		for(int i = 0; i < distance.length; i++){
			for(int j = 0; j < distance[i].length; j++) {
				Edge e = new Edge(vert.get(poi[i]), vert.get(poi[j]), distance[i][j]);
				edges.put(e.getName(), e);
			}
		}
		vertPlane = new HashMap<>();
		edgePlane = new HashMap<>();
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

	public ArrayList<Vertex> getConnectedVert(Vertex vertex) {
		ArrayList<Vertex> near = new ArrayList<>();
		for (String keys : edgePlane.keySet()){
			Edge e = edgePlane.get(keys);
			if (e.getStart().eq(vertex) && e.getLength() != Double.POSITIVE_INFINITY){
				near.add(e.getEnd());
			}
		}
		return near;
	}
}