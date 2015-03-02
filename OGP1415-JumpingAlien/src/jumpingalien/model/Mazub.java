package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;


/**
 * @author Peter Lacko, Sander Switsers
 *
 */
public class Mazub {
	
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
	 * Return the coordinate ascribed to this character at the given index.
	 * @param index
	 * 			The index of coordinate to return (X-coordinate is at the first index, Y-coordinate is at 
	 * 			the second index)
	 * @throws ArrayIndexOutOfBoundsException
	 * 			The given index must be positive and may not be bigger than 2.
	 * 			| (index <= 0) || (index > 2)
	 */
	@Basic
	public int getPositionAt(int index) throws ArrayIndexOutOfBoundsException{
		return Position[index-1];
	}
	
	/**
	 * Check whether the character can have the given coordinate at the given index.
	 * @param coordinate
	 * 			The coordinate to check
	 * @param index
	 * 			The index for the coordinate to check
	 * @return True if it is an X-coordinate in the correct range or a Y-coordinate in the correct range
	 * 			(between X_MIN, X_MAX or Y_MIN, Y_MAX respectively)
	 * 			| if ((index == 1) && (coordinate >= X_MIN) && (coordinate <= X_MAX))
	 * 			|	then result == true
	 * 			| else if ((index ==2) && (coordinate >= Y_MIN) && (coordinate <= Y_MAX))
	 * 			|	then result == true
	 * 			| else result == false
	 */
	public static boolean isValidPositionAt (int coordinate, int index) {
		if ((index == 1) && (coordinate >= X_MIN) && (coordinate <= X_MAX))
			return true;
		if ((index ==2) && (coordinate >= Y_MIN) && (coordinate <= Y_MAX))
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
	public void setPositionAt(int coordinate, int index) throws ArrayIndexOutOfBoundsException, 
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
	public int[] getPosition() {
		return this.Position.clone();
	}
		
	/**
	 * Variable registering the position of the bottom left corner of the Mazub Sprite.
	 */
	private int[] Position = new int[2];
	
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
	public Double getHorizontalVelocity() {
		return this.currHorizontalVelocity;
	}
	
	public Double getHorizontalAcceleration() {
		return this.HORIZONTAL_ACCELERATION;
	}
	
	public Double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	public Double getInitHorizontalVelocity() {
		return this.INIT_HORIZONTAL_VELOCITY;
	}
	
	//moeten deze public of private zijn?
	/**
	 * Constant reflecting the initial velocity of a character.
	 * @return The initial velocity for a character is 1 m/s.
	 * 			| return == 1.0
	 */
	private final Double INIT_HORIZONTAL_VELOCITY = 1.0;
	
	/**
	 * Variable reflecting the maximal horizontal velocity of a character.
	 */
	private Double maxHorizontalVelocity;
	
	/**
	 * Constant reflecting the horizontal acceleration of a character.
	 * @return The horizontal acceleration for all characters is 0.9m/s�
	 * 			| return == 0.9
	 */
	private static final Double HORIZONTAL_ACCELERATION = 0.9;
	
	/**
	 * method that returns the value of the variable isAccelerating
	 */
	@Basic
	public boolean isAccelerating(){
		return this.isAccelerating;
	}
	
	
	/**
	 * value stating whether the character is accelerating
	 */
	private boolean isAccelerating;
	
	
	/**
	 * Initiate or speed up movement in a given direction.
	 * @pre The given direction must be left or right.
	 * 		| (direction == "left") || (direction == "right")
	 * @pre The given duration must be larger than zero.
	 * 		| duration > 0
	 * @param direction
	 * 			The direction of movement
	 * @post	If the absolute value of the current speed is lower than the initial speed, the new speed is calculated as the smallest value between 
	 * 				1) the initial horizontal velocity plus the duration times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			Otherwise, the new speed is calculated as the smallest value between 
	 * 				1) the current horizontal velocity plus the duration times horizontal acceleration. 
	 * 				or 2) the maximal horizontal velocity.
	 * 			This velocity is then multiplied by -1 if the given direction is left.
	 * 			| If (direction == "left")
	 * 			|	then directionModifier == -1
	 * 			| Else directionModifier == 1
	 * 			| If (Math.abs(getHorizontalVelocity()) < getInitHorizontalVelocity())
	 * 			|	then (new.getHorizontalVelocity == 
	 * 			|	directionModifier*Math.min(getInitHorizontalVelocity()+duration*getHorizontalAcceleration(),getMaxHorizontalVelocity()))
	 * 			| If (Math.abs(getHorizontalVelocity()) >= getInitHorizontalVelocity())
	 * 			|	then (new.getHorizontalVelocity == 
	 * 			|	directionModifier*Math.min(getHorizontalVelocity()+duration*getHorizontalAcceleration(),getMaxHorizontalVelocity()))
	 * @post	if the character's horizontal velocity is between 0 and the maximum horizontal velocity then the character is accelerating
	 * 				otherwise he is not.
	 * 			| If ((new.getHorizontalVelocity != 0) && (new.getHorizontalVelocity < getMaxHorizontalVelocity())
	 * 			|	then new.isAccelerating == true
	 * 			| Else
	 * 			|	new.isAccelerating == false
	 */
	public void startMove(String direction, Double duration) {
		int directionModifier = 1;
		if (direction == "left")
			directionModifier = -1;
		Double newVelocity;
		if (Math.abs(getHorizontalVelocity()) < getInitHorizontalVelocity()){
			newVelocity = getInitHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(newVelocity,getMaxHorizontalVelocity());
			setHorizontalVelocity(directionModifier*newVelocity);
		}
		else {
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(newVelocity,getMaxHorizontalVelocity());
			setHorizontalVelocity(directionModifier*newVelocity);
		}
		if ((newVelocity != 0) && (newVelocity < getMaxHorizontalVelocity())){
			this.isAccelerating = true;
		}
		else{ this.isAccelerating = false;
		}
	}
	
	/**
	 * Set the horizontal velocity to the given velocity.
	 * @post The new horizontal velocity is the given velocity.
	 * 			| new.getHorizontalVelocity == velocity
	 */
	public void setHorizontalVelocity(Double velocity) {
		this.currHorizontalVelocity = velocity;
	}
	
	/**
	 * Terminates the horizontal movement of the character
	 * @post	the new horizontal velocity of the character is zero
	 * 			| new.getHorizontalVelocity() == 0.0
	 */
	public void endMove(){
		setHorizontalVelocity(0.0);
	}
	
	/**
	 * A variable reflecting the current horizontal velocity.
	 */
	private Double currHorizontalVelocity;
	
	

}
