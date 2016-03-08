
public class Vertex {

	private Color color;

	private int id;
	
	private int pi;
	
	private double distanceFromSource;
	
	public Vertex(int id) {
		this.id = id;
		this.color = Color.WHITE;
		this.pi = -1;
		this.distanceFromSource = Double.POSITIVE_INFINITY;
	}

	public double getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(double distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public int getPi() {
		return pi;
	}

	public void setPi(int u) {
		this.pi = u;
	}
	
	

}
