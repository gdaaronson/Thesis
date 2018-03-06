
public class Vertex {

	private Color color;

	private Vertex previous;
	
	private double distanceFromSource;

	private int plane;

	public String getFullName() {
		return name + "_" + plane;
	}

	public String getName(){
		return name;
	}

	public String getNameAndUpdatePlane(int plane){
		this.plane = plane;
		return getFullName();
	}

	private String name;

	public int getPlane() {
		return plane;
	}

	public void setPlane(int plane) {
		this.plane = plane;
	}

	public Vertex(String name) {
		this.name = name;
		this.color = Color.WHITE;
		this.previous = null;
		this.distanceFromSource = Double.POSITIVE_INFINITY;
		this.plane = 0;
	}

	public Vertex(String name, double distanceFromSource) {
		this.name = name;
		this.color = Color.WHITE;
		this.previous = null;
		this.distanceFromSource = distanceFromSource;
		this.plane = 0;
	}

	public Vertex(String name, double distanceFromSource, int plane) {
		this.name = name;
		this.color = Color.WHITE;
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
}
