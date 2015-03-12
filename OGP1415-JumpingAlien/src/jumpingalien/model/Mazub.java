package jumpingalien.model;

import jumpingalien.util.*;
import be.kuleuven.cs.som.annotate.*;


/**
 * @author Peter Lacko, Sander Switsers
 * @version 1.0
 */
public class Mazub {
	
	public Mazub(int x_pos, int y_pos, Sprite[] sprites){
		this.setPositionAt((double) x_pos, 1);
		this.setPositionAt((double) y_pos, 2);
		this.images = sprites.clone();
		this.nbRunningCycle = getNbImages()/2-4;
		this.setSprite(getImageAt(1));
	}	
	
	/**
	 * an Array of Sprites for the character
	 */
	private final Sprite[] images;
	
	public int getNbImages() {
		return getImages().length;
	}
	
	public static boolean isValidNbImages(int nbImages){
		return (nbImages > 0);
	}
	
	public Sprite getImageAt(int index) {
		return images[index-1];
	}
	
	public Sprite[] getImages(){
		return this.images.clone();
	}
	
	/**
	 * Return the number of coordinates ascribed to this character.
	 * @throws NullPointerException
	 * 			Null is not a valid array for the Position
	 * 			| this.Position == null
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
	
	public int[] getIntPosition(){
		int[] position = {getIntPositionAt(1),getIntPositionAt(2)};
		return position;
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
	public static boolean isValidPositionAt (double coordinate, int index) {
		if ((index == 1) && ((int)coordinate >= X_MIN) && ((int)coordinate <= X_MAX))
			return true;
		if ((index == 2) && ((int)coordinate >= Y_MIN) && ((int)coordinate <= Y_MAX))
			return true;
		return false;
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
	// bij throws (2de): moeten we ArgumentException vervangen door zelfgemaakte IllegalCoordinateException?
	private void setPositionAt(double coordinate, int index) throws ArrayIndexOutOfBoundsException, 
	IllegalArgumentException{
		if (! isValidPositionAt(coordinate, index))
			throw new IllegalArgumentException();
		this.Position[index-1] = coordinate;
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
	 * Variable registering the position of the bottom left corner of the Mazub Sprite.
	 */
	private double[] Position = new double[2];
	
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
	
	/**
	 * Return the orientation of the Mazub character as a String.
	 * 	The orientation is the direction Mazub is facing or moving.
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
	 * Return the orientation of the Mazub character as an (enumerated) MovementDirection.
	 * 	The orientation is the direction Mazub is facing or moving.
	 */
	public MovementDirection getEnumedOrientation() {
		if (this.isMovingLeft() ^ this.isMovingRight()){
			if (this.isMovingLeft())
				return MovementDirection.LEFT;
			else
				return MovementDirection.RIGHT;
		}
		else
			return hasMoved();
	}
	
	/**
	 * Return the current horizontal velocity.
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.currHorizontalVelocity;
	}
	
	public double getHorizontalAcceleration() {
		return Mazub.HORIZONTAL_ACCELERATION;
	}
	
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	public void setMaxHorizontalVelocity(double velocity){
		this.maxHorizontalVelocity = velocity;
	}
	
	public double getInitHorizontalVelocity() {
		return this.INIT_HORIZONTAL_VELOCITY;
	}
	
	/**
	 * Constant reflecting the initial velocity of a character.
	 * @return The initial velocity for a character is 1 m/s.
	 * 			| return == 1.0
	 */
	private final double INIT_HORIZONTAL_VELOCITY = 1.0;
	
	/**
	 * Variable reflecting the maximal horizontal velocity of a character.
	 */
	private double maxHorizontalVelocity = 3.0;
	
	/**
	 * Constant reflecting the horizontal acceleration of a character.
	 * @return The horizontal acceleration for all characters is 0.9m/s�
	 * 			| return == 0.9
	 */
	private static final double HORIZONTAL_ACCELERATION = 0.9;
	
	/**
	 * method that returns the value of the variable isAccelerating
	 */
	@Basic
	public boolean isAccelerating(){
		return this.isAccelerating;
	}
	
	public void setAccelerating(boolean flag) {
		this.isAccelerating = flag;
	}
		
