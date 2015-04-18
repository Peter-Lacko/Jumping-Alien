package jumpingalien.model;

import jumpingalien.util.*;
import be.kuleuven.cs.som.annotate.*;

//TODO documentatie aanpassen
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
 * 			|	canHaveAsNewPosition(getPositionAt(i),i)
 * @invar	the given field must be valid.
 * 			| isProperField(FIELD_SIZE);
 * @author Peter Lacko (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor)),
 * 			Sander Switsers (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor))
 * @version 1.0
 * Code repository: https://github.com/Peter-Lacko/Jumping-Alien
 */
public abstract class Characters {
	
	// TODO documentatie aanpassen
	/**
	 * initialize a character with a given x_pos, y_pos, sprites, horizontal acceleration,
	 * 	max horizontal velocity, initial horizontal velocity and initial vertical velocity.
	 * @param x_pos
	 * 			the initial x-coordinate for this character.
	 * @param y_pos
	 * 			the initial y-coordinate for this character.
	 * @param sprites
	 * 			the array of sprites used to display the character.
	 * @param hor_acc
	 * 			the horizontal acceleration of the character
	 * @param max_hor_vel
	 * 			the maximum horizontal velocity of the character
	 * @param init_hor_vel
	 * 			the initial horizontal velocity of the character
	 * @param init_ver_vel
	 * 			the initial vertical velocity of the character
	 * @post	The character starts at the given position.
	 * 			| new.getPosition().equals({(double) x_pos, (double) y_pos})
	 * @post	The character's image set is equal to the given one.
	 * 			| new.getImages().equals(images)
	 * @post	the character horizontal acceleration is set to the given horizontal acceleration
	 * 			| new.horizontalAcceleration.equals(hor_acc)
	 * @post	the characters max horizontal velocity is set to the given max horizontal velocity
	 * 			| new.maxHorizontalVelocity.equals(max_hor_acc)
	 * @post	the characters initial horizontal velocity is set to the given initial horizontal velocity
	 * 			| new.initHorizontalVelocity.equals(init_hor_vel)
	 * @post	the characters initial vertical velocity is set to the given initial vertical velocity
	 *			| new.initVerticalVelocity.equals(init_ver_vel)
	 * @throws IllegalArgumentException
	 */
	@Raw
	public Characters(int x_pos, int y_pos, Sprite[] sprites, double hor_acc, double max_hor_vel, double init_hor_vel, double init_ver_vel, int hitpoints)
			throws IllegalArgumentException{
//		if (! canHaveAsNewPosition((double) x_pos, 1))
//			throw new IllegalArgumentException("Illegal x-coordinate!");
//		if (! canHaveAsNewPosition((double) y_pos, 2))
//			throw new IllegalArgumentException("Illegal y-coordinate!");
		if (! isValidNbImages(sprites.length))
			throw new IllegalArgumentException("Illegal sprite array length!");
		if (! hasProperSprites(sprites))
			throw new IllegalArgumentException("Illegal sprite array!");
		if ((! isValidHorizontalAcceleration(hor_acc)) || (! canHaveAsMaxHorizontalVelocity(max_hor_vel)) || (! canHaveAsInitHorizontalVelocity(init_hor_vel)))
			throw new IllegalArgumentException("illegal horizontal details");
		if (! canHaveAsInitVerticalVelocity(init_ver_vel))
			throw new IllegalArgumentException("illegal vertical details");
		if (! canHaveAsHitpoints(hitpoints))
			throw new IllegalArgumentException();
		this.images = sprites.clone();
		this.setSprite(getImageAt(1));
//		this.Position[0] = ((double) x_pos);
//		this.Position[1] = ((double) y_pos);
		this.Position = new Position (x_pos, y_pos);
		this.setHorizontalAcceleration(hor_acc);
		this.setMaxHorizontalVelocity(max_hor_vel);
		this.setInitHorizontalVelocity(init_hor_vel);
		this.setinitVerticalVelocity(init_ver_vel);
		this.setHitPoints(hitpoints);
	}
	
    @Basic @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }
	
    private boolean isTerminated = false;
	
	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

