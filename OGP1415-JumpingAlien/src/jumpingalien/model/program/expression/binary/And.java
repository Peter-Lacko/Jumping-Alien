package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class And extends Binary<Bool,Bool,Bool> {

	public And(Expression<Bool> operand1, Expression<Bool> operand2,SourceLocation sourceLocation) {
		super(operand1, operand2,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(getExpr1().compute().getValue() && getExpr2().compute().getValue());
	}

}
