package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import be.kuleuven.cs.som.annotate.*;


public class Mazub extends Characters {

	public Mazub(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.9, 3.0, 1.0, 8.0);
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
		if (getTimeSinceStep() > 0.03){
			if (getIndex() < getNbRunningCycle()-1)
				setIndex(getIndex() + 1);
			else 
				setIndex(0);
			setTimeSinceStep(0.0);
		}
		if (movingInTwoDirections())
			return leftOrRightSprite(2);
		else if (isMovingRight())
			return getImageAt(9+getIndex());
		else if (isMovingLeft()) 
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

	/**
	 * Compute the new horizontal position after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new horizontal position.
	 * @post 	If the character is accelerating and if the newly calculated position is valid, the new 
	 * 			character is at that position.
	 * 			|newPositionAccelerating = this.getPositionAt(1) +100*duration*this.getHorizontalVelocity() 
	 * 			|				+ 100*0.5*getHorizontalAcceleration()*duration²
	 * 			|if (isAccelerating() && isValidPositionAt(newPositionAccelerating,1))
	 * 			|	new.getPositionAt(1) = newPositionAccelerating
	 * 			otherwise, if the character is not accelerating and if the newly calculated position is
	 * 			valid, the new character is at that position.
	 * 			|newPosition = this.getPositionAt(1) +100*duration*this.getHorizontalVelocity()
	 * 			|if ((! isAccelerating()) && isValidPositionAt(newPositionAccelerating,1))
	 * 			|	new.getPositionAt(1) = newPosition
	 * 			otherwise, if the character is accelerating, the newly calculated position is not
	 * 			valid and the new position is smaller than the smallest possible position, the new
	 * 			character is at the smallest possible position.
	 * 			|if (isAccelerating() && (! isValidPositionAt(newPositionAccelerating,1)) &&
	 * 			|	((int)newPositionAccelerating < X_MIN))
	 * 			|	new.getPositionAt(1) = (double)X_MIN
	 * 			otherwise, if the character is accelerating, the newly calculated position is not
	 * 			valid, the new character is at the highest possible position.
	 * 			|else if (isAccelerating() && (! isValidPositionAt(newPositionAccelerating,1)))
	 * 			|	new.getPositionAt(1) = (double)X_MAX
	 * 			otherwise, if the character is not accelerating, the newly calculated position is not
	 * 			valid and the new position is smaller than the smallest possible position, the new
	 * 			character is at the smallest possible position.
	 * 			|if ((! isAccelerating()) && (! isValidPositionAt(newPositionAccelerating,1)) &&
	 * 			|	((int)newPositionAccelerating < X_MIN))
	 * 			|	new.getPositionAt(1) = (double)X_MIN
	 * 			otherwise, if the character is not accelerating, the newly calculated position is not
	 * 			valid, the new character is at the highest possible position.
	 * 			|else if ((! isAccelerating()) && (! isValidPositionAt(newPositionAccelerating,1)))
	 * 			|	new.getPositionAt(1) = (double)X_MAX
	 * @effect	if the new position is at the lowest position, the character stops moving left.
	 * 			| if (new.getPositionAt(1) == (double)X_MIN)
	 * 			|	this.endMove("left")
	 * @effect	if the new position is at the highest position, the character stops moving right.
	 * 			| if (new.getPositionAt(1) == (double)X_MAX)
	 * 			|	this.endMove("right")
	 */
	@Override
	public void computeNewHorizontalPositionAfter(double duration) {
		if (this.movingInTwoDirections())
			this.setPositionAt(this.getPositionAt(1),1);
		else{
			double newPosition;
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
			if (this.isAccelerating())
				newPosition += 100*0.5*getHorizontalAcceleration()*duration*duration;
			if (isValidPositionAt(newPosition,1))
				this.setPositionAt(newPosition, 1);
			else if ((int)newPosition < X_MIN){
				this.setPositionAt((double)X_MIN, 1);
				this.endMove("left");
			}
			else {
				this.setPositionAt((double)X_MAX, 1);
				this.endMove("right");
			}
		}
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
		double newVelocity = 0.0;
		if ((this.isMovingLeft() || this.isMovingRight()) && (! movingInTwoDirections())){
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
	 * @effect determines whether the character is moving in two directions (ie both direction keys are
	 * 			pushed, so the character is not moving) and what actions should be taken.
	 * 			|determineDoubleDirections()
	 * @effect computes the new horizontal position after the given duration.
	 * 			| computeNewHorizontalPositionAfter(duration)
	 * @effect computes the new horizontal velocity after the given duration.
	 * 			| computeNewHorizontalVelocityAfter(duration)
	 * @effect computes the new vertical position after the given duration.
	 * 			| computeNewVerticalPositionAfter(duration)
	 * @effect computes the new vertical velocity after the given duration.
	 * 			| computeNewVerticalVelocityAfter(duration)
	 * @effect The new sprite is calculated with the character's current values
	 * 			| new.getSprite() == this.getCurrentSprite()
	 * @post	if the character isn't moving, the duration is added to the timer counting the time since
	 * 			the character stopped.
	 * 			|if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
	 * 			|	new.getTimeSinceEndMove() == this.getTimeSinceEndMove() + duration
	 * 			otherwise, the duration is added to the timer counting the time since the previous frame
	 * 			of running animation
	 * 			|else
	 * 			|	new.getTimeSinceStep() == this.getTimeSinceStep() + duration
	 * @post	if the character hasn't moved in the last second, this fact is then registered in the new 
	 * 			character's variable.
	 * 			|if (this.getTimeSinceEndMove() > 1.0)
	 * 			|	new.getHasMovedIn() == MovementDirection.NONE
	 * @throws IllegalArgumentException
	 * 			The given duration is an invalid duration of time.
	 * 			| ((duration < 0.0) || (duration >= 0.2))
	 */
	@Override
	public void advanceTime (double duration) throws IllegalArgumentException {
		try{
			if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
				throw new IllegalArgumentException();
			this.computeNewVerticalPositionAfter(duration);
			this.computeNewVerticalVelocityAfter(duration);
			this.determineDoubleDirections();
			this.computeNewHorizontalPositionAfter(duration);
			this.computeNewHorizontalVelocityAfter(duration);
			if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
				setTimeSinceEndMove(getTimeSinceEndMove() + duration);
			else
				setTimeSinceStep(getTimeSinceStep() + duration);
			if (this.getTimeSinceEndMove() > 1.0)
				this.sethasMovedIn(MovementDirection.NONE);
			this.setSprite(this.getCurrentSprite());
		}
		catch (IllegalArgumentException exc){
			throw exc;
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
		if (isMovingLeft() && isMovingRight())
			this.setMovingInTwoDirections(true);
		else {
			this.setMovingInTwoDirections(false);
			if (this.isMovingRight()){
				this.sethasMovedIn(MovementDirection.RIGHT);
				setTimeSinceEndMove(0.0);
			}
			else if (this.isMovingLeft()){
				this.sethasMovedIn(MovementDirection.LEFT);
				setTimeSinceEndMove(0.0);
			}
		}
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
		}
		else if (direction == "right") {
			this.setMovingRight(true);
		}
		this.setAccelerating(true);
		this.setHorizontalVelocity(getInitHorizontalVelocity());
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

	/**
	 * Compute the new vertical position after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new vertical position.
	 * @post    if the character is in the air and and the calculated position is 
	 * 			a possible height then that is the new height. if it's too high the new
	 * 			height is the maximal height. if it's too low the new height is the minimal 
	 * 			height. if the character is not in the air but it is jumping, the new height
	 * 			is calculated.
	 * 			|if isInAir()
	 * 			|	then newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
	 *			|						100*0.5*getVerticalAcceleration()*duration*duration;
	 * 			|	if isValidPositionAt(newYPosition,2)
	 * 			|		then new.getPositionAt(2) = newYPosition
	 * 			|	else if (int)newYPosition < Y_MIN
	 * 			|		then new.getPositionAt(2) = (double)Y_MIN
	 *			|	else
	 *			|		then new.verticalPosition = (double)Y_MAX
	 *			|else
	 *			|	if isJumping()
	 *			|		then new.getPositionAt(2) = this.getPositionAt(2) + 100*duration
	 *			|									*this.getVerticalVelocity()
	 *			|									+ 100*0.5*getVerticalAcceleration()*duration*duration;
	 */
	@Override
	public void computeNewVerticalPositionAfter(double duration){
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
					100*0.5*getVerticalAcceleration()*duration*duration;
			if (isValidPositionAt(newYPosition,2)){
				this.setPositionAt(newYPosition, 2);
			}
			else if ((int)newYPosition < Y_MIN){
				this.setPositionAt((double)Y_MIN, 2);
			}
			else {
				this.setPositionAt((double)Y_MAX, 2);
			}
		}
		else{
			if (isJumping()){
				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()+ 
						100*0.5*getVerticalAcceleration()*duration*duration;
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
	 * A method that returns whether a character is in the air or not
	 * @return	true if the character is in the air (ie above the 0.0 y-coordinate)
	 * 			else false
	 * 			| if (this.getPositionAt(2) > 0):
	 * 			| 	result == true
	 * 			| else
	 * 			|	result == false
	 */
	// TODO aanpassen voor de nieuwe world
	@Override
	public boolean isInAir(){
		if (! Util.fuzzyGreaterThanOrEqualTo(0,this.getPositionAt(2))){
			return true;
		}
		else {
			return false;
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
	
	/**
	 * A getter method for the boolean isDucked.
	 */
	@Basic
	public boolean isDucked() {
		return this.isDucked;
	}

	/**
	 * A method that starts the character's duck
	 * @post	the new character is ducking
	 * 			| new.isDucked() = true
	 * @effect the maximum horizontal velocity is set to 1
	 * 			| setMaxHorizontalVelocity(1.0)
	 */
	public void startDuck() throws IllegalArgumentException{
		try{
			this.setMaxHorizontalVelocity(1.0);
			this.isDucked = true;
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}
	
	/**
	 * A method that ends the character's duck
	 * @post	the new character is not ducking
	 * 			| new.isDucked() = false
	 * @effect the maximum horizontal velocity is set to 3
	 * 			| setMaxHorizontalVelocity(3.0)
	 */
	public void endDuck() throws IllegalArgumentException{
		try{
			this.setMaxHorizontalVelocity(3.0);
			this.isDucked = false;
		}
		catch (IllegalArgumentException exc) {
			throw exc;
		}
	}
	
	/**
	 * a boolean stating whether the character is ducked.
	 */
	private boolean isDucked = false;
	
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
	

}
