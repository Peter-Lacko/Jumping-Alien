package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.*;
import jumpingalien.model.program.type.DirectionType;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class StartRun extends ActionStatement {

	public StartRun(Expression<DirectionType> expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
	}
	
	private final Expression<DirectionType> expression;
	
	public Expression<DirectionType> getExpression() {
		return expression;
	}

	private StartRun getStartRun(){
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
			public StartRun next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getStartRun();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		if (getExpression().compute().getValue() == Direction.LEFT)
			getProgram().getCharacter().startMove("left");
		else if (getExpression().compute().getValue() == Direction.RIGHT)
			getProgram().getCharacter().startMove("right");
	}

}
