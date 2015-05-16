package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.program.Program;
import jumpingalien.util.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Characters.
 * @invar	the amount of sprites for this character is correct
 * 			|isValidNbImages(getNbImages())
 * @invar	the standard horizontal acceleration must be valid
 * 			|isValidHorizontalAcceleration(getAbsHorizontalAcceleration())
 * @invar	the saved maximum horizontal velocity must be valid. This implicitly implies a valid current
 * 			horizontal velocity and a valid initial horizontal velocity.
 * 			|canHaveAsMaxHorizontalVelocity(getMaxHorizontalVelocity())
 * @invar	the initial vertical velocity must be valid.
 * 			|canHaveAsInitVerticalVelocity(getInitVerticalVelocity())
 * @invar	the saved vertical acceleration must be valid.
 * 			|isValidVerticalAcceleration(getVerticalAcceleration())
 * @invar	the direction the character has moved in must be valid
 * 			|isValidDirection(getHasMovedIn())
 * @invar	the amount of coordintes for the character must be valid
 * 			|isValidNbPosition(getNbPosition())
 * @invar	the amount of hitpoints must be valid
 * 			|isValidHitPoints(getHitPoints())
 * @invar	the character must have a valid world
 * 			|canHaveAsWorld(getWorld())
 * @invar	the character must have proper characters that are close
 * 			|hasProperCloseCharacters()
 * @author Peter Lacko (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor)),
 * 			Sander Switsers (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor))
 * @version 1.2
 * Code repository: https://github.com/Peter-Lacko/Jumping-Alien
 */
public abstract class Characters {

	/**
	 * initialize a character with a given x_pos, y_pos, sprites, horizontal acceleration,
	 * 	maximum horizontal velocity, initial horizontal velocity, initial vertical velocity and hit points.
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
	 * @param hitPoints
	 * 			the starting amount of hit points for the character
	 * @param	behavior
	 * 			the program containing the behavior for the character
	 * @post	This character starts at the given position.
	 * 			| new.getPosition().equals({(double) x_pos, (double) y_pos})
	 * @post	This character's sprite set is equal to the given one.
	 * 			| new.getImages().equals(sprites)
	 * @post	this character's image length is equal to the length of the given sprite array
	 * 			|new.getNbImages() == sprites.length
	 * @post	this character's horizontal acceleration is set to the given horizontal acceleration
	 * 			| new.getAbsHorizontalAcceleration() == hor_acc
	 * @post	this character's max horizontal velocity is set to the given max horizontal velocity
	 * 			| new.getMaxHorizontalVelocity == max_hor_vel
	 * @post	this character's initial horizontal velocity is set to the given initial horizontal velocity
	 * 			| Math.abs(new.getInitHorizontalVelocity()) == init_hor_vel
	 * @post	this character's initial vertical velocity is set to the given initial vertical velocity
	 *			| new.getInitVerticalVelocity() == init_ver_vel
	 * @post	this character's displayed sprite is the first sprite in the given array of sprites
	 * 			| new.getSprite() == sprites[0]
	 * @post	this character's hit points is set to the given amount
	 * 			|new.getHitPoints() == hitPoints
	 * @throws IllegalArgumentException
	 * 			the amount of images provided is invalid
	 * 			|! isValidNbImages(sprites.length)
	 * @throws IllegalArgumentException
	 * 			the given horizontal details are invalid
	 * 			|((! isValidHorizontalAcceleration(hor_acc)) || (! canHaveAsMaxHorizontalVelocity(max_hor_vel)) 
	 *			|	|| (! matchesMaxHorizontalVelocityInitHorizontalVelocity(max_hor_vel, init_hor_vel)))
	 * @throws IllegalArgumentException
	 * 			the given initial vertical velocity is invalid
	 * 			|(! canHaveAsInitVerticalVelocity(init_ver_vel))
	 * @throws IllegalArgumentException
	 * 			the given amount of hitpoints is invalid
	 * 			|(! isValidHitPoints(hitPoints))
	 */
	@Raw
	public Characters(int x_pos, int y_pos, Sprite[] sprites, double hor_acc, double max_hor_vel, 
			double init_hor_vel, double init_ver_vel, int hitPoints, Program behavior)
					throws IllegalArgumentException{
		if (! isValidNbImages(sprites.length))
			throw new IllegalArgumentException("Illegal sprite array length!");
		if ((! isValidHorizontalAcceleration(hor_acc)) || (! canHaveAsMaxHorizontalVelocity(max_hor_vel)) 
				|| (! matchesMaxHorizontalVelocityInitHorizontalVelocity(max_hor_vel, init_hor_vel)))
			throw new IllegalArgumentException("illegal horizontal details");
		if (! canHaveAsInitVerticalVelocity(init_ver_vel))
			throw new IllegalArgumentException("illegal vertical details");
		if (! isValidHitPoints(hitPoints))
			throw new IllegalArgumentException();
		this.images = sprites.clone();
		this.setSprite(getImageAt(1));
		this.Position = new Position (x_pos, y_pos);
		this.setHorizontalAcceleration(hor_acc);
		this.setMaxHorizontalVelocity(max_hor_vel);
		this.setInitHorizontalVelocity(init_hor_vel);
		this.setinitVerticalVelocity(init_ver_vel);
		this.setHitPoints(hitPoints);
		this.program = behavior;
	}
	
	/**
	 * 
	 * @param x_pos
	 * @param y_pos
	 * @param sprites
	 * @param hor_acc
	 * @param max_hor_vel
	 * @param init_hor_vel
	 * @param init_ver_vel
	 * @param hitPoints
	 * @effect	|this(x_pos, y_pos, sprites, hor_acc, max_hor_vel, init_hor_vel, init_ver_vel, hitPoints, null)
	 */
	@Raw
	public Characters(int x_pos, int y_pos, Sprite[] sprites, double hor_acc, double max_hor_vel, 
			double init_hor_vel, double init_ver_vel, int hitPoints){
		this(x_pos, y_pos, sprites, hor_acc, max_hor_vel, init_hor_vel, init_ver_vel, hitPoints, null);
	}

	public Program getProgram(){
		return program;
	}
	
	private final Program program;
	
