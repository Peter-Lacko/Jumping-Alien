package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Util;

public class NotEqual extends Binary<DoubleType,DoubleType,Bool> {

	public NotEqual(Expression<DoubleType> operand1, Expression<DoubleType> operand2,SourceLocation sourceLocation) {
		super(operand1, operand2,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(! Util.fuzzyEquals(this.getExpr1().compute().getValue(), this.getExpr2().compute().getValue()));
	}

}
