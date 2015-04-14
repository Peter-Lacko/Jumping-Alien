package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

public class Shark extends OtherCharacters {

	public Shark(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 1.5, 4.0, 0.0, 2.0,100);
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
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity() + 100*0.5*getHorizontalAcceleration()*duration*duration;
		else
			newPosition = this.getPositionAt(1);
		if (isValidPositionAt(newPosition,1))
			this.setPositionAt(newPosition, 1);
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
		if (!isInAir() && getRandomBoolean() && (getMovementsSinceLastJump() >=4))
			startJump();
		else if (isInWater()){
			if (getRandomBoolean())
				setDiving(true);
			else
				setRising(true);
		}
		setMovementsSinceLastJump(getMovementsSinceLastJump()+1);
	}

	@Override
	public void endMove() {
		setTimeSinceStartMovement(0.0);
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
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 100*0.5*getVerticalAcceleration()*duration*duration;
			if (isValidPositionAt(newYPosition,2)){
				this.setPositionAt(newYPosition, 2);
			}
		}
		else{
			if (isJumping() || isInWater()){
				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()+ 100*0.5*getVerticalAcceleration()*duration*duration;
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
				if ((! isJumping()) && (! Util.fuzzyGreaterThanOrEqualTo(0.0, getVerticalVelocity())))
					setVerticalVelocity(0.0);
				else
					setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration);
			}
			else{
				if (isJumping())
					setVerticalVelocity(getInitVerticalVelocity());
				if (isRising || isDiving)
					setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration);
				else
					setVerticalVelocity(0.0);
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
			setMovementsSinceLastJump(0);
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}

	@Override
	public boolean isInAir(){
		for (int i = getIntPositionAt(1);i<=getIntPositionAt(1+getSprite().getWidth());i++){
			GeoFeature geobot = getWorld().getGeoFeatureAt(i, getIntPositionAt(2));
			GeoFeature geotop = getWorld().getGeoFeatureAt(i, getIntPositionAt(2)+getSprite().getHeight());
			if (geobot == GeoFeature.GROUND){
				// (mss niet nodig) this.setInWater(false);
				setDiving(false);
				return false;
			}
			if (geotop == GeoFeature.WATER){
				this.setInWater(true);
				return false;
			}
		}
		this.setInWater(false);
		this.setRising(false);
		return true;
	}
	
	public int getMovementsSinceLastJump() {
		return movementsSinceLastJump;
	}
	
	public boolean isInWater() {
		return isInWater;
	}

	public void setInWater(boolean isInWater) {
		this.isInWater = isInWater;
	}
	
	public boolean isInWater = false;
	
	public boolean isRising() {
		return isRising;
	}

	public void setRising(boolean isRising) {
		this.isRising = isRising;
	}
	
	public boolean isRising = false;


	public boolean isDiving() {
		return isDiving;
	}

	public void setDiving(boolean isDiving) {
		this.isDiving = isDiving;
	}

	public boolean isDiving = false;
	
	/**
	 * Return the current vertical acceleration.
	 */
	@Override
	@Basic
	public Double getVerticalAcceleration() {
		if (this.isInAir() || this.isJumping())
			return VERTICAL_ACCELERATION;
		else if (this.isInWater() && (!isJumping))
			if  (isDiving)
				return -VERTICAL_ACCELERARION_WATER;
			else if (isRising)
				return VERTICAL_ACCELERARION_WATER;
			else return 0.0;
		else
			return 0.0;
	}
	
	protected static final double VERTICAL_ACCELERARION_WATER = 2.0;

	public void setMovementsSinceLastJump(int movementsSinceLastJump) {
		this.movementsSinceLastJump = movementsSinceLastJump;
	}

	public int movementsSinceLastJump = 0;

	@Override
	public boolean canHaveAsWorld(World world) {
		if (world.isTerminated())
			return false;
		return true;
	}

	@Override
	public void collision(Characters other) {
		if (other instanceof Mazub){
			if (! ((Mazub)other).isImmune())
				this.damage(50);
				other.damage(50);
				((Mazub)other).startImmune();
			this.endMove();
			((Mazub) other).endMove("left");
			((Mazub) other).endMove("right");
		}
		else if (other instanceof Shark){
			this.endMove();
			((Shark)other).endMove();
		}
		else
			other.collision(this);
	}

}
