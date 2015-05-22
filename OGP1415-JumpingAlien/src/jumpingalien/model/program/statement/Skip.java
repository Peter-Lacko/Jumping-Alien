package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class Skip extends ActionStatement {

	public Skip(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	private Skip getSkip(){
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
			public Skip next() throws NoSuchElementException{
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

	}

}
