package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Mazub;
import jumpingalien.model.program.expression.Expression;

public class IsMazub extends Unary<Object> {

	public IsMazub(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return this.getExpr().compute() instanceof Mazub ; 
	}

}
