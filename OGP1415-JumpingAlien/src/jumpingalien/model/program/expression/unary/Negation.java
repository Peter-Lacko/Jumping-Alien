package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class Negation extends Unary<Bool, Bool> {
	
	public Negation(Expression<Bool> operand, SourceLocation sourceLocation){
		super(operand, sourceLocation);
	}

	@Override
	public Bool compute() {
//		return !((boolean) this.getExpr().compute());
		boolean booleanResult = (! this.getExpr().compute().getValue());
		Bool result = new Bool(booleanResult);
		return result;
	}
}
