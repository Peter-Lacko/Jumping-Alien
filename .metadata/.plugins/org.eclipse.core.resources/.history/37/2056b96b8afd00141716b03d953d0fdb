package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.*;

public class GetHp extends Unary<Object> {

	public GetHp(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return  ((Characters) this.getExpr().compute()).getHitPoints();
	}

}