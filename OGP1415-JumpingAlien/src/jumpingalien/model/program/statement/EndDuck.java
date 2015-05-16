package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EndDuck extends ActionStatement {

	public EndDuck(){
		super();
	}
	
	public EndDuck getEndDuck(){
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
