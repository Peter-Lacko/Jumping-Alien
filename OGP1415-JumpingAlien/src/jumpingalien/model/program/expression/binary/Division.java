package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;

public class Division extends Binary<Double> {

	public Division(Expression<Double> operand1, Expression<Double> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Object compute() {
		return (double)this.getExpr1().compute() / (double)this.getExpr2().compute();
	}

}