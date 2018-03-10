import java.util.ArrayList;
import java.util.Map;


public class Speedrunning {

    public ArrayList<Vertex> getList() {
        return list;
    }
    private Graph graph;

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

    private ArrayList<Vertex> list;

    public Speedrunning(Graph g, String source, String target, Map<String,Double> unlock) {
        // Initialization
        list = new ArrayList<>();
        graph = g;

        //Make and connect planes
        graph.makePlanes((int) Math.pow(2,unlock.size()));
        int plane = unlock.size() - 1;
        String[] keys = new String[unlock.size()];
        for(String key: unlock.keySet()) {
            keys[plane] = key;
            plane--;
        }
        for (int planeIndex = 0; planeIndex < graph.planeNum() - 1; planeIndex++){
            for(int edgeIndex = planeIndex + 1; edgeIndex < graph.planeNum(); edgeIndex++){
                if (Integer.bitCount(planeIndex ^ edgeIndex) == 1){
                    int keyIndex = (int)(Math.log(edgeIndex - planeIndex)/Math.log(2));
//                    String[] info = parse(keys[keyIndex], edgeIndex);
                    String[] info = parse(keys[keyIndex]);
                    //makes it so when a key is retrieve, this should open up the new route in a given plane
                    graph.getEdgeAll(info[1] + "_" + edgeIndex, info[2] + "_" + edgeIndex).setLength(unlock.get(keys[keyIndex]));
                    if (keys[keyIndex].contains("<")){
                        graph.getEdgeAll(info[2] + "_" + edgeIndex, info[1] + "_" + edgeIndex).setLength(unlock.get(keys[keyIndex]));
                    }
                    //connects the same edge to a different plane
                    graph.makeEdge(info[0] + "_" + planeIndex, info[0] + "_" + edgeIndex);
                }
            }
        }
        // Call Dijkstra's Algorithm
        searchFromAll(graph.searchThis(graph.getVertexFromPlane(source + "_0")));

        // Find the shortest path of all the possible destinations
        double minDistance = Double.POSITIVE_INFINITY;
        Vertex targetMod = graph.searchThis(graph.getVertexFromPlane(target + "_0"));
        Vertex targetNew = targetMod;
        for(Vertex v : graph.getVertOnAllPlanes(targetMod)){
            if (graph.searchThis(v).getDistanceFromSource() < minDistance) {
                minDistance = v.getDistanceFromSource();
                targetNew = v;
            }
        }
        findPathTo(targetNew);
    }

    public ArrayList<String> getSimpleList(){
        ArrayList<String> strings = new ArrayList<>();
        for (Vertex v: list){
            strings.add(v.getName());
        }
        return strings;
    }

    /**
     * Part of Dijkstra's Algorithm which creates the path
     *
     * @param target
     *            The ending location
     */
    private void findPathTo(Vertex target) {
        Vertex u = target;
        while(u.getPrevious() != null){
            list.add(0, u);
            u = u.getPrevious();
        }
        list.add(0, u);
    }

    private String[] parse(String key){
        String[] info = new String[3];
        info[0] = key.substring(0,key.indexOf(':'));
        if (key.contains("<")){
            info[1] = key.substring(key.indexOf(':') + 1, key.indexOf('<'));
        } else {
            info[1] = key.substring(key.indexOf(':') + 1, key.indexOf('-'));
        }
        info[2] = key.substring(key.indexOf('>')+1);
        return info;
    }

    /**
     * Returns the vertex in the q with the lowest distance from the source
     *
     * @param q the list of neighbors
     */
    private Vertex findClosestVertex(ArrayList<Vertex> q) {
        Vertex u = q.get(0);
        double uDistance = u.getDistanceFromSource();
        for (Vertex v : q) {
            if (v.getDistanceFromSource() < uDistance) {
                u = v;
                uDistance = v.getDistanceFromSource();
            }
        }
        return u;
    }

    /** Getter for the graph */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Part of Dijkstra's Algorithm which calculates distances from the source
     *
     * @param source
     *            The starting location
     */
    private void searchFromAll(Vertex source) {
        ArrayList<Vertex> q = new ArrayList<>();
        for (ArrayList<Vertex> vertices : graph.getVertPlane()) {
            for (Vertex v : vertices) {
                v.setDistanceFromSource(Double.POSITIVE_INFINITY);
                v.setPrevious(null);
                q.add(v);
            }
        }
        source.setDistanceFromSource(0);
        while (!q.isEmpty()) {
            Vertex u = findClosestVertex(q);
            q.remove(u);
            for (Vertex v : graph.getConnectedVert(u)){
                double alt = u.getDistanceFromSource() + graph.getEdgeAll(graph.searchThis(u), graph.searchThis(v)).getLength();
                if (alt < graph.searchThis(v).getDistanceFromSource()) {
                    graph.updateVertex(v, alt, u);
                }
            }
        }
    }
}