package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.model.*;
import jumpingalien.part3.programs.SourceLocation;

public class GetX extends Unary<Object,DoubleType> {

	public GetX(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public DoubleType compute() {
		Expression<Object> e = getOperand();
		Object o = e.compute();
		GameObject g = o.getValue();
		return new DoubleType(((Characters) g).getPositionAt(1));
	}

}
