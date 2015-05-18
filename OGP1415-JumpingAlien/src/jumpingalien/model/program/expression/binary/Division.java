package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class Division extends Binary<DoubleType, DoubleType, DoubleType> {

	public Division(Expression<DoubleType> operand1, Expression<DoubleType> operand2,SourceLocation sourceLocation) {
		super(operand1, operand2, sourceLocation);
	}

	@Override
	public DoubleType compute() {
		return new DoubleType(this.getExpr1().compute().getValue() / this.getExpr2().compute().getValue());
	}

}