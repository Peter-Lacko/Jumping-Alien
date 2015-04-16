package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.model.World;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {

	private static World worldStandard;
	
	private static World worldSmallWindow;
	
	private static World worldSmallSize;
	
	@Before
	public void setUpMutableFixture(){
		worldStandard = new World(4, 300, 300, 500, 500, 150, 150);
	}
	
	@BeforeClass
	public static void setUpImmutableFixture(){
		worldSmallWindow = new World(4, 300, 300, 50, 50, 150, 150);
		worldSmallSize = new World(4, 20, 20, 80, 80, 10, 10);
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
	public void startGame_Correct(){
		worldStandard.startGame();
		assertEquals(worldStandard.isGameStarted(), true);
	}
	
}
