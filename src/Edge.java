
public class Edge {
	
	private int start, end, index;
	
	private double length;
	
	public Edge(int start, int end, double length){
		this.start = start;
		this.end = end;
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public double getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "Edge [start=" + start + ", end=" + end + ", length=" + length + "]";
	}

	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	
	
}
