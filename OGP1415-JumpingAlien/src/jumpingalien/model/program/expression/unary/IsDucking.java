package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsDucking extends Unary<Object,Bool> {

	public IsDucking(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if ( !(this.getOperand().compute().getValue() instanceof Aliens))
			b = false;
		else
			b = ((Aliens) this.getOperand().compute().getValue()).isDucked();
		return new Bool(b);
	}

}
