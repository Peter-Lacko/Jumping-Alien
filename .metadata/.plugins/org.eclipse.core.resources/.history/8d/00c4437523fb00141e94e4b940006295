package jumpingalien.model.program;

import java.util.*;

import jumpingalien.model.Characters;
import jumpingalien.model.World;
import jumpingalien.model.program.statement.Statement;
import jumpingalien.model.program.type.Type;
import be.kuleuven.cs.som.annotate.*;

public class Program {
	
	public Program(Map<String , Type> map,Statement statement){
		this.statement = statement;
		this.globalType = map;
	}
	
	/**
	 * Return the statement of this program
	 * 		The statement references to which statement the program should execute.
	 */
	@Basic
	public Statement getStatement(){
		return this.statement;
	}

	/**
	 * A variable containing to a statement.
	 */
	private final Statement statement;
	
	/**
	 * Return the globalValues of this program
	 *		globalTypes is a map with all global variables in the program an their value.
	 */
	@Basic
	public Map<String, Object> getGlobalValue(){
		return this.globalValue;
	}
	
	/**
	 * A variable referencing to the values of the global variables.
	 */
	private Map<String, Object> globalValue;

	public Map<String, Type> getGlobalType() {
		return globalType;
	}

	private Map<String, Type> globalType;
	
	public Characters getCharacter() {
		return character;
	}

	public void setCharacter(Characters character) {
		this.character = character;
	}

	private Characters character;

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	private World world;
}
