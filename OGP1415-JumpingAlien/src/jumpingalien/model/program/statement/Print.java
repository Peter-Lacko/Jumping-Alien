package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Print<T extends Type<?>> extends Statement {

	public Print(Expression<T> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
		expression.setStatement(this);
	}

	private final Expression<T> expression;
	
	public Expression<T> getExpression() {
		return expression;
	}

	private Print<T> getPrint(){
		return this;
	}
	
	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return (! executed);
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if (! hasNext())
					throw new NoSuchElementException();
				executed = true;
				return getPrint();
			}
			
			private boolean executed = false;
		};
	}

	@Override
	public void execute() {
		System.out.println(getExpression().compute().toString());
	}

}
