package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EndJump extends ActionStatement {

	public EndJump(){
		super();
	}
	
	public EndJump getEndJump(){
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
			public EndJump next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getEndJump();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		getProgram().getCharacter().endJump();
	}

}
