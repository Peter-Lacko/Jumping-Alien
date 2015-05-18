package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

//while loop argument: must become a StatementSequence?
public class StatementSequence extends Statement {

	public StatementSequence(Statement... statements){
		if (statements == null)
			this.statements = new Statement[] {};
		else
			this.statements = statements;
	}

	private final Statement[] statements;

	public Statement[] getStatements() {
		return statements;
	}

	public Statement getStatementAt(int index) throws IndexOutOfBoundsException{
		return statements[index-1];
	}
	
	public int getNbStatements(){
		return statements.length;
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				if (getNbStatements() == 0)
					return false;
				else{
					if (currentIterator == null)
						newIterator = getStatementAt(1).iterator();
					else
						newIterator = currentIterator;
					if (newIterator.hasNext())
						return true;
					else{
						for (int i=1; i <= (getNbStatements()-index); i++){
							newIterator = getStatementAt(index+i).iterator();
							if (newIterator.hasNext())
								return true;
						}
						return false;
					}
				}
			}

			// watch out! what if: statements is empty?
			// what if: statements has 1 statement: an empty StatementSequence?
			// what if: statements has multiple statements, an empty StatementSequence being one or more of
			//		them (or even the last one)?
			@Override
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				if (currentIterator == null)
					currentIterator = getStatementAt(index).iterator();
				while (! currentIterator.hasNext()){
					index++;
					currentIterator = getStatementAt(index).iterator();
				}
				return currentIterator.next();
			}

			private Iterator<Statement> newIterator;
			
			private Iterator<Statement> currentIterator = null;
			
			private int index = 1;

		};
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//hier gebeurd niets? is dat ok?
	}

}
