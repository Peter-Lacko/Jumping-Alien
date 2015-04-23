package jumpingalien.model;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;


public class Slime extends OtherCharacters {

	public Slime(int x_pos, int y_pos, Sprite[] sprites, School school)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.7, 2.5, 0.0, 0.0,100);
		this.setSchool(school);
	}
	
	public double[] getDurationrange() {
		return durationrange;
	}

	public void setDurationrange(double[] durationrange) {
		this.durationrange = durationrange;
	}
	
	public double[] durationrange = {2.0 , 6.0};
	
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		if (school == null)
			this.school = school;
		else{
			school.addAsSlime(this);
			this.school = school;
		}
	}
	
	public School school;

//	@Override
//	protected void computeNewHorizontalPositionAfter(double duration) {
//		double newPosition;
//		if (isMovingLeft() || isMovingRight())
//			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity() + 100*0.5*getHorizontalAcceleration()*duration*duration;
//		else
//			newPosition = this.getPositionAt(1);
//		if (canHaveAsNewPosition(newPosition,1))
//			this.setPositionAt(newPosition, 1);
//	}
//
//	@Override
//	protected void computeNewHorizontalVelocityAfter(double duration) {
//		double newVelocity;
//		if (isMovingLeft() || isMovingRight()){
//			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
//			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
//			if (isMovingLeft())
//				newVelocity = -1.0*newVelocity;
//			this.setHorizontalVelocity(newVelocity);
//		}
//	}

	@Override
	public void startMove() {
		setMovementDuration(randomDuration(getDurationrange()));
		if (getRandomBoolean())
			setMovingLeft(true);
		else
			setMovingRight(true);
	}

	@Override
	public void endMove() {
		setTimeSinceStartMovement(0.0);
		setMovingRight(false);
		setMovingLeft(false);
	}

	@Override
	protected void computeNewVerticalPositionAfter(double duration) {
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 100*0.5*getVerticalAcceleration()*duration*duration;
			if (canHaveAsNewPosition(newYPosition,2)){
				this.setPositionAt(newYPosition, 2);
			}
		}
	}
	
	@Override
	public double calculateNewVerticalPositionAfter(double duration){
		if (! isTerminated()){
			double newYPosition = 0.0;;
			if (isInAir()){
				newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 
					100*0.5*getVerticalAcceleration()*duration*duration;
				return newYPosition;
			}
			else
				return this.getPositionAt(2);
		}
		else
			return 0.0;
	}

	@Override
	public void computeNewVerticalVelocityAfter(double duration){
		if (isInAir())
			setVerticalVelocity(getVerticalVelocity()+getVerticalAcceleration()*duration);		
	}

	@Override
	public void startJump() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInAir(){
		if (! isTerminated()){
			for (int i = getIntPositionAt(1);i<getIntPositionAt(1)+getSprite().getWidth()-1;i++){
				int[] pos = getWorld().getPixelOfTileContaining(i, getIntPositionAt(2));
				GeoFeature geo = getWorld().getGeoFeatureAt(pos[0],pos[1]);
				if (geo == GeoFeature.GROUND)
					return false;
			}
		}
		return true;
	}

