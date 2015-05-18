package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.model.*;
import jumpingalien.part3.programs.SourceLocation;

public class GetY extends Unary<Object,DoubleType> {

	public GetY(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public DoubleType compute() {
		return new DoubleType(((Characters) this.getExpr().compute().getValue()).getPositionAt(2));
	}

}