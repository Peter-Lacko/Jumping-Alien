package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Buzam extends Aliens implements OtherCharacters {

	public Buzam(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.9, 3.0, 1.0, 8.0,500);
		durationRange = new double[] {0.0,0.0};
	}

	/**
	 * A getter method for the variable durationRange
	 */
	@Basic @Override
	public double[] getDurationRange() {
		return durationRange.clone();
	}

	/**
	 * A variable containing the range of the movement durations of the characters
	 */
	public final double[] durationRange;

	@Override
	public double getTimeSinceStartMovement() {
		return timeSinceStartMovement;
	}

	@Override
	public void setTimeSinceStartMovement(double time) {
		timeSinceStartMovement = time;
	}

	/**
	 * A double containing the time since the start of a movement
	 */
	private double timeSinceStartMovement = 0.0;

	@Override
	public double getMovementDuration() {
		return movementDuration;
	}

	@Override
	public void setMovementDuration(double duration) {
		movementDuration = duration;
	}

	/**
	 * A double containing the movement duration
	 */
	private double movementDuration;

	@Override
	public double getTerminateTime() {
		return terminateTime;
	}

	@Override
	public void setTerminateTime(double time) {
		terminateTime = time;
	}	

	/**
	 * A double containing the terminate time
	 */
	private double terminateTime = 0.0;

	@Override
	public boolean collide(Characters other) {
		if (other instanceof Mazub)
			return true;
		else if (other instanceof Shark)
			return true;
		else if (other instanceof Slime)
			return true;
		else if (other instanceof Buzam)
			return true;
		else if (other instanceof Plant)
			return false;
		else
			return other.collide(this);
	}

	/**
	 * A method to determine what to do when colliding with a character.
	 * @effect	the other character is colliding with this character
	 * 			|other.collision(this, isOnTop)
	 */
	@Override
	public void collision(Characters other){
		if (! isImmune()){
			if (other instanceof Mazub){
				if (! ((Mazub)other).isImmune()){
					other.damage(50);
					((Mazub)other).startImmune();
					this.damage(50);				
				}
			}
			else if (other instanceof Shark){
				other.damage(50);
				this.damage(50);
			}
			else if (other instanceof Slime){
				this.damage(50);
				((Slime)other).slimeDamage(50);
			}
			startImmune();
		}
		else if (other instanceof Plant){
			if (! other.isTerminated())
				eat(((Plant)other));
		}
		else if (other instanceof Buzam){}
		else
			other.collision(this);
	}

	@Override
	public void collisionNoDamageFrom(Characters other){
		if (! isImmune()){
			if (other instanceof Mazub){
				if (! ((Mazub)other).isImmune()){
					other.damage(50);
					((Mazub)other).startImmune();				
				}
			}
			else if (other instanceof Shark){
				other.damage(50);
			}
			else if (other instanceof Slime){
				((Slime)other).slimeDamage(50);
			}
		}
		else if (other instanceof Plant){
			collision(other);
		}
		else if (other instanceof Buzam){}
		else
			other.collisionNoDamageTo(this);
	}

	@Override
	public void collisionNoDamageTo(Characters other){
		if (! isImmune()){
			if (other instanceof Mazub){
				if (! ((Mazub)other).isImmune()){
					this.damage(50);				
				}
			}
			else if (other instanceof Shark){
				collision(other);
			}
			else if (other instanceof Slime){
				collision(other);
			}
			startImmune();
		}
		else if (other instanceof Plant){
			collision(other);
		}
		else if (other instanceof Buzam){}
		else
			other.collisionNoDamageFrom(this);
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
		super.advanceTimeLong(duration);
		if (! isTerminated()){
			if (getTimeSinceStartMovement() < getMovementDuration()){
				setTimeSinceStartMovement(getTimeSinceStartMovement() + duration);
			}
			else{
				selectMovements();
			}
			this.determineDirection();
			if (isEndDuck())
				if (canEndDuck() && canEndDuckWithoutMove())
					finishDuck();
			if (isMovingLeft() || isMovingRight())
				setTimeSinceStep(getTimeSinceStep() + duration);
			else
				setTimeSinceEndMove(getTimeSinceEndMove() + duration);
			if (this.getTimeSinceEndMove() > 1.0)
				this.sethasMovedIn(MovementDirection.NONE);
			this.immune(duration);
		}
		else
			getWorld().removeAsObject(this);
	}

	@Override
	public double getDurationRangeValueAt(int index) {
		return 0.0;
	}
	
}
