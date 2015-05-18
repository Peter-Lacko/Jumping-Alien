package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.*;

public class GetX extends Unary<Object> {

	public GetX(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return  ((Characters) this.getExpr().compute()).getPositionAt(1);
	}

}
