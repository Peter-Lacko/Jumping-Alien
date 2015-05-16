package jumpingalien.model;

public class Tile extends GameObject{
	
	public Tile(){
		
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	private int[] position;
	
	public World getWorld() {
		return world;
	}

	private World world;

	public void setWorld(World world) {
		this.world = world;
	}

	public GeoFeature getGeo() {
		return geo;
	}

	public void setGeo(GeoFeature geo) {
		this.geo = geo;
	}

	private GeoFeature geo;

	public int getTyleLength() {
		return tyleLength;
	}

	public void setTyleLength(int tyleLength) {
		this.tyleLength = tyleLength;
	}

	private int tyleLength;
	
}
