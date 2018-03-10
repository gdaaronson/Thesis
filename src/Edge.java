
public class Edge {
	
	private double length;

	public void setLength(double length) {
		this.length = length;
	}

	private Vertex start, end;

	public Edge(Vertex start, Vertex end, double length){
		this.length = length;
		this.start = start;
		this.end = end;
	}

	public Vertex getStart() {
		return start;
	}

	public Vertex getEnd() {
		return end;
	}

	public double getLength() {
		return length;
	}

	@Override
	public String toString() {
		return start.getFullName() + "->" + end.getFullName() + ":" + length;
	}
}
