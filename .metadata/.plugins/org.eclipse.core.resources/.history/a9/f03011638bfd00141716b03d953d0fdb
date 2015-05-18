package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;

public class IsJumping extends Unary<Object> {

	public IsJumping(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		if (this.getExpr().compute() instanceof Aliens)
			return ((Aliens) this.getExpr().compute()).isJumping();
		else if  (this.getExpr().compute() instanceof Shark)
			return ((Shark) this.getExpr().compute()).isJumping();
		return false;
	}

}
