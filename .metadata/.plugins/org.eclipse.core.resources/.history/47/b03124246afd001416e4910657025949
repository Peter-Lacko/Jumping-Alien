package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Slime;
import jumpingalien.model.program.expression.Expression;

public class IsSlime extends Unary<Object> {

	public IsSlime(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return this.getExpr().compute() instanceof Slime ; 
	}

}
