package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.*;

public class GetWidth extends Unary<Object> {

	public GetWidth(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return  ((Characters) this.getExpr().compute()).getSprite().getWidth();
	}

}