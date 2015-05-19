package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.*;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Unary<S extends Type<?>, T extends Type<?>> extends Expression<T>{

	public Unary(Expression<S> operand, SourceLocation sourceLocation){
		super(sourceLocation);
		setOperand(operand);
	}
	
	public Expression<S> getOperand() {
		return operand;
	}
	
	private Expression<S> operand;

	public void setOperand(Expression<S> operand) {
		this.operand = operand;
	}
	
//	public static ExpressionBasic<Bool> negate(Expression<Bool> expression, SourceLocation sourceLocation){
//		boolean booleanResult = expression.compute().getValue();
//		ExpressionBasic<Bool> result = new ExpressionBasic<Bool>(new Bool(booleanResult), sourceLocation);
//		return result;
//	}
	
}
