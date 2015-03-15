package jumpingalien.model;

import jumpingalien.util.*;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Mazub characters, the player controlled character in Jumping Alien.
 * 
 * @invar	The saved index for the running cycle is valid.
 * 			| canHaveAsIndex(getIndex())
 * @invar	The amount of images for the running cycle must be valid.
 * 			| isValidNbRunningCycle(this.getNbRunningCycle()) 
 * @invar	The amount of images must be valid.
 * 			| isValidNbImages(getNbImages())
 * @invar	each sprite from the images array must be a valid sprite
 * 			| hasProperSprites(getImages())
 * @invar	The current horizontal Velocity must be valid.
 * 			| canHaveAsHorizontalVelocity(getHorizontalVelocity())
 * @invar	The saved horizontal acceleration must be valid.
 * 			| isValidHorizontalAcceleration(getHorizontalAcceleration())
 * @invar	The maximum horizontal velocity must be valid.
 * 			| canHaveAsMaxHorizontalVelocity(getMaxHorizontalVelocity())
 * @invar	The initial horizontal velocity must be valid.
 * 			| canHaveAsInitHorizontalVelocity(getInitHorizontalVelocity())
 * @invar	The current vertical Velocity must be valid.
 * 			| canHaveAsVerticalVelocity(getVerticalVelocity())
 * @invar	The initial vertical velocity must be valid.
 * 			| canHaveAsInitVerticalVelocity(getInitVerticalVelocity())
 * @invar	The saved vertical acceleration must be valid.
 * 			| isValidVerticalAcceleration(getVerticalAcceleration())
 * @invar	The time since a move has ended must be valid.
 * 			| isValidTimeSinceEndMove(getTimeSinceEndMove())
 * @invar	The time since a previous step (or running cycle frame) must be valid.
 * 			| isValidTimeSinceStep(getTimeSinceStep())
 * @invar	The direction the character has moved must be valid.
 * 			| isValidDirection(getHasMovedIn())
 * @invar	the amount of coordinates must be valid.
 * 			| isValidNbPosition(getNbPosition())
 * @invar	each coordinate must be a valid coordinate in its axis.
 * 			| for i in Position:
 * 			|	isValidPositionAt(getPositionAt(i),i)
 * @invar	the given field must be valid.
 * 			| isProperField(FIELD_SIZE);
 * @author Peter Lacko, Sander Switsers
 * @version 1.0
 */
public class Mazub {
	
	// defensive
	/**
	 * Initialize the Mazub character with given x-coordinate, y-coordinate and sprites.
	 * @param x_pos
	 * 			the initial x-coordinate for this character.
	 * @param y_pos
	 * 			the initial y-coordinate for this character.
	 * @param sprites
	 * 			the array of sprites used to display the mazub character.
	 * @post	The character starts at the given position.
	 * 			| new.getPosition().equals({(double) x_pos, (double) y_pos})
	 * @post	The character's image set is equal to the given one.
	 * 			| new.getImages().equals(images)
	 * @post	The amount of running cycle images is equal to the total amount of images divided by two,
	 * 			minus four
	 * 			| new.getnbRunningCycle() = getNbImages()/2-4;
	 * @post	The current sprite is the first sprite in the given array of sprites.
	 * 			| new.getSprite().equals(sprites[0])
	 * @throws IllegalArgumentException
	 * 			The given x coordinate is not valid
	 * 			|(! isValidPositionAt((double) x_pos, 1))
	 * @throws IllegalArgumentException
	 * 			The given y coordinate is not valid
	 * 			|(! isValidPositionAt((double) y_pos, 2))
	 * @throws IllegalArgumentException
	 * 			The given array of sprites has an invalid size.
	 * 			|(! isValidNbImages(sprites.length))
	 * @throws IllegalArgumentException
	 * 			There are one or more invalid sprites in the given array of sprites.
	 * 			|(! hasProperSprites(sprites))
	 */
	@Raw
	public Mazub(int x_pos, int y_pos, Sprite[] sprites) throws IllegalArgumentException{
		if (! isValidPositionAt((double) x_pos, 1))
			throw new IllegalArgumentException("Illegal x-coordinate!");
		if (! isValidPositionAt((double) y_pos, 2))
			throw new IllegalArgumentException("Illegal y-coordinate!");
		if (! isValidNbImages(sprites.length))
			throw new IllegalArgumentException("Illegal sprite array length!");
		if (! hasProperSprites(sprites))
			throw new IllegalArgumentException("Illegal sprite array!");
		this.images = sprites.clone();
		this.nbRunningCycle = getNbImages()/2-4;
		this.setSprite(getImageAt(1));
		this.setPositionAt((double) x_pos, 1);
		this.setPositionAt((double) y_pos, 2);
	}	
	
