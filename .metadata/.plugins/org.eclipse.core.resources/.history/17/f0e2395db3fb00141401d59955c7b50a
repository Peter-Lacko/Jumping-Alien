package jumpingalien.model.program.statement;

import jumpingalien.model.program.expression.*;

public class WhileLoop extends Statement {
	
	public WhileLoop(Expression<?> condition, Statement loopstatement){
		super();
		this.condition = condition;
		this.setLoopBody(loopstatement);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	
	public Expression<?> getCondition(){
		return this.condition;
	}

	private Expression<?> condition;

	public Statement getLoopBody() {
		return loopBody;
	}

	public void setLoopBody(Statement loopBody) {
		this.loopBody = loopBody;
	}
	
	private Statement loopBody;
}
