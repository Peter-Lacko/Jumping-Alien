package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;

public class IsPassable extends Unary<Object> {

	public IsPassable(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		if (this.getExpr().compute() instanceof Characters)
			return false;
		else if (this.getExpr().compute() instanceof Tile)
			return ! (((Tile)this.getExpr().compute()).getGeo() == GeoFeature.GROUND);
		return true;
	}

}