	/**
	 * Compute the new horizontal velocity after a given duration
	 * @param duration
	 * 			the duration after which to calculate the new horizontal velocity.
	 * @post	If the character is moving:
	 * 			| If (this.isMovingLeft() || this.isMovingRight()) then:
	 * 				If the absolute value of the current speed is lower than the initial speed, the new speed is calculated as the smallest value between 
	 * 					1) the initial horizontal velocity plus the duration times horizontal acceleration. 
	 * 					or 2) the maximal horizontal velocity.
	 * 				Otherwise, the new speed is calculated as the smallest value between 
	 * 					1) the current horizontal velocity plus the duration times horizontal acceleration. 
	 * 					or 2) the maximal horizontal velocity.
	 * 				This velocity is then multiplied by -1 if the character is moving to the left.
	 * 				| If (direction == "left")
	 * 				|	then directionModifier == -1
	 * 				| Else directionModifier == 1
	 * 				| If (Math.abs(getHorizontalVelocity()) < getInitHorizontalVelocity())
	 * 				|	then (new.getHorizontalVelocity == 
	 * 				|	directionModifier*Math.min(getInitHorizontalVelocity()+duration*getHorizontalAcceleration(),getMaxHorizontalVelocity()))
	 * 				| If (Math.abs(getHorizontalVelocity()) >= getInitHorizontalVelocity())
	 * 				|	then (new.getHorizontalVelocity == 
	 * 				|	directionModifier*Math.min(getHorizontalVelocity()+duration*getHorizontalAcceleration(),getMaxHorizontalVelocity()))
	 * 			Otherwise the new velocity is 0
	 * 			| else new.getHorizontalVelocity == 0
	 * @post	if the character's horizontal velocity is between 0 and the maximum horizontal velocity then the character is accelerating,
	 * 				otherwise he is not.
	 * 			| If ((new.getHorizontalVelocity != 0) && (new.getHorizontalVelocity < getMaxHorizontalVelocity())
	 * 			|	then new.isAccelerating() == true
	 * 			| Else
	 * 			|	new.isAccelerating() == false
	 */
	public void computeNewHorizontalVelocityAfter(double duration) {
		double newVelocity = 0.0;
		if ((this.isMovingLeft() || this.isMovingRight()) && (! movingInTwoDirections())){
			computeNewHorizontalVelocityMoving(duration, newVelocity);
		}
		else {
			newVelocity = 0.0;
			setHorizontalVelocity(newVelocity);
		}
		if ((! Util.fuzzyEquals(newVelocity, 0.0)) && (newVelocity < getMaxHorizontalVelocity())){
			this.setAccelerating(true);
		}
		else{ this.setAccelerating(false);
		}
	}
	
	public void computeNewHorizontalVelocityMoving(double duration, double newVelocity){
		int directionModifier = 1;
		if (isMovingLeft()){
			directionModifier  = -1;
		}
		if (! Util.fuzzyGreaterThanOrEqualTo(Math.abs(getHorizontalVelocity()), getInitHorizontalVelocity())){
			newVelocity = directionModifier*getInitHorizontalVelocity() + duration*directionModifier*getHorizontalAcceleration();
			newVelocity = Math.min(newVelocity,getMaxHorizontalVelocity());
			setHorizontalVelocity(newVelocity);
		}
		else {
		newVelocity = getHorizontalVelocity() + duration*directionModifier*getHorizontalAcceleration();
		newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		setHorizontalVelocity(newVelocity*directionModifier);
		}
	}
	
	/**
	 * value stating whether the character is accelerating
	 */
	private boolean isAccelerating = false;
	
