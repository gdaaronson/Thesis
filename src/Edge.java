
public class Edge {
	
	private double length;
	private String name;
	private Vertex start, end;

	public Edge(Vertex start, Vertex end, double length){
		this.length = length;
		this.name = start.getName() + "->" + end.getName();
		this.start = start;
		this.end = end;
	}


	public void setLength(double length) {
		this.length = length;
	}

	public String getNameupdatePlane(int planeStart, int planeEnd){
		return start.getNameAndUpdatePlane(planeStart) + "->" + end.getNameAndUpdatePlane(planeEnd);
	}

	public Edge getEdge(String name){
		if(name.equals(this.getName())){
			return this;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getFullName(){
		return start.getFullName() + "->" + end.getFullName();
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
		return "Edge [start=" + start.getFullName() + ", end=" + end.getFullName() + ", length=" + length + "]";
	}
}
