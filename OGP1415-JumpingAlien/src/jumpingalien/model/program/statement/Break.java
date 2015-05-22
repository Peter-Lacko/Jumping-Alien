package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class Break extends Statement {

	public Break(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	private LoopStatement loopStatement;
	
	public LoopStatement getLoopStatement() {
		return loopStatement;
	}

	public void setLoopStatement(LoopStatement loopStatement) {
		this.loopStatement = loopStatement;
	}

	private Break getBreak(){
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
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getBreak();
			}
			
			private boolean executed = false;
		};
	}

	@Override
	public void execute() {
		getLoopStatement().setBreak(true);
	}

}
