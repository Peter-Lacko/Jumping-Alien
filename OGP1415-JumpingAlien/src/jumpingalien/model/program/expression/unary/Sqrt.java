package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;

public class Sqrt extends Unary<DoubleType,DoubleType> {

	public Sqrt(Expression<DoubleType> operand,SourceLocation sourceLocation) {
		super(operand , sourceLocation );
	}

	@Override
	public DoubleType compute() {
		double d = Math.sqrt((double) this.getOperand().compute().getValue());
		return new DoubleType(d);
	}

}