	/**
	 * Set the horizontal velocity to the given velocity.
	 * @post The new horizontal velocity is the given velocity.
	 * 			| new.getHorizontalVelocity == velocity
	 */
	private void setHorizontalVelocity(double velocity) {
		this.currHorizontalVelocity = velocity;
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
	 * @post The character's new movement is set in the given direction.
	 * 			| if (direction == "left")
	 * 			|	then new.isMovingLeft() == true
	 * 			| else
	 * 			|	new.isMovingRight() == true
	 */
	public void startMove (String direction) {
		if (direction == "left") {
			this.setMovingLeft(true);
		}
		else {
			this.setMovingRight(true);
		}
		this.setTimeSinceEndMove(0.0);
	}
	
	/**
	 * Stop moving the character in the given direction
	 * @param direction
	 * 			The direction to stop moving in.
	 * @pre The direction must be left or right
	 * 		| (direction == "left") || (direction == "right")
	 * @post The character stops movement in the given direction.
	 * 			| if (direction == "left")
	 * 			|	then new.isMovingLeft() == false
	 * 			| else
	 * 			|	new.isMovingRight() == false
	 */
	public void endMove (String direction) {
		if (direction == "left"){
			this.setMovingLeft(false);
		}
		else {
			this.setMovingRight(false);
		}
		setIndex(0);
	}
	
	public boolean isMovingLeft() {
		return this.isMovingLeft;
	}
	
	public void setMovingLeft(boolean flag) {
		this.isMovingLeft = flag;
	}
	
	/**
	 * A value stating if the character is moving left.
	 */
	private boolean isMovingLeft = false;
	
	public boolean isMovingRight() {
		return this.isMovingRight;
	}
	
	public void setMovingRight(boolean flag) {
		this.isMovingRight = flag;
	}
	
	/**
	 * A value stating if the character is moving right.
	 */
	private boolean isMovingRight = false;
	
	/**
	 * A method to calculate the new position and velocity after a given duration of time. It also
	 * @param duration
	 * 			The duration of time that passes.
	 * @effect computes the new horizontal position after the given duration.
	 * 			| computeNewHorizontalPositionAfter(duration)
	 * @effect computes the new horizontal velocity after the given duration.
	 * 			| computeNewHorizontalVelocityAfter(duration)
	 * @throws IllegalArgumentException
	 * 			The given duration is an invalid duration of time.
	 * 			| ((duration < 0.0) || (duration >= 0.2))
	 */
	public void AdvanceTime (double duration) throws IllegalArgumentException {
		if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2)))
			throw new IllegalArgumentException();
		this.determineDoubleDirections();
		this.computeNewHorizontalPositionAfter(duration);
		this.computeNewHorizontalVelocityAfter(duration);
		this.computeNewVerticalPositionAfter(duration);
		this.computeNewVerticalVelocityAfter(duration);
		if (movingInTwoDirections() || ((! isMovingLeft()) && (! isMovingRight())))
			setTimeSinceEndMove(timeSinceEndMove() + duration);
		else
			setTimeSinceStep(timeSinceStep() + duration);
		if (this.timeSinceEndMove() > 1.0)
			this.setHasMoved(MovementDirection.NONE);
		this.setSprite(this.getCurrentSprite());
	}
	
	private void determineDoubleDirections() {
		if (isMovingLeft() && isMovingRight())
			this.setMovingInTwoDirections(true);
		else {
			this.setMovingInTwoDirections(false);
			if (this.isMovingRight()){
				this.setHasMoved(MovementDirection.RIGHT);
				setTimeSinceEndMove(0.0);
			}
			else if (this.isMovingLeft()){
				this.setHasMoved(MovementDirection.LEFT);
				setTimeSinceEndMove(0.0);
			}
		}
	}
	
	/**
	 * Compute the new horizontal position after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new horizontal position.
	 * @post 
	 * 			| if (isValidPositionAt(new.getPositionAt(1),1))
	 * 			|	then if (this.isAccelerating())
	 * 			|			then new.getPositionAt(1) == this.getPositionAt(1) + duration*this.getHorizontalVelocity() 
	 * 			|			+ 0.5*this.getHorizontalAcceleration()*duration�
	 * 			|		else
	 * 			|			new.getPositionAt(1) == this.getPositionAt(1) + duration*this.getHorizontalVelocity()
	 * 			| else if (new.getIntPositionAt(1) < X_MIN)
	 * 			|	then new.getPositionAt(1) == (double)X_MIN
	 * 			| else new.getPositionAt(1) == (double)X_MAX
	 */
	public void computeNewHorizontalPositionAfter(double duration) {
		double newPosition;
		newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity();
		if (this.isAccelerating()){
			newPosition += 0.5*getHorizontalAcceleration()*duration*duration;
		}
		if (isValidPositionAt(newPosition,1)){
			this.setPositionAt(newPosition, 1);
		}
		else if ((int)newPosition < X_MIN){
			this.setPositionAt((double)X_MIN, 1);
			this.endMove("left");
		}
		else {
			this.setPositionAt((double)X_MAX, 1);
			this.endMove("right");
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
	 * Set the vertical velocity to the given velocity.
	 * @post The new vertical velocity is the given velocity.
	 * 			| new.getVerticalVelocity == velocity
	 */
	private void setVerticalVelocity(Double velocity) {
		this.currVerticalVelocity = velocity;
	}
	
	
	/**
	 * A variable reflecting the current vertical velocity.
	 */
	private Double currVerticalVelocity = 0.0;
	
	/**
	 * Return the speed with which the character starts jumping
	 */
	public Double getInitVerticalVelocity() {
		return this.INIT_VERTICAL_VELOCITY;
	}
	
	
	/**
	 * Constant reflecting the initial jumping velocity of a character.
	 * @return The initial jumping velocity for a character is 8 m/s.
	 * 			| return == 8.0
	 */
	private final Double INIT_VERTICAL_VELOCITY = 8.0;
	
	/**
	 * Constant reflecting the vertical acceleration of a character.
	 * @return The vertical acceleration for all characters is 0.9m/s�
	 * 			| return == -10
	 */
	private static final Double VERTICAL_ACCELERATION = -10.0;
	
	
	/**
	 * Return the current vertical velocity.
	 */
	@Basic
	public Double getVerticalAcceleration() {
		return Mazub.VERTICAL_ACCELERATION;
	}
	
	
	/**
	 * A variable that returns whether a character is in the air or not
	 * @return	true if the character is in the air
	 * 			else false
	 * 			| if this.getPosition()[1] > 0:
	 * 			| 	return true
	 * 			|
	 */
	public boolean isInAir(){
		if (! Util.fuzzyGreaterThanOrEqualTo(0,this.getPosition()[1])){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isJumping(){
		return this.isJumping;
	}
	
	/**
	 * A method that stops the character's jump
	 */
	public void endJump(){
		this.isJumping = false;
	}
	
	/**
	 * A method that starts the character's jump
	 */
	public void startJump(){
		this.isJumping = true;
	}
	
	
	/**
	 * value stating whether the character is jumping
	 */
	private boolean isJumping = false;
	
	
	/**
	 * Compute the new vertical position after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new vertical position.
	 * @post    if the character is in the air and and loc_Y + v_Y*dt + 0.5*a_Y*dt*dt is 
	 * 			a possible height then that is the new height. if it's to high the new
	 * 			height is the maximal height. if it's to low the new height is the minimal 
	 * 			height. if the character is not in the air but it is jumping, the new height
	 * 			is oc_Y + v_Y*dt.
	 * 			|if isInAir()
	 * 			|	then newYPosition = loc_Y + v_Y*dt + 0.5*a_Y*dt*dt
	 * 			|	if isValidPosition(newYPosition)
	 * 			|		then new.verticalPosition = newYPosition
	 * 			|	else if newYPosition < Y_min
	 * 			|		then new.verticalPosition = Y_min
	 *			|	else
	 *			|		then new.verticalPosition = Y_max
	 *			|else
	 *			|	if isJumping()
	 *			|		then new.verticalPosition = loc_Y + v_Y*dt
	 */
	public void computeNewVerticalPositionAfter(double duration){
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 0.5*getVerticalAcceleration()*duration*duration;
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
				newYPosition = this.getPositionAt(2) + duration*this.getVerticalVelocity();
				this.setPositionAt(newYPosition, 2);
			}
		}
	}
	
	/**
	 * Compute the new vertical speed after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new vertical speed.
	 * @post	if the character is in the air and is going upward but no longer jumping the new vertical
	 * 			velocity is zero otherwise the new vertical velocity is the old one plus the acceleration
	 * 			times the duration
	 * 			if the character is on the ground and jumping the new velocity is the initial velocity of
	 * 			the character if it's not jumping the new velocity is zero
	 * 			| if isInAir()
	 * 			|	if not isJumping() && getVertivalVelocity > 0
	 * 			|		then new.getVerticalVelocity() = 0
	 * 			|	else new.getVerticalVelocity() = getVerticalVelocity()+getVerticalAcceleration()*duration
	 * 			| else 
	 * 			|	if isJumping()
	 * 			|		then new.getVerticalVelocity() = getInitVerticalVelocity()
	 * 			|	else new.getVerticalVelocity() = 0
	 */
	public void computeNewVerticalVelocityAfter(double duration){
		if (isInAir() == true){
			if ((! isJumping()) && (! Util.fuzzyGreaterThanOrEqualTo(0, getVerticalVelocity()))){
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
	
	public boolean isDucked() {
		return this.isDucked;
	}
	
	/**
	 * a boolean stating whether the character is ducked
	 */
	private boolean isDucked = false;

	/**
	 * A method that starts the character's duck
	 * @post	the character is ducking and its max horizantal velocity is reduced to 1
	 * 			| new.isDucked() = true
	 * 			| new.getMaxHorizontalVelocity() = 1.0
	 */
	public void startDuck(){
		this.isDucked = true;
		this.setMaxHorizontalVelocity(1.0);
	}
	
	/**
	 * A method that ends the character's duck
	 * @post	the character is no longer ducking and its max horizantal velocity is back to original
	 * 			| new.isDucked() = false
	 * 			| new.getMaxHorizontalVelocity() = 3.0
	 */
	public void endDuck(){
		this.isDucked = false;
		this.setMaxHorizontalVelocity(3.0);
	}
	
	public double timeSinceEndMove() {
		return this.timeSinceEndMove;
	}
	
	public void setTimeSinceEndMove(double time) {
		this.timeSinceEndMove = time;
	}
	
	/**
	 * A variable that states the time that has passed since the character has stopped moving.
	 */
	private double timeSinceEndMove = 0.0;
	
	public double timeSinceStep() {
		return this.timeSinceStep;
	}
	
	public void setTimeSinceStep(double time) {
		this.timeSinceStep = time;
	}
	
	private double timeSinceStep = 0.0;
	
	public MovementDirection hasMoved() {
		return this.hasMoved;
	}
	
	public void setHasMoved(MovementDirection direction) {
		this.hasMoved = direction;
	}
	
	/**
	 * A variable that states in which direction the character has recently moved.
	 */
	private MovementDirection hasMoved = MovementDirection.NONE;
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * A variable that stores the current sprite of the character.
	 */
	private Sprite sprite;
	
	public int index() {
		return this.index;
	}
	
	public void setIndex(int number) {
		this.index = number;
	}
	/**
	 * A variable to determine at which sprite in the run cycle the character is.
	 */
	private int index = 0;
	
	public int getNbRunningCycle() {
		return this.nbRunningCycle;
	}
	
	/**
	 * A variable that stores how many sprites there are for a running animation (left or right).
	 */
	private final int nbRunningCycle ;
	
	public boolean movingInTwoDirections() {
		return this.movingInTwoDirections;
	}
	
	public void setMovingInTwoDirections(boolean flag) {
		this.movingInTwoDirections = flag;
	}
	
	private boolean movingInTwoDirections = false;
	
	/**
	 * A method to return the size of the character's sprite variable.
	 * @return return the width and Height of the current sprite in an array.
	 */
	public int[] getSize(){
		int[] sizes = {0,0};
		sizes[0] = getSprite().getWidth();
		sizes[1] = getSprite().getHeight();
		return sizes;
	}
	
	/**
	 * A method to determine whether to use the left or right version of a sprite.
	 * @pre number is in the range of images
	 * 		| (number >=0) && (number < getNbImages())
	 * @param number
	 * 			the index in images where the left and right version of the sprite to be returned, is stored.
	 * @return return the correct version of the sprite, depending on the state of the character.
	 */
	public Sprite leftOrRightSprite(int number){
		if (! movingInTwoDirections()){
			if ((isMovingLeft()) || (hasMoved() == MovementDirection.LEFT)){
				return getImageAt(number + 2);
			} else{
				return getImageAt(number + 1);
			}
		}
		else{
			if (hasMoved() == MovementDirection.LEFT)
				return getImageAt(number + 2);
			else
				return getImageAt(number + 1);
		}
	}
	
	/**
	 * A method that returns the Sprite for the character with its current variables.
	 * @return The new Sprite is determined depending on the state of the character.
	 */
	public Sprite getCurrentSprite(){
		if ((isInAir()) && (this.hasMoved() != MovementDirection.NONE) && (! isDucked())){
			return leftOrRightSprite(4);
		} else if ((isDucked()) && (this.hasMoved() != MovementDirection.NONE)){
			return leftOrRightSprite(6);
		} else if (this.hasMoved() != MovementDirection.NONE){
			return getCurrentWalkCycleSprite();
		} else if ((isDucked()) && (this.hasMoved() == MovementDirection.NONE)){
			return getImageAt(2);
		} else{
			return getImageAt(1);
		}
	}
	
	private Sprite getCurrentWalkCycleSprite() {
		if (timeSinceStep() > 0.03){
			if (index() < getNbRunningCycle()-1)
				setIndex(index() + 1);
			else 
				setIndex(0);
			setTimeSinceStep(0.0);
		}
		if (movingInTwoDirections())
			return leftOrRightSprite(2);
		else if (isMovingRight())
			return getImageAt(9+index());
		else if (isMovingLeft()) 
			return getImageAt(9+getNbRunningCycle()+index());
		else {
			return leftOrRightSprite(2);
		}
	}
	
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
}
