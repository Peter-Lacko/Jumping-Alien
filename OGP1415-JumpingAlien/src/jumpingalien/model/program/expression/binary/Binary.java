package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Binary<R extends Type<?>,S extends Type<?>,T extends Type<?>> extends Expression<T>{
		
	public Binary(Expression<R> operand1, Expression<S> operand2, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expr1 = operand1;
		this.expr2 = operand2;
	}

	public Expression<R> getExpr1() {
		return expr1;
	}
	
	private final Expression<R> expr1;
	
	public Expression<S> getExpr2() {
		return expr2;
	}
	
	private final Expression<S> expr2;
	
}
