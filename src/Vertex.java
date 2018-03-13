
public class Vertex {

	private Vertex previous;
	
	private double distanceFromSource;

	private int plane;

	public String getFullName() {
		return name + "_" + plane;
	}

	//TODO change to take an input
//	public String getFullNameAri() {
//		return name + "~" + aribitrary[0] +"_" + plane;
//	}


	public String getName(){
		return name;
	}

	private String name;

	public int getPlane() {
		return plane;
	}

	public Vertex(String name) {
		this.name = name;
		this.previous = null;
		this.distanceFromSource = Double.POSITIVE_INFINITY;
		this.plane = 0;
	}

	public Vertex(String name, double distanceFromSource, int plane) {
		this.name = name;
		this.previous = null;
		this.distanceFromSource = distanceFromSource;
		this.plane = plane;
	}

	public Vertex copy(int plane){
		return new Vertex(name,distanceFromSource,plane);
	}

	public double getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(double distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	@Override
	public String toString(){
		return name + "_" + plane + ":" + distanceFromSource;
	}

	public boolean eq(Vertex vertex) {
		return vertex.getName().equals(this.name) && vertex.getPlane() == this.plane;
	}
}
