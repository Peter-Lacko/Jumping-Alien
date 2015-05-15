package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;

public class IsDucking extends Unary<Object> {

	public IsDucking(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		if ( !(this.getExpr().compute() instanceof Aliens))
			return false;
		return ((Aliens) this.getExpr().compute()).isDucked();
	}

}
