/**
 * 
 */
package jumpingalien.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class that controls the game world.
 * @invar	|isValidTargetTile(getTargetTile(), getMaxXTiles(), getMaxYTiles())
 * @invar	|isValidTileLength(getTileLength())
 * @invar	|isValidMaxTiles(getMaxXTiles())
 * @invar	|isValidMaxTiles(getMaxYTiles())
 * @invar	|hasProperGeoFeatures() 
 * @invar	|hasProperObjects()
 * @invar	|hasProperLeftObjects()
 * @invar	|hasProperRightObjects()
 * @invar	|allCharactersLeftOrRight()
 * @invar	|isValidWindowWidth(getWindowWidth(), getMaxXTiles())
 * @invar	|isValidWindowHeight(getWindowHeight(), getMaxYTiles())
 * 
 * @author Peter Lacko (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor)),
 * 			Sander Switsers (2nd Bachelor - Computer Sciences (Major) and Electrical Engineering (Minor))
 * @version 1.0
 * Code repository: https://github.com/Peter-Lacko/Jumping-Alien
 */
public class World {

	/**
	 * Initialize the world with given parameters.
	 * @param tileLength
	 * @param nbTilesX
	 * @param nbTilesY
	 * @param windowWidth
	 * @param windowHeight
	 * @param targetTileX
	 * @param targetTileY
	 * @post	| new.getTargetTile() == {targetTileX, targetTileY}
	 * @post	| new.getTileLength() == tileLength
	 * @post	| new.getMaxXTiles() == nbTilesX
	 * @post	| new.getMaxYTiles() == nbTilesY
	 * @post	| new.getWindowWidth() == windowWidth
	 * @post	| new.getWindowHeight() == windowHeight
	 * @throws IllegalArgumentException
	 * 			| (! isValidTileLength(tileLength))
	 * @throws IllegalArgumentException
	 * 			| (! isValidMaxTiles(nbTilesX))
	 * @throws IllegalArgumentException
	 * 			| (! isValidMaxTiles(nbTilesY))
	 * @throws IllegalArgumentException
	 * 			| (! isValidTargetTile(getTargetTile(), getMaxXTiles(), getMaxYTiles()))
	 * @throws IllegalArgumentException
	 * 			| (! isValidWindowWidth(windowWidth, nbTilesX))
	 * @throws IllegalArgumentException
	 * 			| (! isValidWindowHeight(windowHeight, nbTilesY))
	 */
	@Raw
	public World(int tileLength, int nbTilesX, int nbTilesY,
			int windowWidth, int windowHeight, int targetTileX,
			int targetTileY) throws IllegalArgumentException{
		int [] targetArray = {targetTileX, targetTileY};
		if ((! isValidTileLength(tileLength)) || (! isValidMaxTiles(nbTilesX)) || 
				(! isValidMaxTiles(nbTilesY)) || 
				(! isValidTargetTile(targetArray, nbTilesX, nbTilesY)) || 
				(! isValidWindowWidth(windowWidth, nbTilesX)) || 
				(! isValidWindowHeight(windowHeight, nbTilesY)))
			throw new IllegalArgumentException();
		TILE_LENGTH = tileLength;
		MAX_X_TILES = nbTilesX;
		MAX_Y_TILES = nbTilesY;
		TARGET_TILE = targetArray;
		WINDOW_WIDTH = windowWidth;
		WINDOW_HEIGHT = windowHeight;
	}
	
	@Basic
	public int[] getTargetTile(){
		return TARGET_TILE;
	}
	
	/**
	 * 
	 * @param target
	 * @return	|if (target.length != 2):
	 * 			|	then result == false
	 * 			|else if ((target[0] < 0) || (target[0] > nbXTiles))
	 * 			|	then result == false
	 * 			|else if ((target[1] < 0) || (target[1] > nbYTiles))
	 * 			|	then result == false
	 * 			|else
	 * 			|	result == true
	 */
	@Raw
	public boolean isValidTargetTile(int[] target, int nbXTiles, int nbYTiles){
		if (target.length != 2)
			return false;
		else if ((target[0] < 0) || (target[0] > nbXTiles))
			return false;
		else if ((target[1] < 0) || (target[1] > nbYTiles))
			return false;
		else
			return true;
	}
	
