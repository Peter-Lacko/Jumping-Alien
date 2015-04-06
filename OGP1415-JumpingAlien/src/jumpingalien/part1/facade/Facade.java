package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import jumpingalien.util.ModelException;

public class Facade implements IFacade{
	
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		try{
			return new Mazub(pixelLeftX,pixelBottomY,sprites, 0.9, 3.0, 1.0, 8.0);
		} catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");
		}

	}
	
	public int[] getLocation(Mazub mazub){
		try {
			return mazub.getIntPosition();
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			throw new ModelException("position error");
		}
	}
	
	public double[] getVelocity(Mazub mazub){
	double[] vel = {mazub.getHorizontalVelocity(), mazub.getVerticalVelocity()};
	return vel;
	}
	
	public double[] getAcceleration(Mazub mazub){
		double[] acc = {mazub.getHorizontalAcceleration(), mazub.getVerticalAcceleration()};
		return acc;
	}
	
	public int[] getSize(Mazub mazub){
		return mazub.getSize();
	} 
	
	public Sprite getCurrentSprite(Mazub mazub){
		return mazub.getCurrentSprite();
	}
	
	public void startJump(Mazub mazub) {
		try{
			mazub.startJump();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("jump error");
		}
	}
	
	public void endJump(Mazub mazub) {
		mazub.endJump();
	}
	
	public void startMoveLeft(Mazub mazub) {
		mazub.startMove("left");
	}
	
	public void endMoveLeft(Mazub mazub) {
		mazub.endMove("left");
	}
	
	public void startMoveRight(Mazub mazub) {
		mazub.startMove("right");
	}
	
	public void endMoveRight(Mazub mazub) {
		mazub.endMove("right");
	}
	
	public void advanceTime(Mazub mazub, double duration) {
		try{
			mazub.advanceTime(duration);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException("invalid duration");
		}
	}
	
	public void startDuck(Mazub mazub){
		try {
			mazub.startDuck();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("startDuck error");
		}
	}
	
	public void endDuck(Mazub mazub){
		try {
			mazub.endDuck();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("endDuck error");
		}
	}

}
