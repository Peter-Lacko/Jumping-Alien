package jumpingalien.model;
import jumpingalien.util.Util;
import be.kuleuven.cs.som.annotate.*;


/**
 * @author Peter Lacko, Sander Switsers
 *
 */
public class MazubSander {
	
	/**
	 * Return the number of coordinates ascribed to this character.
	 * @throws NullPointerException
	 * 			Null is not a valid array for the Position
	 * 			| this.Position == null
	 */
	@Basic @Immutable
	//er staat als coding rule dat dit erbij moet, maar Position heeft altijd lengte 2?
	public int getNbPosition() throws NullPointerException {
	// Dit moet defensief zijn. Als Position null is, zal het een NullPointerException gooien.
		return Position.length;
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
	 * Return the rounded down coordinate (in the form of an int) ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @effect	Rounds down the position at the requested index and converts to an int type.
	 * 			| (int)getPositionAt(index)
	 */
	public int getIntPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return (int)Position[index-1];
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
	public void setPositionAt(double coordinate, int index) throws ArrayIndexOutOfBoundsException, 
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
	 * Return the orientation of the Mazub character.
	 * 	The orientation is the direction Mazub is facing or moving.
	 */
	@Basic
	public String getOrientation() {
		return this.Orientation;
	}
	
	/**
	 * Variable registering the orientation of the Mazub character.
	 */
	private String Orientation;
	
	/**
	 * Return the current horizontal velocity.
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.currHorizontalVelocity;
	}
	
	public double getHorizontalAcceleration() {
		return this.HORIZONTAL_ACCELERATION;
	}
	
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	public double getInitHorizontalVelocity() {
		return this.INIT_HORIZONTAL_VELOCITY;
	}
	
	//moeten deze public of private zijn?
	/**
	 * Constant reflecting the initial velocity of a character.
	 * @return The initial velocity for a character is 1 m/s.
	 * 			| return == 1.0
	 */
	private final double INIT_HORIZONTAL_VELOCITY = 1.0;
	
	/**
	 * Variable reflecting the maximal horizontal velocity of a character.
	 */
	private double maxHorizontalVelocity;
	
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
		
	// change name to "computeHorizontalVelocity" and check documentation! (needs to be total programming!)
	// startMove is just a method that changes the boolean variable isMovingRight or isMovingLeft (maybe more), SAME FOR endMove!
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
	 * 			|	then new.isAccelerating == true
	 * 			| Else
	 * 			|	new.isAccelerating == false
	 */
	public void computeHorizontalVelocityAfter(double duration) {
		double newVelocity;
		if (isMovingLeft() || isMovingRight()){
			int directionModifier = 1;
			if (isMovingLeft()){
				directionModifier  = -1;
			}
			if (! Util.fuzzyGreaterThanOrEqualTo(Math.abs(getHorizontalVelocity()), getInitHorizontalVelocity())){
				newVelocity = getInitHorizontalVelocity() + duration*getHorizontalAcceleration();
				newVelocity = Math.min(newVelocity,getMaxHorizontalVelocity());
				setHorizontalVelocity(directionModifier*newVelocity);
			}
			else {
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(newVelocity,getMaxHorizontalVelocity());
			setHorizontalVelocity(directionModifier*newVelocity);
			}
		}
		else {
			newVelocity = 0.0;
			setHorizontalVelocity(newVelocity);
		}
		if ((! Util.fuzzyEquals(newVelocity, 0.0)) && (newVelocity < getMaxHorizontalVelocity())){
			this.isAccelerating = true;
		}
		else{ this.isAccelerating = false;
		}
	}
	
	/**
	 * value stating whether the character is accelerating
	 */
	private boolean isAccelerating;
	
	/**
	 * Set the horizontal velocity to the given velocity.
	 * @post The new horizontal velocity is the given velocity.
	 * 			| new.getHorizontalVelocity == velocity
	 */
	public void setHorizontalVelocity(double velocity) {
		this.currHorizontalVelocity = velocity;
	}
	
	/**
	 * A variable reflecting the current horizontal velocity.
	 */
	private double currHorizontalVelocity;
	
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
			this.isMovingLeft = true;
		}
		else {
			this.isMovingRight = true;
		}
	}
	
	/**
	 * Stop moving the character in the given direction
	 * @param direction
	 * 			The direction to stop moving in.
	 * @pre The direction must be left or right
	 * 		| (direction == "left") || (direction == "right")
	 * @post The character stops in the given direction.
	 * 			| if (direction == "left")
	 * 			|	then new.isMovingLeft() == false
	 * 			| else
	 * 			|	new.isMovingRight() == false
	 */
	public void endMove (String direction) {
		if (direction == "left") {
			this.isMovingLeft = false;
		}
		else {
			this.isMovingRight = false;
		}
	}
	
	/**
	 * Determines whether the character is moving left
	 */
	@Basic
	public boolean isMovingLeft() {
		return this.isMovingLeft;
	}
	
	/**
	 * Determines whether the character is moving right
	 */
	@Basic
	public boolean isMovingRight() {
		return this.isMovingRight;
	}
	
	/**
	 * A value stating whether the character is moving left.
	 */
	private boolean isMovingLeft;
	
	/**
	 * A value stating whether the character is moving right.
	 */
	private boolean isMovingRight;
	
	/**
	 * A method to calculate the new position and horizontal velocity after a given duration of time.
	 * @param duration
	 * 			The duration of time that passes.
	 * @effect computes the new horizontal position after the given duration.
	 * 			| computeHorizontalPositionAfter(duration)
	 * @effect computes the new horizontal velocity after the given duration.
	 * 			| computeHorizontalVelocityAfter(duration)
	 * @throws IllegalArgumentException
	 * 			The given duration is an invalid duration of time.
	 * 			| ((duration < 0.0) || (duration >= 0.2))
	 */
	public void AdvanceTime (double duration) throws IllegalArgumentException {
		if ((! Util.fuzzyGreaterThanOrEqualTo(duration, 0.0)) || (Util.fuzzyGreaterThanOrEqualTo(duration, 0.2))){
			throw new IllegalArgumentException();
		}
		this.computeHorizontalPositionAfter(duration);
		this.computeHorizontalVelocityAfter(duration);
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
	public void computeHorizontalPositionAfter(double duration) {
		double newPosition;
		newPosition = this.getPositionAt(1) + duration*this.getHorizontalVelocity();
		if (this.isAccelerating()){
			newPosition += 0.5*getHorizontalAcceleration()*duration*duration;
		}
		if (isValidPositionAt(newPosition,1)){
			this.setPositionAt(newPosition, 1);
		}
		else if ((int)newPosition < X_MIN){
			this.setPositionAt((double)X_MIN, 1);
		}
		else {
			this.setPositionAt((double)X_MAX, 1);
		}
		
	}

	// vanaf hier zijn het nieuwe functies
	
	
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
	public void setVerticalVelocity(Double velocity) {
		this.currVerticalVelocity = velocity;
	}
	
	
	/**
	 * A variable reflecting the current vertical velocity.
	 */
	private Double currVerticalVelocity;
	
	/**
	 * Return the speed with wich the character starts jumping
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
		return this.VERTICAL_ACCELERATION;
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
	private boolean isJumping;
	
	
	/**
	 * Compute the new vertical speedposition after a given duration.
	 * @param duration
	 * 			The duration after after which to calculate the new vertical position.
	 * @post    if the character is in the air and and loc_Y + v_Y*dt + 0.5*a_Y*dt*dt is 
	 * 			a posible height then that is the new height. if it's to high the new
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
	 *			|	if isJumping
	 *			|		then new.verticalPosition = loc_Y + v_Y*dt
	 */
	public void computeNewVerticalPosition(double duration){
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + duration*this.getVerticalVelocity() + 0.5*getVerticalAcceleration()*duration*duration;
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
			if (isJumping == true){
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
	 * 			|	if not isJumping && getVertivalVelocity > 0
	 * 			|		then new.verticalVelocity = 0
	 * 			|	else new.verticalVelocity = getVerticalVelocity()+getVerticalAcceleration()*duration
	 * 			| else 
	 * 			|	if isJumping
	 * 			|		then new.verticalVelocity = INIT_VERTICAL_VELOCITY
	 * 			|	else new.verticalVelocity = 0
	 */
	public void computeNewVerticalSpeed(double duration){
		if (isInAir() == true){
			if ((isJumping ==false) && (! Util.fuzzyGreaterThanOrEqualTo(0, getVerticalVelocity()))){
				setVerticalVelocity(0.0);
			}
			else{
				setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration);
			}
		}
		else{
			if (isJumping == true){
				setVerticalVelocity(INIT_VERTICAL_VELOCITY);
			}
			else{
				setVerticalVelocity(0.0);
			}
		}
	}
	
}