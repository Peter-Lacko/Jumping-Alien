package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

public class Shark extends OtherCharacters {

	public Shark(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 1.5, 4.0, 0.0, 2.0);
		startMove();
		double[] durationrange = new double[2];
		durationrange[0]=1;
		durationrange[1]=4;
		setDurationrange(durationrange);
	}
	
	public double[] getDurationrange() {
		return durationrange;
	}

	public void setDurationrange(double[] durationrange) {
		this.durationrange = durationrange;
	}
	
	public double[] durationrange;

	@Override
	protected void computeNewHorizontalPositionAfter(double duration) {
		double newPosition;
		if (isMovingLeft() || isMovingRight())
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity() 
				+ 100*0.5*getHorizontalAcceleration()*duration*duration;
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
		double newVelocity;
		if (isMovingLeft() || isMovingRight()){
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		}
	}

	@Override
	public void startMove() {
		setMovementDuration(randomDuration(getDurationrange()));
		if (getRandomBoolean())
			setMovingLeft(true);
		else
			setMovingRight(true);
		if (!isInAir() && getRandomBoolean())
			startJump();
	}

	@Override
	public void endMove() {
		setMovingRight(false);
		setMovingLeft(false);
		endJump();
		setHorizontalVelocity(0.0);
		if (getVerticalVelocity() > 0)
			setVerticalVelocity(0.0);
		startMove();
	}

	@Override
	public void computeNewVerticalPositionAfter(double duration){
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
					100*0.5*getVerticalAcceleration()*duration*duration;
			if (isValidPositionAt(newYPosition,2)){
				this.setPositionAt(newYPosition, 2);
			}
			else if ((int)newYPosition < Y_MAX){
				this.setPositionAt((double)Y_MAX, 2);
			}
		}
		else{
			if (isJumping()){
				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()+ 
						100*0.5*getVerticalAcceleration()*duration*duration;
				if (isValidPositionAt(newYPosition, 2))
					this.setPositionAt(newYPosition, 2);
			}
		}
	}

	/**
	 * Compute the new vertical speed after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new vertical speed.
	 * @effect if the character is in the air, not jumping and the vertical velocity is bigger than 0,
	 * 			the vertical velocity is set to 0
	 * 			| if (isInAir() && (! isJumping()) && (0.0 < getVerticalVelocity()))
	 * 			|	setVerticalVelocity(0.0)
	 * 			otherwise, if the character is in the air, the velocity is set to the calculated velocity
	 * 			| if (isInAir())
	 * 			|	setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration)
	 * 			otherwise, if the character is not in the air, and is jumping, set the velocity to
	 * 			the initial vertical velocity
	 * 			| if ((! isInAir()) && isJumping())
	 * 			|	setVerticalVelocity(getInitVerticalVelocity())
	 * 			otherwise, set the vertical velocity to 0
	 * 			| else
	 * 			| setVerticalVelocity(0.0)
	 */
	@Override
	public void computeNewVerticalVelocityAfter(double duration) throws IllegalArgumentException{
		try{
			if (isInAir()){
				if ((! isJumping()) && (! Util.fuzzyGreaterThanOrEqualTo(0.0, getVerticalVelocity()))){
					setVerticalVelocity(0.0);
				}
				else{
					setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration);
				}
			}
			else{
				if (isJumping()){
					setVerticalVelocity(getInitVerticalVelocity());
				}
				else{
					setVerticalVelocity(0.0);
				}
			}
		}
		catch (IllegalArgumentException exc){
			throw exc;
		}
	}

	/**
	 * A method that starts the character's jump.
	 * @post the character is jumping
	 * 		| new.isJumping() == true
	 * @effect if the character isn't already in the air, its new vertical velocity is equal to the initial
	 * 			vertical velocity
	 * 			| if (! isInAir())
	 * 			|	then setVerticalVelocity(getInitVerticalVelocity())
	 */
	@Override
	public void startJump() throws IllegalArgumentException{
		try {
			if(! isInAir())
				this.setVerticalVelocity(getInitVerticalVelocity());
			this.isJumping = true;
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}

	@Override
	public boolean isInAir(){
		for (int i = getIntPositionAt(1);i<=getIntPositionAt(1+getSprite().getWidth());i++){
			GeoFeature geo = getWorld().getGeoFeatureAt(i, getIntPositionAt(2));
			if (geo == GeoFeature.GROUND || geo == GeoFeature.WATER)
				return false;
		}
		return true;
		
	}

}
