package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsDead extends Unary<Object,Bool> {

	public IsDead(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		boolean b;
		if (! (this.getExpr().compute().getValue() instanceof Characters))
			b = false;
		else
			b = ((Characters)this.getExpr().compute().getValue()).isTerminated();
		return new Bool(b);
	}

}
