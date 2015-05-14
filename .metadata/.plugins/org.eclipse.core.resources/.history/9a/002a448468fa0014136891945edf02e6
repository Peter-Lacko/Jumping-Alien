package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant extends Characters implements OtherCharacters {

	public Plant(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.0, 0.5, 0.5, 0.0,1);
		setMovingRight(true);
		durationRange = new double[] {0.5,0.5};
	}
	
	@Override
	public void advanceTimeLong(double duration){
		super.advanceTimeLong(duration);
		if (! this.isTerminated()){
			if (getTimeSinceStartMovement() < getMovementDuration()){
				setTimeSinceStartMovement(getTimeSinceStartMovement() + duration);
			}
			else{
				selectMovements();
			}
		}
		else{
			this.setTerminateTime(getTerminateTime()+duration);
			if ((this.getTerminateTime() > 0.6) && (! (this.getWorld() == null)))
				this.getWorld().removeAsObject(this);
		}
	}

	@Override
	public void computeNewHorizontalVelocityAfter(double duration) {
		this.setHorizontalVelocity(getInitHorizontalVelocity());
	}
	
	/**
	 * 
	 * @param duration
	 * 
	 * @return	...
	 * 			| if not this.isTerminated()
	 * 			|	then return this.getPositionAt(2)
	 * 			| else
	 * 			|	then return 0.0
	 */
	@Override
	public double calculateNewVerticalPositionAfter(double duration){
		return this.getPositionAt(2);
	}
	
	/**
	 * A getter method for the variable durationRange
	 */
	@Basic @Override
	public double[] getDurationRange() {
		return durationRange.clone();
	}
	
	/**
	 * A variable containing the range of the movement durations of the characters
	 */
	public final double[] durationRange;

	@Override
	public void selectMovements(){
		setTimeSinceStartMovement(getTimeSinceStartMovement() - getMovementDuration());
		if (isMovingRight()){
			endMove("right");
			startMove("left");
		}
		else if (isMovingLeft()){
			endMove("left");
			startMove("right");
		}
	}

	/**
	 * 
	 * @param duration
	 * @effect
	 * 			| this.setinitVerticalVelocity(0.0);
	 */
	@Override
	public void computeNewVerticalVelocityAfter(double duration) {
		this.setVerticalVelocity(getInitVerticalVelocity());
	}

	@Override
	public void startJump() {
	}


	@Override
	public boolean isFalling() {
		return false;
	}

	/**
	 * 
	 * @param other
	 * 			the other character in the collision
	 * @param isBelow
	 * 			a boolean stating whether this is below other
	 * @effect	...
	 * 			| if ((other instanceof Mazub) && (not this.isTerminated())
	 * 			|	then other.eat(this)
	 */
	@Override
	public void collision(Characters other) {
		if (other instanceof Mazub){
			if (! this.isTerminated())
				((Mazub) other).eat(this);
		}
		else if ((other instanceof Plant) || (other instanceof Shark)){}
		else
			other.collision(this);
	}

	@Override
	public void collisionNoDamageFrom(Characters other){
		if (other instanceof Mazub)
			collision(other);
		else if ((other instanceof Plant) || (other instanceof Shark) || (other instanceof Slime)){}
		else
			other.collisionNoDamageTo(this);
	}
	
	@Override
	public void collisionNoDamageTo(Characters other){
		if (other instanceof Mazub)
			collision(other);
		else if ((other instanceof Plant) || (other instanceof Shark) || (other instanceof Slime)){}
		else
			other.collisionNoDamageFrom(this);
	}
	
	@Override
	public boolean collide(Characters other){
		if (other instanceof Plant)
			return false;
		else if (other instanceof Slime)
			return false;
		else if (other instanceof Shark)
			return false;
		else if (other instanceof Mazub)
			return false;
		else
			return other.collide(this);
	}
	
	@Override
	public void environmentDamage(double duration) {		
	}
	
	/**
	 * @effect	...
	 * 			| if not this.isTerminated
	 * 			|	then this.setTerminated(true)
	 * 			|		this.getWorld().removeAsObject(this)
	 * 			|		this.setHorizontalVelocity(0.0)
	 * 			|		this.setVerticalVelocity(0.0)
	 */
	protected void terminate() {
		if (! isTerminated()){
			this.setTerminated(true);
			this.getWorld().removeAsObject(this);
			this.setHorizontalVelocity(0.0);
			this.setVerticalVelocity(0.0);
		}
	}

	@Override
	public boolean checkFalling(){
		return false;
	}

	@Override
	public double getTimeSinceStartMovement() {
		return timeSinceStartMovement;
	}

	@Override
	public void setTimeSinceStartMovement(double time) {
		timeSinceStartMovement = time;
	}
	
	/**
	 * A double containing the time since the start of a movement
	 */
	private double timeSinceStartMovement = 0.0;

	@Override
	public double getMovementDuration() {
		return 0.5;
	}

	@Override
	public void setMovementDuration(double movementDuration) {
	}
	
	@Override
	public double getTerminateTime() {
		return terminateTime;
	}

	@Override
	public void setTerminateTime(double time) {
		terminateTime = time;
	}
	
	/**
	 * A double containing the terminate time
	 */
	private double terminateTime = 0.0;

	@Override
	public double getDurationRangeValueAt(int index) {
		return 0.5;
	}

}
