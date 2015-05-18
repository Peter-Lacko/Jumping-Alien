package jumpingalien.model.program.expression.unary;

import jumpingalien.model.Slime;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;

public class IsSlime extends Unary<Object,Bool> {

	public IsSlime(Expression<Object> unary,SourceLocation sourceLocation) {
		super(unary,sourceLocation);
	}

	@Override
	public Bool compute() {
		return new Bool(this.getExpr().compute().getValue() instanceof Slime); 
	}

}