	//defensive
	/**
	 * A method to return the size of the character's sprite variable.
	 * @return return the width and Height of the current sprite in an array.
	 * 			| return.equals({getSprite().getWidth(),getSprite().getHeight()})
	 */
	public int[] getSize(){
		int[] sizes = {0,0};
		sizes[0] = getSprite().getWidth();
		sizes[1] = getSprite().getHeight();
		return sizes;
	}
	
	//nominal
	/**
	 * A method to determine whether to use the left or right version of a sprite.
	 * @pre number is in the range of images (minus one).
	 * 		| (number >=0) && (number + 1 < getNbImages())
	 * @param number
	 * 			the index in images where the left and right version of the sprite to be returned, is stored.
	 * @return return the correct version of the sprite, depending on the state of the character.
	 * 			if the character isn't moving in both directions, and is moving or has moved left,
	 * 			then return the left sprite, otherwise the right sprite.
	 * 			if the character is moving in both directions, and has moved left, return the left
	 * 			sprite. otherwise return the right sprite.
	 */
	public Sprite leftOrRightSprite(int number){
		assert (number >= 0);
		assert (number + 1 < getNbImages());
		if (! movingInTwoDirections()){
			if ((isMovingLeft()) || (getHasMovedIn() == MovementDirection.LEFT)){
				return getImageAt(number + 2);
			} else{
				return getImageAt(number + 1);
			}
		}
		else{
			if (getHasMovedIn() == MovementDirection.LEFT)
				return getImageAt(number + 2);
			else
				return getImageAt(number + 1);
		}
	}
	
	//nominal
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
	
	//nominal
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

	//nominal
	/**
	 * Return the stored sprite of this character.
	 */
	@Basic
	public Sprite getSprite() {
		return this.sprite;
	}
	
	//nominal
	/**
	 * A method to set the character's sprite to the given sprite.
	 * @pre the sprite must be effective
	 * 		| (sprite != null)
	 * @param sprite
	 * 			The sprite that must be set as the character's sprite.
	 * @post	The character's new sprite is equal to the given sprite.
	 * 			| new.getSprite().equals(sprite)
	 */
	public void setSprite(Sprite sprite) {
		assert (sprite != null);
		this.sprite = sprite;
	}
	
	/**
	 * A variable that stores the current sprite of the character.
	 */
	private Sprite sprite;
	
	/**
	 * A method to return at which sprite in the run cycle the character is.
	 */
	@Basic
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Check whether the character can have the given index for its run cycle.
	 * @param number
	 * 			the number to be checked.
	 * @return True if the index is bigger or equal to 0, and smaller than the amount of images for
	 * 			the character's run cycle.
	 * 			| result == ((number >= 0) && (number < this.getNbRunningCycle()))
	 */
	public boolean canHaveAsIndex(int number) {
		return ((number >= 0) && (number < this.getNbRunningCycle()));
	}
	
	//nominal
	/**
	 * Set the character's running cycle index to the given number
	 * @pre The given number must be a valid index.
	 * 		| canHaveAsIndex(number)
	 * @param number
	 * 			the number to set the index at.
	 * @post	The new index is equal to the given number.
	 * 			new.getIndex() == number
	 */
	public void setIndex(int number) {
		assert canHaveAsIndex(number);
		this.index = number;
	}
	
