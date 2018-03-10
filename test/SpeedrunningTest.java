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
        String shouldBe = "start_0->key1_0:1.0\nkey1_0->start_0:1.0,key1_1:0.0\nkey2_0->key2_2:0.0\nnull\n"
                + "start_1->key1_1:1.0,key2_1:1.0\nkey1_1->start_1:1.0\nkey2_1->start_1:1.0,key2_3:0.0\nnull\n"
                + "start_2->key1_2:1.0\nkey1_2->start_2:1.0,finish_2:1.0,key1_3:0.0\nnull\nnull\n"
                + "start_3->key1_3:1.0,key2_3:1.0\nkey1_3->start_3:1.0,finish_3:1.0\nkey2_3->start_3:1.0\nnull\n";
        assertEquals(shouldBe, e.getGraph().toStringAll());
        assertEquals("[start, key1, key1, start, key2, key2, start, key1, finish]", e.getSimpleList().toString());
    }

//    @Test
//    public void testTripleDepedepentKey() {
//        @SuppressWarnings("unchecked")
//        HashMap<Integer, Double>[] movement = new HashMap[6];
//        for(int i = 0; i < movement.length; i++){
//            movement[i] = new HashMap<>();
//        }
//        movement[3].put(0, 1.0);
//        movement[0].put(3, 1.0);
//        movement[3].put(4, 1.0);
//        movement[4].put(3, 1.0);
//
//        Graph g = new Graph(movement);
//        Map<Integer, Edge> map = new HashMap<>();
//        map.put(2, new Edge(4, 5, 1.0));
//        map.put(3, new Edge(0, 1, 1.0));
//        map.put(4, new Edge(1, 2, 1.0));
//        Speedrunning e = new Speedrunning(g, 0, 5, map);
//
//        assertEquals("0 -> [3]\n1 -> []\n2 -> [8]\n3 -> [0, 4, 15]\n4 -> [3, 28]\n5 -> []\n6 -> [9]\n7 -> []\n8 -> []\n9 -> [6, 10, 21]\n10 -> [9, 11, 34]\n11 -> [10]\n12 -> [13, 15]\n13 -> [12]\n14 -> [20]\n15 -> [12, 16]\n16 -> [15, 40]\n17 -> []\n18 -> [19, 21]\n19 -> [18]\n20 -> []\n21 -> [18, 22]\n22 -> [21, 23, 46]\n23 -> [22]\n24 -> [27]\n25 -> [26]\n26 -> [25, 32]\n27 -> [24, 28, 39]\n28 -> [27]\n29 -> []\n30 -> [33]\n31 -> [32]\n32 -> [31]\n33 -> [30, 34, 45]\n34 -> [33, 35]\n35 -> [34]\n36 -> [37, 39]\n37 -> [36, 38]\n38 -> [37, 44]\n39 -> [36, 40]\n40 -> [39]\n41 -> []\n42 -> [43, 45]\n43 -> [42, 44]\n44 -> [43]\n45 -> [42, 46]\n46 -> [45, 47]\n47 -> [46]\n", e.getGraph().toString());
//        assertEquals("[0, 3, 15, 16, 40, 39, 36, 37, 38, 44, 43, 42, 45, 46, 47]", e.getList().toString());
//        assertEquals("[0, 3, 3, 4, 4, 3, 0, 1, 2, 2, 1, 0, 3, 4, 5]", e.getListMod().toString());
//    }
//
//    @Test
//    public void testDoubleIndepentKeyForPaper() {
//        @SuppressWarnings("unchecked")
//        HashMap<Integer, Double>[] movement = new HashMap[6];
//        for(int i = 0; i < movement.length; i++){
//            movement[i] = new HashMap<>();
//        }
//        movement[0].put(1, 10.0);
//        movement[0].put(3, 10.0);
//        movement[1].put(0, 10.0);
//        movement[3].put(0, 10.0);
//
//        movement[5].put(2, 10.0);
//        movement[5].put(4, 10.0);
//        movement[2].put(5, 10.0);
//        movement[4].put(5, 10.0);
//
//        Graph g = new Graph(movement);
//        Map<Integer, Edge> map = new HashMap<>();
//        map.put(3, new Edge(1, 2, 10.0));
//        map.put(1, new Edge(3, 4, 30.0));
//        Speedrunning e = new Speedrunning(g, 0, 5, map);
//        assertEquals("0 -> [1, 3]\n1 -> [0, 7]\n2 -> [5]\n3 -> [0, 15]\n4 -> [5]\n5 -> [2, 4]\n6 -> [7, 9]\n7 -> [6]\n8 -> [11]\n9 -> [6, 10, 21]\n10 -> [9, 11]\n11 -> [8, 10]\n12 -> [13, 15]\n13 -> [12, 14, 19]\n14 -> [13, 17]\n15 -> [12]\n16 -> [17]\n17 -> [14, 16]\n18 -> [19, 21]\n19 -> [18, 20]\n20 -> [19, 23]\n21 -> [18, 22]\n22 -> [21, 23]\n23 -> [20, 22]\n", e.getGraph().toString());
//        assertEquals("[0, 3, 15, 12, 13, 14, 17]", e.getList().toString());
//        assertEquals("[0, 3, 3, 0, 1, 2, 5]", e.getListMod().toString());
//    }

}
