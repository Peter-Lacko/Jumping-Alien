package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import be.kuleuven.cs.som.annotate.*;


public class Mazub extends Characters {

	public Mazub(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.9, 3.0, 1.0, 8.0,100);
		this.nbRunningCycle = getNbImages()/2-4;
	}

	/**
	 * A method that returns the Sprite for the character with its current variables.
	 * @return The new Sprite is determined depending on the state of the character.
	 * 			if the character is in the air and has moved in the last second and is not ducked,
	 * 			return the correct sided sprite for this case. Otherwise, if the character is ducked and 
	 * 			has moved in the last second, return the correct sided sprite for this case. Otherwise,
	 * 			if the character has move in the last second, return the correct sided (walking/ running 
	 * 			cycle) sprite for this case. otherwise, if the character is ducked and has not moved
	 * 			in the last second, return the correct sprite for this case. Otherwise, return the last
	 * 			available sprite.
	 */
	@Override
	public Sprite getCurrentSprite(){
		if ((isInAir()) && (this.getHasMovedIn() != MovementDirection.NONE) && (! isDucked())){
			return leftOrRightSprite(4);
		} else if ((isDucked()) && (this.getHasMovedIn() != MovementDirection.NONE)){
			return leftOrRightSprite(6);
		} else if (this.getHasMovedIn() != MovementDirection.NONE){
			return getCurrentWalkCycleSprite();
		} else if ((isDucked()) && (this.getHasMovedIn() == MovementDirection.NONE)){
			return getImageAt(2);
		} else{
			return getImageAt(1);
		}
	}
	
	/**
	 * A method that returns the walking/running cycle sprite for the character. It possibly returns a
	 * non-cycle sprite if the character is moving in both directions
	 * @return The new walking/ running cycle Sprite is determined depending on the state of the character.
	 * 			if there has been sufficient time since the last frame of animation, set the index to the
	 * 			next frame and reset the timer. if the character is moving in both directions (ie not 
	 * 			moving) return the correct sided sprite for this case. Otherwise, if the character is 
	 * 			moving right, return the correct sided sprite for this case. Otherwise, if the character
	 * 			is moving left, return the correct sided sprite for this case. Otherwise, return the
	 * 			correct sided sprite for all other cases.
	 */
	private Sprite getCurrentWalkCycleSprite() {
		if (getTimeSinceStep() > 0.075){
			if (getIndex() < getNbRunningCycle()-1)
				setIndex(getIndex() + 1);
			else 
				setIndex(0);
			setTimeSinceStep(getTimeSinceStep() - 0.075);
		}
//		if (movingInTwoDirections())
//			return leftOrRightSprite(2);
		if (this.getHorizontalVelocity() > 0)
			return getImageAt(9+getIndex());
		else if (this.getHorizontalVelocity() < 0) 
			return getImageAt(9+getNbRunningCycle()+getIndex());
		else {
			return leftOrRightSprite(2);
		}
	}
	
	/**
	 * Check whether the character can have the given index for its run cycle.
	 * @param number
	 * 			the number to be checked.
	 * @return True if the index is bigger or equal to 0, and smaller than the amount of images for
	 * 			the character's run cycle.
	 * 			| result == ((number >= 0) && (number < this.getNbRunningCycle()))
	 */
	@Override
	public boolean canHaveAsIndex(int number) {
		return ((number >= 0) && (number < this.getNbRunningCycle()));
	}

	/**
	 * A method to return how many sprites there are for a running animation.
	 */
	@Basic @Immutable
	public int getNbRunningCycle() {
		return this.nbRunningCycle;
	}
	
	/**
	 * Check whether the character can have the given number as the amount of images for the running cycle.
	 * @param number
	 * 			the number to check.
	 * @return	true if the number is bigger than 0.
	 * 			|(number > 0)
	 */
	public boolean isValidNbRunningCycle(int number){
		return (number > 0);
	}
	
	/**
	 * A variable that stores how many sprites there are for a running animation (running to the left and
	 * right have the same amount of sprites for the animation).
	 */
	private final int nbRunningCycle ;
	
	/**
	 * Check whether the given amount of images is valid.
	 * @param nbImages
	 * 			the amount to check.
	 * @return true if the amount is bigger or equal to 10 and divisible by 2. (there are different states
	 * 			of the character summing up to at least 10 different sprites total, and it must be divisible
	 * 			by two due to the fact that there must be left and right sided sprites.)
	 * 			| (nbImages >= 10) && (nbImages % 2 == 0)
	 */
	@Override
	public boolean isValidNbImages(int nbImages){
		return ((nbImages >= 10) && (nbImages % 2 == 0));
	}

