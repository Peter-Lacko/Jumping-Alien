package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class AssignmentDouble extends Assignment<DoubleType> {

	public AssignmentDouble(String name, Expression<DoubleType> value,
			SourceLocation sourceLocation) {
		super(name, value, sourceLocation);
	}

	private AssignmentDouble getAssignmentDouble(){
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
			public AssignmentDouble next() throws NoSuchElementException {
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getAssignmentDouble();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		this.getProgram().getGlobalVariables().put(getVariable(), getExpression().compute());
	}

}
