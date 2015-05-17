package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class Addition extends Binary<DoubleType, DoubleType, DoubleType> {

	public Addition(Expression<DoubleType> operand1, Expression<DoubleType> operand2, SourceLocation sourceLocation) {
		super(operand1, operand2, sourceLocation);
	}

	@Override
	public DoubleType compute() {
//		boolean booleanResult = (! this.getExpr().compute().getValue());
//		Bool result = new Bool(booleanResult);
		double doubleResult = this.getExpr1().compute().getValue() + this.getExpr2().compute().getValue();
		DoubleType result = new DoubleType(doubleResult);
		return result;
	}

}
