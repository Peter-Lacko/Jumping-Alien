package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;

public class Or extends Binary<Boolean> {

	public Or(Expression<Boolean> operand1, Expression<Boolean> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Object compute() {
		return ((boolean)this.getExpr1().compute() || (boolean)this.getExpr2().compute());
	}

}
