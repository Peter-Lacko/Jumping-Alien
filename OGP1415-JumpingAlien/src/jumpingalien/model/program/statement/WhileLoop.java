package jumpingalien.model.program.statement;

import java.util.Iterator;

import jumpingalien.model.program.expression.*;
import jumpingalien.model.program.type.*;

public class WhileLoop extends Statement {
	
	public WhileLoop(Expression<Bool> condition, Statement loopstatement){
		super();
		this.condition = condition;
		this.setLoopBody(loopstatement);
	}

	@Override
	public void execute() {
//		this.iterator();
	}
	
	public Expression<Bool> getCondition(){
		return this.condition;
	}

	private Expression<Bool> condition;

	public Statement getLoopBody() {
		return loopBody;
	}

	public void setLoopBody(Statement loopBody) {
		this.loopBody = loopBody;
	}
	
	private Statement loopBody;

	@Override
	public Iterator<Statement> iterator() {
//		if ((boolean)this.getCondition().compute())
//			return loopBody.iterator();
//		return null;
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				if (! conditionChecked)
					return true;				
			}

			@Override
			public Statement next() {
				// TODO Auto-generated method stub
				return null;
			}
			
			private boolean conditionChecked = false;
			
		};
	}
}
