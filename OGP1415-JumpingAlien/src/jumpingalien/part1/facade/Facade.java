package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;

public class Facade implements IFacade{
	
	// try catch voor exeptions (zie worms facade)
	public void createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		
			return new Mazub(pixelLeftX,pixelBottomY,sprites);

	}
	
	public int[] getLocation(Mazub mazub){
		return mazub.getPosition();
	}
	
	public double[] getVelocity(Mazub mazub){
		double[] vel = {mazub.getHorizontalVelocity(),mazub.getVerticalVelocity()};
		return vel;
	}
	
	public double[] getAcceleration(Mazub mazub){
		double[] acc = {mazub.getHorizontalAcceleration(),mazub.getVerticalAcceleration()};
		return acc;
	}
	
	public int[] getSize(){
		return Mazub.getSize();
	} 
	
	public Sprite getCurrentSprite(){
		return Mazub.getCurrentSprite();
	}
	

}
