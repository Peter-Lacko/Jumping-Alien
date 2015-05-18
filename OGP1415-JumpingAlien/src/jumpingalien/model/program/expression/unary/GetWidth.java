package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.model.*;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth extends Unary<Object,DoubleType> {

	public GetWidth(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public DoubleType compute() {
		return new DoubleType(((Characters) this.getOperand().compute().getValue()).getSprite().getWidth());
	}

}