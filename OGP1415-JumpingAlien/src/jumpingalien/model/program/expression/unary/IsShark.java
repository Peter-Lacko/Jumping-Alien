package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Shark;
import jumpingalien.model.program.expression.Expression;

public class IsShark extends Unary<Object> {

	public IsShark(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return this.getExpr().compute() instanceof Shark ; 
	}

}
