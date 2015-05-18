package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Tile;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsTerrain extends Unary<Object,Bool> {

	public IsTerrain(Expression<Object> unary,SourceLocation sourcelocation) {
		super(unary,sourcelocation);
	}

	@Override
	public Bool compute() {
		return new Bool(this.getOperand().compute().getValue() instanceof Tile);
	}

}