//	public abstract boolean canHaveAsWorld(World world);
	
	/**
	 * returns the world of the character
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}

	/**
	 * a setter method for the variable world
	 * @param world
	 * 			the world the which world has to be set
	 * @post	...
	 * 			| new.world == world
	 * @throws IllegalArgumentException
	 * 			| (! canHaveAsWorld(world))
	 * @throws IllegalArgumentException
	 * 			| (! world.hasAsObject(this))
	 */
	@Raw
	public void setWorld(@Raw World world) throws IllegalArgumentException {
		if (world == null)
			this.world = world;
		else{
			if (! canHaveAsWorld(world))
				throw new IllegalArgumentException("Wrong Arguments");
			if (! world.hasAsObject(this))
				throw new IllegalArgumentException();
			this.world = world;
		}
	}
	
	/**
	 * a variable that stores the world of the character
	 */
	private World world = null;
	
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
	

	public abstract Sprite getCurrentSprite();
	

	/**
	 * Return the stored sprite of this character.
	 */
	@Basic
	public Sprite getSprite() {
		return this.sprite;
	}
	
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
	protected Sprite sprite;
	
	/**
	 * A method to return at which sprite in the run cycle the character is.
	 */
	@Basic
	public int getIndex() {
		return this.index;
	}
	

	public abstract boolean canHaveAsIndex(int number);
	
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
	protected int index = 0;
	
	/**
	 * Return the amount of sprites stored for this character.
	 */
	@Basic @Immutable
	public int getNbImages() {
		return getImages().length;
	}
	
	
	public abstract boolean isValidNbImages(int nbImages);
	
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
	protected Sprite[] images;
	
	protected abstract void computeNewHorizontalPositionAfter(double duration);
	
	protected abstract void computeNewHorizontalVelocityAfter(double duration);
	
	public void advanceTime (double duration){
		double shortduration = shortDuration(duration);
		int iteraties = ((int)(duration/shortduration+0.5));
		for (int i=0 ;i<iteraties ;i++){
			this.advanceTimeLong(shortduration);
			if(! this.isTerminated()){
				if (getWorld().hasAsObject(this)){
					if (this.getIntPositionAt(1) <= getWorld().getWorldSize()[0]/2)
						getWorld().addAsLeftObject(this);
					else
						getWorld().removeAsLeftObject(this);
					if ((this.getIntPositionAt(1) + this.getSize()[0] - 1) >= getWorld().getWorldSize()[0]/2)
						getWorld().addAsRightObject(this);
					else
						getWorld().removeAsRightObject(this);
				}
		}
		}
	}
	
	public abstract void advanceTimeLong (double duration);
	
	public double shortDuration(double duration){
		double speed = Math.sqrt((getVerticalVelocity()*getVerticalVelocity()+
				getHorizontalVelocity()*getHorizontalVelocity()));
		double acceleration = Math.sqrt((getVerticalAcceleration()*getVerticalAcceleration()+
				getHorizontalAcceleration()*getHorizontalAcceleration()));
		return Math.min((0.01 / (speed + acceleration * duration)),duration);
		
	}
	
	/**
	 * Check whether the given horizontal acceleration is valid.
	 * @param acceleration
	 * 			the acceleration to check.
	 * @return True if and only if the acceleration is a number and greater than or equal to zero.
	 * 			| result == (! Double.isNaN(acceleration)) && Util.fuzzyGreaterThanOrEqualTo(acceleration, 0.0)
	 */
	public boolean isValidHorizontalAcceleration(double acceleration) {
		return ((! Double.isNaN(acceleration)) && (Util.fuzzyGreaterThanOrEqualTo(acceleration, 0.0)));
	}
	
	/**
	 * return the absolute value of the horizontal acceleration of the character
	 */
	@Basic
	public double getAbsHorizontalAcceleration(){
		return this.horizontalAcceleration;
	}
	
	/**
	 * set the characters horizontal acceleration to the given double
	 * @pre		the given acceleration is a valid number for horizontal acceleration
	 * 			| isValidHorizontalAcceleration(a)
	 * @param 	a
	 * 			the value to which horizontal acceleration has to be set
	 * @post	horizontal acceleration is set to the given value
	 * 			new.getAbsHorizontalAcceleration().equals(a)
	 */
	protected void setHorizontalAcceleration(double a){
		assert isValidHorizontalAcceleration(a);
		this.horizontalAcceleration = a;
	}
	
	/**
	 * a variable containing the horizontal acceleration of the character
	 */
	protected double horizontalAcceleration;
	
	/**
	 * A method to return the current horizontal acceleration. The acceleration is negative if the 
	 * character is moving left, positive if the character is moving right, and 0.0 if the character is
	 * not moving horizontally.
	 */
	// moving in two directions aanpassen naar nieuwe richtlijnen aanpassen
	@Basic
	public double getHorizontalAcceleration() {
		if (this.isAccelerating() && this.isMovingLeft() && (! this.movingInTwoDirections()))
			return -getAbsHorizontalAcceleration();
		else if (this.isAccelerating() && this.isMovingRight() && (! this.movingInTwoDirections()))
			return getAbsHorizontalAcceleration();
		else
			return 0.0;
	}
	
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
	protected double maxHorizontalVelocity;
	
	/**
	 * A method to return the current horizontal initial velocity. The velocity is negative if the 
	 * character is moving left, otherwise it is positive.
	 */
	@Basic
	public double getInitHorizontalVelocity() {
		if (this.isMovingLeft())
			return -this.initHorizontalVelocity;
		else
			return this.initHorizontalVelocity;
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
	 * A method to set the initial horizontal velocity.
	 * @param velocity
	 * 			the new initial horizontal velocity.
	 * @post The new initial horizontal velocity is equal to the given velocity.
	 * 		| new.getInitHorizontalVelocity() == velocity
	 */
	public void setInitHorizontalVelocity(double velocity){
		this.initHorizontalVelocity = velocity;
	}
	
	/**
	 * Constant reflecting the initial velocity of a character.
	 */
	protected double initHorizontalVelocity;
	
	/**
	 * Return the current horizontal velocity.
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.currHorizontalVelocity;
	}
	
	/**
	 * Set the horizontal velocity to the given velocity.
	 * @param velocity
	 * 			the velocity to be assigned to the character.
	 * @pre The given velocity must be a valid horizontal velocity.
	 * 		| canHaveAsHorizontalVelocity(velocity)
	 * @post The new horizontal velocity is the given velocity.
	 * 			| new.getHorizontalVelocity == velocity
	 */
	protected void setHorizontalVelocity(double velocity) {
		assert this.canHaveAsHorizontalVelocity(velocity);
		this.currHorizontalVelocity = velocity;
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
	
	/**
	 * A variable reflecting the current horizontal velocity.
	 */
	protected double currHorizontalVelocity = 0.0;
	
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
	protected boolean isAccelerating = false;
	
	
	

	

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
	protected boolean isMovingLeft = false;
	
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
	protected boolean isMovingRight = false;
	
	protected abstract void computeNewVerticalPositionAfter(double duration);
	
	protected abstract void computeNewVerticalVelocityAfter(double duration);
	
	public abstract boolean isInAir();

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
	protected void setVerticalVelocity(double velocity) throws IllegalArgumentException {
		if (! this.canHaveAsVerticalVelocity(velocity))
			throw new IllegalArgumentException();
		this.currVerticalVelocity = velocity;
	}
	
	/**
	 * A variable reflecting the current vertical velocity.
	 */
	protected Double currVerticalVelocity = 0.0;
	
	/**
	 * Return the speed with which the character starts jumping
	 */
	@Basic
	public Double getInitVerticalVelocity() {
		return this.initVerticalVelocity;
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
	 * A method to set the initial vertical velocity.
	 * @pre	velocity is a valid value for the initial vertical velocity
	 * 		| canHaveAsInitVerticalVelocity(velocity)
	 * @param velocity
	 * 			the new initial vertical velocity.
	 * @post The new initial vertical velocity is equal to the given velocity.
	 * 		| new.getInitverticalVelocity() == velocity
	 */
	public void setinitVerticalVelocity(double velocity){
		assert canHaveAsInitVerticalVelocity(velocity);
		this.initVerticalVelocity = velocity;
	}
	
	/**
	 * Constant reflecting the initial jumping velocity of a character.
	 */
	protected Double initVerticalVelocity;
	
	/**
	 * Return the current vertical acceleration.
	 */
	@Basic
	public Double getVerticalAcceleration() {
		if (this.isInAir() || this.isJumping())
			return VERTICAL_ACCELERATION;
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
	protected static final Double VERTICAL_ACCELERATION = -10.0;

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
	
	public abstract void startJump();
	
	/**
	 * value stating whether the character is jumping.
	 */
	protected boolean isJumping = false;

	
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
	protected MovementDirection hasMovedIn = MovementDirection.NONE;
	
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
	protected boolean movingInTwoDirections = false;
	
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
	 * Returns the saved Position Value.
	 */
	@Basic
	public Position getPositionValue(){
		return Position;
	}
	
	/**
	 * Return the actual coordinate (in the form of a double) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @effect	returns the saved X coordinate (index 1) or Y coordinate (index 2) for this character
	 * 			|result == getPositionValue().getPosition()[index-1]
	 * @throws ArrayIndexOutOfBoundsException
	 * 			The given index must be positive and may not be bigger than 2.
	 * 			| (index <= 0) || (index > 2)
	 */
	public double getPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return getPositionValue().getPosition()[index-1];
	}
	
	/**
	 * Return the coordinates of the character in the playing field
	 * @effect	return the position array in int form
	 * 			|result == getPositionValue().getIntPosition()
	 */
	public int[] getIntPosition(){
		return getPositionValue().getIntPosition();
	}
	
	/**
	 * Return the rounded down coordinate (in the form of an int) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @effect	Rounds down the position at the requested index and converts to an int type.
	 * 			| result == getIntPosition()[index-1]
	 */
	@Raw
	public int getIntPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return getIntPosition()[index-1];
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
//	public static boolean canHaveAsNewPosition (double coordinate, int index){
//		if ((index == 1) && ((int)coordinate >= X_MIN) && ((int)coordinate <= X_MAX))
//			return true;
//		if ((index == 2) && ((int)coordinate >= Y_MIN) && ((int)coordinate <= Y_MAX))
//			return true;
//		return false;
//	}
	
//	public boolean testHor(double coordinate){
//		for (int i=getIntPositionAt(2)+1; i <= getIntPositionAt(2)+getSprite().getHeight() -1; i++){
//			int[] newPos1 = new int[] {(int)coordinate, i};
//			int[] newPos2 = new int[] {(int)(coordinate + getSprite().getWidth() -1), i};
//			int[] tileCheck1 =getWorld().getPixelOfTileContaining(newPos1[0], newPos1[1]);
//			int[] tileCheck2 =getWorld().getPixelOfTileContaining(newPos2[0], newPos2[1]);
//			if ((getWorld().getGeoFeatureAt(tileCheck1[0], tileCheck1[1]) == GeoFeature.GROUND)
//					|| (getWorld().getGeoFeatureAt(tileCheck2[0], tileCheck2[1]) == GeoFeature.GROUND))
//				return false;
//		}
//		return collisionDetectionHorizontal(coordinate);
//	}
	
	public boolean canHaveAsNewPosition (double coordinate, int index){
		if (! isTerminated())
		if ((index == 1) && (passableTerrainHorizontal(coordinate)))
//		if ((index == 1) && (testHor(coordinate)))
			return true;
		else if ((index == 2) && (passableTerrainVertical(coordinate)))
			return true;
		return false;
	}
	
//	public boolean passableTerrainHorizontal(double newPosition){
//		if (isMovingLeft()){
//			for (int i = getIntPositionAt(2)+1; i<getIntPositionAt(2)+getSprite().getHeight(); i++){
//				int [] pos = getWorld().getPixelOfTileContaining((int)newPosition-1,i);
//				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
//					return false;
//			}
//		}
//		else if (isMovingRight()){
//			for (int i = getIntPositionAt(2)+1;i<getIntPositionAt(2)+getSprite().getHeight();i++){
//				int [] pos = getWorld().getPixelOfTileContaining((int)newPosition+getSprite().getWidth(),i);
//				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
//					return false;
//			}
//		}
//		return this.collisionDetectionHorizontal(newPosition);
//	}
	
	public boolean passableTerrainHorizontal(double newPosition){
		if (this.getHorizontalVelocity() <= 0.0){
//			for (int i = getIntPositionAt(2)+1; i<getIntPositionAt(2)+getSprite().getHeight(); i++){
			for (int i = getIntPositionAt(2)+1; i<=getIntPositionAt(2)+getSprite().getHeight()-1; i++){
				int [] pos = getWorld().getPixelOfTileContaining((int)newPosition,i);
				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
					return false;
			}
		}
		else if (this.getHorizontalVelocity() >= 0.0){
//			for (int i = getIntPositionAt(2)+1;i<getIntPositionAt(2)+getSprite().getHeight();i++){
			for (int i = getIntPositionAt(2)+1;i<=getIntPositionAt(2)+getSprite().getHeight()-1;i++){
				int [] pos = getWorld().getPixelOfTileContaining((int)newPosition+getSprite().getWidth()-1,i);
				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
					return false;
			}
		}
		return this.collisionDetectionHorizontal(newPosition);
	}
	
	public boolean passableTerrainVertical(double newPosition){
		if (getVerticalVelocity() >= 0.0){
//			for (int i = getIntPositionAt(1);i<getIntPositionAt(1)+getSprite().getWidth();i++){
			for (int i = getIntPositionAt(1);i<=getIntPositionAt(1)+getSprite().getWidth()-1;i++){
				int [] pos = getWorld().getPixelOfTileContaining(i, (int)newPosition+getSprite().getHeight()-1);
				if(getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND){
//					setVerticalVelocity(0.0);
					return false;
				}
			}
		}
		else if (getVerticalVelocity() <= 0.0){
//			for (int i = getIntPositionAt(1);i<getIntPositionAt(1)+getSprite().getWidth();i++){
			for (int i = getIntPositionAt(1);i<=getIntPositionAt(1)+getSprite().getWidth()-1;i++){
				int [] pos = getWorld().getPixelOfTileContaining(i, (int)newPosition);
				if ((getWorld().getGeoFeatureAt(pos[0], pos[1]) == GeoFeature.GROUND)
						&& (((int)newPosition +1)%getWorld().getTileLength() != 0))
					return false;
			}
		}
		return this.collisionDetectionVertical(newPosition);
	}
	
	public boolean collisionDetectionHorizontal(double newPosition){
//		List<Characters> characters = world.getAllObjects();
		World world = getWorld();
		Iterable<Characters> characters;
		if (world.hasAsLeftObject(this) && world.hasAsRightObject(this))
			characters = world.getAllObjects();
		else if (world.hasAsLeftObject(this))
			characters = world.getAllLeftObjects();
		else
			characters = world.getAllRightObjects();
		if (Util.fuzzyGreaterThanOrEqualTo(getHorizontalVelocity(), 0.0)){
			for (Characters character : characters){
				if ((character.getIntPositionAt(1) == (int)newPosition + this.getSprite().getWidth()-1) 
						&& (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 >= (this.getIntPositionAt(2))) 
						&& (character.getIntPositionAt(2) <= this.getIntPositionAt(2) + this.getSprite().getHeight() -1)
						&& (this.collide(character))){
						this.collision(character);
						return (((this instanceof Slime) && (character instanceof Slime))
								|| ((this instanceof Mazub) && (character instanceof Plant))
								|| ((this instanceof Plant) && (character instanceof Mazub)));
				}
			}
		}
		if (Util.fuzzyGreaterThanOrEqualTo(0.0, getHorizontalVelocity())){
			for (Characters character : characters){
				if ((character.getIntPositionAt(1) + character.getSprite().getWidth()-1 == (int)newPosition) 
						&& (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 >= (this.getIntPositionAt(2))) 
						&& (character.getIntPositionAt(2) <= this.getIntPositionAt(2) + this.getSprite().getHeight() -1)
						&& (this.collide(character))){
						this.collision(character);
						return (((this instanceof Slime) && (character instanceof Slime))
								|| ((this instanceof Mazub) && (character instanceof Plant))
								|| ((this instanceof Plant) && (character instanceof Mazub)));
				}
			}
		}
		return true;
	}
	
	public boolean collisionDetectionVertical(double newPosition){
//		List<Characters> characters = world.getAllObjects();
		World world = getWorld();
		Iterable<Characters> characters;
		if (world.hasAsLeftObject(this) && world.hasAsRightObject(this))
			characters = world.getAllObjects();
		else if (world.hasAsLeftObject(this))
			characters = world.getAllLeftObjects();
		else
			characters = world.getAllRightObjects();
		if (Util.fuzzyGreaterThanOrEqualTo(getVerticalVelocity(), 0.0)){
			for (Characters character : characters){
				if ((character.getIntPositionAt(2) == (int)newPosition + this.getSprite().getHeight()-1) 
						&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1 >= (this.getIntPositionAt(1))) 
						&& (character.getIntPositionAt(1) <= this.getIntPositionAt(1) + this.getSprite().getWidth() -1)
						&& (this.collide(character))){
					this.collision(character);
					return (((this instanceof Slime) && (character instanceof Slime))
							|| ((this instanceof Mazub) && (character instanceof Plant))
							|| ((this instanceof Plant) && (character instanceof Mazub)));
				}
			}
		}
		if (Util.fuzzyGreaterThanOrEqualTo(0.0, getVerticalVelocity())){
			for (Characters character : characters){
				if ((character.getIntPositionAt(2) + character.getSprite().getHeight()-1 == (int)newPosition) 
						&& (character.getPositionAt(1) +character.getSprite().getWidth() -1>= (this.getPositionAt(1))) 
						&& (character.getPositionAt(1) <= this.getPositionAt(1) + this.getSprite().getWidth() -1)
						&& ((this.collide(character)))	){
					this.collision(character);
					return (((this instanceof Slime) && (character instanceof Slime))
							|| ((this instanceof Mazub) && (character instanceof Plant))
							|| ((this instanceof Plant) && (character instanceof Mazub)));
				}
			}
		}
		return true;
	}
	
	public boolean collide(Characters other){
		if ((this instanceof Plant) && (other instanceof Slime))
			return false;
		else if ((other instanceof Plant) && (this instanceof Slime))
			return false;
		if ((this instanceof Plant) && (other instanceof Shark))
			return false;
		else if ((other instanceof Plant) && (this instanceof Shark))
			return false;
		return true;
	}
	
	/**
	 * Return the position for this Mazub character.
	 * 	The position gives a combination of the X and Y position of the bottom left corner of the character.
	 * @effect return the array for the stored position value
	 * 			|result == getPositionValue().getPosition()
	 */
	public double[] getPosition() {
		return getPositionValue().getPosition();
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
	 * 			| (! canHaveAsNewPosition(coordinate, index))
	 * @post This character has the given coordinate as its coordinate at the given index.
	 * 			| new.getPositionAt(index) == coordinate
	 */
	protected void setPositionAt(double coordinate, int index) throws ArrayIndexOutOfBoundsException, 
	IllegalArgumentException{
		if (! canHaveAsNewPosition(coordinate, index))
			throw new IllegalArgumentException();
		if (index == 1)
			this.Position = new Position(coordinate, getPositionAt(2));
		else if (index == 2)
			this.Position = new Position(getPositionAt(1), coordinate);
	}
		
	/**
	 * Variable registering the position of the bottom left corner of the Mazub Sprite.
	 */
	protected Position Position = new Position(0.0, 0.0);	
	
	public abstract void collision(Characters other);

	protected void terminate() {
		if (! isTerminated()){
			this.setTerminated(true);
			this.setHorizontalVelocity(0.0);
			this.setVerticalVelocity(0.0);
		}
	}
	
	protected void damage(int damage){
		if (Util.fuzzyLessThanOrEqualTo(this.getHitPoints(), damage)){
			this.setHitPoints(0);
			this.terminate();
		}
		else
			this.setHitPoints(this.getHitPoints() - damage);
	}
	
	public boolean canHaveAsHitpoints(int hitpoints){
		if (hitpoints < 0)
			return false;
		return true;
	}
	
	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	public int hitPoints;
	
	public GeoFeature environment(int[] position){
		int[] pos = getWorld().getPixelOfTileContaining(position[0], position[1]);	
		return getWorld().getGeoFeatureAt(pos[0], pos[1]);
	}
	
	public abstract void environmentDamage(double duration);
	
	public double getTimeSinceEnvironmentalDamage() {
		return timeSinceEnvironmentalDamage;
	}

	public void setTimeSinceEnvironmentalDamage(double timeSinceEnvironmentalDamage) {
		this.timeSinceEnvironmentalDamage = timeSinceEnvironmentalDamage;
	}
	
	@Raw
	public boolean canHaveAsWorld(World world) {
		if (this.isTerminated()){
			if (world == null)
				return true;
		}
		if (! world.hasAsObject(this))
			return false;
		if (world.isTerminated())
			return false;
		return true;
	}

	public double timeSinceEnvironmentalDamage = 0.0;
}
