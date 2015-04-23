package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.Arrays;

import jumpingalien.model.GeoFeature;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {

	private static World worldStandard;
	
	private static World worldSmallWindow;
	
	private static World worldSmallSize;
	
	private static Mazub mazub;
	
	private static Mazub mazubMiddle;
	
	private static Plant plant;
	
	private static Shark shark;
	
	private static Sprite[] sprites;
	
	private static Sprite[] newSprites;
	
	@Before
	public void setUpMutableFixture(){
		worldStandard = new World(4, 300, 300, 500, 500, 150, 150);
		for (int i = 0; i <= 299; i++)
			worldStandard.setGeoFeatureAtWithInt(i, 0, 1);

		worldSmallWindow = new World(4, 300, 300, 50, 50, 150, 150);
		worldSmallSize = new World(4, 20, 20, 80, 80, 10, 10);
		mazub = new Mazub(4,3, sprites);
		mazubMiddle = new Mazub(590,3, sprites);
		plant = new Plant(10,10, newSprites);
		shark = new Shark(1000, 50, newSprites);
	}
	
	@BeforeClass
	public static void setUpImmutableFixture(){
		sprites = spriteArrayForSize(70, 70);
		newSprites = Arrays.copyOfRange(sprites, 0, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void World_InvalidTileLength() {
		new World(-4, 150, 150, 460, 460, 75, 75);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void World_InvalidWindow() {
		new World(4, 150, 150, 800, 800, 75, 75);
	}

	@Test(expected = IllegalArgumentException.class)
	public void World_InvalidTargetTile() {
		new World(4, 150, 150, 460, 460, 200, 75);
	}
	
	@Test
	public void getWorldSize_Correct(){
		assertArrayEquals(worldStandard.getWorldSize(), new int[] {1200, 1200});
	}
	
	@Test
	public void getTileLength_Correct(){
		assertEquals(worldStandard.getTileLength(), 4);
	}
	
	@Test
	public void getBottomLeftPixelOfTile_Correct(){
		assertArrayEquals(worldStandard.getBottomLeftPixelOfTile(1, 1), new int[] {4,4});
	}
	
	@Test
	public void getTilePositionsIn_Correct(){
		assertArrayEquals(worldStandard.getTilePositionsIn(5, 5, 15, 8), 
				new int[][] {{1,1},{2,1},{3,1},{1,2},{2,2},{3,2}});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getGeoFeatureAt_IllegalCase(){
		worldStandard.getGeoFeatureAt(1, 1);
	}
	
	@Test
	public void getGeoFeatureAt_Air(){
		assertEquals(worldStandard.getGeoFeatureAt(20, 20), GeoFeature.AIR);
	}
	
	@Test
	public void setGeoFeatureAtWithInt_Ground(){
		worldStandard.setGeoFeatureAtWithInt(7, 4, 1);
		assertEquals(worldStandard.getGeoFeatureAt(28,16), GeoFeature.GROUND);
		worldStandard.setGeoFeatureAtWithInt(8, 4, 0);
		assertEquals(worldStandard.getGeoFeatureAt(32,16), GeoFeature.AIR);
	}
	
	@Test
	public void getPixelOfTileContaining(){
		for (int i=0; i <= 3; i++)
			for (int k=0; k <= 3; k++)
				assertArrayEquals(worldStandard.getPixelOfTileContaining(i, k), new int[] {0,0});
	}
	
	
	@Test
	public void addAndRemoveObjects_Correct(){
		worldStandard.addAsObject(shark);
		worldStandard.addMazub(mazub);
		assertEquals(worldStandard.getObjectAt(1), mazub);
		assertEquals(worldStandard.getObjectAt(2), shark);
		worldStandard.addObjectAt(plant, 2);
		assertEquals(worldStandard.getObjectAt(2), plant);
		worldStandard.removeAsObject(plant);
		assertEquals(worldStandard.getObjectAt(2), shark);
	}
	
	@Test
	public void advanceTime_Correct(){
		worldStandard.addMazub(mazub);
		worldStandard.addAsObject(shark);
		worldStandard.addObjectAt(plant, 2);
		mazub.startMove("right");
		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		worldStandard.advanceTime(0.1);
		assertArrayEquals(intArray(14, 3), mazub.getIntPosition());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void advanceTime_IllegalCase(){
		worldStandard.addMazub(mazub);
		mazub.startMove("right");
		worldStandard.advanceTime(0.22);
	}
	
	@Test
	public void leftRightObjects_Correct(){
		worldStandard.addMazub(mazub);
		worldStandard.addAsObject(shark);
		worldStandard.addObjectAt(plant, 2);
		assertEquals(worldStandard.hasAsLeftObject(mazub), true);
		assertEquals(worldStandard.hasAsRightObject(mazub), false);
		
		assertEquals(worldStandard.hasAsLeftObject(shark), false);
		assertEquals(worldStandard.hasAsRightObject(shark), true);
		worldStandard.removeAsObject(shark);
		mazub.startMove("right");
		for (int i=0 ; i <= 40 ; i++)
			worldStandard.advanceTime(0.1);
		assertEquals(worldStandard.hasAsRightObject(mazub), true);
	}

	@Test
	public void leftAndRight_correct(){
		worldStandard.addMazub(mazubMiddle);
		assertEquals(worldStandard.hasAsLeftObject(mazubMiddle), true);
		assertEquals(worldStandard.hasAsRightObject(mazubMiddle), true);
	}
	
	 @Test(expected = IndexOutOfBoundsException.class)
	 public void checkWindowSize_Invalid(){
		 worldSmallWindow.canHaveAsWindowHeight(worldSmallWindow.getWindowHeight());
	 }
	 
	 @Test
	 public void windowSize_ToSmall(){
		 worldSmallWindow.addMazub(mazub);
		 assertEquals(worldSmallWindow.canHaveAsWindowHeight(worldSmallWindow.getWindowHeight()), false);
		 assertEquals(worldSmallWindow.canHaveAsWindowHeight(worldSmallWindow.getWindowWidth()), false);
	 }
	 
	 @Test
	 public void windowSize_SameAsWorld(){
		 worldSmallSize.addMazub(mazub);
		 assertEquals(worldSmallSize.canHaveAsWindowHeight(worldSmallSize.getWindowHeight()), true);
		 assertEquals(worldSmallSize.canHaveAsWindowHeight(worldSmallSize.getWindowWidth()), true);
	 }
	 
	 @Test
	 public void windowSize_Correct(){
		 worldStandard.addMazub(mazub);
		 assertEquals(worldStandard.canHaveAsWindowHeight(worldStandard.getWindowHeight()), true);
		 assertEquals(worldStandard.canHaveAsWindowHeight(worldStandard.getWindowWidth()), true);
	 }
	
}