//	@Override
//	public void computeNewHorizontalPositionAfter(double duration) {
////		if (! isTerminated()){
////			if (this.movingInTwoDirections())
////				this.setPositionAt(this.getPositionAt(1),1);
////			else{
//			if (! this.movingInTwoDirections()){
//				double newPosition;
//				newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
//				if (this.isAccelerating())
//					newPosition += 100*0.5*getHorizontalAcceleration()*duration*duration;
//				if (canHaveAsNewPosition(newPosition,1))
//					this.setPositionAt(newPosition, 1);
////			else{
////				endMove("left");
////				endMove("right");
////			}
//			}
////		}
//	}
	
	/**
	 * Calculate the new horizontal position after the given time
	 * @param duration
	 * 			the duration of time that passes
	 * @return	The new position is calculated with specific formulas.
	 * 			|result == this.getPositionAt(1) + 100*duration*getHorizontalVelocity()
	 * 			|	+ isAccelerating? 100*0.5*duration�*getHorizontalAcceleration() : 0
	 */
	public double calculateNewHorizontalPositionAfter(double duration) {
		double newPosition = getPositionAt(1);
//		if (! this.movingInTwoDirections()){
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
			if (this.isAccelerating())
				newPosition += 100*0.5*getHorizontalAcceleration()*duration*duration;
//		}
		return newPosition;
	}

	/**
	 * Compute the new horizontal velocity after a given duration
	 * @param duration
	 * 			the duration after which to calculate the new horizontal velocity.
	 * @effect if the character is actually moving, compute the new velocity while it is moving
	 * 			| if ((this.isMovingLeft() || this.isMovingRight()) && (! movingInTwoDirections()))
	 * 			| 	computeNewHorizontalVelocityMoving(duration)
	 * @post if the character isn't actually moving, the new horizontal velocity is 0
	 * 			| if (! ((this.isMovingLeft() || this.isMovingRight()) && (! movingInTwoDirections())))
	 * 			|	new.getHorizontalVelocity == 0.0
	 * @post	if the character's horizontal velocity is between 0 and the maximum horizontal velocity 
	 * 			then the character is accelerating
	 * 			| If ((new.getHorizontalVelocity != 0) && (new.getHorizontalVelocity < getMaxHorizontalVelocity())
	 * 			|	then new.isAccelerating() == true
	 * 			Otherwise he is not accelerating.
	 * 			| Else
	 * 			|	new.isAccelerating() == false
	 */
	@Override
	public void computeNewHorizontalVelocityAfter(double duration) {
		if (! isTerminated()){
			double newVelocity = 0.0;
			if ((this.isMovingLeft() || this.isMovingRight()) ){
				newVelocity = computeNewHorizontalVelocityMoving(duration);
			}
			else {
				newVelocity = 0.0;
				setHorizontalVelocity(newVelocity);
			}
			if ((! Util.fuzzyEquals(newVelocity, 0.0)) && (! Util.fuzzyGreaterThanOrEqualTo(
					Math.abs(newVelocity),getMaxHorizontalVelocity()))){
				this.setAccelerating(true);
			}
			else
				this.setAccelerating(false);
		}
	}
	
	/**
	 * Calculate the new horizontal velocity when the character is moving.
	 * @param duration
	 * 			the duration after which to calculate the new horizontal velocity.
	 * @post	If the character is moving left, it has a negative velocity.
	 * 			| If (direction == "left")
	 * 			|	then directionModifier == -1
	 * 			| Else directionModifier == 1	
	 * 			If the absolute value of the current speed is lower than the absolute value of the 
	 * 			initial speed, the new speed is calculated as the smallest value between 
	 * 				1) the absolute value of the initial horizontal velocity plus the duration 
	 * 					times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			| If (Math.abs(getHorizontalVelocity()) < getInitHorizontalVelocity())
	 * 			|	then (new.getHorizontalVelocity == 
	 * 			|	directionModifier*Math.min(Math.abs(getInitHorizontalVelocity()
	 * 			|	+ duration*getHorizontalAcceleration()),getMaxHorizontalVelocity()))
	 * 			Otherwise, the new speed is calculated as the smallest value between 
	 * 				1) the absolute value of the current horizontal velocity plus the duration 
	 * 					times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			| If (Math.abs(getHorizontalVelocity()) >= getInitHorizontalVelocity())
	 * 			|	then (new.getHorizontalVelocity == 
	 * 			|	directionModifier*Math.min(Math.abs(getHorizontalVelocity()
	 * 			|	+ duration*getHorizontalAcceleration()),getMaxHorizontalVelocity()))
	 * @return Returns the newly calculated velocity.
	 *			If the character is moving left, it has a negative velocity.
	 * 			| If (direction == "left")
	 * 			|	then directionModifier == -1
	 * 			| Else directionModifier == 1	
	 * 			If the absolute value of the current speed is lower than the absolute value of the 
	 * 			initial speed, the new speed is calculated as the smallest value between 
	 * 				1) the absolute value of the initial horizontal velocity plus the duration 
	 * 					times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			| If (Math.abs(getHorizontalVelocity()) < getInitHorizontalVelocity())
	 * 			|	then (result == 
	 * 			|	directionModifier*Math.min(Math.abs(getInitHorizontalVelocity()
	 * 			|	+ duration*getHorizontalAcceleration()),getMaxHorizontalVelocity()))
	 * 			Otherwise, the new speed is calculated as the smallest value between 
	 * 				1) the absolute value of the current horizontal velocity plus the duration 
	 * 					times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			| If (Math.abs(getHorizontalVelocity()) >= getInitHorizontalVelocity())
	 * 			|	then (result == 
	 * 			|	directionModifier*Math.min(Math.abs(getHorizontalVelocity()
	 * 			|	+ duration*getHorizontalAcceleration()),getMaxHorizontalVelocity()))
	 */
	private double computeNewHorizontalVelocityMoving(double duration){
		double newVelocity = 0.0;
		if (! Util.fuzzyGreaterThanOrEqualTo(Math.abs(getHorizontalVelocity()), 
				Math.abs(getInitHorizontalVelocity()))){
			newVelocity = getInitHorizontalVelocity() + 
					duration*getHorizontalAcceleration();
			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		}
		else {
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		}
		if (isMovingLeft())
			newVelocity = -1.0*newVelocity;
		setHorizontalVelocity(newVelocity);
		return newVelocity;
	}

	/**
	 * A method to calculate the new position and velocity after a given duration of time. It also 
	 * calculates the time since the previous frame of running animation and time since previous stop.
	 * finally, it determines the correct sprite for the character.
	 * @param duration
	 * 			The duration of time that passes.
	 * @effect	if the character Mazub is terminated, destroy the link between it and the world.
	 * 			|if (isTerminated() && getWorld() != null)
	 * 			|	then getWorld().removeAsObject(this)
	 * @effect the next cases only happen when Mazub is not terminated
	 * 			|if (! this.isTerminated())
	 * @effect determines whether the character is moving in two directions (ie both direction keys are
	 * 			pushed) and what actions should be taken.
	 * 			|determineDoubleDirections()
	 * @effect determines if the character can stand up
	 * 			|if (isEndDuck() && canEndDuck() && canEndDuckWithoutMove())
	 * 			|	then finishDuck()
	 * @effect computes the new horizontal position and velocity after the given duration.
	 * 			| computeHorizontalMovement()
	 * @effect computes the new vertical position and velocity after the given duration.
	 * 			| computeVerticalMovement()
	 * @post The new sprite is calculated with the character's current values
	 * 			| new.getSprite() == this.getCurrentSprite()
	 * @effect	if the character isn't moving, the duration is added to the timer counting the time since
	 * 			the character stopped.
	 * 			|if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
	 * 			|	this.setTimeSinceEndMove(this.getTimeSinceEndMove() + duration)
	 * 			otherwise, the duration is added to the timer counting the time since the previous frame
	 * 			of running animation
	 * 			|else
	 * 			|	this.setTimeSinceStep(this.getTimeSinceStep() + duration)
	 * @effect	if the character hasn't moved in the last second, this fact is then registered in the new 
	 * 			character's variable.
	 * 			|if (this.getTimeSinceEndMove() > 1.0)
	 * 			|	this.setHasMovedIn((MovementDirection.NONE)
	 * @effect	check if the mazub character has won yet
	 * 			|getWorld().checkIfWin(this);
	 * @effect	the mazub character is dealt environment damage as needed
	 * 			|environmentDamage(duration);
	 * @effect	the character Mazub's immune status is updated
	 * 			|immune(duration)
	 * @effect	if Mazub has no more hitpoints, it is dead.
	 * 			|if (getHitPoints() == 0)
	 * 			|	then terminate()
	 * @throws IllegalArgumentException
	 * 			The given duration is an invalid duration of time.
	 * 			| ((duration < 0.0) || (duration >= 0.2))
	 */
	@Override
	public void advanceTimeLong (double duration) throws IllegalArgumentException {
		if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
			throw new IllegalArgumentException();
		if (! isTerminated()){
		removeAllCloseCharacters();
		this.determineDoubleDirections();
		if (isEndDuck())
			if (canEndDuck() && canEndDuckWithoutMove())
				finishDuck();
		this.computeHorizontalMovement(duration);
		removeAllCloseCharacters();
		this.computeVerticalMovement(duration);
//		this.computeNewVerticalPositionAfter(duration);
//		this.computeNewVerticalVelocityAfter(duration);
		if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
			setTimeSinceEndMove(getTimeSinceEndMove() + duration);
		else
			setTimeSinceStep(getTimeSinceStep() + duration);
		if (this.getTimeSinceEndMove() > 1.0)
			this.sethasMovedIn(MovementDirection.NONE);
		this.setSprite(this.getCurrentSprite());
		this.getWorld().checkIfWin(this);	
		this.environmentDamage(duration);
		this.immune(duration);
		if (this.getHitPoints() == 0)
			this.terminate();
		}
		else if (getWorld() != null)
			getWorld().removeAsObject(this);
	}
