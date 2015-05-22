package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.model.program.Program;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;


public class Slime extends Characters implements OtherCharacters {

	/**
	 * initialize a slime with a given x_pos, y_pos, and sprites.
	 * @param x_pos
	 * 			the initial x-coordinate for this character.
	 * @param y_pos
	 * 			the initial y-coordinate for this character.
	 * @param sprites
	 * 			the array of sprites used to display the character.
	 * @param school
	 * 			the school to set
	 * @post	This character starts at the given position.
	 * 			| new.getPosition().equals({(double) x_pos, (double) y_pos})
	 * @post	This character's sprite set is equal to the given one.
	 * 			| new.getImages().equals(sprites)
	 * @post	this character's image length is equal to the length of the given sprite array
	 * 			|new.getNbImages() == sprites.length
	 * @post	this character's horizontal acceleration is set to 0.7
	 * 			| new.getAbsHorizontalAcceleration() == 0.7
	 * @post	this character's max horizontal velocity is set to 2.5
	 * 			| new.getMaxHorizontalVelocity == 2.5
	 * @post	this character's initial horizontal velocity is set to 0
	 * 			| Math.abs(new.getInitHorizontalVelocity()) == 0
	 * @post	this character's initial vertical velocity is set to 0
	 *			| new.getInitVerticalVelocity() == 0.0
	 * @post	this character's displayed sprite is the first sprite in the given array of sprites
	 * 			| new.getSprite() == sprites[0]
	 * @post	this character's hit points is set to 100
	 * 			|new.getHitPoints() == 100
	 * @post	|new.getDurationRange() == {2.0, 6.0}
	 * @post	|new.getSchool() == school
	 * @throws IllegalArgumentException
	 * 			the amount of images provided is invalid
	 * 			|! isValidNbImages(sprites.length)
	 */
	@Raw
	public Slime(int x_pos, int y_pos, Sprite[] sprites, School school, Program behavior)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.7, 2.5, 0.0, 0.0,100, behavior);
		this.setSchool(school);
		durationRange = new double[] {2.0, 6.0};
	}
	
	/**
	 * 
	 * @param x_pos
	 * @param y_pos
	 * @param sprites
	 * @param school
	 * @throws IllegalArgumentException
	 * @effect	|this(x_pos, y_pos, sprites, school, null)
	 */
	@Raw
	public Slime(int x_pos, int y_pos, Sprite[] sprites, School school)
			throws IllegalArgumentException {
		this(x_pos, y_pos, sprites, school, null);
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
	 * @effect	|if ! isTerminated(){
	 * @effect	|	if (getTimeSinceStartMovement() < getMovementDuration())
	 * 			|		then setTimeSinceStartMovement(getTimeSinceStartMovement() + duration)
	 * 			|	else selectMovements()
	 * 			|}
	 * @throws IllegalArgumentException
	 * 			the given duration is invalid (below 0 or above 0.2)
	 * 			|(duration < 0.0) || (duration >= 0.2)
	 */
	@Override
	public void advanceTime(double duration)throws IllegalArgumentException{
		if(getProgram() != null){
			for(double i=0.0; (! Util.fuzzyGreaterThanOrEqualTo(i, duration)); i+= 0.001){
				double newDuration = Math.min(0.001, duration-i);
				advanceTimeWithProgram();
				super.advanceTime(newDuration);
			}
		}
		else{
			if (! this.isTerminated()){
				if (getTimeSinceStartMovement() < getMovementDuration()){
					setTimeSinceStartMovement(getTimeSinceStartMovement() + duration);
				}
				else{
					selectMovements();
				}
			}
			super.advanceTime(duration);
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
	 * 			|if (getHitPoints() != 0){
	 * @post	a new horizontal position and velocity is set
	 * 			|new.getHorizontalPosition() ?= this.getHorizontalPosition()
	 * 			|new.getHorizontalVelocity() ?= this.getHorizontalVelocity()
	 * @post	a new vertical position and velocity is set
	 * 			|new.getVerticalPosition() ?= this.getVerticalPosition()
	 * 			|new.getVerticalVelocity() ?= this.getVerticalVelocity()
	 * @post	a new sprite is set
	 * @effect	the character suffers from possible environmental damage (and loses hit points)
	 * 			|this.environmentalDamage(duration)
	 * 			|}
	 * @effect	|if isTerminated()
	 * 			|	then {setTerminateTime(getTerminateTime()+duration)
	 * 			|		if ((this.getTerminateTime()+duration > 0.6) && (! (this.getWorld() == null)))
	 * 			|			then getWorld().removeAsObject(this)
	 */
	@Override
	protected void advanceTimeLong(double duration){
		super.advanceTimeLong(duration);
		if (isTerminated()){
			this.setTerminateTime(getTerminateTime()+duration);
			if ((this.getTerminateTime() > 0.6) && (! (this.getWorld() == null))){
				this.getWorld().removeAsObject(this);
				getSchool().removeAsSlime(this);
				this.setSchool(null);
			}
		}
	}
	
	/**
	 * A getter method for the variable durationRange
	 */
	@Basic @Override @Immutable
	public double[] getDurationRange() {
		return durationRange.clone();
	}

	/**
	 * A variable containing the range of the movement durations of the characters
	 */
	public final double[] durationRange;

	/**
	 * A getter method for the variable school
	 */
	@Basic
	public School getSchool() {
		return school;
	}

	/**
	 * A setter method for the variable school
	 * @param school
	 * @post	|new.getSchool() == school
	 * @effect	|if school != null
	 * 			|	then school.addAsSlime(this)
	 */
	protected void setSchool(School school) {
		if (school == null)
			this.school = school;
		else{
			school.addAsSlime(this);
			this.school = school;
		}
	}

	/**
	 * A variable containing the School of the slime
	 */
	private School school;

	/**
	 * Determine what happens to this and another colliding character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @effect	|if other instanceof Mazub && ! other.isImmune()
	 * 			|	then {this.damageSlime(50)
	 * 			|	other.damage(50)
	 * 			|	other.startImmune()}
	 * 			|else if other instanceof Shark{
	 * 			|	this.damageSlime(50)
	 * 			|	other.damage(50)}
	 * 			|else if other instanceof Slime
	 * 			|	then this.changeSchool(other)
	 * 			|else if other instanceof Plant{}
	 * 			|else other.collision(this)
	 */
	@Override
	public void collision(Characters other) {
		if (other instanceof Mazub){
			if (! ((Mazub)other).isImmune()){
				other.damage(50);
				((Mazub)other).startImmune();
				damageSlime(50);
			}
		}
		else if (other instanceof Shark){
			damageSlime(50);
			other.damage(50);
		}
		else if (other instanceof Slime){
			this.changeSchool((Slime)other);
		}
		else if (other instanceof Plant){}
		else
			other.collision(this);
	}

	/**
	 * Determine what happens to this and another colliding character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @effect	|if other instanceof Mazub || Shark || Slime || Plant
	 * 			|	then collision(other)
	 * 			|else other.collisionNoDamageTo(this)
	 */
	@Override
	public void collisionNoDamageFrom(Characters other){
		if ((other instanceof Mazub) || (other instanceof Shark) || (other instanceof Slime))
			collision(other);
		else if (other instanceof Plant){}
		else
			other.collisionNoDamageTo(this);
	}

	/**
	 * Determine what happens to this and another colliding character
	 * @param other
	 * 			the other character that is colliding with this character
	 * @effect	|if other instanceof Mazub && ! other.isImmune()
	 * 			|	then this.damageSlime(50)
	 * 			|else if other instanceof Shark || Slime || Plant
	 * 			|	then collision(other)
	 * 			|else other.collision(this)
	 */
	@Override
	public void collisionNoDamageTo(Characters other){
		if (other instanceof Mazub){
			if (! ((Mazub)other).isImmune()){
				this.damageSlime(50);
			}
		}
		else if ((other instanceof Shark) || (other instanceof Slime))
			collision(other);
		else if (other instanceof Plant){}
		else
			other.collisionNoDamageFrom(this);
	}

	/**
	 * @param other
	 * @post	|if (other instanceof Mazub)
	 *			|	then result == true;
	 *			|else if (other instanceof Shark)
	 *			|	then result == true;
	 *			|else if (other instanceof Slime)
	 *			|	then reult == false;
	 *			|else
	 *			|	the result == other.collide(this);
	 */
	@Override @Raw
	public boolean collide(Characters other){
		if (other instanceof Mazub)
			return true;
		else if (other instanceof Slime)
			return false;
		else if (other instanceof Shark)
			return true;
		else
			return other.collide(this);
	}

	/**
	 * 
	 * @param other
	 * 			the other slime with which the change school happens
	 * @effect	...
	 * 			| if this.getSchool().getNbSlimes < other.getSchool().getNbSlimes()
	 * 			|	then this.getSchool().removeAsSlime(this)
	 * 			|		for slime in this.getSchool().getSlimes()
	 * 			|			slime.damage(-1)
	 * 			|		for slime2 in other.getSchool().getSlimes()
	 * 			|			slime2.damage(1)
	 * 			|		this.damage(this.getSchool().getNbSlimes() - other.getSchool().getNbSlimes())
	 * 			|		this.setSchool(other.getSchool())
	 *  		| if this.getSchool().getNbSlimes > other.getSchool().getNbSlimes()
	 * 			|	then other.getSchool().removeAsSlime(other)
	 * 			|		for slime in other.getSchool().getSlimes()
	 * 			|			slime.damage(-1)
	 * 			|		for slime2 in this.getSchool().getSlimes()
	 * 			|			slime2.damage(1)
	 * 			|		other.damage(other.getSchool().getNbSlimes() - this.getSchool().getNbSlimes())
	 * 			|		other.setSchool(this.getSchool())
	 */
	private void changeSchool(Slime other){
		if (getSchool() != other.getSchool()){
			if (this.getSchool().getNbSlimes() < other.getSchool().getNbSlimes()){
				this.getSchool().removeAsSlime(this);
				getSchool().damageAllSlimesBut(this, -1);
				other.getSchool().damageAllSlimesBut(this, 1);
				this.damage(this.getSchool().getNbSlimes() - other.getSchool().getNbSlimes());
				this.setSchool(other.getSchool());
			}
			else if (this.getSchool().getNbSlimes() > other.getSchool().getNbSlimes()){
				other.getSchool().removeAsSlime(other);
				getSchool().damageAllSlimesBut(this, 1);
				other.getSchool().damageAllSlimesBut(this, -1);
				other.damage(other.getSchool().getNbSlimes() - this.getSchool().getNbSlimes());
				other.setSchool(getSchool());
			}
		}
	}

	/**
	 * 
	 * @param damage
	 * @effect	|if (! isTerminated())
	 * 			|	then{damage(damage)
	 * @effect	|		getSchool().damageAllSlimesBut(this, 1)}
	 */
	protected void damageSlime(int damage){
		if (! isTerminated()){
			damage(damage);
			getSchool().damageAllSlimesBut(this, 1);
		}
	}

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
	 * 			|						then this.damageSlime(50)
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
	 * 			|					then this.damageSlime(2)
	 * 			|				setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage()+duration)
	 * 			|				if (getTimeSinceEnvironmentalDamage() >= 0.2))
	 * 			|					then setTimeSinceEnvironmentalDamage(getTimeSinceEnvironmentalDamage() - 0.2)
	 * 			else mazub is not in a badEnvironment. The timer is set correctly.
	 * 			|		else {setBadEnvironment(false)
	 * 			|			setTimeSinceEnvironmentalDamage(0.0)}
	 * 			
	 */
	@Override
	protected void environmentDamage(double duration) {
		int[] pos = {getIntPositionAt(1),getIntPositionAt(2) + getSprite().getHeight()};
		int[] pos1 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)};
		int[] pos2 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)+getSprite().getHeight()};
		if ((environment(getIntPosition()) == GeoFeature.MAGMA) || (environment(pos) == GeoFeature.MAGMA)
				|| (environment(pos1) == GeoFeature.MAGMA) || (environment(pos2) == GeoFeature.MAGMA)){
			this.setBadEnvironment(true);
			if (Util.fuzzyEquals(getTimeSinceEnvironmentalDamage(), 0.0)){
				this.damageSlime(50);
			}
		} else if ((environment(getIntPosition()) == GeoFeature.WATER) || (environment(pos) == GeoFeature.WATER)
				|| (environment(pos1) == GeoFeature.WATER) || (environment(pos2) == GeoFeature.WATER)){
			this.setBadEnvironment(true);
			if (Util.fuzzyGreaterThanOrEqualTo(getTimeSinceEnvironmentalDamage()+duration, 0.2)){
				this.damageSlime(2);
			}
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
	public void setMovementDuration(double duration) throws IllegalArgumentException{
		if (! canHaveAsMovementDuration(duration))
			throw new IllegalArgumentException();
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
	public double getDurationRangeValueAt(int index) {
		return getDurationRange()[index-1];
	}
}
