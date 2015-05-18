package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.*;

public class Assignment<T extends Type<?>> extends Statement {

	public Assignment(String name, Expression<T> value, SourceLocation sourceLocation, T type){
		super(sourceLocation);
		this.expression = value;
		this.variable = name;
	}
	
//	@Override
//	public void execute(){
////		this.getProgram().addValue(this.getVariable(), this.getExpression());
////		this.setDone(true);
//		if (this.getProgram().getGlobalVariables().get(getVariable()).getClass() == getType().getClass())
//			this.getProgram().getGlobalVariables().put(getVariable(), getExpression().compute());
//	}
//	
	@Basic @Immutable
	public String getVariable() {
		return this.variable;
	}
	
	private final String variable;
	
	@Basic @Immutable
	public Expression<T> getExpression() {
		return this.expression;
	}
	
	private final Expression<T> expression;
	
	@Override
	public void execute() {
		this.getProgram().getGlobalVariables().put(getVariable(), getExpression().compute());
	}

	private Assignment<T> getAssignment(){
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
			public Assignment<T> next() throws NoSuchElementException {
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getAssignment();
			}
			
			private boolean executed = false;
			
		};
	}

}
