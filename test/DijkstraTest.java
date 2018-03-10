import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DijkstraTest {

	private String[] poiMaker(int... name){
        String[] poi = new String[name.length];
        for(int i = 0; i < name.length; i++){
            poi[i] = "" + name[i];
        }
        return poi;
    }

    private double[][] distMaker(int size){
        double[][] dis = new double[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i != j) {
                    dis[i][j] = Double.POSITIVE_INFINITY;
                } else {
                    dis[i][j] = 0;
                }
            }
        }
        return dis;
    }

    private double[][] distUpdate(double[][] dist, int start, int end, double distance){
        dist[start][end] = distance;
        return dist;
    }

	@Test
	public void testA() {
		@SuppressWarnings("unchecked")
        String[] poi = poiMaker(0,1,2,3,4);
        double[][] dist = distMaker(5);
        distUpdate(dist,0,1,5.0);
        distUpdate(dist,0,4,4.0);
        distUpdate(dist,1,2,10.0);
        distUpdate(dist,3,2,2.0);
        distUpdate(dist,4,2,3.0);
		Graph g = new Graph(poi,dist);
		g.makePlanes(1);
		Dijkstra d = new Dijkstra("0", "2", g);
        assertEquals("[0, 4, 2]", d.getPathNames().toString());

    }

	@Test
	public void testB() {
		@SuppressWarnings("unchecked")
        String[] poi = poiMaker(0,1,2,3,4);
        double[][] dist = distMaker(5);
        distUpdate(dist,0,1,56.0);
        distUpdate(dist,0,3,8.0);
        distUpdate(dist,1,0,56.0);
        distUpdate(dist,1,3,9.0);
        distUpdate(dist,2,1,6.0);
        distUpdate(dist,2,4,12.0);
        distUpdate(dist,3,2,18.0);
        distUpdate(dist,4,3,20.0);
		Graph g = new Graph(poi,dist);
        g.makePlanes(1);
        Dijkstra d = new Dijkstra("3","0", g);
		assertEquals("[3, 2, 1, 0]", d.getPathNames().toString());
	}

	@Test
	public void testC(){
        String[] poi = poiMaker(0,1,2,3,4);
        double[][] dist = distMaker(5);
        distUpdate(dist,0,1,3.0);
        distUpdate(dist,0,3,10.0);
        distUpdate(dist,1,2,4.0);
        distUpdate(dist,2,4,2.0);
        distUpdate(dist,3,4,3.0);
		Graph g = new Graph(poi,dist);
        g.makePlanes(1);
        Dijkstra d = new Dijkstra("0","4", g);
		assertEquals("[0, 1, 2, 4]", d.getPathNames().toString());
	}


}
