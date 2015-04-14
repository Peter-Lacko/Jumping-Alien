package jumpingalien.model;

import jumpingalien.util.Sprite;


public class Slime extends OtherCharacters {

	public Slime(int x_pos, int y_pos, Sprite[] sprites, School school)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.7, 2.5, 0.0, 0.0,100);
		startMove();
		double[] durationrange = new double[2];
		durationrange[0]=2;
		durationrange[1]=6;
		setDurationrange(durationrange);
		this.setSchool(school);
	}
	
	public double[] getDurationrange() {
		return durationrange;
	}

	public void setDurationrange(double[] durationrange) {
		this.durationrange = durationrange;
	}
	
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	public double[] durationrange;
	
	public School school;

	@Override
	protected void computeNewHorizontalPositionAfter(double duration) {
		double newPosition;
		if (isMovingLeft() || isMovingRight())
			newPosition = this.getPositionAt(1) + 100*duration*this.getHorizontalVelocity() + 100*0.5*getHorizontalAcceleration()*duration*duration;
		else
			newPosition = this.getPositionAt(1);
		if (isValidPositionAt(newPosition,1))
			this.setPositionAt(newPosition, 1);
	}

	@Override
	protected void computeNewHorizontalVelocityAfter(double duration) {
		double newVelocity;
		if (isMovingLeft() || isMovingRight()){
			newVelocity = getHorizontalVelocity() + duration*getHorizontalAcceleration();
			newVelocity = Math.min(Math.abs(newVelocity),getMaxHorizontalVelocity());
		}
	}

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
		startMove();
	}

	@Override
	protected void computeNewVerticalPositionAfter(double duration) {
		double newYPosition;
		if (isInAir()){
			newYPosition = this.getPositionAt(2) + 100*duration*this.getVerticalVelocity() + 100*0.5*getVerticalAcceleration()*duration*duration;
			if (isValidPositionAt(newYPosition,2)){
				this.setPositionAt(newYPosition, 2);
			}
		}
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
		for (int i = getIntPositionAt(1);i<=getIntPositionAt(1+getSprite().getWidth());i++){
			GeoFeature geo = getWorld().getGeoFeatureAt(i, getIntPositionAt(2));
			if (geo == GeoFeature.GROUND)
				return false;
		}
		return true;
	}

	@Override
	public boolean canHaveAsWorld(World world) {
		if (world.isTerminated())
			return false;
		return true;
	}
	
	@Override
	public void collision(Characters other) {
		if (other instanceof Mazub){
			if (! ((Mazub)other).isImmune())
				this.damage(50);
				other.damage(50);
				((Mazub)other).startImmune();
			this.endMove();
			((Mazub) other).endMove("left");
			((Mazub) other).endMove("right");
		}
		else if (other instanceof Shark){
			this.damage(50);
			other.damage(50);
			this.endMove();
			((Shark) other).endMove();
		}
		else if (other instanceof Slime){
			this.getSchool().changeSchool(this,(Slime)other);
		}
		else
			other.collision(this);
	}
}
