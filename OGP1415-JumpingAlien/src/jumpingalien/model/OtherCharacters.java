package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class OtherCharacters extends Characters {

	public OtherCharacters(int x_pos, int y_pos, Sprite[] sprites,double hor_acc, double max_hor_vel, 
			double init_hor_vel, double init_ver_vel,int hitPoints) throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, hor_acc, max_hor_vel, init_hor_vel, init_ver_vel, hitPoints);
	}

	/**
	 * ...
	 * @return	...
	 * 			| leftOrRightSprite(0)
	 */
	@Override
	public Sprite getCurrentSprite() {
		return leftOrRightSprite(0);
	}

	/**
	 * ...
	 * @return	...
	 * 			| result == (number >= 0 && number <2)
	 */
	@Override
	public boolean canHaveAsIndex(int number) {
		return ((number >= 0) && (number < 2));
	}

	/**
	 * ...
	 * @return	...
	 * 			| result == (number == 2)
	 */
	@Override
	public boolean isValidNbImages(int nbImages) {
		return (nbImages == 2);
	}

//	@Override
//	protected abstract void computeNewHorizontalPositionAfter(double duration) ;
//
//	@Override
//	protected abstract void computeNewHorizontalVelocityAfter(double duration) ;
	
	/**
	 * 
	 * @param duration
	 * @return	...
	 * 			| newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity() + 100*0.5*getHorizontalAcceleration()*duration*duration
	 * 			| return newPosition
	 */
	public double calculateNewHorizontalPositionAfter(double duration) {
		double newPosition = getPositionAt(1);
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity()
				 + 100*0.5*getHorizontalAcceleration()*duration*duration;
		return newPosition;
	}

	/**
	 * @effect	...
	 * 			| if isMovingLeft() || isMovingRight()
	 * 			|	then newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration()
	 * 			|		newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity())
	 * 			|		if iMovingLeft()
	 * 			|			then newVelocity = -1.0*newVelocity
	 * 			|		this.setHorizontalVelocity(newVelocity)
	 */
	@Override
	protected void computeNewHorizontalVelocityAfter(double duration) {
		double newVelocity;
		if (isMovingLeft() || isMovingRight()){
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
			if (isMovingLeft())
				newVelocity = -1.0*newVelocity;
			this.setHorizontalVelocity(newVelocity);
		}
		else
			this.setHorizontalVelocity(0.0);
	}
	
	/**
	 * @return	...
	 * 			| if this.isMovingLeft()
	 * 			|	then return -getAbsHorizontalAcceleration()
	 * 			| else if this.isMovingRight()
	 * 			|	then return getAbsHorizontalAcceleration()
	 * 			| else
	 * 			|	return 0.0
	 */
	@Override
	public double getHorizontalAcceleration() {
		if (this.isMovingLeft())
			return -getAbsHorizontalAcceleration();
		else if (this.isMovingRight())
			return getAbsHorizontalAcceleration();
		else
			return 0.0;
	}
	
	
	/**
	 * @effect	...
	 * 			| if not this.isTerminated
	 * 			|	if getTimeSinceStartMovement() < getMovementDuration()
	 * 			|		then removeAllCloseCharacters()
	 * 			|			this.computeHorizontalMovement(duration)
	 * 			|			removeAllCloseCharacters()
	 * 			|			this.computeVerticalMovement(duration)
	 * 			|			setTimeSinceStartMovement(getTimeSinceStartMovement() + duration)
	 * 			|	else
	 * 			|		then endMove()
	 * 			|			startMove()
	 * 			| else
	 * 			|	then this.setTerminateTime(getTerminateTime()+duration)
	 * 			|		if (this.getTerminateTime() > 0.6) && (! (this.getWorld() == null))
	 * 			|			this.getWorld().removeAsObject(this)
	 * 			| if this.getHitPoints == 0
	 * 			|	then this.terminate()
	 *  @throws	...
	 *  		| ((duration < 0.0) || (duration >= 0.2))
	 */
	@Override
	public void advanceTimeLong(double duration){
		try {
			if (! this.isTerminated()){
				if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
					throw new IllegalArgumentException();
				if (getTimeSinceStartMovement() < getMovementDuration()){
					removeAllCloseCharacters();
					this.computeHorizontalMovement(duration);
					removeAllCloseCharacters();
					this.computeVerticalMovement(duration);
					setTimeSinceStartMovement(getTimeSinceStartMovement() + duration);
				}
//				else if (Util.fuzzyLessThanOrEqualTo(getTimeSinceStartMovement(), 0.0))
//					startMove();
				else{
					endMove();
					//setTimeSinceStartMovement(0.0);
					startMove();
				}
//				if (!this.isTerminated())
					this.environmentDamage(duration);
				}
			else{
				this.setTerminateTime(getTerminateTime()+duration);
				if ((this.getTerminateTime() > 0.6) && (! (this.getWorld() == null)))
					this.getWorld().removeAsObject(this);
			}
			if (this.getHitPoints() == 0)
				this.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param duration
	 * @effect	...
	 * 			| newPos = calculateNewHorizontalPositionAfter(duration)
	 * 			| oldPos = getIntPositionAt(1)
	 * 			| canMove = true
	 * 			| if (int)newPos > oldPos
	 * 			| 	then collisionDetectionRight(newPos)
	 * 			|		for character in getAllCloseCharacters()
	 * 			|			if character.getIntPositionAt(2) -this.getSprite().getHeight() -1 == getIntPositionAt(2)
	 * 			|				then collision(character , true)
	 * 			|			else
	 * 			|				then collision(character , false)
	 * 			|		if (character instanceof Slime) || (character instanceof Shark)
	 * 			|			if not (this instanceof Slime)
	 * 			|				then canMove = false
	 * 			|	if canMove
	 * 			|		canMove = passableTerrainRight(newPos)
	 * 			| if (int)newPos < oldPos
	 * 			| 	then collisionDetectionLeft(newPos)
	 * 			|		for character in getAllCloseCharacters()
	 * 			|			if character.getIntPositionAt(2) -this.getSprite().getHeight() -1 == getIntPositionAt(2)
	 * 			|				then collision(character , true)
	 * 			|			else
	 * 			|				then collision(character , false)
	 * 			|		if (character instanceof Slime) || (character instanceof Shark)
	 * 			|			if not (this instanceof Slime)
	 * 			|				then canMove = false
	 * 			|	if canMove
	 * 			|		canMove = passableTerrainLeft(newPos)
	 * 			| if canMove
	 * 			|	then setPositionAt(newPos , 2)
	 * 			| else
	 * 			|	then setVerticalVelocity(0.0)
	 * 			| computeNewVerticalVelocityAfter(duration)
	 */
	private void computeHorizontalMovement(double duration) {
		double newPos = calculateNewHorizontalPositionAfter(duration);
		int oldPos = getIntPositionAt(1);
		boolean canMove = true;
		if ((int)newPos > oldPos){
			collisionDetectionRight(newPos);
			for (Characters character: getAllCloseCharacters()){
				if (character.getIntPositionAt(2) -this.getSprite().getHeight() -1 
						== getIntPositionAt(2))
					collision(character, true);
				else{
					collision(character, false);
					if ((character instanceof Slime) || (character instanceof Shark)){
						if (!(this instanceof Slime)) 
							canMove = false;
					}
				}
			}
			if (canMove){
				canMove = passableTerrainRight(newPos);
			}
		}
		if ((int)newPos < oldPos){
			collisionDetectionLeft(newPos);
			for (Characters character: getAllCloseCharacters()){
				if (character.getIntPositionAt(2)  -this.getSprite().getHeight() -1 
						== getIntPositionAt(2))
					collision(character, true);
				else{
					collision(character, false);
					if ((character instanceof Slime) || (character instanceof Shark)){
						if (!(this instanceof Slime))
							canMove = false;
					}
				}
			}
			if (canMove){
				canMove = passableTerrainLeft(newPos);
			}
		}
		if (canMove){
			setPositionAt(newPos, 1);
			//calculate new hor velocity
		}
//		else{
//			// new hor velocity = 0.0
//		}
		computeNewHorizontalVelocityAfter(duration);
	}

	public abstract double calculateNewVerticalPositionAfter(double duration);
	
	/**
	 * 
	 * @param duration
	 * @effect	...
	 * 			| newPos = calculateNewVerticalPositionAfter(duration)
	 * 			| oldPos = getIntPositionAt(2)
	 * 			| canMove = true
	 * 			| if (int)newPos > oldPos
	 * 			| 	then collisionDetectionUp(newPos)
	 * 			|		for character in getAllCloseCharacters()
	 * 			|		collision(character , true)
	 * 			|		if (character instanceof Slime) || (character instanceof Shark)
	 * 			|			if not (this instanceof Slime)
	 * 			|				then canMove = false
	 * 			|	if canMove
	 * 			|		canMove = passableTerrainUp(newPos)
	 * 			| if (int)newPos < oldPos
	 * 			| 	then collisionDetectionDown(newPos)
	 * 			|		for character in getAllCloseCharacters()
	 * 			|		collision(character , false)
	 * 			|		if (character instanceof Slime) || (character instanceof Shark)
	 * 			|			if not (this instanceof Slime)
	 * 			|				then canMove = false
	 * 			|	if canMove
	 * 			|		canMove = passableTerrainUp(newPos)
	 * 			| if canMove
	 * 			|	then setPositionAt(newPos , 2)
	 * 			| else
	 * 			|	then setVerticalVelocity(0.0)
	 * 			| computeNewVerticalVelocityAfter(duration)
	 */
	private void computeVerticalMovement(double duration) {
		double newPos = calculateNewVerticalPositionAfter(duration);
		int oldPos = getIntPositionAt(2);
		boolean canMove = true;
		if ((int)newPos > oldPos){
			collisionDetectionUp(newPos);
			for (Characters character: getAllCloseCharacters()){
				collision(character,true);
				if ((character instanceof Slime) || (character instanceof Shark)){
					if (! (this instanceof Slime))
						canMove = false;
				}
			}
			if (canMove){
				canMove = passableTerrainUp(newPos);
			}
		}
		if ((int)newPos < oldPos){
			collisionDetectionDown(newPos);
			for (Characters character: getAllCloseCharacters()){
				collision(character, false );
				if ((character instanceof Slime) || (character instanceof Shark)){
					if (! (this instanceof Slime))
						canMove = false;
				}
			}
			if (canMove)
				canMove = passableTerrainDown(newPos);
		}
		if (canMove){
			setPositionAt(newPos, 2);
			//calculate new hor velocity
		}
		else{
			setVerticalVelocity(0.0);
		}
		computeNewVerticalVelocityAfter(duration);
	}

	public abstract void startMove();

	public abstract void endMove();
	
	/**
	 * 
	 * @return	...
	 * 			| Random random = new Random()
	 * 			| return random.nextBoolean()
	 */
	public boolean getRandomBoolean() {
	    Random random = new Random();
	    return random.nextBoolean();
	}
	
	/**
	 * A getter method for the variable timeSinceStartMovement
	 */
	@Basic
	public double getTimeSinceStartMovement() {
		return timeSinceStartMovement;
	}

	/**
	 * A setter method for the variable timeSinceStartMovement
	 */
	@Basic
	public void setTimeSinceStartMovement(double timeSinceStartMovement) {
		this.timeSinceStartMovement = timeSinceStartMovement;
	}
	
	/**
	 * A double containing the time since the start of a movement
	 */
	public double timeSinceStartMovement = 0.0;

	/**
	 * A getter method for the variable movementDuration
	 */
	@Basic
	public double getMovementDuration() {
		return movementDuration;
	}

	/**
	 * A setter method for the variable movementDuration
	 */
	@Basic
	public void setMovementDuration(double movementDuration) {
		this.movementDuration = movementDuration;
	}
	
	/**
	 * 
	 * @param range
	 * @return	...
	 * 			| Random t = new Random()
	 * 			| return range[0] + (range[1]-range[0])*r.nextDouble()
	 */
	public double randomDuration(double[] range){
		Random r = new Random();
		double duration = range[0] + (range[1]-range[0])*r.nextDouble();
		return duration;
	}
	
	/**
	 * A double containing the movement duration
	 */
	public double movementDuration;

	@Override
	protected abstract void computeNewVerticalPositionAfter(double duration) ;

	@Override
	protected abstract void computeNewVerticalVelocityAfter(double duration) ;

	@Override
	public abstract void startJump() ;
	
	/**
	 * A getter method for the variable terminate time
	 */
	@Basic
	public double getTerminateTime() {
		return terminateTime;
	}

	/**
	 * A setter method for the variable terminate time
	 */
	@Basic
	public void setTerminateTime(double terminateTime) {
		this.terminateTime = terminateTime;
	}
	
	/**
	 * A double containing the terminate time
	 */
	public double terminateTime = 0.0;


}
