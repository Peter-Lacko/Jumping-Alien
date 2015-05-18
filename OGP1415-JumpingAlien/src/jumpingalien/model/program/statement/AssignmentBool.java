package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.Bool;
import jumpingalien.part3.programs.SourceLocation;

public class AssignmentBool extends Assignment<Bool> {

	public AssignmentBool(String name, Expression<Bool> value,
			SourceLocation sourceLocation) {
		super(name, value, sourceLocation);
	}

	private AssignmentBool getAssignmentBool(){
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
			public AssignmentBool next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getAssignmentBool();
			}
			
			private boolean executed = false;
			
		};
	}

	@Override
	public void execute() {
		this.getProgram().getGlobalVariables().put(getVariable(), getExpression().compute());
	}

}
