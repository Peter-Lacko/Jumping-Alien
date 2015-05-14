package jumpingalien.model.program.expression.binary;

import jumpingalien.model.program.expression.Expression;

public abstract class Binary<T> extends Expression<T>{
		
	public Binary(Expression<T> operand1,Expression<T> operand2){
		this.expr1 = operand1;
		this.expr2 = operand2;
	}

	public Expression<T> getExpr1() {
		return expr1;
	}
	
	private Expression<T> expr1;
	
	public Expression<T> getExpr2() {
		return expr2;
	}
	
	private Expression<T> expr2;
	
}