	/**
	 * A variable to determine at which sprite in the run cycle the character is.
	 */
	private int index = 0;
	
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
	 * Return the amount of sprites stored for this character.
	 */
	@Basic @Immutable
	public int getNbImages() {
		return getImages().length;
	}
	
	/**
	 * Check whether the given amount of images is valid.
	 * @param nbImages
	 * 			the amount to check.
	 * @return true if the amount is bigger or equal to 10 and divisible by 2. (there are different states
	 * 			of the character summing up to at least 10 different sprites total, and it must be divisible
	 * 			by two due to the fact that there must be left and right sided sprites.)
	 * 			| (nbImages >= 10) && (nbImages % 2 == 0)
	 */
	public static boolean isValidNbImages(int nbImages){
		return ((nbImages >= 10) && (nbImages % 2 == 0));
	}
	
	//nominal
	/**
	 * Return the sprite from the character's array of sprites at the given index.
	 * @param index
	 * 			the index of the sprite to return.
	 * @pre	The given index must be positive and may not exceed the total number of sprites that the
	 * 		character has.
	 * 		| (index > 0) && (index <= getNbImages())
	 */
	@Basic
	public Sprite getImageAt(int index) {
		return images[index-1];
	}
	
	/**
	 * Return the character's array of sprites
	 * @return the length of the returned array is equal to the number of sprites this character has.
	 * 			| result.length == getNbImages()
	 * @return Each sprite in the array is equal to the sprite ascribed to this character at the
	 * 			corresponding index.
	 * 			| for i in 0..result.length-1:
	 * 			|	result[i].equals(getImageAt(i+1))
	 */
	@Immutable
	public Sprite[] getImages(){
		return this.images.clone();
	}
	
	/**
	 * Check whether the given array of sprites is valid.
	 * @param images
	 * 			the array of sprites to be checked.
	 * @return true if and only if there are no null entities in the array
	 * 			| for sprite in images:
	 * 			|	if (sprite == null)
	 * 			|		return false
	 * 			| return true
	 */
	public boolean hasProperSprites(Sprite[] images){
		for (Sprite sprite: images){
			if (sprite == null)
				return false;
		}
		return true;
	}
	
	/**
	 * An array of Sprites to display the character.
	 */
	private final Sprite[] images;
	
	//defensive
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
	
	//defensive
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
	 * Compute the new horizontal position after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new horizontal position.
	 * @post 	If the character is accelerating and if the newly calculated position is valid, the new 
	 * 			character is at that position.
	 * 			|newPositionAccelerating = this.getPositionAt(1) +100*duration*this.getHorizontalVelocity() 
	 * 			|				+ 100*0.5*getHorizontalAcceleration()*duration�
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

	//total
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
	