	/**
	 * A getter method for the variable isTerminated
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * A setter method for the variable isTerminated
	 * @param isTerminated
	 * 			the new status to be changed to
	 * @post	the new terminated status is equal to the given status
	 * 			|new.isTerminated() == isTerminated
	 */
	private void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	/**
	 * A variable containing whether a character is terminated
	 */
	private boolean isTerminated = false;

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
	 * 			the world to be set
	 * @post	the new world is equal to the given world
	 * 			| new.getWorld() == world
	 * @throws IllegalArgumentException
	 * 			the given world is not null, but invalid.
	 * 			| ((world != null) && (! canHaveAsWorld(world)))
	 */
	@Raw
	protected void setWorld(@Raw World world) throws IllegalArgumentException {
		if (world == null)
			this.world = world;
		else{
			if (! canHaveAsWorld(world))
				throw new IllegalArgumentException("Wrong Arguments");
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
	 * A method to determine which sprite must be displayed
	 * @return	The sprite that must be displayed based on the character's values.
	 */
	public Sprite getCurrentSprite() {
		return leftOrRightSprite(0);
	}

	/**
	 * A method to determine which sided sprite must be displayed
	 * @pre	the given index must be in the correct range (0 to the number of images minus 1)
	 * @param number
	 * 			the index in the list of images indicating witch action must be displayed
	 * @return	the correct sided sprite based on what direction the character is moving and 
	 * 			which index it was given
	 */
	protected Sprite leftOrRightSprite(int number){
		assert (number >= 0);
		assert (number + 1 < getNbImages());
		if ((this.getHorizontalVelocity() < 0) || (getHasMovedIn() == MovementDirection.LEFT)){
			return getImageAt(number + 1);
		} else{
			return getImageAt(number + 2);
		}
	}

	/**
	 * Check whether the given number of images is valid
	 * @param nbImages
	 * 			the amount to check.
	 * @return	the number is invalid if it is smaller than 2 or not even
	 * 			| if (nbImages < 2) || (nbImages % 2 != 0)
	 * 			|	result == false
	 */
	@Raw
	public boolean isValidNbImages(int nbImages) {
		if ((nbImages >= 2) && (nbImages % 2 == 0))
			return true;
		else
			return false;
	}

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
	@Raw
	private void setSprite(Sprite sprite) {
		assert (sprite != null);
		this.sprite = sprite;
	}

	/**
	 * A variable that stores the current sprite of the character.
	 */
	private Sprite sprite;

	/**
	 * Return the amount of sprites stored for this character.
	 */
	@Basic @Immutable
	public int getNbImages() {
		return getImages().length;
	}

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
		assert ((index > 0) && (index <= getNbImages()));
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
	 * An array of Sprites to display the character.
	 */
	private Sprite[] images;

	/**
	 * Compute the new horizontal velocity after a given duration (and then set it)
	 * @param duration
	 * 			the duration after which to calculate the new horizontal velocity.
	 * @post	if the character is moving left or right, the new velocity is equal to a velocity calculated
	 * 			by the standard formula
	 * 			|if (isMovingLeft() || isMovingRight())
	 * 			|	then new.getHorizontalVelocity() == computeNewHorizontalVelocityMoving(duration)
	 * 			otherwise, the new velocity is 0
	 * 			|else new.getHorizontalVelocity() == 0.0
	 * @post	if the character is moving, but below their maximum horizontal velocity, 
	 * 			then they are accelerating
	 * 			|if ((! Util.fuzzyEquals(newVelocity, 0.0)) && (! Util.fuzzyGreaterThanOrEqualTo(
	 *			|	Math.abs(newVelocity),getMaxHorizontalVelocity())))
	 *			|	then new.isAccelerating() == true
	 *			otherwise, they are not accelerating
	 *			|else new.isAccelerating() == false
	 */
	protected void computeNewHorizontalVelocityAfter(double duration) {
		double newVelocity = 0.0;
		if (this.isMovingLeft() || this.isMovingRight()){
			newVelocity = computeNewHorizontalVelocityMoving(duration);
			setHorizontalVelocity(newVelocity);
		}
		else {
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
	 * @return	The new velocity is calculated as v_horizontal,new = v_horizontal,current 
	 * 			+ duration*a_horizontal, or the maximum horizontal velocity, whichever is smallest in
	 * 			absolute value.
	 * 			|if this.isMovingLeft()
	 * 			|	then result == Max(-getMaxHorizontalVelocity(),
	 * 			|			Min(getHorizontalVelocity(),getInitHorizontalVelocity())+duration*
	 * 			|			getHorizontalAcceleration())
	 * 			|else if this.isMovingRight()
	 * 	 		|	then result == Min(getMaxHorizontalVelocity(),
	 * 			|			Max(getHorizontalVelocity(),getInitHorizontalVelocity())+duration*
	 * 			|			getHorizontalAcceleration())
	 */
	@Model
	private double computeNewHorizontalVelocityMoving(double duration){
		double newVelocity = 0.0;
		newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
		newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		if (isMovingLeft())
			newVelocity = -1.0*newVelocity;
		return newVelocity;
	}

	/**
	 * Calculate the new horizontal position after the given time
	 * @param duration
	 * 			the duration of time that passes
	 * @return	The new position is calculated with specific formulas.
	 * 			|result == this.getPositionAt(1) + 100*duration*getHorizontalVelocity()
	 * 			|	+ isAccelerating()? 100*0.5*duration²*getHorizontalAcceleration() : 0
	 */
	protected double calculateNewHorizontalPositionAfter(double duration) {
		double newPosition = getPositionAt(1);
		newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
		if (this.isAccelerating())
			newPosition += 100*0.5*getHorizontalAcceleration()*duration*duration;
		return newPosition;
	}

	/**
	 * Compute the new vertical speed after a given duration (and set it).
	 * @param duration
	 * 			The duration after after which to calculate the new vertical speed.
	 * @post	if the character is falling, the new velocity is calculated according to the formula
	 * 			v_y,new = v_y,old+a_y*duration
	 * 			|if (isFalling())
	 * 			|	then new.getVerticalVelocity() == getVerticalVelocity()+getVerticalAcceleration()
	 * 			|		*duration
	 * 			otherwise, if the character is jumping, the same formula is used, but v_y,old is replaced
	 * 			with v_init
	 * 			|else
	 * 			|	if (isJumping())
	 * 			|		then new.getVerticalVelocity() == getInitVerticalVelocity()
	 * 			|			+getVerticalAcceleration()*duration
	 */
	protected void computeNewVerticalVelocityAfter(double duration) throws IllegalArgumentException{
		if (isFalling()){
			double newVelocity = getVerticalVelocity()+getVerticalAcceleration()*duration;
			setVerticalVelocity(newVelocity);
		}
		else{
			if (isJumping())
				setVerticalVelocity(getInitVerticalVelocity()+getVerticalAcceleration()*duration);
			else
				setVerticalVelocity(0.0);
		}
	}

	/**
	 * Calculate the new vertical position after the given duration.
	 * @param duration
	 * 			the duration of time that passes
	 * @return the new position is calculated with the specific formula y_new = y_old + v_y*duration
	 * 			+a_y*0.5*duration²
	 * 			|result == getPositionAt(2) + 100*duration*getVerticalVelocity()
	 * 			|	+100*0.5*getVerticalAcceleration()*duration²
	 */
	protected double calculateNewVerticalPositionAfter(double duration){
		double newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity()
				+100*0.5*getVerticalAcceleration()*duration*duration;
		return newYPosition;
	}

	/**
	 * Check if the character is falling
	 * @result if the character is not in a world, he is not falling
	 * 			|if getWorld() == null
	 * 			|	then result == false
	 * 			if the bottom row of pixels (excluding corners) is overlapping with ground tiles then the
	 * 			character is not falling.
	 * 			|for i in getIntPositionAt(1)+1..getIntPositionAt(1)+getSprite().getWidth()-2;
	 * 			|	let pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2))
	 * 			|	if getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND
	 * 			|		then result == false
	 * 			if the bottom row of pixel (excluding corners) is overlapping with an impassible character,
	 * 			then the character is not falling.
	 * 			|if isCharacterBlockingDown(getPositionAt(2))
	 * 			|	then result == false
	 */
	protected boolean checkFalling(){
		if (getWorld() == null){
			return false;
		}
		else{
			int width = getIntPositionAt(1)+getSprite().getWidth()-2;
			boolean looping = true;
			int i = getIntPositionAt(1)+1;
			while (looping){
				if (i == width)
					looping = false;
				int[] pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2));
				if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
					return false;
				i = Math.min(width, i+getWorld().getTileLength());
			}
			if (isCharacterBlockingDown(this.getPositionAt(2)))
				return false;
		}
		return true;
	}

	/**
	 * A method to advance time for a give duration
	 * @param duration
	 * 			the amount of time to advance
	 * @post	The new falling status is set to the appropriate value.
	 * 			|if checkFalling()
	 * 			|	then new.isFalling() == true
	 * 			|else
	 * 			|	new.isFalling() == false
	 * @effect	a more precise version of advanceTimeLong is invoked for each shorter duration the original
	 * 			duration is split into
	 * 			|for (double shorter = shortDuration(duration); shorter <= duration; shorter += sortDuration(duration))
	 * 			|	advanceTimeLong(shorter)
	 * @post	the character is set to the right side of its world, if it is still in its world
	 * 			|if (getWorld().hasAsObject(this))
	 *			|	then if (this.getIntPositionAt(1) <= getWorld().getWorldSize()[0]/2)
	 *			|		then (new getWorld()).hasAsLeftObject(this);
	 *			|	if ((this.getIntPositionAt(1) + this.getSize()[0] - 1) >= getWorld().getWorldSize()[0]/2)
	 *			|		then getWorld().hasAsRightObject(this);
	 * @throws IllegalArgumentException
	 * 			the given duration is invalid (below 0 or above 0.2)
	 * 			|(duration < 0.0) || (duration >= 0.2)
	 */
	public void advanceTime (double duration) throws IllegalArgumentException{
		if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
			throw new IllegalArgumentException();
		if (checkFalling())
			setFalling(true);
		else
			setFalling(false);
		double shortduration = shortDuration(duration);
		int iteraties = ((int)(duration/shortduration));
		for (int i=0 ;i<iteraties ;i++){
			this.advanceTimeLong(shortduration);
			if(! this.isTerminated()){
				if (checkFalling())
					setFalling(true);
				else
					setFalling(false);
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
		this.advanceTimeLong(duration - shortduration*iteraties);
		if(! this.isTerminated()){
			if (checkFalling())
				setFalling(true);
			else
				setFalling(false);
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

	/**
	 * A more specific (detailed) method to advance time.
	 * @param duration
	 * 			the amount of time to advance
	 * @post	if the character's hit points are 0, then the new character is terminated.
	 * 			|if(getHitPoints() == 0)
	 * 			|	then isTerminated() == true;
	 * @post	for the next postconditions, the following condition applies: the characters hitpoints is not 0
	 * 			|if (getHitPoints() != 0)
	 * @post	a new horizontal position and velocity is set
	 * 			|new.getHorizontalPosition() ?= this.getHorizontalPosition()
	 * 			|new.getHorizontalVelocity() ?= this.getHorizontalVelocity()
	 * @post	a new vertical position and velocity is set
	 * 			|new.getVerticalPosition() ?= this.getVerticalPosition()
	 * 			|new.getVerticalVelocity() ?= this.getVerticalVelocity()
	 * @post	a new sprite is set
	 * @post	the character suffers from possible enviromental damage (and loses hit points)
	 * 			|new.getHitPoints() <= this.getHitPoints()
	 * 			|new.getTimeSinceEnvironmentalDamage() ?= this.getTimeSinceEnvironmentalDamage()
	 * 			|new.isBadEnvironment == true || false
	 */
	protected void advanceTimeLong (double duration){
		if (this.getHitPoints() == 0){
			this.terminate();
		}
		if (! isTerminated()){
			this.computeHorizontalMovement(duration);
			this.computeVerticalMovement(duration);
			this.setSprite(this.getCurrentSprite());
			this.environmentDamage(duration);
		}
	}

	/**
	 * A method to calculate an optimal shorter duration
	 * @param duration
	 * @return	the optimal shorter duration with certain formulas
	 * 			| speed = Math.sqrt((getVerticalVelocity()*getVerticalVelocity() + getHorizontalVelocity()*getHorizontalVelocity()));
	 * 			| acceleration = Math.sqrt((getVerticalAcceleration()*getVerticalAcceleration() + getHorizontalAcceleration()*getHorizontalAcceleration()))
	 * 			| return Math.min((0.01 / (speed + acceleration * duration)),duration)
	 */
	protected double shortDuration(double duration){
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
	 * @return True if and only if the acceleration is greater than or equal to zero.
	 * 			| result == (acceleration >= 0)
	 */
	@Raw
	public boolean isValidHorizontalAcceleration(double acceleration) {
		return (Util.fuzzyGreaterThanOrEqualTo(acceleration, 0.0));
	}

	/**
	 * return the horizontal acceleration of the character (always positive)
	 */
	@Basic
	public double getAbsHorizontalAcceleration(){
		return this.horizontalAcceleration;
	}

	/**
	 * set the characters horizontal acceleration to the given double
	 * @param 	acceleration
	 * 			the value to which horizontal acceleration has to be set
	 * @post	the new horizontal acceleration is equal to the given value
	 * 			| new.getAbsHorizontalAcceleration() == acceleration
	 */
	@Raw
	protected void setHorizontalAcceleration(double acceleration){
		this.horizontalAcceleration = Math.abs(acceleration);
	}

	/**
	 * a variable containing the horizontal acceleration of the character
	 */
	private double horizontalAcceleration;

	/**
	 * A method to return the current horizontal acceleration.
	 * @return	if the character is not accelerating, the value is 0
	 * 			|if (! isAccelerating())
	 * 			|	then result == 0.0
	 * 			otherwise, if the character is moving left, the value is negative
	 * 			|else if (isMovingLeft())
	 * 			|	then result == -getAbsHorizontalAcceleration()
	 * 			otherwise, the value is positive
	 * 			|	else result == getAbsHorizontalAcceleration()
	 */
	public double getHorizontalAcceleration() {
		if (! this.isAccelerating())
			return 0.0;
		else if (this.isMovingLeft())
			return -getAbsHorizontalAcceleration();
		else		
			return getAbsHorizontalAcceleration();
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
	 * @return true if the velocity is valid, and matches the current horizontal velocity, and matches the 
	 * 			initial horizontal velocity.
	 * 			| result == (isPossibleMaxHorizontalVelocity(velocity) 
	 *			|	&& matchesHorizontalVelocityMaxHorizontalVelocity(getHorizontalVelocity(), velocity)
	 *			|	&& matchesMaxHorizontalVelocityInitHorizontalVelocity(velocity, getInitHorizontalVelocity()))
	 */
	@Raw
	public boolean canHaveAsMaxHorizontalVelocity(double velocity) {
		return (isPossibleMaxHorizontalVelocity(velocity) 
				&& matchesHorizontalVelocityMaxHorizontalVelocity(getHorizontalVelocity(), velocity)
				&& matchesMaxHorizontalVelocityInitHorizontalVelocity(velocity, getInitHorizontalVelocity()));
	}

	/**
	 * Check whether the given maximum horizontal velocity is possible
	 * @param velocity
	 * 			the velocity to check
	 * @return	true if the velocity is greater than or equal to 0
	 * 			|result == (velocity >= 0.0)
	 */
	@Raw
	public boolean isPossibleMaxHorizontalVelocity(double velocity){
		return (Util.fuzzyGreaterThanOrEqualTo(velocity, 0.0));
	}

	/**
	 * Check if the given maximum horizontal velocity and initial horizontal velocity match.
	 * @param maxVelocity
	 * 			the maximum velocity to check
	 * @param initVelocity
	 * 			the initial velocity to check
	 * @return	true if the max velocity is greater than or equal to the absolute value of the init velocity
	 * 			|result == (maxVelocity >= Math.abs(initVelocity))
	 */
	@Raw
	public boolean matchesMaxHorizontalVelocityInitHorizontalVelocity(double maxVelocity,
			double initVelocity){
		return(Util.fuzzyGreaterThanOrEqualTo(maxVelocity,Math.abs(initVelocity)));
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
	@Raw
	protected void setMaxHorizontalVelocity(double velocity) throws IllegalArgumentException{
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
	 * A method to set the initial horizontal velocity.
	 * @param velocity
	 * 			the new initial horizontal velocity.
	 * @post The new initial horizontal velocity is equal to the given velocity.
	 * 		| Math.abs(new.getInitHorizontalVelocity()) == velocity
	 */
	@Raw
	protected void setInitHorizontalVelocity(double velocity){
		this.initHorizontalVelocity = velocity;
	}

	/**
	 * Constant reflecting the initial velocity of a character.
	 */
	private double initHorizontalVelocity;

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
	 * 		| new.getHorizontalVelocity() == velocity
	 */
	protected void setHorizontalVelocity(double velocity) {
		assert this.matchesHorizontalVelocityMaxHorizontalVelocity(velocity, getMaxHorizontalVelocity());
		this.currHorizontalVelocity = velocity;
	}

	/**
	 * Check whether the given horizontal velocity and maximum horizontal velocity match.
	 * @param velocity
	 * 			the velocity to check
	 * @param maxVelocity
	 * 			the maximum velocity to check
	 * @return	true if the given max velocity is greater than or equal to the absolute value of the given velocity
	 *			|result == (maxVelocity >= Math.abs(velocity))
	 */
	public boolean matchesHorizontalVelocityMaxHorizontalVelocity(double velocity, double maxVelocity){
		return(Util.fuzzyGreaterThanOrEqualTo(maxVelocity,Math.abs(velocity)));
	}

	/**
	 * A variable reflecting the current horizontal velocity.
	 */
	private double currHorizontalVelocity = 0.0;

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
		setAccelerating(true);
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
	 */
	public void endMove (String direction) {
		assert (direction == "left" || direction == "right");
		if (direction == "left"){
			this.setMovingLeft(false);
		}
		else {
			this.setMovingRight(false);
		}
	}

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
	protected void setAccelerating(boolean flag) {
		this.isAccelerating = flag;
	}

	/**
	 * value stating whether the character is accelerating
	 */
	private boolean isAccelerating = false;

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
	protected void setMovingLeft(boolean flag) {
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
	 * 			| new.isMovingRight() == flag
	 */
	protected void setMovingRight(boolean flag) {
		this.isMovingRight = flag;
	}

	/**
	 * A value stating if the character is moving right.
	 */
	private boolean isMovingRight = false;

	/**
	 * A getter method for the variable isFalling
	 */
	@Basic
	public boolean isFalling(){
		return this.isFalling;
	}

	/**
	 * A setter method for the variable isFalling
	 * @param falling
	 * 			the boolean to which isFalling has to be set
	 * @post	isFalling is set to the given value
	 * 			|new.isFalling() == falling
	 */
	protected void setFalling(boolean falling) {
		this.isFalling = falling;
	}

	/**
	 * A variable stating whether the character is falling
	 */
	public boolean isFalling;

	/**
	 * Return the current vertical velocity.
	 */
	@Basic
	public double getVerticalVelocity() {
		return this.currVerticalVelocity;
	}

	/**
	 * Set the vertical velocity to the given velocity.
	 * @post The new vertical velocity is the given velocity.
	 * 			| new.getVerticalVelocity() == velocity
	 */
	protected void setVerticalVelocity(double velocity){
		this.currVerticalVelocity = velocity;
	}

	/**
	 * A variable reflecting the current vertical velocity.
	 */
	private double currVerticalVelocity = 0.0;

	/**
	 * Return the speed with which the character starts jumping
	 */
	@Basic
	public double getInitVerticalVelocity() {
		return this.initVerticalVelocity;
	}

	/**
	 * a checker method for whether the double acceleration is valid as verticalVelocity
	 * @param velocity
	 * 			the double which has to be checked whether its valid
	 * @return	true if velocity is greater than or equal to zero and is a number
	 * 			| result == (velocity >= 0)
	 */
	@Raw
	public boolean canHaveAsInitVerticalVelocity(double velocity) {
		return (Util.fuzzyGreaterThanOrEqualTo(velocity, 0.0));
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
	@Raw
	protected void setinitVerticalVelocity(double velocity){
		assert canHaveAsInitVerticalVelocity(velocity);
		this.initVerticalVelocity = velocity;
	}

	/**
	 * Constant reflecting the initial jumping velocity of a character.
	 */
	protected Double initVerticalVelocity;

	/**
	 * Return the current vertical acceleration, or 0 if the character isn't moving vertically
	 */
	@Basic
	public double getVerticalAcceleration() {
		if (this.isFalling() || this.isJumping())
			return VERTICAL_ACCELERATION;
		else
			return 0.0;
	}

	/**
	 * a checker method for whether the double acceleration is valid as verticalAcceleration
	 * @param acceleration
	 * 			the double which has to be checked whether it is valid
	 * @return	true if acceleration is greater than or equal to zero and a number
	 * 			| result == (acceleration >= 0)
	 */
	public boolean isValidVerticalAcceleration(double acceleration) {
		return ((Util.fuzzyGreaterThanOrEqualTo(0.0, acceleration)));
	}

	/**
	 * Constant reflecting the vertical acceleration of a character.
	 * @return The vertical acceleration for all characters is 0.9m/s²
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
	 * @post 	the character is no longer jumping
	 * 			| new.isJumping() == false
	 * @post	the character's new vertical velocity is smaller or equal to 0
	 * 			|new.getVerticalVelocity() <= 0.0
	 */
	public void endJump(){
		this.isJumping = false;
		setVerticalVelocity(Math.min(0.0, getVerticalVelocity()));
	}

	/**
	 * A method that starts the character's jump.
	 * @post the character is jumping
	 * 		| new.isJumping() == true
	 * @effect if the character isn't already in the air, its new vertical velocity is equal to the initial
	 * 			vertical velocity
	 * 			| if (! isFalling())
	 * 			|	then setVerticalVelocity(getInitVerticalVelocity())
	 */
	public void startJump(){
		this.isJumping = true;
		if (! isFalling())
			setVerticalVelocity(getInitVerticalVelocity());
	}

	/**
	 * value stating whether the character is jumping.
	 */
	private boolean isJumping = false;

	/**
	 * An empty method for all characters. Only aliens can duck, but this method must be defined at this
	 * level for the programs. this is overwriten at the level of aliens.
	 * @post	|new.getMaximumHorizontalVelocity() ?= this.getMaximumHorizontalVelocity()
	 */
	public void startDuck(){
		
	}
	
	/**
	 * An empty method for all characters. Only aliens can duck, but this method must be defined at this
	 * level for the programs. this is overwriten at the level of aliens.
	 */
	public void endDuck(){
		
	}
	
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
	private MovementDirection getEnumedOrientation() {
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
	protected void sethasMovedIn(MovementDirection direction) {
		this.hasMovedIn = direction;
	}

	/**
	 * A variable that states in which direction the character has recently moved.
	 */
	private MovementDirection hasMovedIn = MovementDirection.NONE;

	/**
	 * Check whether the given MovementDirection is a valid one.
	 * @param direction
	 * 			the direction to check.
	 * @return True if and only if the direction is left, right or none.
	 * 			| result == (direction == MovementDirection.NONE) || (direction == MovementDirection.LEFT) ||
	 *			|	(direction == MovementDirection.RIGHT)
	 * 
	 */
	@Raw
	public static boolean isValidDirection(MovementDirection direction) {
		return (direction == MovementDirection.NONE) || (direction == MovementDirection.LEFT) ||
				(direction == MovementDirection.RIGHT);
	}

	/**
	 * Return the number of coordinates ascribed to this character.
	 */
	@Basic @Immutable
	public int getNbPosition(){
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
	 * @return	the returned value is a position value with the same coordinates as the saved position
	 * 			|result.equals(new Position(getPositionAt(1),getPositionAt(2))
	 */
	protected Position getPositionValue(){
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
	@Raw
	public double getPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return getPositionValue().getPosition()[index-1];
	}

	/**
	 * Return the coordinates of the character in the playing field
	 * @effect	return the position array in integer form
	 * 			|result == getPositionValue().getIntPosition()
	 */
	@Raw
	public int[] getIntPosition(){
		return getPositionValue().getIntPosition();
	}

	/**
	 * Return the rounded down coordinate (in the form of an integer) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @effect	Rounds down the position at the requested index and converts to an integer type.
	 * 			| result == getIntPosition()[index-1]
	 * @throws ArrayIndexOutOfBoundsException
	 * 			The given index must be positive and may not be bigger than 2.
	 * 			| (index <= 0) || (index > 2)
	 */
	@Raw
	public int getIntPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return getIntPosition()[index-1];
	}

	/**
	 * Check whether the character can have the given value as a coordinate at the given index
	 * @param coordinate
	 * 			the coordinate to check
	 * @param index
	 * 			the index at which the coordinate must be set
	 * @return	any coordinate is allowed, as long as it is in the first or second index.
	 * 			| if index == 1
	 * 			|	then return true
	 * 			| else if index == 2
	 * 			|	then return true
	 * 			| return false
	 */
	public boolean canHaveAsNewPositionValueAt (double coordinate, int index){
		if (index == 1)
			return true;
		else if (index == 2)
			return true;
		return false;
	}

	/**
	 * Return all characters that are in the nearby playing field
	 * @return	if the character is a right object of its world, return all right objects. if it's a left
	 * 			object, return all left objects. if its part of both, return all objects.
	 * 			| let world = getWorld()
	 * 			| if world.hasAsLeftObject(this) && world.hasAsRightObject(this)
	 * 			|	then result == world.getAllObjects()
	 * 			| else if world.hasAsLeftObject(this)
	 * 			|	then result == world.getAllLeftObjects()
	 * 			| else
	 * 			|	then result == world.getAllRightObjects()
	 */
	public Iterable<Characters> getNearbyCharacters(){
		World world = getWorld();
		Iterable<Characters> characters;
		if (world.hasAsLeftObject(this) && world.hasAsRightObject(this))
			characters = world.getAllObjects();
		else if (world.hasAsLeftObject(this))
			characters = world.getAllLeftObjects();
		else
			characters = world.getAllRightObjects();
		return characters;
	}

	/**
	 * Check whether a character is blocking the top row of pixels (excluding corners)
	 * @param position
	 * 		the position that would be used to store the height of this character
	 * @return	true if a top row pixel is overlapping with an impassible character
	 * 			|for character in getNearbyCharacters()
	 * 			|if ((character.getIntPositionAt(2) == (int)position + this.getSprite().getHeight()-1) 
	 *			|	&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1 >= (this.getIntPositionAt(1))) 
	 *			|	&& (character.getIntPositionAt(1) <= this.getIntPositionAt(1) + this.getSprite().getWidth() -1))
	 *			|		then result == collide(character)
	 *			|else result == false
	 */
	protected boolean isCharacterBlockingUp(double position){
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(2) == (int)position + this.getSprite().getHeight()-1) 
					&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1 >= (this.getIntPositionAt(1))) 
					&& (character.getIntPositionAt(1) <= this.getIntPositionAt(1) + this.getSprite().getWidth() -1))
				return (collide(character));
		}
		return false;
	}
	/**
	 * Check whether a character is blocking the bottom row of pixels (excluding corners)
	 * @param position
	 * 		the position that would be used to store the height of this character
	 * @return	true if a bottom row pixel is overlapping with an impassible character
	 * 			|for character in getNearbyCharacters()
	 *			|if ((character.getIntPositionAt(2) + character.getSprite().getHeight()-1 == (int)position) 
	 *			|	&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1>= (this.getIntPositionAt(1))) 
	 *			|	&& (character.getIntPositionAt(1) <= this.getIntPositionAt(1) + this.getSprite().getWidth() -1))
	 *			|		then result == collide(character)
	 *			|else result == false
	 */
	protected boolean isCharacterBlockingDown(double position){
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(2) + character.getSprite().getHeight()-1 == (int)position) 
					&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1>= (this.getIntPositionAt(1))) 
					&& (character.getIntPositionAt(1) <= this.getIntPositionAt(1) + this.getSprite().getWidth() -1))
				return (collide(character));
		}
		return false;
	}

	/**
	 * Determine whether the given character blocks this character's movement.
	 * @param other
	 * 			the other character in question
	 * @return	true or false depending on which this character is and which the other character is.
	 * 			result == (true || false)
	 */
	@Raw
	public abstract boolean collide(Characters other);

	/**
	 * Return the position for this Mazub character.
	 * 	The position gives a combination of the X and Y position of the bottom left corner of the character.
	 */
	@Basic @Raw
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
	 * 			The given coordinate must be a valid coordinate at the given index.
	 * 			| (! canHaveAsNewPositionValueAt(coordinate, index))
	 * @post This character has the given coordinate as its coordinate at the given index.
	 * 			| new.getPositionAt(index) == coordinate
	 */
	protected void setPositionAt(double coordinate, int index) throws ArrayIndexOutOfBoundsException{
		if (! canHaveAsNewPositionValueAt(coordinate, index))
			throw new ArrayIndexOutOfBoundsException();
		if (index == 1)
			this.Position = new Position(coordinate, getPositionAt(2));
		else if (index == 2)
			this.Position = new Position(getPositionAt(1), coordinate);
	}

	/**
	 * Variable registering the position of the bottom left corner of this character.
	 */
	private Position Position = new Position(0.0, 0.0);	

	/**
	 * Calculate the new horizontal position and velocity of this character and set them, as well as damage 
	 * it recieves and gives to other characters, after the given duration.
	 * @param duration
	 * 			the duration of time that elapses.
	 * @post	the new horizontal position is set if nothing is in the way.
	 * 			|let oldPos = getIntPosition()
	 * 			|for i in 1..getSprite().getHeight()-2
	 * 			|	for k in 1.. getSprite().getWidth()-2
	 * 			|		if (getWorld().getGeoFeatureAt(
	 * 			|			getWorld().getBottomLeftPixelOf(oldPos[0]+k, oldPos[1]+i)[0],
	 * 			|			getWorld().getBottomLeftPixelOf(oldPos[0]+k, oldPos[1]+i)[1]) != geoFeature.GROUND
	 * 			|			&& for each character in Characters: 
	 * 			|				for j in 1..character.getSprite().getHeight()-2
	 * 			|					for l in 1..character.getSprite().getWidth()-2
	 * 			|						if ! (character.getIntPositionAt(1)+l == oldPos[0]+k	
	 * 			|							&& character.getIntPositionAt(2)+j == oldPos[1]+i
	 * 			|							&& this.collide(character))		
	 * 			|							then new.getPositionAt(1) 
	 * 			|								== calculateNewHorizontalPositionAfter(duration)
	 * @effect	All necessary damage is given and received from other overlapping characters
	 * 			|for character in getWorld().getAllObjects()
	 * 			|	for thisPixel in {getIntPositionAt(1),getIntPositionAt(2)}..
	 * 			|		{getIntPositionAt(1)+getSize()[0]-1,getIntPositionAt(2)+getSize()[1]-1}
	 * 			|		for otherPixel in {character.getIntPositionAt(1),character.getIntPositionAt(2)}..
	 * 			|			{character.getIntPositionAt(1)+character.getSize()[0]-1,
	 * 			|			character.getIntPositionAt(2)+character.getSize()[1]-1}
	 * 			|			if thisPixel == thatPixel
	 * 			|				then if thisPixel[1]==this.getIntPositionAt(2)+getSize()[1]-1
	 * 			|					then this.collisionNoDamageTo(character)
	 * 			|				else if thisPixel[1] == this.getIntPositionAt(2)
	 * 			|					then this.collisionNoDamageFrom(character)
	 * 			|				else this.collision(character)
	 * 			This only happens once, even if multiple pixels are overlapping.
	 * 			|				break
	 * @effect 	the new Horizontal velocity is set
	 * 			|computeNewHorizontalVelocity()
	 * @effect	any close characters are removed
	 * 			|removeAllCloseCharacters()
	 */
	protected void computeHorizontalMovement(double duration) {
		double newPos = calculateNewHorizontalPositionAfter(duration);
		int oldPos = getIntPositionAt(1);
		boolean canMove = true;
		if ((int)newPos > oldPos)
			canMove = passableTerrainRight(oldPos);
		else if ((int)newPos < oldPos)
			canMove = passableTerrainLeft(oldPos);
		removeAllCloseCharacters();
		collisionDetectionRight(oldPos);
		for (Characters character: getAllCloseCharacters()){
			if (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
					== getIntPositionAt(2))
				collisionNoDamageFrom(character);
			else if (character.getIntPositionAt(2) 
					== getIntPositionAt(2)+this.getSprite().getHeight() -1 )
				collisionNoDamageTo(character);
			else{
				collision(character);
				if (((int)newPos > oldPos) && collide(character))
					canMove = false;
			}
			if (character.getHitPoints() == 0)
				character.terminate();
		}
		removeAllCloseCharacters();
		collisionDetectionLeft(oldPos);
		for (Characters character: getAllCloseCharacters()){
			if (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
					== getIntPositionAt(2))
				collisionNoDamageFrom(character);
			else if (character.getIntPositionAt(2) 
					== getIntPositionAt(2)+this.getSprite().getHeight() -1 )
				collisionNoDamageTo(character);
			else{
				collision(character);
				if (((int)newPos < oldPos) && collide(character))
					canMove = false;
			}
			if (character.getHitPoints() == 0)
				character.terminate();
		}
		if (canMove)
			setPositionAt(newPos, 1);
		computeNewHorizontalVelocityAfter(duration);
		removeAllCloseCharacters();
	}


	/**
	 * Calculate the new vertical position and velocity of this character and set them, as well as damage 
	 * it recieves and gives to other characters, after the given duration.
	 * @param duration
	 * 			the duration of time that elapses.
	 * @post	the new vertical position is set if nothing is in the way.
	 * 			|let oldPos = getIntPosition()
	 * 			|for i in 1..getSprite().getHeight()-2
	 * 			|	for k in 1.. getSprite().getWidth()-2
	 * 			|		if (getWorld().getGeoFeatureAt(
	 * 			|			getWorld().getBottomLeftPixelOf(oldPos[0]+k, oldPos[1]+i)[0],
	 * 			|			getWorld().getBottomLeftPixelOf(oldPos[0]+k, oldPos[1]+i)[1]) != geoFeature.GROUND
	 * 			|			&& for each character in Characters: 
	 * 			|				for j in 1..character.getSprite().getHeight()-2
	 * 			|					for l in 1..character.getSprite().getWidth()-2
	 * 			|						if ! (character.getIntPositionAt(1)+l == oldPos[0]+k	
	 * 			|							&& character.getIntPositionAt(2)+j == oldPos[1]+i
	 * 			|							&& this.collide(character))		
	 * 			|							then new.getPositionAt(2) 
	 * 			|								== calculateNewVerticalPositionAfter(duration)
	 * @effect	Otherwise, the new vertical velocity is set to 0 before the final recalculation
	 * 			|						else setVerticalVelocity(0.0)
	 * @effect	All necessary damage is given and received from other overlapping characters
	 * 			|for character in getWorld().getAllObjects()
	 * 			|	for thisPixel in {getIntPositionAt(1),getIntPositionAt(2)}..
	 * 			|		{getIntPositionAt(1)+getSize()[0]-1,getIntPositionAt(2)+getSize()[1]-1}
	 * 			|		for otherPixel in {character.getIntPositionAt(1),character.getIntPositionAt(2)}..
	 * 			|			{character.getIntPositionAt(1)+character.getSize()[0]-1,
	 * 			|			character.getIntPositionAt(2)+character.getSize()[1]-1}
	 * 			|			if thisPixel == thatPixel
	 * 			|				then if thisPixel[1]==this.getIntPositionAt(2)+getSize()[1]-1
	 * 			|					then this.collisionNoDamageTo(character)
	 * 			|				else if thisPixel[1] == this.getIntPositionAt(2)
	 * 			|					then this.collisionNoDamageFrom(character)
	 * 			|				else this.collision(character)
	 * 			This only happens once, even if multiple pixels are overlapping.
	 * 			|				break
	 * @effect 	the new vertical velocity is set
	 * 			|computeNewVerticalVelocity()
	 * @effect	any close characters are removed
	 * 			|removeAllCloseCharacters()
	 */
	protected void computeVerticalMovement(double duration) {
		double newPos = calculateNewVerticalPositionAfter(duration);
		int oldPos = getIntPositionAt(2);
		boolean canMove = true;
		if ((int)newPos > oldPos)
			canMove = passableTerrainUp(oldPos);
		else if ((int)newPos < oldPos)
			canMove = passableTerrainDown(oldPos);
		removeAllCloseCharacters();
		collisionDetectionUp(oldPos);
		for (Characters character: getAllCloseCharacters()){
			collisionNoDamageTo(character);
			if (((int)newPos > oldPos) && collide(character))
				canMove = false;
			if (character.getHitPoints() == 0)
				character.terminate();
		}
		removeAllCloseCharacters();
		collisionDetectionDown(oldPos);
		for (Characters character: getAllCloseCharacters()){
			collisionNoDamageFrom(character);
			if (((int)newPos < oldPos) && collide(character))
				canMove = false;
			if (character.getHitPoints() == 0)
				character.terminate();
		}
		if (canMove){
			setPositionAt(newPos, 2);
		}
		else{
			setVerticalVelocity(0.0);
		}
		computeNewVerticalVelocityAfter(duration);
		removeAllCloseCharacters();
	}

	/**
	 * Determine what happens to this and another colliding character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @post	This character and the other character's hit points may be changed.
	 * 			|new.getHitPoints() ?= this.getHitPoints()
	 * 			|(new other).getHitPoints() ?= other.getHitPoints()
	 */
	public abstract void collision(Characters other);

	/**
	 * Determine what happens to this and another colliding character while this character possibly
	 * receives no damage from the other character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @post	This character and the other character's hit points may be changed.
	 * 			|new.getHitPoints() ?= this.getHitPoints()
	 * 			|(new other).getHitPoints() ?= other.getHitPoints()
	 */
	public abstract void collisionNoDamageFrom(Characters other);

	/**
	 * Determine what happens to this and another colliding character while this character possibly
	 * deals no damage to the other character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @post	This character and the other character's hit points may be changed.
	 * 			|new.getHitPoints() ?= this.getHitPoints()
	 * 			|(new other).getHitPoints() ?= other.getHitPoints()
	 */
	public abstract void collisionNoDamageTo(Characters other);	

	/**
	 * A method to terminate a character
	 * @post	if the character is not already terminated, terminate it and set all velocities to 0.
	 * 			this caracter may be removed from the world immediately.
	 * 			| if not isTerminated()
	 * 			|	then{ new.isTerminated() == true
	 * 			|		new.getHorizontalVelocity() == 0.0
	 * 			|		new.getVerticalVelocity() == 0.0
	 * 			|		(new World).hasAsObject(this) == false || true}
	 */
	@Raw
	protected void terminate() {
		if (! isTerminated()){
			this.setTerminated(true);
			this.setHorizontalVelocity(0.0);
			this.setVerticalVelocity(0.0);
		}
	}

	/**
	 * A method to damage a character and only the character. It may be possible that other methods are
	 * needed for a correct way to damage characters, defined at their subclass (eg someSlime.damageSlime(damage))
	 * @param damage
	 * 			the amount of damage to give. A positive amount reduces the amount of hit points, a negative
	 * 			amount increases the amount of hit points
	 * @effect	If it is not terminated,  character's hit points will be minimally set to 0
	 * 			|if ! isTerminated(){
	 * 			| 	if (this.getHitPoints() -damage <= 0)
	 * 			|		then this.setHitPoints(0)
	 * 			and maximally to 500
	 * 			| 	else if (this.getHitPoints() -damage>= 500)
	 * 			|		then this.setHitPoints(500)
	 * 			otherwise reduce this hcaracter's hitpoints with the given amount.
	 * 			| 	else
	 * 			|		then this.setHitPoints(this.getHitPoints() - damage)}
	 */
	protected void damage(int damage){
		if (! isTerminated()){
			if (this.getHitPoints() -damage <= 0){
				this.setHitPoints(0);
			}
			else if (this.getHitPoints() -damage>= 500){
				this.setHitPoints(500);
			}
			else{
				this.setHitPoints(this.getHitPoints() - damage);
			}
		}
	}

	/**
	 * A method to determine whether the given amount of hit points is valid
	 * @param hitpoints
	 * 			the amount of hit points to check
	 * @return	False if the amount is below 0 or above 500.
	 * 			| if ((hitpoints < 0) || (hitpoints > 500))
	 * 			|	then result == false
	 * 			| else result == true
	 */
	@Raw
	public boolean isValidHitPoints(int hitpoints){
		if ((hitpoints < 0) || (hitpoints > 500))
			return false;
		return true;
	}

	/**
	 * A getter method for the variable hitPoints
	 */
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * A setter method for the variable hitPoints
	 * @param hitPoints
	 * 			the amount of hit points to set
	 * @post	this new character's hitpoints is equal to the given amount
	 * 			|new.getHitPoints() == hitPoints
	 */
	@Raw
	protected void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	/**
	 * A variable conatining the hitPoints of a character
	 */
	private int hitPoints;

	/**
	 * A method to determine which GeoFeature is in this character's world at the given position.
	 * @param position
	 * @return	the geoFeature at the tile corresponding to the given pixel coordinates
	 * 			| let pos == getWorld().getPixelOfTileContaining(position[0], position[1])
	 * 			| result == getWorld().getGeoFeatureAt(pos[0], pos[1])
	 */
	public GeoFeature environment(int[] position){
		int[] pos = getWorld().getPixelOfTileContaining(position[0], position[1]);	
		return getWorld().getGeoFeatureAt(pos[0], pos[1]);
	}

	/**
	 * A getter method for the variable timeSinceEnvironmentalDamage
	 */
	@Basic
	public double getTimeSinceEnvironmentalDamage() {
		return timeSinceEnvironmentalDamage;
	}

	/**
	 * A setter method for the variable timeSinceEnvironmentalDamage
	 * @param time
	 * 			the time to set
	 * @post this character's new time since environmental damage is equal to the given value
	 * 		|new.getTimeSinceEnvironmentalDamage() == time
	 */
	protected void setTimeSinceEnvironmentalDamage(double time) {
		this.timeSinceEnvironmentalDamage = time;
	}

	/**
	 * Check whether this character can have the given world as a world.
	 * @param world
	 * 			the world to check
	 * @return	if the character is terminated, it can have the null reference as a world
	 * 			| if this.isTerminated()
	 * 			|	if world == null
	 * 			|		result == true
	 * 			if the world doesn't have this character as an object, then this character canno have the
	 * 			given world as a world.
	 * 			| if not world.hasAsObject(this)
	 * 			|	then result == false
	 * 			this character cannot have a terminated world
	 * 			| if world.isTerminated()
	 * 			|	then result == false
	 * 			all other worlds are valid
	 * 			| result == true
	 */
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

	/**
	 * A double containing the timeSinceEnvironmentDamage
	 */
	private double timeSinceEnvironmentalDamage = 0.0;

	/**
	 * Check whether a given close character is valid
	 * @param character
	 * @return	true if the character is not a null reference.
	 * 			| return (character != null)
	 */
	@Raw
	public boolean isValidCloseCharacter(Characters character){
		return (character != null);
	}

	/**
	 * Check whether this character has proper close characters
	 * @return	true if each close character is a valid close character
	 * 			| for character in getAllCloseCharacters()
	 * 			|	if not isValidCloseCharacter(character)
	 * 			|		then return false
	 * 			| return true
	 */
	public boolean hasProperCloseCharacters(){
		for (Characters character: getAllCloseCharacters())
			if (! isValidCloseCharacter(character))
				return false;
		return true;
	}

	/**
	 * A method to return all close characters
	 */
	@Basic
	public Set<Characters> getAllCloseCharacters(){
		return new HashSet<Characters>(closeCharacters);
	}

	/**
	 * A method to add a character as a close characters
	 * @param character
	 * 			the character to add
	 * @post	the given character is saved as a close character
	 * 			|new.getAllCloseCharacters().contains(character) == true
	 * @throws IllegalArgumentException
	 * 			| ! isValidCloseCharacter(character)
	 */
	protected void addAsCloseCharacter(Characters character) throws IllegalArgumentException{
		if (! isValidCloseCharacter(character))
			throw new IllegalArgumentException();
		closeCharacters.add(character);
	}

	/**
	 * Remove a given character from all saved close characters
	 * @param character
	 * 			the character to remove
	 * @post	the given character is no longer saved as a close character
	 * 			|new.getAllCloseCharacters().contains(character) == false
	 */
	protected void removeAsCloseCharacter(Characters character){
		closeCharacters.remove(character);
	}

	/**
	 * Empty the set of all saved close characters
	 * @effect	remove each close character in the set of close characters
	 * 			| for character in getAllCloseCharacters()
	 * 			|	removeAsCloseCharacter(character)
	 */
	protected void removeAllCloseCharacters(){
		for (Characters character: getAllCloseCharacters())
			removeAsCloseCharacter(character);
	}

	/**
	 * Check whether this character's world has passible terrain to the right.
	 * @param position
	 * 			the position that would be stored to save this character's x coordinate
	 * @return	true if this character's 'theoretical' right side (excluding corners) is not overlapping 
	 * 			with ground tiles, otherwise false
	 * 			|for i in getIntPositionAt(2)+1..getIntPositionAt(2)+getSprite().getHeight()-2
	 * 			|	if (environment((int)position+getSprite().getWidth()-1,i) == GROUND)
	 * 			|		then result == false
	 * 			|else result == true
	 */
	protected boolean passableTerrainRight(double position) {
		int height = getIntPositionAt(2)+getSprite().getHeight()-2;
		int i = getIntPositionAt(2)+1;
		int[] pos = getWorld().getPixelOfTileContaining((int)position+getSprite().getWidth()-1,i);
		boolean looping = true;
		while (looping){
			if (i == height)
				looping = false;
			pos = getWorld().getPixelOfTileContaining((int)position+getSprite().getWidth()-1,i);
			if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
				return false;
			i = Math.min(height,i+getWorld().getTileLength());
		}
		return true;
	}

	/**
	 * Check whether this character's world has passible terrain to the left.
	 * @param position
	 * 			the position that would be stored to save this character's x coordinate
	 * @return	true if this character's 'theoretical' left side (excluding corners) is not overlapping 
	 * 			with ground tiles, otherwise false
	 * 			|for i in getIntPositionAt(2)+1..getIntPositionAt(2)+getSprite().getHeight()-2
	 * 			|	if (environment((int)position,i) == GROUND)
	 * 			|		then result == false
	 * 			|else result == true
	 */
	protected boolean passableTerrainLeft(double position) {
		int height = getIntPositionAt(2)+getSprite().getHeight()-2;
		int i = getIntPositionAt(2)+1;
		int[] pos = getWorld().getPixelOfTileContaining((int)position,i);
		boolean looping = true;
		while (looping){
			if (i == height)
				looping = false;
			pos = getWorld().getPixelOfTileContaining((int)position,i);
			if (getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
				return false;
			i = Math.min(height,i+getWorld().getTileLength());
		}
		return true;
	}

	/**
	 * Check whether this character's world has passible terrain upwards.
	 * @param position
	 * 			the position that would be stored to save this character's y coordinate
	 * @return	true if this character's 'theoretical' top side (excluding corners) is not overlapping 
	 * 			with ground tiles, otherwise false
	 * 			|for i in getIntPositionAt(1)+1..getIntPositionAt(1)+getSprite().getWidth()-2
	 * 			|	if (environment(i, (int)position+getSprite().getHeight()-1) == GROUND)
	 * 			|		then result == false
	 * 			|else result == true
	 */
	protected boolean passableTerrainUp(double position) {
		int width = getIntPositionAt(1)+getSprite().getWidth()-2;
		int i = getIntPositionAt(1)+1;
		boolean looping = true;
		while (looping){
			if (i == width)
				looping = false;
			int [] pos = getWorld().getPixelOfTileContaining(i, (int)position+getSprite().getHeight()-1);
			if(getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
				return false;
			i = Math.min(width,i+getWorld().getTileLength());
		}
		return true;
	}

	/**
	 * Check whether this character's world has passible terrain downwards.
	 * @param position
	 * 			the position that would be stored to save this character's y coordinate
	 * @return	true if this character's 'theoretical' bottom side (excluding corners) is not overlapping 
	 * 			with ground tiles, otherwise false
	 * 			|for i in getIntPositionAt(1)+1..getIntPositionAt(1)+getSprite().getWidth()-2
	 * 			|	if (environment(i, (int)position) == GROUND)
	 * 			|		then result == false
	 * 			|else result == true
	 */
	protected boolean passableTerrainDown(double position) {
		int width = getIntPositionAt(1)+getSprite().getWidth()-2;
		int i = getIntPositionAt(1)+1;
		int [] pos = getWorld().getPixelOfTileContaining(i, (int)position);
		boolean looping = true;
		while (looping){
			if (i == width)
				looping = false;
			pos = getWorld().getPixelOfTileContaining(i, (int)position);
			if(getWorld().getGeoFeatureAt(pos[0],pos[1]) == GeoFeature.GROUND)
				return false;
			i = Math.min(width,i+getWorld().getTileLength());
		}
		return true;
	}

	/**
	 * Check whether this character's is overlapping with another character to the right. If so, add the
	 * other character to all close characters
	 * @param position
	 * 			the position that would be stored to save this character's x coordinate
	 * @effect	if a character is overlapping with this character's 'theoretical' right side add it 
	 * 			to all close characters
	 * 			|for character in getNearbyCharacters()
	 * 			|	for i in getIntPositionAt(2)..getIntPositionAt(2) + getSprite().getHeight() -1
	 * 			|		for otherPos in character.getIntPosition()..
	 * 			|			character.getIntPosition()+character.getSize()
	 * 			|			if otherPos == {(int)position + this.getSprite().getWidth()-1,i}
	 * 			|				then addAsCloseCharacter(character)
	 */
	protected void collisionDetectionRight(double position) {
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(1) == (int)position + this.getSprite().getWidth()-1) 
					&& (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
							>= (this.getIntPositionAt(2))) 
							&& (character.getIntPositionAt(2) 
									<= this.getIntPositionAt(2) + this.getSprite().getHeight() -1)){
				addAsCloseCharacter(character);
			}
		}
	}

	/**
	 * Check whether this character's is overlapping with another character to the left. If so, add the
	 * other character to all close characters
	 * @param position
	 * 			the position that would be stored to save this character's x coordinate
	 * @effect	if a character is overlapping with this character's 'theoretical' left side, add it 
	 * 			to all close characters
	 * 			|for character in getNearbyCharacters()
	 * 			|	for i in getIntPositionAt(2)..getIntPositionAt(2) + getSprite().getHeight() -1
	 * 			|		for otherPos in character.getIntPosition()..
	 * 			|			character.getIntPosition()+character.getSize()
	 * 			|			if otherPos == {(int)position,i}
	 * 			|				then addAsCloseCharacter(character)
	 */
	protected void collisionDetectionLeft(double position) {
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(1) + character.getSprite().getWidth()-1 == (int)position) 
					&& (character.getIntPositionAt(2) +character.getSprite().getHeight() -1 
							>= (this.getIntPositionAt(2))) 
							&& (character.getIntPositionAt(2) 
									<= this.getIntPositionAt(2) + this.getSprite().getHeight() -1)){
				addAsCloseCharacter(character);
			}
		}
	}

	/**
	 * Check whether this character's is overlapping with another character upwards. If so, add the
	 * other character to all close characters
	 * @param position
	 * 			the position that would be stored to save this character's y coordinate
	 * @effect	if a character is overlapping with this character's 'theoretical' top side, add it 
	 * 			to all close characters
	 * 			|for character in getNearbyCharacters()
	 * 			|	for i in getIntPositionAt(1)..getIntPositionAt(1) + getSprite().getWidth() -1
	 * 			|		for otherPos in character.getIntPosition()..
	 * 			|			character.getIntPosition()+character.getSize()
	 * 			|			if otherPos == {i,(int)position+getSprite().getHeight()-1}
	 * 			|				then addAsCloseCharacter(character)
	 */
	protected void collisionDetectionUp(double position) {
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(2) == (int)position + this.getSprite().getHeight()-1) 
					&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1 
							>= (this.getIntPositionAt(1))) 
							&& (character.getIntPositionAt(1) 
									<= this.getIntPositionAt(1) + this.getSprite().getWidth() -1)){
				addAsCloseCharacter(character);
			}
		}
	}

	/**
	 * Check whether this character's is overlapping with another character downwards. If so, add the
	 * other character to all close characters
	 * @param position
	 * 			the position that would be stored to save this character's y coordinate
	 * @effect	if a character is overlapping with this character's 'theoretical' bottom side, add it 
	 * 			to all close characters
	 * 			|for character in getNearbyCharacters()
	 * 			|	for i in getIntPositionAt(1)..getIntPositionAt(1) + getSprite().getWidth() -1
	 * 			|		for otherPos in character.getIntPosition()..
	 * 			|			character.getIntPosition()+character.getSize()
	 * 			|			if otherPos == {i,(int)position}
	 * 			|				then addAsCloseCharacter(character)
	 */
	protected void collisionDetectionDown(double position) {
		Iterable<Characters> characters = getNearbyCharacters();
		for (Characters character : characters){
			if ((character.getIntPositionAt(2) + character.getSprite().getHeight()-1 == (int)position) 
					&& (character.getIntPositionAt(1) +character.getSprite().getWidth() -1 
							>= (this.getIntPositionAt(1))) 
							&& (character.getIntPositionAt(1) 
									<= this.getIntPositionAt(1) + this.getSprite().getWidth() -1)){
				addAsCloseCharacter(character);
			}
		}
	}

	/**
	 * A method to determine what damage this character receives from the environment.
	 * @param duration
	 * 			the amount of time that passes
	 * @post	if this character is in MAGMA or WATER, it's new value for a bad environtment may be set
	 * 			to true. It's time since environmental damage may be increased with the given duration,
	 * 			if that does not cause it to go over 0.2, otherwise it may set it back to 0
	 * 			|for pos in getIntPosition()..getIntPosition()+getSize()
	 * 			|	if environment(pos) == MAGMA
	 * 			|		then new.isBadEnvironment() ?= true
	 * 			|		then if getTimeSinceEnvironmentalDamage()+duration >= 0.2
	 * 			|			then new.getTimeSinceEnvironmentalDamage() ?= 0.0
	 * 			|		else new.getTimeSinceEnvironmentalDamage() ?= getTimeSinceEnvironmentalDamage()+duration
	 * @post	if this character is in lava, and the time stored to receive environmental damage is
	 * 			equal to 0, then it can receive some damage.
	 *			|		if getTimeSinceEnvironmentalDamage() == 0
	 * 			|			then new.getHitPoints() <= this.getHitPoints()
	 * @post	if this character is in water, and the time stored to receive environmental damage plus
	 * 			the duration is greater than or equal to 0.2, then it can receive some damage.
	 * 			|	else if environment(pos) == WATER
	 * 			|		then new.isBadEnvironment() ?= true
	 * 			|		then if getTimeSinceEnvironmentalDamage()+duration >= 0.2
	 * 			|			then new.getTimeSinceEnvironmentalDamage() ?= 0.0
	 * 			|		else new.getTimeSinceEnvironmentalDamage() ?= getTimeSinceEnvironmentalDamage()+duration
	 *			|		if getTimeSinceEnvironmentalDamage()+duration == 0
	 * 			|			then new.getHitPoints() <= this.getHitPoints()
	 * 			
	 */
	protected void environmentDamage(double duration) {
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
				setTimeSinceEnvironmentalDamage(0.0);
		}else{
			this.setTimeSinceEnvironmentalDamage(0.0);
		}
	}

	/**
	 * A getter method for the variable badEnvironment
	 */
	@Basic
	public boolean isBadEnvironment() {
		return badEnvironment;
	}

	/**
	 * A setter method for the variable badEnvironment
	 * @param badEnvironment
	 * 			the status to set
	 * @post	the new value for isBadEnvironment is equal to the given value
	 * 			|new.getBadEnvironment() == badEnvironment
	 */
	protected void setBadEnvironment(boolean badEnvironment) {
		this.badEnvironment = badEnvironment;
	}

	/**
	 * A set to store all close characters
	 */
	private Set<Characters> closeCharacters = new HashSet<>();

	/**
	 * A boolean stating whether the character is in a bad environment
	 */
	private boolean badEnvironment = false;

}
