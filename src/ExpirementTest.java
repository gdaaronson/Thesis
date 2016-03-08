import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ExpirementTest {

	Expirement e;
	
	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[5];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		//movement[0].put(1, 1.0);
		movement[0].put(3, 1.0);
		//movement[1].put(0, 1.0);
		movement[1].put(2, 1.0);
		movement[2].put(1, 1.0);
		movement[2].put(4, 1.0);
		movement[3].put(0, 1.0);
		movement[4].put(2, 1.0);

		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(3, new Edge(0, 1, 1.0));
		e = new Expirement(g, 0, 4, map);
		System.out.println(g);		
	}

	@Test
	public void test() {
		assertEquals("[0, 3, 8, 5, 6, 7, 9]", e.getList().toString());
	}

}
