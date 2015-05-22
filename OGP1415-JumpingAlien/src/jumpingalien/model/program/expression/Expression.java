package jumpingalien.model.program.expression;

import jumpingalien.model.program.statement.Statement;
import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T extends Type<?>> {

	public Expression(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	protected SourceLocation sourceLocation;
	
	private Statement getBasicStatement(){
		return this.statement;
	}
	 
	public Statement getStatement(){
		if (this.statement == null){
			setStatement(getTopExpression().getStatement());
		}
		return this.statement;
	}
	
	public void setStatement(Statement statement){
		this.statement = statement;
	}
	
	protected Statement statement = null;
	 
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public abstract T compute();
	
	private Expression<? extends Type<?>> enclosingExpression;

	public Expression<? extends Type<?>> getEnclosingExpression() {
		return enclosingExpression;
	}

	public void setEnclosingExpression(
			Expression<? extends Type<?>> enclosingExpression) {
		this.enclosingExpression = enclosingExpression;
	}

	public Expression<? extends Type<?>> getTopExpression(){
		Expression<? extends Type<?>> superExpression = this;
		if (getBasicStatement() == null){
			superExpression = getEnclosingExpression().getTopExpression();
		}
		return superExpression;
	}
}