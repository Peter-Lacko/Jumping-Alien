package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;

public interface OtherCharacters {

	public default boolean isValidNbDurationRange(int number){
		return (number == 2);
	}

	public default int getNbDurationRange(){
		if (getDurationRange() != null)
			return getDurationRange().length;
		return 0;
	}

	public default boolean canHaveAsDurationRangeValue(double value){
		return(Util.fuzzyGreaterThanOrEqualTo(value, 0.0));
	}

	public default boolean canHaveAsDurationRangeValueAt(double value, int index){
		if (! canHaveAsDurationRangeValue(value))
			return false;
		else{
			if (index == 1){
				if (Util.fuzzyGreaterThanOrEqualTo(getDurationRangeValueAt(2), value))
					return true;
				else 
					return false;
			}
			else if (index == 2){
				if (Util.fuzzyGreaterThanOrEqualTo(value, getDurationRangeValueAt(1)))
					return true;
				else 
					return false;
			}
			else
				return false;
		}
	}

	public default boolean hasProperDurationRange(){
		if (! isValidNbDurationRange(getNbDurationRange()))
			return false;
		else{
			for(int i=1; i<=getNbDurationRange(); i++)
				if(! canHaveAsDurationRangeValueAt(getDurationRangeValueAt(i),i))
					return false;
			return true;
		}
	}
	
	public abstract double getDurationRangeValueAt(int index);
	
	/**
	 * A getter method for the variable durationRange
	 */
	@Basic
	public abstract double[] getDurationRange();

	public default void selectMovements(){
		setTimeSinceStartMovement(getTimeSinceStartMovement() - getMovementDuration());
		if (isMovingRight())
			endMove("right");
		else if (isMovingLeft())
			endMove("left");
		setMovementDuration(randomValue(getDurationRange()));
		if (getRandomBoolean())
			startMove("left");
		else
			startMove("right");
	}

	public abstract boolean isMovingRight();

	public abstract boolean isMovingLeft();

	public abstract void startMove(String Direction);

	public abstract void endMove(String Direction);

	/**
	 * 
	 * @return	...
	 * 			| Random random = new Random()
	 * 			| return random.nextBoolean()
	 */
	public default boolean getRandomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

	public default boolean canHaveAsTimeSinceStartMovement(double time){
		return (isPossibleTimeSinceStartMovement(time) && matchesMovementDurationTimeSinceStartMovement(
				getMovementDuration(), time));
	}

	public default boolean isPossibleTimeSinceStartMovement(double time){
		return(Util.fuzzyGreaterThanOrEqualTo(time, 0.0));
	}

	/**
	 * A getter method for the variable timeSinceStartMovement
	 */
	@Basic
	public abstract double getTimeSinceStartMovement();

	/**
	 * A setter method for the variable timeSinceStartMovement
	 */
	@Basic
	public abstract void setTimeSinceStartMovement(double time);

	public default boolean canHaveAsMovementDuration(double duration){
		return (isPossibleMovementDuration(duration) && matchesMovementDurationDurationRange(
				duration, getDurationRange()) && matchesMovementDurationTimeSinceStartMovement(
						duration, getTimeSinceStartMovement()));
	}

	public default boolean isPossibleMovementDuration(double duration){
		return (Util.fuzzyGreaterThanOrEqualTo(duration, 0.0));
	}

	public default boolean matchesMovementDurationDurationRange(double movementDuration, 
			double[] durationRange){
		return (Util.fuzzyGreaterThanOrEqualTo(durationRange[1], movementDuration)
				&& Util.fuzzyGreaterThanOrEqualTo(movementDuration, durationRange[1]));
	}

	public default boolean matchesMovementDurationTimeSinceStartMovement(double movementDuration,
			double timeSinceStartMovement){
		return (Util.fuzzyGreaterThanOrEqualTo(movementDuration, timeSinceStartMovement));
	}

	/**
	 * A getter method for the variable movementDuration
	 */
	@Basic
	public abstract double getMovementDuration();

	/**
	 * A setter method for the variable movementDuration
	 */
	@Basic
	public abstract void setMovementDuration(double duration);

	/**
	 * 
	 * @param range
	 * @return	...
	 * 			| Random t = new Random()
	 * 			| return range[0] + (range[1]-range[0])*r.nextDouble()
	 */
	public default double randomValue(double[] range){
		Random r = new Random();
		double value = range[0] + (range[1]-range[0])*r.nextDouble();
		return value;
	}

	public default boolean isValidTerminateTime(double time){
		return (Util.fuzzyGreaterThanOrEqualTo(time, 0.0));
	}

	/**
	 * A getter method for the variable terminate time
	 */
	@Basic
	public abstract double getTerminateTime();

	/**
	 * A setter method for the variable terminate time
	 */
	@Basic
	public abstract void setTerminateTime(double time);

}
