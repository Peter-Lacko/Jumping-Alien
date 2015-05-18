package jumpingalien.model.program.expression;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionVariableDouble extends ExpressionVariable<DoubleType> {

	public ExpressionVariableDouble(SourceLocation sourceLocation,
			Program program, String name) {
		super(sourceLocation, program, name);
	}

	public ExpressionVariableDouble(SourceLocation sourceLocation, String name) {
		this(sourceLocation, null, name);
	}
	
	@Override
	public DoubleType compute() {
		DoubleType result = (DoubleType) getProgram().getGlobalVariables().get(getName());
		return result;
	}

}