	private final int[] TARGET_TILE;
	
	/**
	 * check if the given character's saved position overlaps with the target tile.
	 * @param mazub
	 * @return	|if {getTilePosition(mazub.getIntPositionAt(1), mazub.getIntPositionAt(2)}
	 * 			|	== getTargetTile())
	 * 			|	then result == true
	 * 			|else
	 * 			|	result == false
	 */
	public boolean checkIfWin(Characters mazub){
		int[] mazubPosition = mazub.getIntPosition();
		if ((mazubPosition[0] == getTargetTile()[0]) && (mazubPosition[1] == getTargetTile()[1]))
			return true;
		else
			return false;
	}
	
	@Basic
	public boolean hasPlayerWon(){
		return hasPlayerWon;
	}
	
	/**
	 * 
	 * @param flag
	 * @post new.hasPlayerWon() == flag
	 */
	public void setPlayerWon(boolean flag){
		hasPlayerWon = flag;
	}
	
	private boolean hasPlayerWon = false;
	
	@Basic
	public int getTileLength(){
		return TILE_LENGTH;
	}
	
	/**
	 * @return	| result == (length >= 1)
	 */
	@Raw
	private boolean isValidTileLength(int length){
		return (length >= 1);
	}
	
	private final int TILE_LENGTH;
	
	@Basic
	public int getMaxXTiles(){
		return MAX_X_TILES;
	}
	
	/**
	 * @return	| result == (amount >= 1)
	 */
	//misschien maken zodat minstens 1 mazub net past
	@Raw
	private boolean isValidMaxTiles(int amount){
		return (amount >= 1);
	}
	
	//this is actually the maximum number minus 1
	private final int MAX_X_TILES;
	
	@Basic
	public int getMaxYTiles(){
		return MAX_Y_TILES;
	}
	
	//this is actually the maximum number minus 1
	private final int MAX_Y_TILES;
	
	
	/**
	 * @return	| result == {(getMaxXTiles()+1)*getTileLength(), (getMaxYTiles()+1)*getTileLength()}
	 */
	public int[] getWorldSize(){
		int[] WorldSize = {(getMaxXTiles()+1)*getTileLength(), (getMaxYTiles()+1)*getTileLength()};
		return WorldSize;
	}
	
