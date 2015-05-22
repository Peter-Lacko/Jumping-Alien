package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsPassable extends Unary<Object ,Bool> {

	public IsPassable(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if (this.getOperand().compute().getValue() instanceof Characters)
			b = false;
		else if (this.getOperand().compute().getValue() instanceof Tile)
			b= ! (((Tile)this.getOperand().compute().getValue()).getGeo() == GeoFeature.GROUND);
		else
			b = true;
		return new Bool(b);
	}

}
