package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionVariableDouble extends ExpressionVariable<DoubleType> {

	public ExpressionVariableDouble(SourceLocation sourceLocation, String name) {
		super(sourceLocation, name);
	}

	@Override
	public DoubleType compute() {
		DoubleType result = (DoubleType) getStatement().getProgram().getGlobalVariables().get(getName());
		return result;
	}

}
