package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Util;

public class Wait extends ActionStatement {

	public Wait(Expression<DoubleType> expression, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		expression.setStatement(this);
	}
	
	private final Expression<DoubleType> expression;
	
	public Expression<DoubleType> getExpression() {
		return expression;
	}

	private Wait getWait(){
		return this;
	}
	
	private double timeToWait = 0.001;
	
	public double getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(double timeToWait) {
		this.timeToWait = timeToWait;
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return (! Util.fuzzyGreaterThanOrEqualTo(timeWaited, getTimeToWait()));
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				timeWaited += 0.001;
				if (Util.fuzzyEquals(timeWaited, 0.001))
					return getWait();
				else{
					Skip skip = new Skip(getSourceLocation());
					skip.setEnclosingStatement(getWait());
					return skip;
				}
				
			}
			
			private double timeWaited = 0.0;
		};
	}

	@Override
	public void execute() {
		setTimeToWait(getExpression().compute().getValue());
	}

}
