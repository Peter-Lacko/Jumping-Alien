package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionVariableGameObject extends ExpressionVariable<Object> {

	public ExpressionVariableGameObject(SourceLocation sourceLocation, String name) {
		super(sourceLocation, name);
		
	}

	@Override
	public Object compute() {
		Object result = (Object) getStatement().getProgram().getGlobalVariables().get(getName());
		return result;
	}

}
