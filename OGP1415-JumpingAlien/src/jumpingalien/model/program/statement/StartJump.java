package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StartJump extends ActionStatement {

	public StartJump(){
		super();
	}
	
	public StartJump getStartJump(){
		return this;
	}
	
	@Override
	public Iterator iterator() {
		return new Iterator(){

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
