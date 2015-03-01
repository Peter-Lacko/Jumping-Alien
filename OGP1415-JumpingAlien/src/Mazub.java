
/**
 * @author Peter Lacko, Sander Switsers
 *
 */
public class Mazub {
	
	public String getOrientation() {
		return this.Orientation;
	}
	
	private String Orientation;
	
	public Double getHorizontalVelocity() {
		return this.HorizontalVelocity;
	}
	
	private Double HorizontalVelocity;
	
	public Double getHorizontalAcceleration() {
		return this.HORIZONTAL_ACCELERATION;
	}
	
	//moeten deze public of private zijn?
	public final Double INIT_HORIZONTAL_VELOCITY = 1.0;
	
	public final Double MAX_HORIZONTAL_VELOCITY = 3.0;
	
	public static final Double HORIZONTAL_ACCELERATION = 0.9;
	
	public void startMove(String direction) {
		int direction_number = 1;
		if (direction == "left")
			direction_number = -1;
		
	}

}
