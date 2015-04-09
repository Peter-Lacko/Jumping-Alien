package jumpingalien.model;

import jumpingalien.util.Sprite;

public class Plant extends OtherCharacters {

	public Plant(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.0, 0.5, 0.5, 0.0);
		setMovementDuration(0.5);
		setMovingRight(true);
	}

	@Override
	// TODO rekening houden met collisions
	protected void computeNewHorizontalPositionAfter(double duration) {
		double newPosition;
		if (isMovingLeft() || isMovingRight())
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
		else
			newPosition = this.getPositionAt(1);
		if (isValidPositionAt(newPosition,1))
			this.setPositionAt(newPosition, 1);
		else if ((int)newPosition < X_MIN){
			this.setPositionAt((double)X_MIN, 1);
			this.endMove();
		}
		else {
			this.setPositionAt((double)X_MAX, 1);
			this.endMove();
		}
	}

	
	@Override
	protected void computeNewHorizontalVelocityAfter(double duration) {
		 if (isMovingLeft())
			this.setHorizontalVelocity(-0.5);
		 if (isMovingRight())
			this.setHorizontalVelocity(0.5);
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
		setMovingRight(false);
		setMovingLeft(false);

	}

	@Override
	protected void computeNewVerticalPositionAfter(double duration) {

	}

	@Override
	protected void computeNewVerticalVelocityAfter(double duration) {

	}

	@Override
	public void startJump() {

	}


	@Override
	public boolean isInAir() {
		return false;
	}

}
