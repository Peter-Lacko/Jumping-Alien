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
		this.setMainIterator(getStatement().iterator());
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
	
	public void setStatement(Statement statement){
		this.statement = statement;
	}

	/**
	 * A variable containing to a statement.
	 */
	private Statement statement;
	
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
	
	public Iterator<Statement> getMainIterator() {
		return mainIterator;
	}

	public void setMainIterator(Iterator<Statement> mainIterator) {
		this.mainIterator = mainIterator;
	}

	private Iterator<Statement> mainIterator;

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return getMainIterator().hasNext();
			}

			@Override
			public Statement next() {
				if (! hasNext())
					throw new NoSuchElementException();
				return getMainIterator().next();
			}
			
			
		};
	}
	
	public boolean isWellFormed(Program program){
		Iterator<Statement> iterator = program.getMainIterator();
		while (iterator.hasNext()){
			Statement statement = iterator.next();
			if ((statement instanceof Break) && (! inLoopStatment(statement))){
				return false;
			}
			if ((statement instanceof Action) )
		}
	}

	private boolean inLoopStatment(Statement statement2) {
		// TODO Auto-generated method stub
		return false;
	}
}
