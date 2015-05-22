package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.*;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class WhileLoop extends LoopStatement {
	
	public WhileLoop(Expression<Bool> condition, Statement loopStatement, SourceLocation sourceLocation){
		super(sourceLocation, loopStatement);
		this.condition = condition;
		condition.setStatement(this);
	}

	@Override
	public void execute() {
		if (getCondition().compute().getValue())
			setConditionStatus(true);
		else
			setConditionStatus(false);
	}
	
	private boolean conditionStatus = false;
	
	public boolean isConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(boolean conditionStatus) {
		this.conditionStatus = conditionStatus;
	}

	public Expression<Bool> getCondition(){
		return this.condition;
	}

	private final Expression<Bool> condition;

	private WhileLoop getWhileLoop(){
		return this;
	}
	
	@Override
	public void setBreak(boolean isBreak){
		super.setBreak(isBreak);
		if (isBreak)
			setConditionStatus(false);
	}
	
	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				if(firstIteration)
					return true;
				else if (isBreak())
					return false;
				else if (! conditionChecked)
					return true;
				else if (isConditionStatus() == false)
					return false;
				else
					return true;
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				if (firstIteration){
					firstIteration = false;
					setBreak(false);
				}
				if (! conditionChecked){
					conditionChecked = true;
					return getWhileLoop();
				}
				else{
					// can throw exceptions if loopBody is empty
					// the loopBody of a while loop must be filled, otherwise infinite loop.
					Statement nextStatement = currentIterator.next();
					if (! currentIterator.hasNext()){
						currentIterator = getLoopBody().iterator();
						conditionChecked = false;
					}
					return nextStatement;
				}
			}
			
			private Iterator<Statement> currentIterator = getLoopBody().iterator();
			
			private boolean firstIteration = true;
			
			private boolean conditionChecked = false;
			
		};
	}
}
