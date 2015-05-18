package jumpingalien.model.program.expression.unary;

import jumpingalien.model.GeoFeature;
import jumpingalien.model.Tile;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsAir extends Unary<Object,Bool> {

	public IsAir(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if (! (this.getExpr().compute().getValue() instanceof Tile))
			b = false;
		else
			b = ((Tile)this.getExpr().compute().getValue()).getGeo() == GeoFeature.WATER;
		return new Bool(b);
	}
}