import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SpeedrunningTest {

    private String[] poiMaker(int... poi){
        String[] p = new String[poi.length];
        for (int i = 0; i < poi.length; i++){
            p[i] = "" + poi[i];
        }
        return p;
    }

    private String[] poiMaker(String... poi){
        String[] p = new String[poi.length];
        for (int i = 0; i < poi.length; i++) {
            p[i] = poi[i];
        }
        return p;
    }

    private double[][] distanceMaker(int size){
        double[][] distance = new double[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                distance[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        return distance;
    }

    @Test
    public void singleKeyTest(){
        String[] PoI = new String[3];
        PoI[0] = "start";
        PoI[1] = "key";
        PoI[2] = "finish";
        double[][] distance = new double[PoI.length][PoI.length];
        distance[0][0] = Double.POSITIVE_INFINITY;
        distance[0][1] = 2;
        distance[0][2] = Double.POSITIVE_INFINITY;
        distance[1][0] = 2;
        distance[1][1] = Double.POSITIVE_INFINITY;
        distance[1][2] = Double.POSITIVE_INFINITY;
        distance[2][0] = Double.POSITIVE_INFINITY;
        distance[2][1] = Double.POSITIVE_INFINITY;
        distance[2][2] = Double.POSITIVE_INFINITY;
        Graph g = new Graph(PoI, distance);
        Map<String,Double> unlock = new HashMap<>();
        unlock.put("key:start->finish",3.0);
        Speedrunning e = new Speedrunning(g, "start", "finish",unlock);
        String shouldBe = "start_0->key_0:2.0\nkey_0->start_0:2.0,key_1:0.0\nnull\n" +
                "start_1->key_1:2.0,finish_1:3.0\nkey_1->start_1:2.0\nnull\n";
        assertEquals(shouldBe, e.getGraph().toStringAll());
        assertEquals("[start, key, key, start, finish]", e.getSimpleList().toString());
    }

    @Test
    public void testDoubleIndepentKey() {
        @SuppressWarnings("unchecked")
        String[] poi = poiMaker(0,1,2,3,4,5);
        double[][] movement = distanceMaker(poi.length);

        movement[0][1] = 1.0;
        movement[0][3] = 1.0;
        movement[1][0] = 1.0;
        movement[3][0] = 1.0;

        movement[5][2] = 1.0;
        movement[5][4] = 1.0;
        movement[2][5] = 1.0;
        movement[4][5] = 1.0;

        Graph g = new Graph(poi,movement);
        Map<String, Double> map = new HashMap<>();
        map.put("3:1->2", 10.0);
        map.put("1:3->4",1.0);
        Speedrunning e = new Speedrunning(g, "0", "5", map);
        assertEquals("[0, 1, 1, 0, 3, 4, 5]", e.getSimpleList().toString());
    }

    @Test
    public void testDoubleDepedepentKeyWords() {
        String[] poi = new String[4];
        poi[0] = "start";
        poi[1] = "key1";
        poi[2] = "key2";
        poi[3] = "finish";
        double[][] movement = distanceMaker(poi.length);
        movement[0][1] = 1.0;
        movement[1][0] = 1.0;
        Graph g = new Graph(poi,movement);
        Map<String, Double> map = new HashMap<>();
        map.put("key1:start<->key2",1.0);
        map.put("key2:key1->finish",1.0);
        Speedrunning e = new Speedrunning(g, "start", "finish", map);
        assertEquals("[start, key1, key1, start, key2, key2, start, key1, finish]", e.getSimpleList().toString());
    }

    @Test
    public void testTripleDepedepentKey() {
        String[] poi = poiMaker(0,1,2,3,4,5);
        double[][] movement = distanceMaker(poi.length);
        movement[3][0] = 1.0;
        movement[0][3] = 1.0;
        movement[3][4] = 1.0;
        movement[4][3] = 1.0;
        Graph g = new Graph(poi, movement);
        Map<String, Double> map = new HashMap<>();
        map.put("2:4->5", 1.0);
        map.put("3:0<->1", 1.0);
        map.put("4:1<->2", 1.0);
        Speedrunning e = new Speedrunning(g, "0", "5", map);
        assertEquals("[0, 3, 3, 4, 4, 3, 0, 1, 2, 2, 1, 0, 3, 4, 5]", e.getSimpleList().toString());
    }

    @Test
    public void multiUnlockKeyTest(){
        String[] poi = poiMaker("start","key1~1","key2~1","key3~1","gate1~1","gate2~1","end");
        double[][] movement = distanceMaker(poi.length);
        movement[0][1] = 1.0;
        movement[0][2] = 2.0;
        movement[0][3] = 3.0;
        movement[1][2] = 1.0;
        movement[2][1] = 1.0;
        movement[2][3] = 1.0;
        movement[3][2] = 1.0;
        movement[5][6] = 1.0;
        Graph g = new Graph(poi, movement);
        Map<String, Double> map = new HashMap<>();
        map.put("key1~1:key1~1->gate1~1",1.0);
        map.put("key2~1:key2~1->gate1~1",1.0);
        map.put("key3~1:key3~1->gate1~1",1.0);
        map.put("key1~1:gate1~1->gate2~1",1.0);
        map.put("key2~1:gate1~1->gate2~1",1.0);
        map.put("key3~1:gate1~1->gate2~1",1.0);
        Speedrunning s = new Speedrunning(g, "start", "end", map);
        assertEquals("[start, key1~1, key1~1, key2~1, key2~1, gate1~1, gate2~1, end]",s.getSimpleList().toString());
    }
}
