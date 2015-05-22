package jumpingalien.model.program.expression.unary;

import jumpingalien.model.GeoFeature;
import jumpingalien.model.Tile;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsMagma extends Unary<Object,Bool> {

	public IsMagma(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if (! (this.getOperand().compute().getValue() instanceof Tile))
			b = false;
		else
			b = ((Tile)this.getOperand().compute().getValue()).getGeo() == GeoFeature.MAGMA;
		return new Bool(b);
	}

}
