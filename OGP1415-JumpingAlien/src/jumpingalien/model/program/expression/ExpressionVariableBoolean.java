package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionVariableBoolean extends ExpressionVariable<Bool> {

	public ExpressionVariableBoolean(SourceLocation sourceLocation, String name) {
		super(sourceLocation, name);
		
	}

	@Override
	public Bool compute() {
		Bool result = (Bool) getStatement().getProgram().getGlobalVariables().get(getName());
		return result;
	}

}
