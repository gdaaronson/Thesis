import java.util.ArrayList;
import java.util.LinkedList;


public class Speedrunning {

    /** The graph of the game */
    private Graph graph;

    /** The list of vertices */
    private ArrayList<Vertex> vertices;

    public LinkedList<Vertex> getList() {
        return list;
    }

    public void setList(LinkedList<Vertex> list) {
        this.list = list;
    }

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

    private LinkedList<Vertex> list;

    public Speedrunning(Graph g, String source, String target) {
        // Initialization
        vertices = g.getVert();
        list = new LinkedList<>();

        // Put the keys in sequential order
        ArrayList<Edge> mapIndex = g.getEdges();

        graph = g;
        // Call Dijkstra's Algorithm
        searchFrom(g, source);
        double minDistance = Double.POSITIVE_INFINITY;
        // Find the shortest path of all the possible destinations
        Vertex pathTo = null;
        for(Vertex v : getVertexFromKey(target)){
            if (v.getDistanceFromSource() < minDistance) {
                pathTo = v;
                minDistance = v.getDistanceFromSource();
            }
        }
        findPathTo(pathTo);
    }

    /**
     * Returns the vertex in the q with the lowest distance from the source
     *
     *
     * @param q
     *            the list of neighbors
     */
    public Vertex findClosestVertex(ArrayList<Vertex> q) {
        Vertex u = q.get(0);
        double distance = Double.POSITIVE_INFINITY;
        //TODO might need to change to just vertecies
        for (Vertex v : getVertexFromKey(u.getName())) {
            if (v.getDistanceFromSource() < distance) {
                u = v;
                distance = v.getDistanceFromSource();
            }
        }
        return u;
    }

    public LinkedList<Vertex> getVertexFromKey(String key){
        LinkedList<Vertex> verts = new LinkedList<>();
        for(Vertex v : vertices){
            if(v.getName().equals(key)){
                verts.add(v);
            }
        }
        return verts;
    }

    /**
     * Part of Dijkstra's Algorithm which creates the path
     *
     * @param target
     *            The ending location
     */
    public void findPathTo(Vertex target) {
        //TODO might need to append numbers onto stings for different planes of existance
        for(Vertex v : vertices){
            if(v.getPrevious() != null) {
                list.add(0, target);
                target = v.getPrevious();
            }
        }
        list.add(0, target);
    }

    /** Getter for the graph */
    public Graph getGraph() {
        return graph;
    }


    /**
     * Part of Dijkstra's Algorithm which calculates distances from the source
     *
     * @param graph
     *            The graph
     * @param source
     *            The starting location
     */
    public void searchFrom(Graph graph, String source) {
        ArrayList<Vertex> q = new ArrayList<>();
        for (Vertex v: vertices) {
            q.add(v);
        }
//        getVertexFromKey(source).setDistanceFromSource(0);
        while (!q.isEmpty()) {
            Vertex u = findClosestVertex(q);
            q.remove(new Vertex(u.getName()));
            for (Vertex v : graph.getVert()) {
                double alt = u.getDistanceFromSource() + u.getDistanceFromSource();
                if (alt < v.getDistanceFromSource()) {
                    v.setDistanceFromSource(alt);
                    v.setPrevious(u.getPrevious());
                }
            }
        }
    }
}
