package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.Arrays;

import jumpingalien.model.Plant;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

public class CharacterTest {

	@Test
	public void testPlant() {
		Sprite[] sprites = spriteArrayForSize(2, 2);
		Sprite[] newsprites = Arrays.copyOfRange(sprites, 0, 2);
		Plant joske = new Plant(5,5,newsprites);
		for (int i=0 ; i<2 ; i++){
			joske.advanceTime(0.05);
		}
		assert(joske.getHorizontalVelocity() == 0.5);
		assertArrayEquals(doubleArray(10, 5), joske.getPosition(),Util.DEFAULT_EPSILON);
		for (int i=0 ; i<181 ; i++){
			joske.advanceTime(0.0050);
		}
		assertArrayEquals(doubleArray(5, 5), joske.getPosition(),
				Util.DEFAULT_EPSILON);
		Plant barry = new Plant(1023,5,newsprites);
		barry.advanceTime(0.1);
		assert(barry.getPositionAt(1) == 1023);
	}
}
