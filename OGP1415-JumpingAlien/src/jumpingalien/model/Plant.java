package jumpingalien.model;

import jumpingalien.util.Sprite;

public class Plant extends OtherCharacters {

	public Plant(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.0, 0.5, 0.5, 0.0,1);
		setMovementDuration(0.5);
		setMovingRight(true);
		setHeight(getHeight());
		setWidth(getWidth());
	}

	public static int getHeight() {
		return height;
	}


	public static void setHeight(int height) {
		Plant.height = height;
	}
	
	public static int height;
	
	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Plant.width = width;
	}
	
	public static int width;

	@Override
	// TODO rekening houden met collisions
	protected void computeNewHorizontalPositionAfter(double duration) {
		double newPosition;
		if (isMovingLeft() || isMovingRight())
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
		else
			newPosition = this.getPositionAt(1);
		if (canHaveAsNewPosition(newPosition,1))
			this.setPositionAt(newPosition, 1);
	}

	
	@Override
	protected void computeNewHorizontalVelocityAfter(double duration) {
		 if (isMovingLeft())
			this.setHorizontalVelocity(-0.5);
		 if (isMovingRight())
			this.setHorizontalVelocity(0.5);
	}
	
	@Override
	public double calculateNewVerticalPositionAfter(double duration){
		if (! isTerminated()){		
			return this.getPositionAt(2);
		}
		else
			return 0.0;
	}
	
	public MovementDirection getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(MovementDirection lastDirection) {
		this.lastDirection = lastDirection;
	}
	
	public MovementDirection lastDirection = MovementDirection.RIGHT;

	@Override
	public void startMove() {
		if (lastDirection == MovementDirection.RIGHT){
			setMovingLeft(true);
			setLastDirection(MovementDirection.LEFT);
		}
		else{
			setLastDirection(MovementDirection.RIGHT);
			setMovingRight(true);
		}
	}

	@Override
	public void endMove() {
		setTimeSinceStartMovement(0.0);
		setMovingRight(false);
		setMovingLeft(false);
	}

	@Override
	protected void computeNewVerticalPositionAfter(double duration) {
		this.setPositionAt(this.getPositionAt(2), 2);
	}

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
		// TODO Auto-generated method stub
		
	}
	
	protected void terminate() {
		if (! isTerminated()){
			this.setTerminated(true);
			this.getWorld().removeAsObject(this);
			this.setHorizontalVelocity(0.0);
			this.setVerticalVelocity(0.0);
		}
	}

}
