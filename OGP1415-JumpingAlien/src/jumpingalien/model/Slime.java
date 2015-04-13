package jumpingalien.model;

import jumpingalien.util.Sprite;


public class Slime extends OtherCharacters {

	public Slime(int x_pos, int y_pos, Sprite[] sprites)
			throws IllegalArgumentException {
		super(x_pos, y_pos, sprites, 0.7, 2.5, 0.0, 0.0);
		startMove();
		double[] durationrange = new double[2];
		durationrange[0]=2;
		durationrange[1]=6;
		setDurationrange(durationrange);
	}
	
	public double[] getDurationrange() {
		return durationrange;
	}

	public void setDurationrange(double[] durationrange) {
		this.durationrange = durationrange;
	}
	
	public double[] durationrange;

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

}
