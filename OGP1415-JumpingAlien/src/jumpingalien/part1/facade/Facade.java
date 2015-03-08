package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;

public class Facade implements IFacade{
	
	// try catch voor exeptions (zie worms facade)
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		
			return new Mazub(pixelLeftX,pixelBottomY,sprites);

	}
	
	public int[] getLocation(Mazub mazub){
		return mazub.getIntPosition();
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
		mazub.startJump();
	}
	
	public void endJump(Mazub mazub) {
		mazub.endJump();
	}
	
	public void startMoveLeft(Mazub mazub) {
		mazub.startMove("left");
	}
	
	public void endMoveLeft(Mazub mazub) {
		mazub.endMove();
	}
	
	public void startMoveRight(Mazub mazub) {
		mazub.startMove("right");
	}
	
	public void endMoveRight(Mazub mazub) {
		mazub.endMove();
	}
	
	public void advanceTime(Mazub mazub, double duration) {
		mazub.AdvanceTime(duration);
	}
	
	public void startDuck(Mazub mazub){
		mazub.startDuck();
	}
	
	public void endDuck(Mazub mazub){
		mazub.endDuck();
	}

}