//	public void advanceTimeLong (double duration) throws IllegalArgumentException {
//		try{
//			if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
//				throw new IllegalArgumentException();
//			this.computeNewVerticalPositionAfter(duration);
//			this.computeNewVerticalVelocityAfter(duration);
//			this.determineDoubleDirections();
//			this.computeNewHorizontalPositionAfter(duration);
//			this.computeNewHorizontalVelocityAfter(duration);
//			if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
//				setTimeSinceEndMove(getTimeSinceEndMove() + duration);
//			else
//				setTimeSinceStep(getTimeSinceStep() + duration);
//			if (this.getTimeSinceEndMove() > 1.0)
//				this.sethasMovedIn(MovementDirection.NONE);
//			this.setSprite(this.getCurrentSprite());
//			if (! isTerminated()){
//				if (this.isEndDuck())
//					this.tryEndDuck();
//				this.getWorld().checkIfWin(this);	
//				this.environmentDamage(duration);
//			}
//			else{
//				this.setTerminateTime(getTerminateTime()+duration);
//				if ((this.getTerminateTime() > 0.6) &&(! (this.getWorld() == null)))
//					this.getWorld().removeAsObject(this);
//			}
//			this.immune(duration);
//		}
//		catch (IllegalArgumentException exc){
//			throw exc;
//		}
//	}
	
	/**
	 * Calculate the new horizontal position and velocity of the Mazub character, as well as damage it recieves
	 * and gives to other characters, after the given duration.
	 * @param duration
	 * 			the duration of time that elapses.
	 * @post	the new horizontal position is set if nothing is in the way.
	 * 			|let newPos = calculateNewHorizontalPositionAfter(duration)
	 * 			|for i in 0..getSprite().getHeight()
	 * 			|	for k in 0.. getSprite().getWidth()
	 * 			|		if (getWorld().getGeoFeatureAt(
	 * 			|			getWorld().getBottomLeftPixelOf(newPos[0]+k, newPos[1]+i)[0],
	 * 			|			getWorld().getBottomLeftPixelOf(newPos[0]+k, newPos[1]+i)[1]) != geoFeature.GROUND
	 * 			|			&& for each character in Characters: 
	 * 			|				for j in 0..character.getSprite().getHeight()-1
	 * 			|					for l in 0..character.getSprite().getWidth()
	 * 			|						if (character.getIntPositionAt(1)+l != newPos[0]+k	
	 * 			|							&& character.getIntPositionAt(2)+j != newPos[1]+i)		
	 * 			|			then new.getPositionAt(1) == newPos
	 * @post	all necessary damage is dealt to any colliding characters.
	 * 			|let newPos = calculateNewHorizontalPositionAfter(duration)
	 * 			|for i in 0..getSprite().getHeight()
	 * 			|	for k in 0.. getSprite().getWidth()
	 * 			|		for each character in Characters: 
	 * 			|			for j in 0..character.getSprite().getHeight()
	 * 			|				for l in 0..character.getSprite().getWidth()
	 * 			|					if (character.getIntPositionAt(1)+l == newPos[0]+k	
	 * 			|						&& character.getIntPositionAt(2)+j == newPos[1]+i)
	 * 			|						then if (j == character.getSprite().getHeight()
	 * 			|								&& i == 0)
	 * 			|							collision(character, true)
	 * 			|						else collision(character,false)
	 * @effect the new Horizontal velocity is set
	 * 			|computeNewHorizontalVelocity()
	 */
	private void computeHorizontalMovement(double duration) {
		double newPos = calculateNewHorizontalPositionAfter(duration);
		int oldPos = getIntPositionAt(1);
		boolean canMove = true;
		if ((int)newPos > oldPos){
			collisionDetectionRight(newPos);
			for (Characters character: getAllCloseCharacters()){
				if (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
						== getIntPositionAt(2))
					collision(character, true);
				else{
					collision(character, false);
					if ((character instanceof Slime) || (character instanceof Shark))
						canMove = false;
				}
			}
			if (canMove){
				canMove = passableTerrainRight(newPos);
			}
		}
		if ((int)newPos < oldPos){
			collisionDetectionLeft(newPos);
			for (Characters character: getAllCloseCharacters()){
				if (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
						== getIntPositionAt(2))
					collision(character, true);
				else{
					collision(character, false);
					if ((character instanceof Slime) || (character instanceof Shark))
						canMove = false;
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
		else{
			// new hor velocity = 0.0
		}
		computeNewHorizontalVelocityAfter(duration);
	}

	
	/**
	 * Calculate the new vertical position and velocity of the Mazub character, as well as damage it recieves
	 * and gives to other characters, after the given duration.
	 * @param duration
	 * 			the duration of time that elapses.
	 * @post	the new vertical position is set if nothing is in the way.
	 * 			|let newPos = calculateNewVerticalPositionAfter(duration)
	 * 			|for i in 0..getSprite().getHeight()
	 * 			|	for k in 0.. getSprite().getWidth()
	 * 			|		if (getWorld().getGeoFeatureAt(
	 * 			|			getWorld().getBottomLeftPixelOf(newPos[0]+k, newPos[1]+i)[0],
	 * 			|			getWorld().getBottomLeftPixelOf(newPos[0]+k, newPos[1]+i)[1]) != geoFeature.GROUND
	 * 			|			&& for each character in Characters: 
	 * 			|				for j in 0..character.getSprite().getHeight()-1
	 * 			|					for l in 0..character.getSprite().getWidth()
	 * 			|						if (character.getIntPositionAt(1)+l != newPos[0]+k	
	 * 			|							&& character.getIntPositionAt(2)+j != newPos[1]+i)		
	 * 			|			then new.getPositionAt(2) == newPos
	 * @post	all necessary damage is dealt to any colliding characters.
	 * 			|let newPos = calculateNewVerticalPositionAfter(duration)
	 * 			|for i in 0..getSprite().getHeight()
	 * 			|	for k in 0.. getSprite().getWidth()
	 * 			|		for each character in Characters: 
	 * 			|			for j in 0..character.getSprite().getHeight()
	 * 			|				for l in 0..character.getSprite().getWidth()
	 * 			|					if (character.getIntPositionAt(1)+l == newPos[0]+k	
	 * 			|						&& character.getIntPositionAt(2)+j == newPos[1]+i)
	 * 			|						then if (j == character.getSprite().getHeight()
	 * 			|								&& i == 0)
	 * 			|							collision(character, true)
	 * 			|						else collision(character,false)
	 * @effect the new vertical velocity is set
	 * 			|computeNewVerticalVelocity()
	 */
	private void computeVerticalMovement(double duration) {
		computeNewVerticalVelocityAfter(duration);
		double newPos = calculateNewVerticalPositionAfter(duration);
		int oldPos = getIntPositionAt(2);
		boolean canMove = true;
		if ((int)newPos > oldPos){
			collisionDetectionUp(newPos);
			for (Characters character: getAllCloseCharacters()){
				collision(character, false);
				if ((character instanceof Slime) || (character instanceof Shark))
					canMove = false;
			}
			if (canMove){
				canMove = passableTerrainUp(newPos);
			}
		}
		if ((int)newPos < oldPos){
			collisionDetectionDown(newPos);
			for (Characters character: getAllCloseCharacters()){
				collision(character, true);
			}
			canMove = passableTerrainDown(newPos);
		}
		if (canMove){
			setPositionAt(newPos, 2);
			//calculate new hor velocity
		}
		else{
			setVerticalVelocity(0.0);
		}
	}
	
	/**
	 * A method to determine what actions should be taken if the character is moving in two directions
	 * (when both keys are pushed). The character will not move in such a case.
	 * @post if the character is moving in both directions, the new character is moving in both directions
	 * 		| if (isMovingLeft() && isMovingRight())
	 * 		|	new.movingInTwoDirections() == true
	 * 		otherwise the new character is not moving in both directions.
	 * 		| else
	 * 		| 	new.movingInTwoDirections() == false
	 * @post 	if the character is not moving in both directions, and is moving right, then the new character
	 * 			has moved right.
	 * 			|if (! (isMovingLeft() && isMovingRight())) && isMovingRight()
	 * 			|	new.getHasMovedIn() == MovementDirection.RIGHT
	 * @post 	if the character is not moving in both directions, and is moving right, then no time has 
	 * 			passed since the new character stopped.
	 * 			|if (! (isMovingLeft() && isMovingRight())) && isMovingRight()
	 * 			|	new.getTimeSinceEndMove() == 0.0
	 * @post 	if the character is not moving in both directions, and is moving left, then the new character
	 * 			has moved left.
	 * 			|if (! (isMovingLeft() && isMovingRight())) && isMovingLeft()
	 * 			|	new.getHasMovedIn() == MovementDirection.LEFT
	 * @post 	if the character is not moving in both directions, and is moving left, then no time has 
	 * 			passed since the new character stopped.
	 * 			|if (! (isMovingLeft() && isMovingRight())) && isMovingLeft()
	 * 			|	new.getTimeSinceEndMove() == 0.0
	 */
	@Model
	private void determineDoubleDirections() {
//		if (isMovingLeft() && isMovingRight())
//			this.setMovingInTwoDirections(true);
//		else {
			this.setMovingInTwoDirections(false);
			if (this.isMovingRight()){
				this.sethasMovedIn(MovementDirection.RIGHT);
				setTimeSinceEndMove(0.0);
			}
			else if (this.isMovingLeft()){
				this.sethasMovedIn(MovementDirection.LEFT);
				setTimeSinceEndMove(0.0);
			}
//		}
	}
	
	/**
	 * Start moving the character in the given direction
	 * @param direction
	 * 			The direction to start moving in.
	 * @pre The direction must be left or right
	 * 		| (direction == "left") || (direction == "right")
	 * @post if the direction was left, the new character is moving left.
	 * 		| if (direction == "left")
	 * 		|	then new.isMovingLeft() == true
	 * @post if the direction was right, the new character is moving right.
	 * 		| if (direction == "right")
	 * 		|	then new.isMovingRight() == true
	 * @post the new character is accelerating
	 * 		| new.isAccelerating() == true
	 * @post The character's new velocity is equal to the initial velocity.
	 * 		| new.getHorizontalVelocity() == new.getInitHorizontalVelocity()
	 */
	public void startMove (String direction) {
		assert (direction == "left" || direction == "right");
		if (direction == "left") {
			this.setMovingLeft(true);
			this.setHorizontalVelocity(getInitHorizontalVelocity());
		}
		else if (direction == "right") {
			this.setMovingRight(true);
			this.setHorizontalVelocity(getInitHorizontalVelocity());
		}
//		this.setAccelerating(true);
//		this.setHorizontalVelocity(getInitHorizontalVelocity());
	}

	/**
	 * Stop moving the character in the given direction
	 * @param direction
	 * 			The direction to stop moving in.
	 * @pre The direction must be left or right
	 * 		| (direction == "left") || (direction == "right")
	 * @post if the direction was left, the new character is not moving left.
	 * 		| if (direction == "left")
	 * 		|	then new.isMovingLeft() == false
	 * @post if the direction was right, the new character is not moving right.
	 * 		| if (direction == "right")
	 * 		|	then new.isMovingRight() == false
	 * @post The index of the new character's running cycle is reset.
	 * 		| new.getIndex() = 0
	 */
	public void endMove (String direction) {
		assert (direction == "left" || direction == "right");
		if (direction == "left"){
			this.setMovingLeft(false);
		}
		else {
			this.setMovingRight(false);
		}
		setIndex(0);
	}

//	/**
//	 * Compute the new vertical position after a given duration.
//	 * @param duration
//	 * 			The duration after after which to calculate the new vertical position.
//	 * @post    if the character is in the air and and the calculated position is 
//	 * 			a possible height then that is the new height. if it's too high the new
//	 * 			height is the maximal height. if it's too low the new height is the minimal 
//	 * 			height. if the character is not in the air but it is jumping, the new height
//	 * 			is calculated.
//	 * 			|if isInAir()
//	 * 			|	then newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
//	 *			|						100*0.5*getVerticalAcceleration()*duration*duration;
//	 * 			|	if canHaveAsNewPosition(newYPosition,2)
//	 * 			|		then new.getPositionAt(2) = newYPosition
//	 * 			|	else if (int)newYPosition < Y_MIN
//	 * 			|		then new.getPositionAt(2) = (double)Y_MIN
//	 *			|	else
//	 *			|		then new.verticalPosition = (double)Y_MAX
//	 *			|else
//	 *			|	if isJumping()
//	 *			|		then new.getPositionAt(2) = this.getPositionAt(2) + 100*duration
//	 *			|									*this.getVerticalVelocity()
//	 *			|									+ 100*0.5*getVerticalAcceleration()*duration*duration;
//	 */
//	@Override
//	public void computeNewVerticalPositionAfter(double duration){
//		if (! isTerminated()){
//			double newYPosition;
//			if (isInAir()){
//				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
//					100*0.5*getVerticalAcceleration()*duration*duration;
//				if (canHaveAsNewPosition(newYPosition,2))
//					this.setPositionAt(newYPosition, 2);
//				else
//					endJump();
//			}
//			else{
//				if (isJumping()){
//					newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()+ 
//							100*0.5*getVerticalAcceleration()*duration*duration;
//					if (canHaveAsNewPosition(newYPosition, 2))
//						this.setPositionAt(newYPosition, 2);
//					else
//						endJump();
//				}
//			}
//		}
//	}
	
	/**
	 * Calculate the new vertical position after the given duration.
	 * @param duration
	 * 			the duration of time that passes
	 * @return if the character isn't terminated, and is in the air, calculate its new (parabolic) position
	 * 			after the duration
	 * 			|if (! isTerminated())
	 * 			|	if (isInAir())
	 * 			|		then result == getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
	 *			|			100*0.5*getVerticalAcceleration()*duration�
	 *			else if the character is terminated and is Jumping, then calculate its new (parabolic)
	 *			position after the duration
	 *			|	else if (isJumping())
	 *			|		then result == getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
	 *			|			100*0.5*getVerticalAcceleration()*duration� 
	 *			else if the character is terminated, return the old position
	 *			|	else result == getP�sitionAt(2)
	 *			if the character is terminated, return 0.0
	 *			|if (isTerminated())
	 *			|	then result == 0.0
	 */
	public double calculateNewVerticalPositionAfter(double duration){
		if (! isTerminated()){
			double newYPosition = this.getPositionAt(2);
			if (isInAir()){
				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
					100*0.5*getVerticalAcceleration()*duration*duration;
				return newYPosition;
			}
			else{
				if (isJumping()){
					newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()+ 
							100*0.5*getVerticalAcceleration()*duration*duration;
					return newYPosition;
				}
				return newYPosition;
			}
		}
		else
			return 0.0;
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
				else
					setVerticalVelocity(0.0);
			}
		}
		catch (IllegalArgumentException exc){
			throw exc;
		}
	}
	
	/**
	 * Check if the Mazub character is in the Air
	 * @result if mazub is not in a world, he is not in the air
	 * 			|if getWorld() == null
	 * 			|	then result == false
	 * 			if the bottom row of pixels is not overlapping with anything, then mazub is in the air
	 * 			|for i in getIntPositionAt(1)..getIntPositionAt(1)+getSprite().getWidth()-1
	 * 			|	let pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2))
	 * 			|	if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
	 * 			|		then result == false
	 * 			|	if (! collisionDetectionVertical(this.getPositionAt(2)-1))
	 * 			|		then result == false
	 * 			|else result == true
	 */
	@Override
	public boolean isInAir(){
		if (getWorld() == null){
			return false;
			}
		else{
			for (int i = getIntPositionAt(1);i<=getIntPositionAt(1)+getSprite().getWidth()-1;i++){
				int[] pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2));
				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
					return false;
				if (! collisionDetectionVertical(this.getPositionAt(2)-1))
					return false;
			}
		}
		return true;
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
//			if(! isInAir())
//				this.setVerticalVelocity(getInitVerticalVelocity());
			this.isJumping = true;
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}

	/**
	 * A method that starts the character's duck
	 * @post	the new character is ducking
	 * 			| new.isDucked() = true
	 * @effect the maximum horizontal velocity is set to 1
	 * 			| setMaxHorizontalVelocity(1.0)
	 * @effect	the character is not trying to end their duck
	 * 			| setEndDuck(false)
	 */
	public void startDuck() throws IllegalArgumentException{
		try{
			this.setMaxHorizontalVelocity(1.0);
			this.setIsDucked(true);
			this.setEndDuck(false);
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}
	
	/**
	 * A method that says when mazub should try to stand up.
	 * @effect setEndDuck(true);
	 */
	public void endDuck() throws IllegalArgumentException{
		try{
			setEndDuck(true);
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}
	
	/**
	 * Check whether mazub can stand up without moving. This is due to the fact that the sprite width of a
	 * ducked mazub is smaller than the sprite width of a standing mazub.
	 * @return false if the added width when standing up is in any ground tile.
	 * 			| let standingSprite = [sprite with mazub's current attributes, but standing]
	 * 			|for j in 1..standingSprite.getHeight()
	 * 			|	for i in 1..standingSprite.getWidth() - getSprite().getWidth()
	 * 			|		let pos = {getIntPositionAt(1) + getSprite().getWidth() +i,getIntPositionAt(2) +j}
	 * 			|		if (getWorld().getGeoFeatureAt(getWorld().getPixelOfTileContaining(pos[0], pos[1])[0]
	 * 			|			, getWorld().getPixelOfTileContaining(pos[0], pos[1])[1]) == GeoFeature.GROUND)
	 * 			|			then result == false
	 * 			|else result == true
	 */
	public boolean canEndDuckWithoutMove(){
		boolean oldDuck = isDucked();
		this.setIsDucked(false);
		Sprite standingSprite = getCurrentSprite();
		this.setIsDucked(oldDuck);
		int nbPixelsHeight = standingSprite.getHeight();
		int nbPixelsWidth = standingSprite.getWidth() - getSprite().getWidth();
		int tileLength = getWorld().getTileLength();
		for (int j=1;j <=nbPixelsHeight; j = j + tileLength){
			for (int i = 1; i <=nbPixelsWidth; i = i +tileLength){
				int[] pos = new int[] {getIntPositionAt(1) + getSprite().getWidth() +i,
						getIntPositionAt(2) +j};
				pos = getWorld().getPixelOfTileContaining(pos[0], pos[1]);
				if (getWorld().getGeoFeatureAt(pos[0], pos[1]) == GeoFeature.GROUND){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Check whether mazub can stand up. This is calculated in the space above the ducking mazub, not
	 * including the extra space that it takes up on the side when it stands up.
	 * @return false if the added width when standing up is in any ground tile.
	 * 			| let standingSprite = [sprite with mazub's current attributes, but standing]
	 * 			|for j in 1..standingSprite.getHeight() - getSprite().getHeight()
	 * 			|	for i in 1..getSprite().getWidth()
	 * 			|		let pos = {getIntPositionAt(1) +i,getIntPositionAt(2) + getSprite().getHeight() +j}
	 * 			|		if (getWorld().getGeoFeatureAt(getWorld().getPixelOfTileContaining(pos[0], pos[1])[0]
	 * 			|			, getWorld().getPixelOfTileContaining(pos[0], pos[1])[1]) == GeoFeature.GROUND)
	 * 			|			then result == false
	 * 			|else result == true
	 */
	public boolean canEndDuck(){
		boolean oldDuck = isDucked();
		this.setIsDucked(false);
		Sprite standingSprite = getCurrentSprite();
		this.setIsDucked(oldDuck);
		int nbPixelsHeight = standingSprite.getHeight() - getSprite().getHeight();
		int tileLength = getWorld().getTileLength();
		for (int j=1;j <=nbPixelsHeight; j = j + tileLength){
			for (int i = 1; i <=getSprite().getWidth(); i = i +tileLength){
				int[] pos = new int[] {getIntPositionAt(1) +i, getIntPositionAt(2) + getSprite().getHeight() +j};
				pos = getWorld().getPixelOfTileContaining(pos[0], pos[1]);
				if (getWorld().getGeoFeatureAt(pos[0], pos[1]) == GeoFeature.GROUND){
					return false;
				}
			}
		}
		return true;
	}
	
//	private void moveAndFinishDuck(){
//		boolean oldDuck = isDucked();
//		this.setIsDucked(false);
//		Sprite standingSprite = getCurrentSprite();
//		this.setIsDucked(oldDuck);
//		int nbPixelsHeight = standingSprite.getHeight();
//		int nbPixelsWidth = standingSprite.getWidth() - getSprite().getWidth();
//		double currentPos = getPositionAt(1);
//		for (int j=1 ; j <= nbPixelsWidth ; j++){
//			setPositionAt(currentPos -j, 1);
//			
//		}
//			
//	}
	
	/**
	 * Effectively end Mazub's duck.
	 * @effect Mazub is not trying to stand up any more
	 * 			|setEndDuck(false)
	 * @effect	Mazub is not ducking anymore
	 * 			|setIsDucked(false);
	 * @effect	Mazub's max horizontal speed is set higher
	 * 			|setMaxHorizontalVelocity(3.0)
	 */
	private void finishDuck(){
		setEndDuck(false);
		setIsDucked(false);
		setMaxHorizontalVelocity(3.0);
	}
	
//	public void tryEndDuck(){
//		if (canEndDuck()){
//			this.isDucked = false;
//			this.setMaxHorizontalVelocity(3.0);
//			this.setEndDuck(false);
//		}
//	}
//	
//	public boolean canEndDuck(){
//		this.getPositionAt(2);
//		int nbTiles = (int)((this.getImageAt(1).getHeight()-this.getSprite().getHeight())/getWorld().getTileLength());
//		for (int j=1;j<=nbTiles+1;j++){
//			for (int i = getIntPositionAt(1);i<getIntPositionAt(1)+getSprite().getWidth();i++){
//				int [] pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2)+getWorld().getTileLength()*j+1);
//				if (getWorld().getGeoFeatureAt(pos[0], pos[1]) == GeoFeature.GROUND){
//					return false;
//				}
//			}
//		}
//		return true;
//	}
	
	/**
	 * A getter method for the boolean isDucked.
	 */
	@Basic
	public boolean isDucked() {
		return this.isDucked;
	}
	
	/**
	 * A setter method to change the boolean isDucked
	 * @param flag
	 * 			the new value to set the boolean to
	 * @post	the new value of isDucked is equal to the given one
	 * 			|new.isDucked() == flag
	 */
	public void setIsDucked(boolean flag){
		this.isDucked = flag;
	}
	
	/**
	 * a boolean stating whether the character is ducked.
	 */
	private boolean isDucked = false;
	
	/**
	 * A getter method for the boolean endDucked.
	 */
	public boolean isEndDuck() {
		return endDuck;
	}

	/**
	 * A setter method to change the boolean endDucked
	 * @param flag
	 * 			the new value to set the boolean to
	 * @post	the new value of endDucked is equal to the given one
	 * 			|new.endDucked() == flag
	 */
	public void setEndDuck(boolean endduck) {
		this.endDuck = endduck;
	}
	
	/**
	 * For when the character is ducking, but has a signal to end the duck, this is true.
	 */
	private boolean endDuck = false;

	/**
	 * A getter method for the variable timeSinceEndMove
	 */
	@Basic
	public double getTimeSinceEndMove() {
		return this.timeSinceEndMove;
	}
	
	/**
	 * a checker method for whether the double time is valid as timeSinceEndMove
	 * @param time
	 * 			the double which has to be checked whether its valid
	 * @return	true if time is greater or equal to zero and a number
	 * 			| result == ((time >= 0) && (! Double.isNaN(time)))
	 */
	public boolean isValidTimeSinceEndMove(double time) {
		return (Util.fuzzyGreaterThanOrEqualTo(time, 0.0) && (! Double.isNaN(time)));
	}
	
	/**
	 * A setter method for the variable timeSinceEndMove
	 * @param time
	 * 			the time to which timeSinceEndMove has to be set
	 * @post	timeSinceEndMove is set to time
	 * 			| new.getTimeSinceEndMove() == time
	 */
	public void setTimeSinceEndMove(double time) {
		this.timeSinceEndMove = time;
	}
	
	/**
	 * A variable that states the time that has passed since the character has stopped moving.
	 */
	private double timeSinceEndMove = 0.0;
	
	/**
	 * A getter method for the variable timeSinceStep
	 */
	@Basic
	public double getTimeSinceStep() {
		return this.timeSinceStep;
	}
	
	/**
	 * a checker method for whether the double time is valid as timeSinceStep
	 * @param time
	 * 			the double which has to be checked whether its valid
	 * @return	true if time is greater or equal to zero and a number
	 * 			| result == ((time >= 0) && (! Double.isNaN(time)))
	 */
	public boolean isValidTimeSinceStep(double time) {
		return (Util.fuzzyGreaterThanOrEqualTo(time, 0.0) && (! Double.isNaN(time)));
	}
	
	/**
	 * a setter method for the variable timeSinceStep
	 * @param time
	 * 			the time to which timeSinceStep has to be set
	 * @post	timeSinceStep is set to time
	 * 			| new.getTimeSinceStep() == time
	 */
	public void setTimeSinceStep(double time) {
		this.timeSinceStep = time;
	}
	
	/**
	 * a variable containing the time since the character has stepped (in the run cycle).
	 */
	private double timeSinceStep = 0.0;
	
	/**
	 * A method to determine what to do when colliding with a character.
	 * @effect	the other character is colliding with this character
	 * 			|other.collision(this, isOnTop)
	 */
	@Override
	public void collision(Characters other, boolean isOnTop){
		 other.collision(this, isOnTop);
	}
	 
	/**
	 * Decide what to do when 'eating' a plant
	 * @param plant
	 * 			the plant to eat
	 * @effect	if Mazub's hitpoints aren't full, add 50 or set to the maximum, and terminate the plant.
	 * 			|if (getHitPoints() < 500)
	 * 			|	then{ setHitPoints(Math.min(500, getHitPoints()+50))
	 * 			|		plant.terminate()}
	 */
	public void eat(Plant plant){
//		if (this.getHitPoints() <= 450)
//			this.setHitPoints(this.getHitPoints()+50);
//		else
//			this.setHitPoints(500);
		if (getHitPoints() < 500)
			setHitPoints(Math.min(500, getHitPoints()+50));
			plant.terminate();
	}
	
	/**
	 * determine what to do with mazub's immunity.
	 * @param duration
	 * 			the duration of time that passes.
	 * @effect if Mazub still has time left to be immune, then his immunity is set to true
	 * 			|if (this.getImmuneTime()>0)
	 * 			|	then setImmune(true)
	 * 			else set his immunity to false
	 * 			|else setImmune(false)
	 * @effect	if Mazub still has time left to be immune, the timer is decreased with the given duration.
	 * 			|if (this.getImmuneTime()>0)
	 * 			|	then setImmuneTime(getImmuneTime()-duration)
	 */
	protected void immune(double duration){
		if (this.getImmuneTime()>0){
			setImmune(true);
			setImmuneTime(getImmuneTime()-duration);
		}
		else
			setImmune(false);
	}
	
	/**
	 * Mazub starts to be immune
	 * @effect Mazub's time to be immune is set to 0.6s
	 * 			|setImmuneTime(0.6)
	 */
	protected void startImmune(){
		setImmuneTime(0.6);
	}

	/**
	 * A getter to return Mazub's time left to be immune
	 * @return the amount of time mazub has left to be immune
	 */
	@Basic
	public double getImmuneTime() {
		return immuneTime;
	}

	/**
	 * A setter to set the amount of time Mazub has left to be immune
	 * @param immuneTime
	 * 			the amount of time mazub has to be immune
	 * @post	mazub's new immune time is equal to the given time.
	 * 			|new.getImmuneTime() == immuneTime
	 */
	public void setImmuneTime(double immuneTime) {
		this.immuneTime = immuneTime;
	}
	
	/**
	 * a variable to store how much time mazub has left to be immune
	 */
	public double immuneTime;
	
	/**
	 * a getter to tell if mazub is immune
	 * @return the boolean stating if mazub is immune
	 */
	@Basic
	public boolean isImmune() {
		return isImmune;
	}

	/**
	 * A setter to set whether mazub is immune or not
	 * @param isImmune
	 * 			the value to set
	 * @post mazub's new immune status is equal to the given value
	 * 			|new.isImmune() == isImmune
	 */
	public void setImmune(boolean isImmune) {
		this.isImmune = isImmune;
	}
	
	/**
	 * a variable stating whether mazub is immune or not
	 */
	public boolean isImmune;

	/**
	 * A method to determine what damage mazub receives from the environment.
	 * @effect	if mazub is in lava, it is in a bad environment and receives 50 damage. The timer is 
	 * 			set correctly.
	 * 			|for i in 0..getSprite().getWidth()
	 * 			|	for j in 0..getSprite().getHeight()
	 * 			|		if (getGeoFeatureAt(getPixelOfTileContaining(getIntPositionAt(1)+i,
	 * 			|			getIntPositionAt(2)+j)[0], getPixelOfTileContaining(getIntPositionAt(1)+i,
	 * 			|			getIntPositionAt(2)+j)[1]) == GeoFeature.MAGMA)
	 * 			|			then {setBadEnvironment(true)
	 * 			|					if getTimeSinceEnvironmentalDamage() == 0.0
	 * 			|						then this.damage(50)
	 * 			|					setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage()+duration)
	 * 			|					if (getTimeSinceEnvironmentalDamage() >= 0.2))
	 * 			|						then setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage() - 0.2)
	 * 			else if mazub is in water, it is in a bad environment and receives 2 damage after the first
	 * 			0.2 seconds.The timer is set correctly.
	 * 			|		else if (getGeoFeatureAt(getPixelOfTileContaining(getIntPositionAt(1)+i,
	 * 			|			getIntPositionAt(2)+j)[0], getPixelOfTileContaining(getIntPositionAt(1)+i,
	 * 			|			getIntPositionAt(2)+j)[1]) == GeoFeature.WATER)
	 * 			|		then {setBadEnvironment(true)
	 * 			|				if getTimeSinceEnvironmentalDamage()+duration >= 0.0
	 * 			|					then this.damage(2)
	 * 			|				setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage()+duration)
	 * 			|				if (getTimeSinceEnvironmentalDamage() >= 0.2))
	 * 			|					then setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage() - 0.2)
	 * 			else mazub is not in a badEnvironment. The timer is set correctly.
	 * 			|		else {setBadEnvironment(false)
	 * 			|			setTimeSinceEnvironmentalDamage(0.0)}
	 * 			
	 */
	@Override
	public void environmentDamage(double duration) {
		int[] pos = {getIntPositionAt(1),getIntPositionAt(2) + getSprite().getHeight()};
		int[] pos1 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)};
		int[] pos2 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)+getSprite().getHeight()};
		if ((environment(getIntPosition()) == GeoFeature.MAGMA) || (environment(pos) == GeoFeature.MAGMA)
				|| (environment(pos1) == GeoFeature.MAGMA) || (environment(pos2) == GeoFeature.MAGMA)){
			this.setBadEnvironment(true);
			if (Util.fuzzyEquals(getTimeSinceEnvironmentalDamage(), 0.0))
				this.damage(50);
		} else if ((environment(getIntPosition()) == GeoFeature.WATER) || (environment(pos) == GeoFeature.WATER)
				|| (environment(pos1) == GeoFeature.WATER) || (environment(pos2) == GeoFeature.WATER)){
			this.setBadEnvironment(true);
			if (Util.fuzzyGreaterThanOrEqualTo(getTimeSinceEnvironmentalDamage()+duration, 0.2))
				this.damage(2);
		}else{
			this.setBadEnvironment(false);
		}
		if (this.isBadEnvironment()){
			this.setTimeSinceEnvironmentalDamage(this.getTimeSinceEnvironmentalDamage()+duration);
			if (Util.fuzzyGreaterThanOrEqualTo(getTimeSinceEnvironmentalDamage(), 0.2))
				setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage() - 0.2);
		}else{
			this.setTimeSinceEnvironmentalDamage(0.0);
		}
	}
	
	/**
	 * A getter for the variable badEnvironment
	 * @return the variable badEnvironment
	 */
	@Basic
	public boolean isBadEnvironment() {
		return badEnvironment;
	}

	/**
	 * A setter for the variable badEnvironment
	 * @param badEnvironment
	 * 			the value to set
	 * @post	the new badEnvironment variable is equal to the given value
	 * 			|new.isBadEnvironmnet() == badEnvironmnet
	 */
	public void setBadEnvironment(boolean badEnvironment) {
		this.badEnvironment = badEnvironment;
	}

	/**
	 * A boolean storing whether mazub is in a bad environment.
	 */
	public boolean badEnvironment = false;
	
//	public double terminateTime = 0.0;
//
//	public double getTerminateTime() {
//		return terminateTime;
//	}
//
//	public void setTerminateTime(double terminateTime) {
//		this.terminateTime = terminateTime;
//	}
	

}
