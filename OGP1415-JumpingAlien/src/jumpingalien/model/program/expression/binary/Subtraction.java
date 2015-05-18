package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class Subtraction extends Binary<DoubleType, DoubleType, DoubleType> {

	public Subtraction(Expression<DoubleType> operand1, Expression<DoubleType> operand2, SourceLocation sourceLocation) {
		super(operand1, operand2, sourceLocation);
	}

	@Override
	public DoubleType compute() {
		double doubleResult = this.getOperand1().compute().getValue() - this.getOperand2().compute().getValue();
		DoubleType result = new DoubleType(doubleResult);
		return result;
	}

}