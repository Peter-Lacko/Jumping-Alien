package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.util.Util;

public class NotEqual extends Binary<Double> {

	public NotEqual(Expression<Double> operand1, Expression<Double> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Object compute() {
		return ! Util.fuzzyEquals((double)this.getExpr1().compute(), (double)this.getExpr2().compute());
	}

}
