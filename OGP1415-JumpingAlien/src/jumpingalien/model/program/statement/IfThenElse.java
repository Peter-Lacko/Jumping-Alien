package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.Bool;
import jumpingalien.part3.programs.SourceLocation;

public class IfThenElse extends Statement {

	public IfThenElse(Expression<Bool> ifCondition, Statement ifStatement, Statement elseStatement,
			SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = ifCondition;
		ifCondition.setStatement(this);
		this.ifStatement = ifStatement;
		ifStatement.setEnclosingStatement(this);
		this.elseStatement = elseStatement;
		elseStatement.setEnclosingStatement(this);
	}

	private IfThenElse getIfThenElse(){
		return this;
	}

	private boolean canDoIfStatement = true;
	
	private boolean canDoElseStatement = true;

	public boolean isCanDoIfStatement() {
		return canDoIfStatement;
	}

	public void setCanDoIfStatement(boolean canDoIfStatement) {
		this.canDoIfStatement = canDoIfStatement;
	}

	public boolean isCanDoElseStatement() {
		return canDoElseStatement;
	}

	public void setCanDoElseStatement(boolean canDoElseStatement) {
		this.canDoElseStatement = canDoElseStatement;
	}

	public Statement getIfStatement() {
		return ifStatement;
	}

	public Statement getElseStatement() {
		return elseStatement;
	}

	public Expression<Bool> getCondition(){
		return this.condition;
	}

	private final Expression<Bool> condition;

	private final Statement ifStatement;

	private final Statement elseStatement;

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				if (! conditionChecked)
					return true;
				else{
					if (currentIterator == null){
						if (isCanDoIfStatement() && isCanDoElseStatement()){
							return (getIfStatement().iterator().hasNext()
									|| getElseStatement().iterator().hasNext());
						}
						else if (isCanDoIfStatement())
							return getIfStatement().iterator().hasNext();
						else
							return getElseStatement().iterator().hasNext();
					}
					else
						return currentIterator.hasNext();
				}
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				if (! conditionChecked){
					conditionChecked = true;
					return getIfThenElse();
				}
				else{
					if (currentIterator == null){
						if (isCanDoIfStatement() && isCanDoElseStatement()){
							Statement newStatement = new StatementSequence(
									null, getIfStatement(), getElseStatement());
							currentIterator = newStatement.iterator();
						}
						else if (isCanDoIfStatement())
							currentIterator = getIfStatement().iterator();
						else
							currentIterator = getElseStatement().iterator();
					}
					return currentIterator.next();
				}
			}

			private Iterator<Statement> currentIterator = null;

			private boolean conditionChecked = false;

		};
	}

	@Override
	public void execute() {
		if (getCondition().compute().getValue()){
			setCanDoIfStatement(true);
			setCanDoElseStatement(false);
		}
		else{
			setCanDoIfStatement(false);
			setCanDoElseStatement(true);
		}
	}

}
