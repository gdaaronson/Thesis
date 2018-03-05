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
		HashMap<Integer, Double>[] neighbors = new HashMap[6];
		for(int i = 0; i < neighbors.length; i++){
			neighbors[i] = new HashMap<>();
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
		assertEquals("[2, 5, 0, 1, 4, 3]", t.getList().toString());
	}

}
