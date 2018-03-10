import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphTest {

    Graph g;
    @Before
    public void setUp() {
        @SuppressWarnings("unchecked")
        String[] poi = new String[]{"1","2","3","4","5"};
        double[][] dis = new double[][]{
                {0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,1.0,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,0,2.0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,3.0,0,Double.POSITIVE_INFINITY,4.0},
                {5.0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,0,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,6.0,Double.POSITIVE_INFINITY,0}};
        g = new Graph(poi,dis);
    }

    @Test
    public void toStringTest() {
        String shouldBe = "1->4:1.0\n2->3:2.0\n3->2:3.0,5:4.0\n4->1:5.0\n5->3:6.0\n";
        assertEquals(shouldBe, g.toString());
    }

    @Test
    public void getTest(){
        assertEquals(25,g.getEdges().size());
        int counter = 0;
        for(Edge edge: g.getEdges()){
            if(edge.getLength() != 0 && edge.getLength() != Double.POSITIVE_INFINITY){
                counter++;
            }
        }
        assertEquals(6, counter);
        g.makePlanes(1);
        assertEquals(6.0, g.getEdgeAll("5_0","3_0").getLength(), .01);
        assertEquals("2", g.getVertex("2").getName());
    }

    @Test
    public void makePlanesTest(){
        g.makePlanes(2);
        String shouldBe = "1_0->4_0:1.0\n2_0->3_0:2.0\n3_0->2_0:3.0,5_0:4.0\n4_0->1_0:5.0\n5_0->3_0:6.0\n1_1->4_1:1.0\n" +
                "2_1->3_1:2.0\n" +
                "3_1->2_1:3.0,5_1:4.0\n" +
                "4_1->1_1:5.0\n" +
                "5_1->3_1:6.0\n";
        assertEquals("5", g.getVertexFromPlane("5_0").getName());
        assertEquals("5_0", g.getVertexFromPlane("5_0").getFullName());
        assertEquals("5_1", g.getVertexFromPlane("5_1").getFullName());
        assertEquals(1, g.getVertexFromPlane("3_1").getPlane());
        assertEquals(2, g.planeNum()) ;
        assertEquals(shouldBe, g.toStringFull());
    }
}
