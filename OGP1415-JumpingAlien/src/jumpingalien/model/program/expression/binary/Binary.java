package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Binary<R extends Type<?>,S extends Type<?>,T extends Type<?>> extends Expression<T>{
		
	public Binary(Expression<R> operand1, Expression<S> operand2, SourceLocation sourceLocation){
		super(sourceLocation);
		this.operand1 = operand1;
		this.operand2 = operand2;
		operand1.setEnclosingExpression(this);
		operand2.setEnclosingExpression(this);
	}

	public Expression<R> getOperand1() {
		return operand1;
	}
	
	private final Expression<R> operand1;
	
	public Expression<S> getOperand2() {
		return operand2;
	}
	
	private final Expression<S> operand2;
	
}
