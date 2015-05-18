package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Plant;
import jumpingalien.model.program.expression.Expression;

public class IsPlant extends Unary<Object> {

	public IsPlant(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return this.getExpr().compute() instanceof Plant ; 
	}

}
