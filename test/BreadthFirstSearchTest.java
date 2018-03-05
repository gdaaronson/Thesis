import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class BreadthFirstSearchTest {

	BreadthFirstSearch b;
	
	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] neighbors = new Map[5];
		for(int i = 0; i < neighbors.length; i++){
			neighbors[i] = new HashMap<Integer, Double>();
		}
		neighbors[0].put(1, 1.0);
		neighbors[0].put(3, 1.0);
		neighbors[1].put(2, 1.0);
		neighbors[2].put(4, 1.0);
		neighbors[3].put(4, 1.0);
		Graph g = new Graph(neighbors);
		b = new BreadthFirstSearch(g, 0);
	}

	@Test
	public void testGetPath() {
		assertEquals("[0, 3, 4]", b.getPath(4).toString());
		assertEquals("[0, 1, 2]", b.getPath(2).toString());
	}

}
