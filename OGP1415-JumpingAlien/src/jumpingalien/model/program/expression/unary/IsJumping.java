package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsJumping extends Unary<Object,Bool> {

	public IsJumping(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if (this.getOperand().compute().getValue() instanceof Aliens)
			b = ((Aliens) this.getOperand().compute().getValue()).isJumping();
		else if  (this.getOperand().compute().getValue() instanceof Shark)
			b = ((Shark) this.getOperand().compute().getValue()).isJumping();
		else
			b = false;
		return new Bool(b);
	}

}
