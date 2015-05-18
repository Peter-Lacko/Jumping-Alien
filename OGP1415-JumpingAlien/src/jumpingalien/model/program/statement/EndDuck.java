package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class EndDuck extends ActionStatement {

	public EndDuck(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	private EndDuck getEndDuck(){
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
			public EndDuck next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getEndDuck();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		getProgram().getCharacter().endDuck();
	}

}
