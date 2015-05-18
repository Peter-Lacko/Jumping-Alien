package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class StartJump extends ActionStatement {

	public StartJump(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	private StartJump getStartJump(){
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
			public StartJump next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getStartJump();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		getProgram().getCharacter().startJump();
	}

}
