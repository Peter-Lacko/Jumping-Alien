package jumpingalien.model.program;

import java.util.*;

import jumpingalien.model.Characters;
import jumpingalien.model.World;
import jumpingalien.model.program.statement.*;
import jumpingalien.model.program.type.Type;
import be.kuleuven.cs.som.annotate.*;

public class Program implements Iterable<Statement>{
	
	public Program(Statement mainStatement, Map<String , Type<?>> globalVariables){
		this.statement = mainStatement;
		this.globalVariables = globalVariables;
	}
	
	private final Map<String, Type<?>> globalVariables;
	
	public Map<String, Type<?>> getGlobalVariables() {
		return globalVariables;
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
	
//	/**
//	 * Return the globalValues of this program
//	 *		globalTypes is a map with all global variables in the program an their value.
//	 */
//	@Basic
//	public Map<String, Object> getGlobalValue(){
//		Map<String , Object> copy;
//		copy = new HashMap<String, Object>(globalValue);
//		return copy;
//	}
//	
//	public void addValue(String name, Expression<?> value){
//		this.globalValue.put(name, value.compute());
//	}
//	
//	/**
//	 * A variable referencing to the values of the global variables.
//	 */
//	private Map<String, Object> globalValue;
//
//	public Map<String, Type> getGlobalType() {
//		return globalType;
//	}
//
//	private Map<String, Type> globalType;
	
	public jumpingalien.model.program.type.Object getObject() {
		return object;
	}

	public void setObject( jumpingalien.model.program.type.Object object) {
		this.object = object;
	}

	private  jumpingalien.model.program.type.Object object;

	public World getWorld() {
		return world;
	}
	
	public Characters getCharacter(){
		return (Characters)this.getObject().getValue();
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	private World world;

	@Override
	public Iterator<Statement> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
