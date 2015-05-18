package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Plant;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsPlant extends Unary<Object,Bool> {

	public IsPlant(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(this.getOperand().compute().getValue() instanceof Plant) ; 
	}

}
