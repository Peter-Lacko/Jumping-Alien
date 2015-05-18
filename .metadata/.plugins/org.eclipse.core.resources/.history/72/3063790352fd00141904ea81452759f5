package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class StartDuck extends ActionStatement {

	public StartDuck(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	private StartDuck getStartDuck(){
		return this;
	}
	
	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return (! executed);
			}

			@Override
			public StartDuck next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getStartDuck();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		getProgram().getCharacter().startDuck();
	}

}
