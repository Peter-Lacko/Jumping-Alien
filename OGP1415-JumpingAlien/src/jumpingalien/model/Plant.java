package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant extends OtherCharacters {

	public Plant(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.0, 0.5, 0.5, 0.0,1);
		setMovementDuration(0.5);
		setMovingRight(true);
	}

	/**
	 * 
	 * @param duration
	 * @effect	...
	 * 			| if isMovinLeft()
	 * 			|	then this.setHorizontalVelocity(-0.5)
	 * 			| if isMovingRight
	 * 			|	then this.setHorizontalVelocity(0.5)
	 */
	@Override
	protected void computeNewHorizontalVelocityAfter(double duration) {
		 if (isMovingLeft())
			this.setHorizontalVelocity(-0.5);
		 if (isMovingRight())
			this.setHorizontalVelocity(0.5);
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
		if (! isTerminated()){		
			return this.getPositionAt(2);
		}
		else
			return 0.0;
	}
	
	/**
	 * A getter method for the variable lastDirection
	 */
	@Basic
	public MovementDirection getLastDirection() {
		return lastDirection;
	}

	/**
	 * A setter method for the variable lastDirection
	 */
	@Basic
	public void setLastDirection(MovementDirection lastDirection) {
		this.lastDirection = lastDirection;
	}
	
	/**
	 * A boolean stating the last movement direction of the plant
	 */
	public MovementDirection lastDirection = MovementDirection.RIGHT;
	
	/**
	 * @effect	...
	 * 			| if getLastDirection() == MovementDirection.RIGHT
	 * 			|	then setMovingLeft(true)
	 * 			|		setLastDirection(MovementDirection.LEFT)
	 * 			|		setHorizontalVelocity(-getInitHorizontalVelocity())
	 * 			| else
	 * 			|	then setLastDirection(MovementDirection.RIGHT)
	 * 			|		setLastDirection(MovementDirection.RIGHT)
	 * 			|		setHorizontalVelocity(getInitHorizontalVelocity())
	 */
	@Override
	public void startMove() {
		if (getLastDirection() == MovementDirection.RIGHT){
			setMovingLeft(true);
			setLastDirection(MovementDirection.LEFT);
			setHorizontalVelocity(-getInitHorizontalVelocity());
		}
		else{
			setLastDirection(MovementDirection.RIGHT);
			setMovingRight(true);
			setHorizontalVelocity(getInitHorizontalVelocity());
		}
	}

	/**
	 * @effect	...
	 * 			|setTimeSinceStartMovement(0.0)
	 * 			|setMovingRight(false)
	 * 			|setMovingLeft(false)
	 */
	@Override
	public void endMove() {
		setTimeSinceStartMovement(0.0);
		setMovingRight(false);
		setMovingLeft(false);
	}

	/**
	 * 
	 * @param duration
	 * @effect
	 * 			| this.setinitVerticalVelocity(0.0);
	 */
	@Override
	protected void computeNewVerticalVelocityAfter(double duration) {
		this.setinitVerticalVelocity(0.0);
	}

	@Override
	public void startJump() {
	}


	@Override
	public boolean isInAir() {
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
	public void collision(Characters other, boolean isBelow) {
		if (other instanceof Mazub){
			if (! this.isTerminated())
				((Mazub) other).eat(this);
//			this.terminate();
		}
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
	public void checkInAir() {
		// TODO Auto-generated method stub
		
	}

}