	//total
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
	@Model
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
	 * Return the current horizontal velocity.
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.currHorizontalVelocity;
	}
	
	/**
	 * Check whether the character can have the given horizontal velocity
	 * @param velocity
	 * 			the horizontal velocity to check
	 * @return true if it is smaller in absolute value than the maximum velocity and if it is a number.
	 * 			| return ((Math.abs(velocity) <= getMaxHorizontalVelocity()) && (! Double.isNaN(velocity)))
	 */
	public boolean canHaveAsHorizontalVelocity(double velocity) {
		return (Util.fuzzyLessThanOrEqualTo(Math.abs(velocity),getMaxHorizontalVelocity()) &&
				(!Double.isNaN(velocity)));
	}
	
	//nominal
	/**
	 * Set the horizontal velocity to the given velocity.
	 * @param velocity
	 * 			the velocity to be assigned to the character.
	 * @pre The given velocity must be a valid horizontal velocity.
	 * 		| canHaveAsHorizontalVelocity(velocity)
	 * @post The new horizontal velocity is the given velocity.
	 * 			| new.getHorizontalVelocity == velocity
	 */
	private void setHorizontalVelocity(double velocity) {
		assert this.canHaveAsHorizontalVelocity(velocity);
		this.currHorizontalVelocity = velocity;
	}
	
	/**
	 * A variable reflecting the current horizontal velocity.
	 */
	private double currHorizontalVelocity = 0.0;
	
	/**
	 * A method to return the current horizontal acceleration. The acceleration is negative if the 
	 * character is moving left, positive if the character is moving right, and 0.0 if the character is
	 * not moving horizontally.
	 */
	@Basic
	public double getHorizontalAcceleration() {
		if (this.isAccelerating() && this.isMovingLeft() && (! this.movingInTwoDirections()))
			return -Mazub.HORIZONTAL_ACCELERATION;
		else if (this.isAccelerating() && this.isMovingRight() && (! this.movingInTwoDirections()))
			return Mazub.HORIZONTAL_ACCELERATION;
		else
			return 0.0;
	}

	/**
	 * Check whether the given horizontal acceleration is valid.
	 * @param acceleration
	 * 			the acceleration to check.
	 * @return True if and only if the acceleration is a number.
	 * 			| result == (! Double.isNaN(acceleration))
	 */
	public boolean isValidHorizontalAcceleration(double acceleration) {
		return (! Double.isNaN(acceleration));
	}
	
	/**
	 * Constant reflecting the horizontal acceleration of a character.
	 * @return the Horizontal acceleration for all characters is 0.9 m/s�
	 * 			| result == 0.9
	 */
	private static final double HORIZONTAL_ACCELERATION = 0.9;
	
	/**
	 * Return the maximum horizontal velocity of the character.
	 */
	@Basic
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	/**
	 * Check whether the given maximum velocity is valid.
	 * @param velocity
	 * 			the maximum velocity to check.
	 * @return true if and only if the velocity is greater than or equal to the absolute value of the 
	 * 			initial velocity and is a number.
	 * 			| result == (getMaxHorizontalVelocity() >= Math.abs(getInitHorizontalVelocity()) &&
	 * 			|			(! Double.isNaN(velocity)))
	 */
	public boolean canHaveAsMaxHorizontalVelocity(double velocity) {
		return (Util.fuzzyGreaterThanOrEqualTo(this.getMaxHorizontalVelocity(), 
				Math.abs(this.getInitHorizontalVelocity())) && (! Double.isNaN(velocity)));
	}
	
	//defensive
	/**
	 * A method to set the maximum horizontal velocity.
	 * @param velocity
	 * 			the new maximum velocity.
	 * @post The new maximum horizontal velocity is equal to the given velocity.
	 * 		| new.getMaxHorizontalVelocity() == velocity
	 * @throws	the new maximum velocity is invalid for this character.
	 * 		| (! canHaveAsMaxHorizontalVelocity(velocity))
	 */
	public void setMaxHorizontalVelocity(double velocity) throws IllegalArgumentException{
		if (! canHaveAsMaxHorizontalVelocity(velocity))
			throw new IllegalArgumentException();
		this.maxHorizontalVelocity = velocity;
	}

	/**
	 * Variable reflecting the maximal horizontal velocity of a character, in absolute value.
	 */
	private double maxHorizontalVelocity = 3.0;
	
	/**
	 * A method to return the current horizontal initial velocity. The velocity is negative if the 
	 * character is moving left, otherwise it is positive.
	 */
	@Basic
	public double getInitHorizontalVelocity() {
		if (this.isMovingLeft())
			return -this.INIT_HORIZONTAL_VELOCITY;
		else
			return this.INIT_HORIZONTAL_VELOCITY;
	}
	
	/**
	 * Check whether the given velocity is valid for this character.
	 * @param velocity
	 * 			the velocity to check
	 * @return	true if the velocity is a number.
	 * 			| result == (! Double.isNaN(velocity))
	 */
	public boolean canHaveAsInitHorizontalVelocity(double velocity) {
		return (! Double.isNaN(velocity));
	}
	
	/**
	 * Constant reflecting the initial velocity of a character.
	 */
	private final double INIT_HORIZONTAL_VELOCITY = 1.0;
	
	/**
	 * Method that returns the value of the variable isAccelerating
	 */
	@Basic
	public boolean isAccelerating(){
		return this.isAccelerating;
	}
	
	/**
	 * Set the variable determining whether the character is accelerating to the given flag.
	 * @param flag
	 * 			the flag to set the variable isAccelerating to.
	 * @post the new variable isAccelerating is equal to the given flag.
	 * 			| new.isAccelerating() == flag
	 */
	public void setAccelerating(boolean flag) {
		this.isAccelerating = flag;
	}
	
	/**
	 * value stating whether the character is accelerating
	 */
	private boolean isAccelerating = false;
	
	//nominal
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
//		this.setTimeSinceEndMove(0.0);
		this.setAccelerating(true);
		this.setHorizontalVelocity(getInitHorizontalVelocity());
	}
	
	//nominal
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
	 * A getter method for the variable isMovingLeft
	 */
	@Basic
	public boolean isMovingLeft() {
		return this.isMovingLeft;
	}
	
	/**
	 * A setter method for the variable isMovingLeft
	 * @param flag
	 * 			the boolean to which isMovingLeft has to be set
	 * @post	isMovingLeft is set to flag
	 * 			| new.isMovingLeft == flag
	 */
	public void setMovingLeft(boolean flag) {
		this.isMovingLeft = flag;
	}
	
	/**
	 * A value stating if the character is moving left.
	 */
	private boolean isMovingLeft = false;
	
	/**
	 * A getter method for the variable isMovingRight
	 */
	@Basic
	public boolean isMovingRight() {
		return this.isMovingRight;
	}
	
	/**
	 * A setter method for the variable isMovingRight
	 * @param flag
	 * 			the boolean to which isMovingRight has to be set
	 * @post	isMovingRight is set to flag
	 * 			| new.isMovingRight == flag
	 */
	public void setMovingRight(boolean flag) {
		this.isMovingRight = flag;
	}
	
	/**
	 * A value stating if the character is moving right.
	 */
	private boolean isMovingRight = false;
	
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
	public boolean isInAir(){
		if (! Util.fuzzyGreaterThanOrEqualTo(0,this.getPositionAt(2))){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Return the current vertical velocity.
	 */
	@Basic
	public Double getVerticalVelocity() {
		return this.currVerticalVelocity;
	}
	
	/**
     * a checker method for whether the double velocity is valid as verticalVelocity
	 * @param velocity
	 * 			the double which has to be checked whether its valid
	 * @return	true if velocity is greater than zero and a number
	 * 			| result == (! Double.isNaN(velocity))
	 */
	public boolean canHaveAsVerticalVelocity(double velocity) {
		return (! Double.isNaN(velocity));
	}
	
	/**
	 * Set the vertical velocity to the given velocity.
	 * @post The new vertical velocity is the given velocity.
	 * 			| new.getVerticalVelocity() == velocity
	 * @throws IllegalArgumentException
	 * 			the given velocity is an invalid vertical velocity
	 */
	private void setVerticalVelocity(double velocity) throws IllegalArgumentException {
		if (! this.canHaveAsVerticalVelocity(velocity))
			throw new IllegalArgumentException();
		this.currVerticalVelocity = velocity;
	}
	
	/**
	 * A variable reflecting the current vertical velocity.
	 */
	private Double currVerticalVelocity = 0.0;
	
	/**
	 * Return the speed with which the character starts jumping
	 */
	@Basic @Immutable
	public Double getInitVerticalVelocity() {
		return this.INIT_VERTICAL_VELOCITY;
	}
	
	/**
     * a checker method for whether the double acceleration is valid as verticalVelocity
	 * @param velocity
	 * 			the double which has to be checked whether its valid
	 * @return	true if velocity is greater than or equal to zero and is a number
	 * 			| result == ((velocity >= 0) && (! Double.isNaN(velocity)))
	 */
	public boolean canHaveAsInitVerticalVelocity(double velocity) {
		return (Util.fuzzyGreaterThanOrEqualTo(velocity, 0.0) && (! Double.isNaN(velocity)));
	}
	
	/**
	 * Constant reflecting the initial jumping velocity of a character.
	 * @return The initial jumping velocity for a character is 8 m/s.
	 * 			| result == 8.0
	 */
	private final Double INIT_VERTICAL_VELOCITY = 8.0;
	
	/**
	 * Return the current vertical acceleration.
	 */
	@Basic
	public Double getVerticalAcceleration() {
		if (this.isInAir() || this.isJumping())
			return Mazub.VERTICAL_ACCELERATION;
		else
			return 0.0;
	}
	
	/**
     * a checker method for whether the double acceleration is valid as verticalAcceleration
	 * @param acceleration
	 * 			the double which has to be checked whether it is valid
	 * @return	true if acceleration is greater than or equal to zero and a number
	 * 			| result == ((acceleration >= 0) && (! Double.isNaN(acceleration)))
	 */
	public boolean isValidVerticalAcceleration(double acceleration) {
		return ((Util.fuzzyGreaterThanOrEqualTo(0.0, acceleration)) && (! Double.isNaN(acceleration)));
	}
	
	/**
	 * Constant reflecting the vertical acceleration of a character.
	 * @return The vertical acceleration for all characters is 0.9m/s�
	 * 			| result == -10
	 */
	private static final Double VERTICAL_ACCELERATION = -10.0;

	/**
	 * A boolean stating whether the character is jumping
	 */
	@Basic
	public boolean isJumping(){
		return this.isJumping;
	}
	
	/**
	 * A method that stops the character's jump
	 * @post the character is no longer jumping
	 * 			| new.isJumping() == false
	 */
	public void endJump(){
		this.isJumping = false;
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
	 * value stating whether the character is jumping.
	 */
	private boolean isJumping = false;

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
	 * Return the orientation of the Mazub character as an (enumerated) MovementDirection.
	 * 	The orientation is the direction Mazub is facing or moving.
	 * @return if the character is only moving left, return left
	 * 			| if (isMovingLeft() && (! isMovingRight())
	 * 			| result == MovementDirection.LEFT
	 * 			otherwise, if the character is only moving right, return right
	 * 			| if (isMovingRight() && (! isMovingLeft())
	 * 			| result == MovementDirection.RIGHT
	 * 			otherwise, return the direction the character has moved in within the last second.
	 * 			| else
	 * 			| result == getHasMovedIn()
	 */
	public MovementDirection getEnumedOrientation() {
		if (this.isMovingLeft() ^ this.isMovingRight()){
			if (this.isMovingLeft())
				return MovementDirection.LEFT;
			else
				return MovementDirection.RIGHT;
		}
		else
			return getHasMovedIn();
	}
	
	/**
	 * Return the orientation of the Mazub character as a String.
	 * 	The orientation is the direction Mazub is facing or moving.
	 * @effect return the orientation as a string
	 *			|if (this.getEnumedOrientation() == MovementDirection.LEFT)
	 *			|	then result == "left"
	 *			|else if (this.getEnumedOrientation() == MovementDirection.RIGHT)
	 *			|	then result == "right"
	 *			|else
	 *			|	result == "none"
	 */
	public String getOrientation() {
		if (this.getEnumedOrientation() == MovementDirection.LEFT)
			return "left";
		else if (this.getEnumedOrientation() == MovementDirection.RIGHT)
			return "right";
		else
			return "none";
	}
	
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
	 * returns the movementDirection that the character has moved in
	 */
	@Basic
	public MovementDirection getHasMovedIn() {
		return this.hasMovedIn;
	}
	
	/**
	 * a method for setting the direction that the character has moved in.
	 * @param direction
	 * 			a movement direction for the character
	 * @post	hasMovedIn is set to direction
	 * 			| new.gethasMovedIn() == direction
	 */
	public void sethasMovedIn(MovementDirection direction) {
		this.hasMovedIn = direction;
	}
	
	/**
	 * A variable that states in which direction the character has recently moved.
	 */
	private MovementDirection hasMovedIn = MovementDirection.NONE;
	
	/**
	 * return the boolean movingInTwoDirections
	 */
	@Basic
	public boolean movingInTwoDirections() {
		return this.movingInTwoDirections;
	}
	
	/**
	 * a method that sets the boolean movingInTwoDirections
	 * @param flag
	 * 			a boolean to which moving in two directions has to be changed
	 * @post	moving in two directions is set to the given flag
	 * 			| new.movingInTwoDirections == flag
	 */
	public void setMovingInTwoDirections(boolean flag) {
		this.movingInTwoDirections = flag;
	}
	
	/**
	 * A boolean that states if the character gets the command to move in both directions.
	 */
	private boolean movingInTwoDirections = false;
	
	/**
	 * Check whether the given MovementDirection is a valid one.
	 * @param direction
	 * 			the direction to check.
	 * @return True if and only if the direction is left, right or none.
	 * 			| result == (direction == MovementDirection.NONE) || (direction == MovementDirection.LEFT) ||
	 *			|	(direction == MovementDirection.RIGHT)
	 * 
	 */
	public static boolean isValidDirection(MovementDirection direction) {
		return (direction == MovementDirection.NONE) || (direction == MovementDirection.LEFT) ||
				(direction == MovementDirection.RIGHT);
	}

	/**
	 * Return the number of coordinates ascribed to this character.
	 * @throws NullPointerException
	 * 			Null is not a valid array for the Position
	 * 			| this.getPosition() == null
	 */
	@Basic @Immutable
	public int getNbPosition() throws NullPointerException {
		return getPosition().length;
	}
	
	/**
	 * Check whether the number of coordinates is valid for any character.
	 * 
	 * @param nbPosition
	 * 			The number of coordinates to check.
	 * @return True if and only if the given number of coordinates is 2 (the game takes place in a 2D plane)
	 * 			| result == (nbPosition == 2)
	 */
	public static boolean isValidNbPosition(int nbPosition){
		return (nbPosition == 2);
	}
	
	/**
	 * Return the actual coordinate (in the form of a double) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @throws ArrayIndexOutOfBoundsException
	 * 			The given index must be positive and may not be bigger than 2.
	 * 			| (index <= 0) || (index > 2)
	 */
	@Basic
	public double getPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return Position[index-1];
	}
	
	/**
	 * Return the coordinates of the character in the playing field
	 * @return	the rounded down coordinates in the form of an int[]
	 * 			| return [getIntPositionAt(1),getIntPositionAt(2)]
	 */
	public int[] getIntPosition(){
		try{
			int[] position = {getIntPositionAt(1),getIntPositionAt(2)};
			return position;
		}
		catch (ArrayIndexOutOfBoundsException exc){
			throw exc;
		}
	}
	
	/**
	 * Return the rounded down coordinate (in the form of an int) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @effect	Rounds down the position at the requested index and converts to an int type.
	 * 			| (int)getPositionAt(index)
	 */
	public int getIntPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return (int)getPositionAt(index);
	}
	
	/**
	 * Check whether the character can have the given coordinate at the given index.
	 * @param coordinate
	 * 			The coordinate to check
	 * @param index
	 * 			The index for the coordinate to check
	 * @return True if it is an X-coordinate in the correct range or a Y-coordinate in the correct range
	 * 			(the rounded down value must be between X_MIN, X_MAX or Y_MIN, Y_MAX respectively)
	 * 			| if ((index == 1) && ((int)coordinate >= X_MIN) && ((int)coordinate <= X_MAX))
	 * 			|	then result == true
	 * 			| else if ((index ==2) && ((int)coordinate >= Y_MIN) && ((int)coordinate <= Y_MAX))
	 * 			|	then result == true
	 * 			| else result == false
	 */
	public static boolean isValidPositionAt (double coordinate, int index){
		if ((index == 1) && ((int)coordinate >= X_MIN) && ((int)coordinate <= X_MAX))
			return true;
		if ((index == 2) && ((int)coordinate >= Y_MIN) && ((int)coordinate <= Y_MAX))
			return true;
		return false;
	}
	
	/**
	 * Return the position for this Mazub character.
	 * 	The position gives a combination of the X and Y position of the bottom left corner of the character.
	 * @return The length of the resulting array is equal to the number of coordinates ascribed to this 
	 * 			character (2).
	 * 			| result.length == getNbPosition()
	 * @return Each element in the resulting array is equal to the coordinate ascribed to this character
	 * 			at the corresponding index.
	 * 			| for each I in 0..result.length-1:
	 * 			|	result[I].equals(getPositionAt(I+1))
	 */
	public double[] getPosition() {
		return this.Position.clone();
	}
	
	/**
	 * Set the coordinate ascribed to this character at the given index to the given coordinate
	 * 
	 * @param coordinate
	 * 			The coordinate to register
	 * @param index
	 * 			The index for the coordinate to register
	 * @throws ArrayIndexOutOfBoundsException
	 * 			The index must be positive and may not be greater than 2
	 * 			| (index <= 0) || (index > 2)
	 * @throws IllegalArgumentException
	 * 			The given coordinate must be a valid coordinate at the given index.
	 * 			| (! isValidPositionAt(coordinate, index))
	 * @post This character has the given coordinate as its coordinate at the given index.
	 * 			| new.getPositionAt(index) == coordinate
	 */
	private void setPositionAt(double coordinate, int index) throws ArrayIndexOutOfBoundsException, 
	IllegalArgumentException{
		if (! isValidPositionAt(coordinate, index))
			throw new IllegalArgumentException();
		this.Position[index-1] = coordinate;
	}
		
	/**
	 * Variable registering the position of the bottom left corner of the Mazub Sprite.
	 */
	private double[] Position = {0.0, 0.0};
	
	/**
	 * a boolean that says whether the given field is permitted as a playing field
	 * @param field
	 * 			the dimension of the playing field
	 * @return true if the playing field is allowed, false if it isn't
	 * 			|if field.length != 2
	 * 			| then return false
	 * 			|else if (field[0]<=1) || (field[1]<=1)
	 * 			| then return false
	 * 			|else
	 * 			| return true
	 */
	public boolean isProperField(int[] field) {
		if (field.length != 2)
			return false;
		else{
			for (int size: field){
				if (size <= 1)
					return false;
			}
			return true;
		}
	}
	
	/**
	 * Constant reflecting the size of the playing field.
	 * @return	The size of the field is 1024 pixels long and 768 pixels high.
	 * 			| result == {1024, 768}
	 */
	private static final int[] FIELD_SIZE = {1024, 768};
	
	/**
	 * Constant reflecting the maximal X coordinate.
	 * @return The highest X coordinate is the field length minus one.
	 * 			| result == FIELD_SIZE[0]-1
	 */
	private static final int X_MAX = FIELD_SIZE[0]-1;
	
	/**
	 * Constant reflecting the maximal Y coordinate.
	 * @return The highest Y coordinate is the field height minus one.
	 * 			| result == FIELD_SIZE[0]-1
	 */
	private static final int Y_MAX = FIELD_SIZE[1]-1;
	
	/**
	 * Constant reflecting the minimal X coordinate.
	 * @return The lowest X coordinate is zero.
	 * 			| result == 0
	 */
	private static final int X_MIN = 0;
	
	/**
	 * Constant reflecting the minimal Y coordinate.
	 * @return The lowest Y coordinate is zero.
	 * 			| result == 0
	 */
	private static final int Y_MIN = 0;

}
