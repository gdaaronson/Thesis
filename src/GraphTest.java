import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	Graph g;
	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[5];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[0].put(3, 1.0);
		movement[1].put(2, 2.0);
		movement[2].put(1, 3.0);
		movement[2].put(4, 4.0);
		movement[3].put(0, 5.0);
		movement[4].put(2, 6.0);
		g = new Graph(movement);
	}

	@Test
	public void testMakePlanes() {
		g.makePlanes(1);
		assertEquals(10, g.size());
		assertEquals(5, g.getSizeOfPlane());
	}

	@Test
	public void testMakePlanes2() {
		g.makePlanes(3);
		assertEquals(5, g.getSizeOfPlane());
		assertEquals(40, g.size());
		assertEquals(1.0, g.getNeighbors(35).get(38), .001);
	}
}
