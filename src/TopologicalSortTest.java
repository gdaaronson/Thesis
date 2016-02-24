import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TopologicalSortTest {

	TopologicalSort t;
	
	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] neighbors = new Map[6];
		for(int i = 0; i < neighbors.length; i++){
			neighbors[i] = new HashMap<Integer, Double>();
		}
		neighbors[0].put(1, 1.0);
		neighbors[0].put(3, 1.0);
		neighbors[1].put(4, 1.0);
		neighbors[2].put(4, 1.0);
		neighbors[2].put(5, 1.0);
		neighbors[3].put(1, 1.0);
		neighbors[4].put(3, 1.0);
		neighbors[5].put(5, 1.0);
		Graph g = new Graph(neighbors);
		t = new TopologicalSort(g);
	}

	@Test
	public void testTopologicalSort() {
		assertEquals("[3, 4, 1, 0, 5, 2]", t.getList().toString());
	}

}
