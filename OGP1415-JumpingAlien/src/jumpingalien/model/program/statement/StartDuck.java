package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StartDuck extends ActionStatement {

	public StartDuck(){
		super();
	}
	
	public StartDuck getStartDuck(){
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
