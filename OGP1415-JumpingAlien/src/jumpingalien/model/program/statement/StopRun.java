package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DirectionType;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;

public class StopRun extends ActionStatement {

	public StopRun(Expression<DirectionType> expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		expression.setStatement(this);
	}

	private final Expression<DirectionType> expression;
	
	public Expression<DirectionType> getExpression() {
		return expression;
	}

	private StopRun getStopRun(){
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
			public StopRun next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getStopRun();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		if (getExpression().compute().getValue() == Direction.LEFT)
			getProgram().getCharacter().endMove("left");
		else if (getExpression().compute().getValue() == Direction.RIGHT)
			getProgram().getCharacter().endMove("right");
	}

}
