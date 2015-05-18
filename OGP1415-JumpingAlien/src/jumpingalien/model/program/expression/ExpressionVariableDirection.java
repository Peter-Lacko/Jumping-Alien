package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionVariableDirection extends ExpressionVariable<DirectionType> {

	public ExpressionVariableDirection(SourceLocation sourceLocation, String name) {
		super(sourceLocation, name);
		
	}

	@Override
	public DirectionType compute() {
		DirectionType result = (DirectionType) getStatement().getProgram().getGlobalVariables().get(getName());
		return result;
	}

}