	/**
	 * @param tileX
	 * @param tileY
	 * @return	| result == {tileX*getTileLength(), tileY*getTileLength()}
	 */
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		int[] coordinates = {tileX*getTileLength(), tileY*getTileLength()};
		return coordinates;
	}
	
	/**
	 * Return the coordinates of the tile that contain the given coordinates.
	 * @param xPixel
	 * 			the x-coordinate
	 * @param yPixel
	 * 			the y-coordinate
	 * @return	| result == {(xPixel-xPixel%getTileLength())/getTileLength(),
				|				(yPixel-yPixel%getTileLength())/getTileLength()}
	 */
	public int[] getTilePosition(int xPixel, int yPixel){
		int[] position = {(xPixel-xPixel%getTileLength())/getTileLength(),
				(yPixel-yPixel%getTileLength())/getTileLength()};
		return position;
		
	}
	
	/**
	 * 
	 * @param pixelLeft
	 * @param pixelBottom
	 * @param pixelRight
	 * @param pixelTop
	 * @return	| for (y in getTilePosition(pixelLeft,pixelBottom)[1]
	 * 			|			..getTilePosition(pixelLeft,pixelTop)[1])
	 * 			|	for (x in getTilePosition(pixelLeft,pixelBottom)[0]
	 * 			|			..getTilePosition(pixelRight,pixelBottom)[0])
	 * 			|		positions.add({x,y})
	 * 			| result == positions
	 */
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom, int pixelRight, int pixelTop){
		int yStart = getTilePosition(pixelLeft,pixelBottom)[1];	
		int yEnd = getTilePosition(pixelLeft,pixelTop)[1];
		int xStart = getTilePosition(pixelLeft,pixelBottom)[0];
		int xEnd = getTilePosition(pixelRight,pixelBottom)[0];
		List<int[]> positions = new ArrayList<int[]>();
		for (int yIndex = yStart ; yIndex <= yEnd ; yIndex++){
			for (int xIndex = xStart ; xIndex <= xEnd ; xIndex++){
				int[] coordinates = {xIndex,yIndex};
				positions.add(coordinates);
			}
		}
		int[][] positionArray = new int[positions.size()][];
		positions.toArray( positionArray );
		return positionArray;
	}
	
	/**
	 * 
	 * @param xPixel
	 * @param yPixel
	 * @throws IllegalArgumentException
	 * 			| ! isValidGeoPosition({xPixel,yPixel})
	 */
	@Basic
	public GeoFeature getGeoFeatureAt(int xPixel, int yPixel) throws IllegalArgumentException{
		int[] position = {xPixel, yPixel};
		if (! isValidGeoPosition(position))
			throw new IllegalArgumentException();
		GeoFeature geoFeature = geoFeatures.get(position);
		if (geoFeature == null)
			geoFeature = GeoFeature.AIR;
		return geoFeature;
	}
	
	/**
	 * @param xPixel
	 * @param yPixel
	 * @post	|new.getGeoFeatureAt(xPixel, yPixel) == feature
	 * @throws IllegalArgumentException
	 * 			| ! isValidGeoPosition({xPixel,yPixel})
	 * @throws IllegalArgumentException
	 * 			| ! isValidGeoFeature(feature)
	 */
	public void setGeoFeatureAt(int xPixel, int yPixel, GeoFeature feature) throws IllegalArgumentException{
		int[] position = {xPixel, yPixel};
		if ((! isValidGeoPosition(position)) || (! isValidGeoFeature(feature)))
			throw new IllegalArgumentException();
		if (feature == GeoFeature.AIR){
			if (hasAsKeyGeoFeaturePosition(position))
				geoFeatures.remove(position);
		}
		else
			geoFeatures.put(position, feature);
	}
	
	/**
	 * @param feature
	 * @return	| result == (feature == GeoFeature.AIR || feature == GeoFeature.GROUND 
	 * 			|	|| feature == GeoFeature.WATER || feature == GeoFeature.MAGMA)
	 */
	private boolean isValidGeoFeature(GeoFeature feature){
		return (feature == GeoFeature.AIR || feature == GeoFeature.GROUND 
				|| feature == GeoFeature.WATER || feature == GeoFeature.MAGMA);
	}
	
	/**
	 * 
	 * @param position
	 * @return	|result == ((position.length == 2) && (position[0]%getTileLength() == 0) && 
	 * 			|				(position[1]%getTileLength()==0) && (position[0] < getWorldSize()[0])
	 * 			|				&& (position[1] < getWorldSize()[1]))
	 */
	private boolean isValidGeoPosition(int[] position){
		return ((position.length == 2) && (position[0]%getTileLength() == 0) && 
				(position[1]%getTileLength()==0) && (position[0] < getWorldSize()[0])
				&& (position[1] < getWorldSize()[1]));
	}
	
	/**
	 * 
	 * @return	|result ==
	 * 			|	for x in 0..getMaxXTiles():
	 * 			|			for y in 0..getMaxYTiles():
	 * 			|				isValidGeoFeature(getGeoFeatureAt(x,y));
	 */
	private boolean hasProperGeoFeatures(){
		for (int xIndex=0 ; xIndex <= getMaxXTiles() ; xIndex++){
			for (int yIndex=0 ; yIndex <= getMaxYTiles() ; yIndex++){
				if (! isValidGeoFeature(getGeoFeatureAt(xIndex, yIndex)))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param position
	 * @return	|result == for one I in geoFeatures:
	 * 			|		key == position;
	 */
	private boolean hasAsKeyGeoFeaturePosition(int[] position){
		return geoFeatures.containsKey(position);
	}
	
	/**
	 * @invar	|geoFeatures != null
	 * @invar	|for each key in geoFeatures.keySet():
	 * 			|	isValidGeoPosition();
	 * @invar	The default state is air, thus AIR shall not be saved in the effective Map
	 * 			(if a position (key) is asked that has no value, that means the tile is an AIR tile)
	 * 			|for each value in geoFeatures:
	 * 			|	value == GeoFeature.GROUND || value == GeoFeature.WATER || value == GeoFeature.MAGMA
	 */
	private final Map<int[], GeoFeature> geoFeatures = new HashMap<int[], GeoFeature>();
	
	/**
	 * 
	 * @param duration
	 * @effect	|for each object in getAllObjects():
	 * 			|	object.advanceTime(duration);
	 * @post	|for each object in getAllObjects():
	 * 			|	if(object.getIntPositionAt(1) <= getWorldSize()[0]/2)
	 * 			|		then hasAsLeftObject(object);
	 * 			|	else
	 * 			|		(! hasAsLeftObject(object));
	 * @post	|for each object in getAllObjects():
	 * 			|	if((object.getIntPositionAt(1) + object.getSize()[0] - 1) >= getWorldSize()[0]/2)
	 * 			|		then hasAsRightObject(object);
	 * 			|	else
	 * 			|		(! hasAsRightObject(object));
	 * @throws	IllegalArgumentException
	 * 			| (duration >= 0.2) || (duration < 0.0)
	 */
	public void advanceTime(double duration) throws IllegalArgumentException{
		if ((duration >= 0.2) || (duration < 0.0))
			throw new IllegalArgumentException();
		for (Characters object: getAllObjects()){
			object.advanceTime(duration);
			if (hasAsObject(object)){
				if (object.getIntPositionAt(1) <= getWorldSize()[0]/2)
					addAsLeftObject(object);
				else
					removeAsLeftObject(object);
				if ((object.getIntPositionAt(1) + object.getSize()[0] - 1) >= getWorldSize()[0]/2)
					addAsRightObject(object);
				else
					removeAsRightObject(object);
			}
		}
	}
	
	/**
	 * @post new.isGameStarted() == true;
	 */
	public void startGame(){
		isGameStarted = true;
	}
	
	@Basic
	public boolean isGameStarted(){
		return isGameStarted;
	}
	
	private boolean isGameStarted = false;
	
	/**
	 * 
	 * @return	|if object.isTerminated()
	 * 			|	then result == ((object.terminationTime() <= 0.6) && (object != null))
	 * 			|else
	 * 			|	result == (object != null)
	 */
	private boolean canHaveAsObject(Characters object){
		if (object.isTerminated())
			return ((object.notFullyDeadYet()) && (object != null));
		else
			return (object != null);
	}
	
	/**
	 * 
	 * @param object
	 * @param index
	 * @return	| if (! canHaveAsObject())
	 * 			|	then result == false;
	 * 			|else if ((index < 1) || index > getNbObjects() +1)
	 * 			|	then result == false;
	 * 			|else if ((index == 1) && (! (object instanceof Mazub)))
	 * 			|	then result == false
	 * 			|else result ==
	 * 			|		for each i in 1..getNbObjects():
	 * 			|			((i == index) || (getObjectAt(i) != object))
	 */
	private boolean canHaveAsObjectAt(Characters object, int index){
		if (! canHaveAsObject(object))
			return false;
		else if ((index < 1) || index > getNbObjects() +1)
			return false;
		for (int newIndex=1; newIndex <= getNbObjects()+1; newIndex++){
			if ((newIndex != index) && (getObjectAt(newIndex) == object))
				return false;
		}
		if ((index == 1) && (! (object instanceof Mazub)))
			return false;
		return true;
	}
	
	@Basic
	public int getNbObjects(){
		int nbObjectsSoFar = 0;
		for (int index=0; index < objects.size(); index++){
			Characters currentObject = objects.get(index);
			if ((currentObject != null) && (objects.indexOf(currentObject) == index))
					nbObjectsSoFar++;
		}
		return nbObjectsSoFar;
	}
	
	/**
	 * 
	 * @param number
	 * @return	|result == (number <= 100)
	 */
	private boolean canHaveAsNbObjects(int number){
		return ((number >=0) && (number <= 100) );
	}
	
	@Basic
	public Characters getObjectAt(int index) throws IndexOutOfBoundsException{
		return objects.get(getInternalIndexOfObjectAt(index));
	}
	
	/**
	 * 
	 * @param index
	 * @return	|if (index <= 0)
	 * 			|	then result == -1
	 * @return	|if (index > 0)
	 * 			|	then objects.get(result) != null
	 * @return	|if (index > 0)
	 * 			|	then for each i in 0..result-1:
	 * 			|		(objects.get(i) != objects.get(result))
	 * @return	|if (index > 0)
	 * 			|	then index == card ({{ object in Characters | 
	 * 			|							for some i in 0..result:
	 * 			|								(object == objects.get(i)) }})
	 * @throws IndexOutOfBoundsException
	 * 			| index >= getNbObjects()
	 */
	private int getInternalIndexOfObjectAt(int index) throws IndexOutOfBoundsException{
		int nbObjectsSoFar = 0;
		int pos = 0;
		while (nbObjectsSoFar < index) {
			Characters currentObject = objects.get(pos);
			if ((currentObject != null) && (objects.indexOf(currentObject) == pos))
				nbObjectsSoFar++;
			pos++;
		}
		return pos - 1;
	}
	
	/**
	 * 
	 * @param object
	 * @return	|result ==
	 * 			|	for some i in 1..getNbObjects():
	 * 			|		(getObjectAt(i) == object);
	 */
	public boolean hasAsObject(Characters object){
		return objects.contains(object);
	}
	
	/**
	 * 
	 * @return	|result ==
	 * 			|	for each i in 1..getNbObjects():
	 * 			|		((canHaveAsObjectAt(getObjectAt(i),i))
	 * 			|		&& (getObjectAt(i).getWorld() == this))
	 * 			|	&& canHaveAsNbObjects(getNbObjects())
	 */
	private boolean hasProperObjects(){
		for (int index = 1; index <= getNbObjects(); index++){
			if ((! canHaveAsObjectAt(getObjectAt(index),index)) || (getObjectAt(index).getWorld() != this))
				return false;
		}
		if (! canHaveAsNbObjects(getNbObjects()))
			return false;
		return true;
	}
	
	/**
	 * 
	 * @param object
	 * @param index
	 * @post	|new.getObjectAt(index) == object
	 * @post	|new.getNbObjects() == getNbObjects() +1
	 * @post	|for each i in index..getNbObjects():
	 * 			|	(new.getObjectAt(i+1) == getObjectAt(i));
	 * @post	|(new object).getWorld() == this;
	 * @post	|(new.hasAsLeftObject(object) || new.hasAsRightObject(object))
	 * @throws IllegalArgumentException
	 * 			| (! canHaveAsObjectAt(object, index))
	 */
	public void addObjectAt(Characters object, int index) throws IllegalArgumentException{
		if (! canHaveAsObjectAt(object, index))
			throw new IllegalArgumentException("Invalid object");
		int indexOfNewObject = getInternalIndexOfObjectAt(index-1)+1;
		if (indexOfNewObject < objects.size()) {
			Characters oldObject = objects.get(indexOfNewObject);
			if ((oldObject == null) || (objects.indexOf(oldObject) < indexOfNewObject))
				objects.set(indexOfNewObject, object);
			else
				objects.add(indexOfNewObject, object);
		}
		else
			objects.add(object);
	}
	
	/**
	 * 
	 * @param index
	 * @post	|new.hasAsObject(object) == false
	 * @post	|new.getNbObjects() == getNbObjects() -1
	 * @post	|for each i in index..getNbObjects():
	 * 			|	(new.getObjectAt(i-1) == getObjectAt(i));
	 * @post	|if (hasAsObject(object)):
	 * 			|	then ((new object).getWorld() == null)
	 * @post	|((! hasAsLeftObject(object)) && (! hasAsRightObject(object))
	 * @throws IndexOutOfBoundsException
	 * 			|(index < 1) || (index >= getNbObjects() +1)
	 */
	public void removeObjectAt(int index) throws IndexOutOfBoundsException{
		int indexOfObject = getInternalIndexOfObjectAt(index);
		Characters objectToRemove = objects.get(indexOfObject);
		for (int pos = indexOfObject; pos < objects.size(); pos++){
			if (objects.get(pos) == objectToRemove)
				objects.set(pos, null);
		}
	}
	
	/**
	 * 
	 * @param object
	 * @post	|new.getObjectAt(getNbObjects()+1) == object
	 * @post	|new.getNbObjects() == getNbObjects()+1
	 * @post	|(new object).getWorld() == this;
	 * @post	|(new.hasAsLeftObject(object) || new.hasAsRightObject(object))
	 * @throws IllegalArgumentException
	 * 			| (! canHaveAsObjectAt(object, getNbObjects() +1))
	 */
	public void addAsObject(Characters object) throws IllegalArgumentException{
		if (! canHaveAsObjectAt(object, getNbObjects() +1))
			throw new IllegalArgumentException();
		if ((! objects.isEmpty()) && (objects.get(objects.size()-1) == null))
			objects.set(objects.size()-1, object);
		else
			objects.add(object);
	}
	
	/**
	 * 
	 * @param object
	 * @effect	|if hasAsObject(object):
	 * 			|	then removeObjectAt(getIndexOfObject(object))
	 */
	public void removeAsObject(Characters object){
		int firstIndexOfObject = objects.indexOf(object);
		while (firstIndexOfObject != -1){
			objects.set(firstIndexOfObject, null);
			firstIndexOfObject = objects.indexOf(object);
		}
	}
	
	/**
	 * 
	 * @param object
	 * @return	|getObjectAt(result) == object
	 * @throws IllegalArgumentException
	 * 			|(! hasAsObject(object))
	 */
	public int getIndexOfObject(Characters object) throws IllegalArgumentException{
		if (! hasAsObject(object))
			throw new IllegalArgumentException();
		int nbObjectsSoFar = 0;
		int index = 0;
		Characters currentObject = objects.get(index);
		while (currentObject != object) {
			currentObject = objects.get(index);
			if ((currentObject != null) && (objects.indexOf(currentObject) == index))
				nbObjectsSoFar++;
			index++;
		}
		return nbObjectsSoFar;
	}
	
	/**
	 * 
	 * @return 	|for each i in 0..getNbObjects()-1:
	 * 			|	(result.get(i) == getObjectAt(i+1))
	 * @return	| result.length() == getNbObjects()
	 */
	public List<Characters> getAllObjects(){
		LinkedList<Characters> result = new LinkedList<Characters>();
		for(int index = getNbObjects(); index <= 1; index--)
			result.addFirst(getObjectAt(index));
		return result;
	}
	
	/**
	 * @invar	|objects != null
	 * @invar	|for each object in objects:
	 * 			|	((object == null) || (canHaveAsObject(object)))
	 * @invar	|if objects.size() >= 1:
	 * 			|	((objects.getFirst() == null) || (objects.getFirst() instanceOf Mazub)
	 */
	private List<Characters> objects = new LinkedList<Characters>();
	
	@Basic
	public boolean hasAsLeftObject(Characters object){
		return leftObjects.contains(object);
	}
	
	/**
	 * @return	|result ==
	 * 			|	for each object in getAllLeftObjects:
	 * 			|		canHaveAsObject(object);
	 * 			|	&& object.getWorld() == this
	 */
	public boolean hasProperLeftObjects(){
		for (Characters object: getAllLeftObjects()){
			if ((! canHaveAsObject(object)) || (object.getWorld() != this))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param object
	 * @post	|new.hasAsLeftObject(object)
	 * @throws IllegalArgumentException
	 * 			|(! canHaveAsObject(object))
	 * @throws IllegalArgumentException
	 * 			|object.getWorld != this
	 */
	private void addAsLeftObject(Characters object) throws IllegalArgumentException{
		if((! canHaveAsObject(object)) || (object.getWorld() != this))
			throw new IllegalArgumentException();
		leftObjects.add(object);
	}
	
	/**
	 * 
	 * @param object
	 * @post	|((! hasAsLeftObject(this)) && hasAsRightObject(this))
	 */
	private void removeAsLeftObject(Characters object){
		leftObjects.remove(object);
		addAsRightObject(object);
	}
	
	/**
	 * @return	|result != null
	 * @return	|for each object in leftObjects:
	 * 			|	result.contains(object) == hasAsLeftObject(object)
	 */
	public Set<Characters> getAllLeftObjects(){
		return new HashSet<Characters>(this.leftObjects);
	}
	
	/**
	 * @invar	|leftObjects != null
	 * @invar	|for each object in objects:
	 * 			|	(canHaveAsObject(object) && object.getWorld() == this)
	 */
	private Set<Characters> leftObjects = new HashSet<Characters>();
	
	@Basic
	public boolean hasAsRightObject(Characters object){
		return rightObjects.contains(object);
	}
	
	/**
	 * @return	|result ==
	 * 			|	for each object in getAllRightObjects:
	 * 			|		canHaveAsObject(object);
	 * 			|	&& object.getWorld() == this
	 */
	public boolean hasProperRightObjects(){
		for (Characters object: getAllRightObjects()){
			if ((! canHaveAsObject(object)) || (object.getWorld() != this))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param object
	 * @post	|new.hasAsRightObject(object)
	 * @throws IllegalArgumentException
	 * 			|(! canHaveAsObject(object))
	 * @throws IllegalArgumentException
	 * 			|object.getWorld != this
	 */
	private void addAsRightObject(Characters object) throws IllegalArgumentException{
		if((! canHaveAsObject(object)) || (object.getWorld() != this))
			throw new IllegalArgumentException();
		rightObjects.add(object);
	}
	
	/**
	 * 
	 * @param object
	 * @post	|((! hasAsRightObject(this)) && hasAsLeftObject(this))
	 */
	private void removeAsRightObject(Characters object){
		rightObjects.remove(object);
		addAsLeftObject(object);
	}
	
	/**
	 * @return	|result != null
	 * @return	|for each object in rightObjects:
	 * 			|	result.contains(object) == hasAsLeftObject(object)
	 */
	public Set<Characters> getAllRightObjects(){
		return new HashSet<Characters>(this.rightObjects);
	}
	
	/**
	 * @invar	|rightObjects != null
	 * @invar	|for each object in objects:
	 * 			|	(canHaveAsObject(object) && object.getWorld() == this)
	 */
	private Set<Characters> rightObjects = new HashSet<Characters>();
	
	/**
	 * 
	 * @return	|result ==
	 * 			|	for each object in getAllObjects:
	 * 			|		(hasAsLeftObject(object) || hasAsRightObject(object))
	 */
	private boolean allCharactersLeftOrRight(){
		for (Characters object : getAllObjects())
			if ((! hasAsLeftObject(object)) && (! hasAsRightObject(object)))
				return false;
		return true;
	}
	
	@Basic
	public int getWindowWidth(){
		return WINDOW_WIDTH;
	}
	
	/**
	 * 
	 * @param width
	 * @return	|result == ((width > 0) && (width <= nbXTiles))
	 */
	@Raw
	public boolean isValidWindowWidth(int width, int nbXTiles){
		return ((width > 0) && (width <= nbXTiles));
	}
	
	private final int WINDOW_WIDTH;
	
	@Basic
	public int getWindowHeight(){
		return WINDOW_HEIGHT;
	}
	
	/**
	 * 
	 * @param height
	 * @return	|result == ((height > 0) && (height <= nbYTiles))
	 */
	@Raw
	public boolean isValidWindowHeight(int height, int nbYTiles){
		return ((height > 0) && (height <= nbYTiles));
	}
	
	private final int WINDOW_HEIGHT;
	
	/**
	 * 
	 * @return	|result == {getWindowWidth(), getWindowHeight()}
	 */
	public int[] getVisibleWindow(){
		return new int[] {getWindowWidth(), getWindowHeight()};
	}
	
	/**
	 * @post	| new.isTerminated()
	 */
	public void terminate(){
		isTerminated = false;
	}
	
	@Basic
	public boolean isTerminated(){
		return isTerminated;
	}
	
	private boolean isTerminated = false;
	
}
