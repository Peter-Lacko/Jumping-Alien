package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Skip extends ActionStatement {

	public Skip(){
		super();
	}
	
	public Skip getSkip(){
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
			public Object next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getSkip();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		//er gebeurt niets

	}

}