//	@Override
//	public void collision(Characters other) {
//		if (other instanceof Mazub){
//			if (! ((Mazub)other).isImmune()){
//				this.damage(50);
//				other.damage(50);
//				((Mazub)other).startImmune();
//			}
//			this.endMove();
////			((Mazub) other).endMove("left");
////			((Mazub) other).endMove("right");
//		}
//		else if (other instanceof Shark){
//			this.damage(50);
//			other.damage(50);
//			this.endMove();
//			((Shark) other).endMove();
//		}
//		else if (other instanceof Slime){
//			this.changeSchool((Slime)other);
//		}
//		else
//			other.collision(this);
//	}
	
	@Override
	public void collision(Characters other, boolean isBelow) {
		if (other instanceof Mazub){
			if (! ((Mazub)other).isImmune()){
				if (! isBelow){
					other.damage(50);
					((Mazub)other).startImmune();
				}
				this.damage(50);
			}
			this.endMove();
//			((Mazub) other).endMove("left");
//			((Mazub) other).endMove("right");
		}
		else if (other instanceof Shark){
			this.damage(50);
			other.damage(50);
			this.endMove();
			((Shark) other).endMove();
		}
		else if (other instanceof Slime){
			this.changeSchool((Slime)other);
		}
		else
			other.collision(this, isBelow);
	}
	
	private void changeSchool(Slime other){
		if (this.getSchool().getNbSlimes() < other.getSchool().getNbSlimes()){
			this.getSchool().removeAsSlime(this);
			for (Characters slime : this.getSchool().getSlimes())
				slime.damage(-1);
			for (Characters slime2 : other.getSchool().getSlimes())
				slime2.damage(1);
			this.damage(this.getSchool().getNbSlimes() - other.getSchool().getNbSlimes());
			this.setSchool(other.getSchool());
		}
		else if (this.getSchool().getNbSlimes() > other.getSchool().getNbSlimes()){
			other.getSchool().removeAsSlime(other);
			for (Characters slime : other.getSchool().getSlimes())
				slime.damage(-1);
			for (Characters slime2 : this.getSchool().getSlimes())
				slime2.damage(1);
			this.damage(other.getSchool().getNbSlimes() - this.getSchool().getNbSlimes());
			other.setSchool(school);
		}
	}
	
	@Override
	protected void terminate() {
		if (! isTerminated()){
			getWorld().removeAsObject(this);
			this.setWorld(null);
			getSchool().removeAsSlime(this);
			this.setSchool(null);
			this.setTerminated(true);
		}
	}
	
//	@Override
//	public boolean collisionDetectionHorizontal(double newPosition){
////		List<Characters> characters = world.getAllObjects();
//		World world = getWorld();
//		Iterable<Characters> characters;
//		if (world.hasAsLeftObject(this) && world.hasAsRightObject(this))
//			characters = world.getAllObjects();
//		else if (world.hasAsLeftObject(this))
//			characters = world.getAllLeftObjects();
//		else
//			characters = world.getAllRightObjects();
//		if (isMovingRight()){
//			for (Characters character : characters){
//				if ((character.getIntPositionAt(1) == (int)newPosition + this.getSprite().getWidth()) 
//						&& (character.getPositionAt(2) > (this.getPositionAt(2) - character.getSprite().getHeight())) 
//						&& (character.getPositionAt(2) < this.getPositionAt(2) + this.getSprite().getHeight())){
//						this.collision(character);
//						if (character instanceof Slime)
//							return true;
//						else
//							return false;
//				}
//			}
//		}
//		if (isMovingLeft()){
//			for (Characters character : characters){
//				if ((character.getIntPositionAt(1) + character.getSprite().getWidth() == (int)newPosition) 
//						&& (character.getPositionAt(2) > (this.getPositionAt(2) - character.getSprite().getHeight())) 
//						&& (character.getPositionAt(2) < this.getPositionAt(2) + this.getSprite().getHeight())){
//						this.collision(character);
//						if (character instanceof Slime)
//							return true;
//						else
//							return false;
//				}
//			}
//		}
//		return true;
//	}
	
	@Override
	public void environmentDamage(double duration) {
		int[] pos = {getIntPositionAt(1),getIntPositionAt(2) + getSprite().getHeight()};
		int[] pos1 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)};
		int[] pos2 = {getIntPositionAt(1) + getSprite().getWidth(),getIntPositionAt(2)+getSprite().getHeight()};
		if ((environment(getIntPosition()) == GeoFeature.MAGMA) || (environment(pos) == GeoFeature.MAGMA)
				|| (environment(pos1) == GeoFeature.MAGMA) || (environment(pos2) == GeoFeature.MAGMA)){
			this.setBadEnvironment(true);
			if (Util.fuzzyEquals(getTimeSinceEnvironmentalDamage(), 0.0))
				this.damage(50);
		} else{
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
	
	public boolean isBadEnvironment() {
		return badEnvironment;
	}

	public void setBadEnvironment(boolean badEnvironment) {
		this.badEnvironment = badEnvironment;
	}

	public boolean badEnvironment = false;
}
