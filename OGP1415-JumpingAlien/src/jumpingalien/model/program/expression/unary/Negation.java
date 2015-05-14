package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;

public class Negation extends Unary<Boolean> {
	
	public Negation(Expression<Boolean> operand){
		super(operand);
	}

	@Override
	public Object compute() {
		return !((boolean) this.getExpr().compute());
	}

}