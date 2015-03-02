package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;

public class Facade {
	
	public void createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		
		Mazub myMazub = Mazub(pixelLeftX,pixelBottomY,sprites);
		
	}
	
	public int[] getLocation(){
		int[] loc={myMazub.loc_x,myMazub.loc_y};
		return loc;
	}
	
	public double[] getVelocity(){
		double[] vel = {myMazub.vx,myMazub.vy};
		return vel;
	}
	
	public double[] getAcceleration(){
		double[] acc = {myMazub.ax,myMazub.ay};
		return acc;
	}
	
	public int[] getSize(){
		int[] size = {myMazub.width,myMazub.height};
		return size;
	} 
	
	public Sprite getCurrentSprite(){
		return myMazub.sprite;
	}
	

}
