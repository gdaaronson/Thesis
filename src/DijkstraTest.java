import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DijkstraTest {

	private Dijkstra d;
	
	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] neighbors = new Map[5];
		for(int i = 0; i < neighbors.length; i++){
			neighbors[i] = new HashMap<Integer, Double>();
		}
		neighbors[0].put(1, 5.0);
		neighbors[0].put(4, 4.0);
		neighbors[1].put(2, 10.0);
		neighbors[3].put(0, 2.0);
		neighbors[4].put(2, 3.0);
		Graph g = new Graph(neighbors);
		d = new Dijkstra(3, 2, g);
	}

	@Test
	public void testRun() {
		assertEquals("[3, 0, 4, 2]", d.getPath().toString());
	}


}
