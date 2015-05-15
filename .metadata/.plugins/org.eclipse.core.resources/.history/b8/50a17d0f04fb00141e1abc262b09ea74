package jumpingalien.model.program.expression.unary;

import jumpingalien.model.GeoFeature;
import jumpingalien.model.Tyle;
import jumpingalien.model.program.expression.Expression;

public class IsMagma extends Unary<Object> {

	public IsMagma(Expression<Object> unary) {
		super(unary);
	}

	@Override
	public Object compute() {
		if (! (this.getExpr().compute() instanceof Tyle))
			return false;
		return ((Tyle)this.getExpr().compute()).getGeo() == GeoFeature.MAGMA;
	}

}
