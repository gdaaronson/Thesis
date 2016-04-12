import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ExpirementTest {

	@Test
	public void testSingleKey() {
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
	public void testDoubleIndepentKey() {
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
		assertEquals("0 -> [1, 3]\n1 -> [0, 7]\n2 -> [5]\n3 -> [0, 15]\n4 -> [5]\n5 -> [2, 4]\n6 -> [7, 9]\n7 -> [6]\n8 -> [11]\n9 -> [6, 10, 21]\n10 -> [9, 11]\n11 -> [8, 10]\n12 -> [13, 15]\n13 -> [12, 14, 19]\n14 -> [13, 17]\n15 -> [12]\n16 -> [17]\n17 -> [14, 16]\n18 -> [19, 21]\n19 -> [18, 20]\n20 -> [19, 23]\n21 -> [18, 22]\n22 -> [21, 23]\n23 -> [20, 22]\n", e.getGraph().toString());
		assertEquals("[0, 1, 7, 6, 9, 10, 11]", e.getList().toString());
		assertEquals("[0, 1, 1, 0, 3, 4, 5]", e.getListMod().toString());
	}

	@Test
	public void testDoubleDepedepentKey() {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[4];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[0].put(1, 1.0);
		movement[1].put(0, 1.0);

		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(1, new Edge(0, 2, 1.0));
		map.put(2, new Edge(1, 3, 1.0));
		Expirement e = new Expirement(g, 0, 3, map);
		assertEquals("[0, 1, 5, 4, 6, 14, 12, 13, 15]", e.getList().toString());
	}
	
	@Test
	public void testTripleDepedepentKey() {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[6];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[3].put(0, 1.0);
		movement[0].put(3, 1.0);
		movement[3].put(4, 1.0);
		movement[4].put(3, 1.0);
		
		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(2, new Edge(4, 5, 1.0));
		map.put(3, new Edge(0, 1, 1.0));
		map.put(4, new Edge(1, 2, 1.0));
		Expirement e = new Expirement(g, 0, 5, map);
		
		assertEquals("0 -> [3]\n1 -> []\n2 -> [8]\n3 -> [0, 4, 15]\n4 -> [3, 28]\n5 -> []\n6 -> [9]\n7 -> []\n8 -> []\n9 -> [6, 10, 21]\n10 -> [9, 11, 34]\n11 -> [10]\n12 -> [13, 15]\n13 -> [12]\n14 -> [20]\n15 -> [12, 16]\n16 -> [15, 40]\n17 -> []\n18 -> [19, 21]\n19 -> [18]\n20 -> []\n21 -> [18, 22]\n22 -> [21, 23, 46]\n23 -> [22]\n24 -> [27]\n25 -> [26]\n26 -> [25, 32]\n27 -> [24, 28, 39]\n28 -> [27]\n29 -> []\n30 -> [33]\n31 -> [32]\n32 -> [31]\n33 -> [30, 34, 45]\n34 -> [33, 35]\n35 -> [34]\n36 -> [37, 39]\n37 -> [36, 38]\n38 -> [37, 44]\n39 -> [36, 40]\n40 -> [39]\n41 -> []\n42 -> [43, 45]\n43 -> [42, 44]\n44 -> [43]\n45 -> [42, 46]\n46 -> [45, 47]\n47 -> [46]\n", e.getGraph().toString());
		assertEquals("[0, 3, 15, 16, 40, 39, 36, 37, 38, 44, 43, 42, 45, 46, 47]", e.getList().toString());
		assertEquals("[0, 3, 3, 4, 4, 3, 0, 1, 2, 2, 1, 0, 3, 4, 5]", e.getListMod().toString());
	}
	
	@Test
	public void testDoubleIndepentKeyForPaper() {
		@SuppressWarnings("unchecked")
		Map<Integer, Double>[] movement = new Map[6];
		for(int i = 0; i < movement.length; i++){
			movement[i] = new HashMap<Integer, Double>();
		}
		movement[0].put(1, 10.0);
		movement[0].put(3, 10.0);
		movement[1].put(0, 10.0);
		movement[3].put(0, 10.0);

		movement[5].put(2, 10.0);
		movement[5].put(4, 10.0);
		movement[2].put(5, 10.0);
		movement[4].put(5, 10.0);

		Graph g = new Graph(movement);
		Map<Integer, Edge> map = new HashMap<Integer, Edge>();
		map.put(3, new Edge(1, 2, 10.0));
		map.put(1, new Edge(3, 4, 30.0));
		Expirement e = new Expirement(g, 0, 5, map);
		assertEquals("0 -> [1, 3]\n1 -> [0, 7]\n2 -> [5]\n3 -> [0, 15]\n4 -> [5]\n5 -> [2, 4]\n6 -> [7, 9]\n7 -> [6]\n8 -> [11]\n9 -> [6, 10, 21]\n10 -> [9, 11]\n11 -> [8, 10]\n12 -> [13, 15]\n13 -> [12, 14, 19]\n14 -> [13, 17]\n15 -> [12]\n16 -> [17]\n17 -> [14, 16]\n18 -> [19, 21]\n19 -> [18, 20]\n20 -> [19, 23]\n21 -> [18, 22]\n22 -> [21, 23]\n23 -> [20, 22]\n", e.getGraph().toString());
		assertEquals("[0, 3, 15, 12, 13, 14, 17]", e.getList().toString());
		assertEquals("[0, 3, 3, 0, 1, 2, 5]", e.getListMod().toString());
	}

}
