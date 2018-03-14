import java.util.ArrayList;
import java.util.Map;


public class SpeedrunningMap {

    private GraphMap graph;

    private double shortestDistance;

    public double getShortestDistance() {
        return shortestDistance;
    }

    /**
     * The constructor for the class
     *
     * @param g
     *            The graph
     * @param source
     *            The name you used for there start of the game
     * @param target
     *            The name you used for the end of the game
     * @param map
     *            The map is what how you unlock things and progress, the string should be formatted as such:
     *            'key':'start'->'finish', if it unlocks in both directions replace '->' with '<->' and the double is the
     *            new time at it takes to get between 'start'->'finish'
     */

    private ArrayList<Vertex> list;

    private String[] keeperOfKeys;

    public SpeedrunningMap(GraphMap g, String source, String target, Map<String,Double> unlock) {
        // Initialization
        list = new ArrayList<>();
        graph = g;
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> gates = new ArrayList<>();
        keeperOfKeys = new String[unlock.size()];
        int size = (int) Math.pow(2,unlock.size());
        //Make and connect planes
        graph.makePlanes(size);
        int plane = 0;
        for(String key : unlock.keySet()) {
            keeperOfKeys[plane] = key;
            plane++;
        }
        for(String key : unlock.keySet()) {
            String[] p = parse(key);
            for (String s : getUnique(p[1],p[2])){
                if (!gates.contains(s)) {
                    gates.add(s);
                }
            }
            for (String s : getUnique(p[0])) {
                if(!keys.contains(s)) {
                    keys.add(s);
                }
            }
        }
        for (int planeIndex = 0; planeIndex < size - 1; planeIndex++) {
            ArrayList<String> used = new ArrayList<>();
            for (int edgeIndex = planeIndex + 1; edgeIndex < size; edgeIndex++) {
                if (Integer.bitCount(planeIndex ^ edgeIndex) == 1) {
                    int keyIndex = (int) (Math.log(edgeIndex - planeIndex) / Math.log(2));
                    String[] info = parse(keeperOfKeys[keyIndex]);
                    if (info.length == 3) {
                        //makes it so when a key is retrieve, this should open up the new route in a given plane
                        graph.makeEdge(info[1] + "_" + edgeIndex, info[2] + "_" + edgeIndex, unlock.get(keeperOfKeys[keyIndex]));
                        if (keeperOfKeys[keyIndex].contains("<")) {
                            graph.makeEdge(info[2] + "_" + edgeIndex, info[1] + "_" + edgeIndex, unlock.get(keeperOfKeys[keyIndex]));
                        }
                        //connects the same edge to a different plane
                        graph.makeEdge(info[0] + "_" + planeIndex, info[0] + "_" + edgeIndex);
                    } else {
                        //if there are non unique keys try the route with some of them
                        if (notUsed(info[0], info[1], info[2], used)){
                            //makes it so when a key is retrieve, this should open up the new route in a given plane
                            graph.getEdgeAll(info[1] + "_" + edgeIndex, info[2] + "_" + edgeIndex).setLength(unlock.get(keeperOfKeys[keyIndex]));
                            if (keeperOfKeys[keyIndex].contains("<")) {
                                graph.getEdgeAll(info[2] + "_" + edgeIndex, info[1] + edgeIndex).setLength(unlock.get(keeperOfKeys[keyIndex]));
                            }
                            //connects the same edge to a different plane
                            graph.makeEdge(info[0] + "_" + planeIndex, info[0] + "_" + edgeIndex);
                            used.add(info[0] + ":" + info[1] + "->" + info[2]);
                        }
                    }
                }
            }
        }
        // Call Dijkstra's Algorithm
        searchFromAll(graph.getVertexFromPlane(source + "_0"));
        // Find the shortest path of all the possible destinations
        double minDistance = Double.POSITIVE_INFINITY;
        Vertex targetMod = graph.getVertexFromPlane(target + "_0");
        Vertex targetNew = targetMod;
        for(Vertex v : graph.getVertOnAllPlanes(targetMod)){
            if (graph.distanceFromSource(v) < minDistance) {
                minDistance = graph.distanceFromSource(v);
                targetNew = v;
            }
        }
        findPathTo(targetNew);
        shortestDistance = minDistance;
    }

    private boolean notUsed(String key, String start, String end, ArrayList<String> used){
        for (String full : used){
            if (full.contains(key + ":") || full.contains(":" + start) || full.contains('>' + end)){
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getUnique(String key){
        ArrayList<String> thing = new ArrayList<>();
        for (String unlock : keeperOfKeys){
            String s = parse(unlock)[0];
            if (!thing.contains(s) && unlock.contains(key)){
                thing.add(s);
            }
        }
        return thing;
    }

    private ArrayList<String> getUnique(String start, String end){
        ArrayList<String> thing = new ArrayList<>();
        for (String unlock : keeperOfKeys){
            String s = parse(unlock)[1] + "->" + parse(unlock)[2];
            if (!thing.contains(s) && unlock.contains(start) && unlock.contains(end)) {
                thing.add(s.substring(0, s.indexOf('-')) +  "->" + s.substring(s.indexOf('>') + 1));
            }
        }
        return thing;
    }

    /**
     * Gives an output without most of the logic attached
     */
    public ArrayList<String> getSimpleList(){
        ArrayList<String> strings = new ArrayList<>();
        for (Vertex v: list){
            strings.add(v.getName());
        }
        return strings;
    }

    public ArrayList<String> getSuperSimpleList(){
        ArrayList<String> strings = new ArrayList<>();
        String last = "";
        for (Vertex v : list){
            if (!last.equals(v.getName())){
                strings.add(v.getName());
                last = v.getName();
            }
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
        Vertex u = graph.getVertexFromPlane(target);
        while(u.getPrevious() != null){
            list.add(0, u);
            u = u.getPrevious();
        }
        list.add(0, u);
    }


    private String[] parse(String key){
        String[] info;
        if(!key.contains("~")) {
            info = new String[3];
        } else {
            int k = key.indexOf('~');
            String[] temp = key.substring(k,key.indexOf(':')).split("~");
            info = new String[3 + temp.length];
            for (int i = 0; i < temp.length -1; i++){
                info[i + 3] = temp[i + 1];
            }
        }
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
        Vertex u = graph.getVertexFromPlane(q.get(0));
        double uDistance = graph.distanceFromSource(u);
        for (Vertex v : q) {
            if (graph.distanceFromSource(v) < uDistance) {
                u = v;
                uDistance = graph.distanceFromSource(v);
            }
        }
        return u;
    }

    /**
     * Part of Dijkstra's Algorithm which calculates distances from the source
     *
     * @param source
     *            The starting location
     */
    private void searchFromAll(Vertex source) {
        ArrayList<Vertex> q = new ArrayList<>();
        for (String s : graph.getVertPlane().keySet()){
            Vertex v = graph.getVertexFromPlane(s);
            graph.setDistanceFromSource(s, Double.POSITIVE_INFINITY);
            graph.setPrev(s, null);
            q.add(v);
        }
        graph.setDistanceFromSource(source, 0);
        while (!q.isEmpty()) {
            Vertex u = graph.getVertexFromPlane(findClosestVertex(q));
            q.remove(u);
            for (Vertex v : graph.getConnectedVert(u)){
                double alt = graph.distanceFromSource(u) + graph.getEdgeLength(u, v);
                if (alt < graph.distanceFromSource(v)) {
                    graph.updateVertex(graph.getVertexFromPlane(v), alt, graph.getVertexFromPlane(u));
                }
            }
        }
    }
}