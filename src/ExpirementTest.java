import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ExpirementTest {

	@Test
	public void testA() {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[5];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[0].put(3, 1.0);
		movement[1].put(2, 1.0);
		movement[2].put(1, 1.0);
		movement[2].put(4, 1.0);
		movement[3].put(0, 1.0);
		movement[4].put(2, 1.0);
		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(3, new Edge(0, 1, 1.0));
		Expirement e = new Expirement(g, 0, 4, map);
		assertEquals("[0, 3, 8, 5, 6, 7, 9]", e.getList().toString());
	}

	@Test
	public void testB() {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[6];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[0].put(1, 1.0);
		movement[0].put(3, 1.0);
		movement[1].put(0, 1.0);
		movement[3].put(0, 1.0);

		movement[5].put(2, 1.0);
		movement[5].put(4, 1.0);
		movement[2].put(5, 1.0);
		movement[4].put(5, 1.0);

		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(3, new Edge(1, 2, 10.0));
		map.put(1, new Edge(3, 4, 1.0));
		Expirement e = new Expirement(g, 0, 5, map);
		System.out.println(g);	
		assertEquals("[0, 1, 7, 6, 9, 15, 16, 17]", e.getList().toString());
	}
	
}
