package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Mazub;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsMazub extends Unary<Object,Bool> {

	public IsMazub(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(this.getOperand().compute().getValue() instanceof Mazub) ; 
	}

}
