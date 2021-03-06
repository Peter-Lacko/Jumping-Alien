package jumpingalien.part2.facade;

import java.util.Collection;
import java.util.LinkedList;

import jumpingalien.model.Characters;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart2 {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		try{
			return new Mazub(pixelLeftX,pixelBottomY,sprites);
		} catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");
		}

	}

	@Override
	public int[] getLocation(Mazub mazub){
		try {
			return mazub.getIntPosition();
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			throw new ModelException("position error");
		}
	}

	@Override
	public double[] getVelocity(Mazub mazub){
	double[] vel = {mazub.getHorizontalVelocity(), mazub.getVerticalVelocity()};
	return vel;
	}

	@Override
	public double[] getAcceleration(Mazub mazub){
		double[] acc = {mazub.getHorizontalAcceleration(), mazub.getVerticalAcceleration()};
		return acc;
	}

	@Override
	public int[] getSize(Mazub mazub){
		return mazub.getSize();
	} 

	@Override
	public Sprite getCurrentSprite(Mazub mazub){
		return mazub.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub mazub) {
		try{
			mazub.startJump();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("jump error");
		}
	}

	@Override
	public void endJump(Mazub mazub) {
		mazub.endJump();
	}

	@Override
	public void startMoveLeft(Mazub mazub) {
		mazub.startMove("left");
	}

	@Override
	public void endMoveLeft(Mazub mazub) {
		mazub.endMove("left");
	}

	@Override
	public void startMoveRight(Mazub mazub) {
		mazub.startMove("right");
	}

	@Override
	public void endMoveRight(Mazub mazub) {
		mazub.endMove("right");
	}

	@Override
	public void startDuck(Mazub mazub){
		try {
			mazub.startDuck();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("startDuck error");
		}
	}

	@Override
	public void endDuck(Mazub mazub){
		try {
			mazub.endDuck();
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("endDuck error");
		}
	}

	@Override
	public int getNbHitPoints(Mazub alien){
		return alien.getHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		try{
			return new World(tileSize, nbTilesX, nbTilesY, visibleWindowWidth, visibleWindowHeight, targetTileX, targetTileY);
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");	
		}
	}

	@Override
	public int[] getWorldSizeInPixels(World world){
		return world.getWorldSize();
	}

	@Override
	public int getTileLength(World world){
		return world.getTileLength();
	}

	@Override
	public void startGame(World world){
		world.startGame();
	}

	@Override
	public boolean isGameOver(World world){
		return (world.isGameOver() || world.hasPlayerWon());
	}

	@Override
	public boolean didPlayerWin(World world){
		return world.hasPlayerWon();
	}

	@Override
	public void advanceTime(World world, double dt){
		world.advanceTime(dt);
	}

	@Override
	public int[] getVisibleWindow(World world){
		if (! isGameOver(world))
			return world.getVisibleWindow();
		else
			return null;
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY){
		return world.getBottomLeftPixelOfTile(tileX, tileY);
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException{
		try{
			return world.getGeoFeatureAt(pixelX, pixelY).getValue();
		} catch (Exception exc){
			throw new ModelException("get geofeature error");
		}
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY, int tileType) 
			throws ModelException{
		try{
//			world.setGeoFeatureAt(tileX, tileY, GeoFeature.values()[tileType]);
			world.setGeoFeatureAtWithInt(tileX, tileY, tileType);
		} catch (Exception exc){
			throw new ModelException("set geofeature error");
		}
	}

	@Override
	public void setMazub(World world, Mazub alien){
		world.addMazub(alien);
	}

	@Override
	public boolean isImmune(Mazub alien){
		return alien.isImmune();
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites){
		try{
			return new Plant(x,y,sprites);
		} catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");
		}
	}

	@Override
	public void addPlant(World world, Plant plant){
		world.addAsObject(plant);
	}

	@Override
	public Collection<Plant> getPlants(World world){
		return world.getAllPlants();
	}

	@Override
	public int[] getLocation(Plant plant){
		return plant.getIntPosition();
	}

	@Override
	public Sprite getCurrentSprite(Plant plant){
		return plant.getSprite();
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites){
		try{
			return new Shark(x,y,sprites);
		} catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");
		}
	}

	@Override
	public void addShark(World world, Shark shark){
		world.addAsObject(shark);
	}

	@Override
	public Collection<Shark> getSharks(World world){
		return world.getAllSharks();
	}

	@Override
	public int[] getLocation(Shark shark){
		return shark.getIntPosition();
	}

	@Override
	public Sprite getCurrentSprite(Shark shark){
		return shark.getSprite();
	}

	@Override
	public School createSchool(){
		School school = new School();
		return school;
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school){
		try{
			return new Slime(x,y,sprites,school);
		} catch (IllegalArgumentException exc){
			throw new ModelException("invalid initialisation");
		}
	}

	@Override
	public void addSlime(World world, Slime slime){
		world.addAsObject(slime);
	}

	@Override
	public Collection<Slime> getSlimes(World world){
		return world.getAllSlimes();
	}

	@Override
	public int[] getLocation(Slime slime){
		return slime.getIntPosition();
	}

	@Override
	public Sprite getCurrentSprite(Slime slime){
		return slime.getSprite();
	}

	@Override
	public School getSchool(Slime slime){
		return slime.getSchool();
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {		
	}

}
