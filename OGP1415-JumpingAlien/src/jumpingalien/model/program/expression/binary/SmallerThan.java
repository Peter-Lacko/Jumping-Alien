package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Util;

public class SmallerThan extends Binary<DoubleType,DoubleType,Bool> {

	public SmallerThan(Expression<DoubleType> operand1, Expression<DoubleType> operand2,SourceLocation sourceLocation) {
		super(operand1, operand2,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(!(Util.fuzzyGreaterThanOrEqualTo(this.getOperand1().compute().getValue(), this.getOperand2().compute().getValue())));
	}

}
