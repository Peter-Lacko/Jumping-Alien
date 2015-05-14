package jumpingalien.model.program.expression.unary;

import jumpingalien.model.program.expression.Expression;

public abstract class Unary<T> extends Expression<T>{

	public Unary(Expression<T> unary){
		this.expr = unary;
	}
	
	public Expression<T> getExpr() {
		return expr;
	}

	private Expression<T> expr;
	
	
}
