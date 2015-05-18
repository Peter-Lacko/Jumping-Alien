package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Tile;
import jumpingalien.model.program.expression.Expression;

public class IsTerrain extends Unary<Object> {

	public IsTerrain(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		return this.getExpr().compute() instanceof Tile;
	}

}
