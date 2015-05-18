package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;

public class IsDead extends Unary<Object> {

	public IsDead(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		if (! (this.getExpr().compute() instanceof Characters))
			return false;
		return ((Characters)this.getExpr().compute()).isTerminated();
	}

}
